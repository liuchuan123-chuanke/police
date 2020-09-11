package com.imeng.web.controller;

import cn.hutool.core.lang.Assert;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imeng.auth.token.MobileAuthenticationToken;
import com.imeng.auth.token.OpenIdAuthenticationToken;
import com.imeng.common.constant.SecurityConstants;
import com.imeng.common.context.TenantContextHolder;
import com.imeng.common.model.LoginAppUser;
import com.imeng.common.model.SimpleClientDto;
import com.imeng.common.utils.ResponseUtil;
import com.imeng.web.model.OauthDto;
import com.imeng.web.service.ZltClientDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * OAuth2相关操作
 *
 * @author zlt
 */
@Api(tags = "OAuth2相关操作")
@Slf4j
@RestController
public class OAuth2Controller {
    @Resource
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ZltClientDetailsService clientDetailsService;

    @ApiOperation(value = "邮箱、密码获取token")
    @PostMapping(SecurityConstants.PASSWORD_LOGIN_PRO_URL)
    public void getUserTokenInfo(
            @ApiParam(required = true, name = "userName", value = "账号") @RequestParam(required = false,value = "userName") String userName,
            @ApiParam(required = true, name = "password", value = "密码") @RequestParam(required = false,value = "password") String password,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        Assert.isTrue(!StringUtils.isEmpty(userName),"用户名不能为空");
        Assert.isTrue(!StringUtils.isEmpty(password),"密码不能为空");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);
        writerToken(request, response, token, "用户名或密码错误");
    }

    @ApiOperation(value = "openId获取token")
    @PostMapping(SecurityConstants.OPENID_TOKEN_URL)
    public void getTokenByOpenId(
            @RequestBody OauthDto dto,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        Assert.notNull(dto,"非法登陆参数");
        Assert.isTrue(!StringUtils.isEmpty(dto.getOpenId()),"openId不能为空");
        OpenIdAuthenticationToken token = new OpenIdAuthenticationToken(dto.getOpenId());
        writerToken(request, response, token, "openId错误");
    }

    @ApiOperation(value = "mobile获取token")
    @PostMapping(SecurityConstants.MOBILE_TOKEN_URL)
    public void getTokenByMobile(
            @RequestBody OauthDto dto,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        Assert.notNull(dto,"非法登陆参数");
        Assert.isTrue(!StringUtils.isEmpty(dto.getPhone()),"手机号不能为空");
        Assert.isTrue(!StringUtils.isEmpty(dto.getPassword()),"密码不能为空");
        MobileAuthenticationToken token = new MobileAuthenticationToken(dto.getPhone(), dto.getPassword());
        writerToken(request, response, token, "手机号或密码错误");
    }

    private void writerToken(HttpServletRequest request, HttpServletResponse response, AbstractAuthenticationToken token
            , String badCredenbtialsMsg) throws IOException {
        try {
            //final String[] clientInfos = AuthUtils.extractClient(request);
            //String clientId = clientInfos[0];
            /*String clientSecret = clientInfos[1];
            String scope = clientInfos[2];
            ClientDetails clientDetails = getClient(clientId, clientSecret,scope);*/
            //保存租户id
            //TenantContextHolder.setTenant(new SimpleClientDto(clientId,null,scope));

            Authentication authentication = authenticationManager.authenticate(token);
            String clientId = ((LoginAppUser)authentication.getPrincipal()).getTenantId();
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
            //保存租户信息
            TenantContextHolder.setTenant(new SimpleClientDto(clientId,null,""));
            TokenRequest tokenRequest =
                    new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails==null? Collections.emptyList():clientDetails.getScope()
                    , "customer");
            OAuth2Request oAuth2Request = null;
            if(clientDetails!=null) {
                oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
            OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
            oAuth2Authentication.setAuthenticated(true);
            ResponseUtil.responseSucceed(objectMapper, response, oAuth2AccessToken);
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            exceptionHandler(response, badCredenbtialsMsg);
        } catch (Exception e) {
            exceptionHandler(response, e);
        }
    }

    private void exceptionHandler(HttpServletResponse response, Exception e) throws IOException {
        log.error("exceptionHandler-error:", e);
        exceptionHandler(response, e.getMessage());
    }

    private void exceptionHandler(HttpServletResponse response, String msg) throws IOException {
        /*response.setStatus(HttpStatus.UNAUTHORIZED.value());*/
        response.setStatus(HttpStatus.OK.value());
        ResponseUtil.responseFailed(objectMapper, response, msg);
    }

    private ClientDetails getClient(String clientId, String clientSecret,String scope) {
        ClientDetails clientDetails = clientDetailsService.loadClientByKeyGen(clientId,scope);
        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的信息不存在");
        } else if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配");
        }
        return clientDetails;
    }
}
