package com.imeng.common.resolver;

import cn.hutool.core.util.StrUtil;
import com.imeng.common.annotation.LoginClient;
import com.imeng.common.constant.SecurityConstants;
import com.imeng.common.model.Client;
import com.imeng.common.service.ClientBaseService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * head中的应用参数注入clientId中
 *
 * @author zlt
 * @date 2019/7/10
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ClientArgumentResolver implements HandlerMethodArgumentResolver {

    private ClientBaseService clientBaseService;

    /**
     * 入参筛选
     *
     * @param methodParameter 参数集合
     * @return 格式化后的参数
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(LoginClient.class) && methodParameter.getParameterType().equals(Client.class);
    }

    /**
     * @param methodParameter       入参集合
     * @param modelAndViewContainer model 和 view
     * @param nativeWebRequest      web相关
     * @param webDataBinderFactory  入参解析
     * @return 包装对象
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        LoginClient loginClient = methodParameter.getParameterAnnotation(LoginClient.class);
        String clientId = request.getHeader(SecurityConstants.TENANT_HEADER);
        String scope = request.getHeader(SecurityConstants.TENANT_SCOPE_HEADER);
        if (StrUtil.isBlank(clientId)) {
            log.warn("resolveArgument error clientId is empty");
        }
        Client clientDetails;
        //查询全部信息
        if (loginClient.isFull()) {
            clientDetails = clientBaseService.queryByClientId(clientId);
        } else {
            Client client = new Client();
            client.setClientId(clientId);
            client.setScope(scope);
            clientDetails = client;
        }
        return clientDetails;
    }
}
