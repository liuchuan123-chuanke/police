package com.imeng.web.model;

import com.alibaba.fastjson.JSON;
import com.imeng.common.model.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyClientDetails implements ClientDetails {
    //数据库client信息
    private Client client;
    @Override
    public String getClientId() {
        return client==null?null:client.getClientId();
    }
    @Override
    public Set<String> getResourceIds() {
        if(client==null){
            return Collections.emptySet();
        }
        List<String> list = Arrays.asList(client.getResourceIds().split(","));
        return new HashSet<>(list);
    }
    @Override
    public boolean isSecretRequired() {
        return false;
    }

    @Override
    public String getClientSecret() {
        return client==null?"":client.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Set<String> getScope() {
        if(client==null){
            return Collections.emptySet();
        }
        Set<String> set = Collections.emptySet();
        set.add(client.getScope());
        return set;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        if(client==null){
            return Collections.emptySet();
        }
        List<String> list = Arrays.asList(client.getAuthorizedGrantTypes().split(","));
        return new HashSet<>(list);
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        if(client==null){
            return Collections.emptySet();
        }
        List<String> list = Arrays.asList(client.getWebServerRedirectUri().split(","));
        Set<String> s = new HashSet<>();
        s.addAll(list);
        return s;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        if(client==null){
            return Collections.emptySet();
        }
        List<GrantedAuthority> ret = Collections.emptyList();
        Arrays.asList(client.getAuthorities().split(",")).
                forEach(e->{
                    GrantedAuthority g = (GrantedAuthority) () -> e;
                    ret.add(g);
                });
        return ret;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return client==null?null:client.getAccessTokenValiditySeconds();
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return client==null?null:client.getRefreshTokenValiditySeconds();
    }

    @Override
    public boolean isAutoApprove(String s) {
        return client != null && client.getAutoapprove().equalsIgnoreCase("true");
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return client==null?Collections.emptyMap(): (Map<String, Object>) JSON.parse(client.getAdditionalInformation());
    }
}
