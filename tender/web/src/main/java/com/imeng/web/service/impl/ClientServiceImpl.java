package com.imeng.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.constant.SecurityConstants;
import com.imeng.common.lock.DistributedLock;
import com.imeng.common.model.Client;
import com.imeng.common.model.PageResult;
import com.imeng.common.model.Result;
import com.imeng.common.service.ClientBaseService;
import com.imeng.common.service.impl.SuperServiceImpl;
import com.imeng.redisStarter.template.RedisRepository;
import com.imeng.web.mapper.ClientMapper;
import com.imeng.web.service.IClientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zlt
 */
@Slf4j
@Service
public class ClientServiceImpl extends SuperServiceImpl<ClientMapper, Client> implements IClientService, ClientBaseService {
    //private final static String LOCK_KEY_CLIENTID = CommonConstant.LOCK_KEY_PREFIX+"clientId:";

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DistributedLock lock;

    @Override
    public Result saveClient(Client client) {
        client.setClientSecret(passwordEncoder.encode(client.getClientSecretStr()));
        String clientId = client.getClientId();
        try {
            super.saveOrUpdateIdempotency(client, lock
                    , clientRedisKeyWithScope(clientId,client.getScope())
                    , new QueryWrapper<Client>().eq("client_id", clientId).eq("scope",client.getScope())
                    , clientId + "已存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.succeed("操作成功");
    }

    @Override
    public PageResult<Client> listClent(Map<String, Object> params, boolean isPage) {
        Page<Client> page;
        if (isPage) {
            page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        } else {
            page = new Page<>(1, -1);
        }
        List<Client> list = baseMapper.findList(page, params);
        page.setRecords(list);
        return PageResult.<Client>builder().data(list).code(0).count(page.getTotal()).build();
    }

    @Override
    public void delClient(long id) {
        Client client = baseMapper.selectById(id);
        String clientId = client.getClientId();
        baseMapper.deleteById(id);
        redisRepository.del(clientRedisKeyWithScope(clientId,client.getScope()));
    }

    @Override
    public Client queryClient(String clientId, String scope) {
        return queryClientWithScope(clientId,scope);
    }


    private Client queryClientWithScope(String clientId, String scope) {
       /* MyClientDetails clientDetails = (MyClientDetails) redisRepository.get(clientRedisKeyWithScope(clientId,scope));*/
        QueryWrapper query = new QueryWrapper();
        query.eq("client_id",clientId);
        query.eq("scope",scope);
        return baseMapper.selectOne(query);
    }

    private String clientRedisKey(String clientId) {
        return SecurityConstants.CACHE_CLIENT_KEY + ":" + clientId;
    }
    private String clientRedisKeyWithScope(String clientId,String scope) {
        return SecurityConstants.CACHE_CLIENT_KEY + ":" + clientId+":"+scope;
    }

    @Override
    public Client queryByClientId(String clientId) {
        QueryWrapper query = new QueryWrapper();
        query.eq("client_id",clientId);
        return baseMapper.selectOne(query);
    }
}
