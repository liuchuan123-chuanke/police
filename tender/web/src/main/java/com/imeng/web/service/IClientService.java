package com.imeng.web.service;


import com.imeng.common.model.Client;
import com.imeng.common.model.PageResult;
import com.imeng.common.model.Result;
import com.imeng.common.service.ISuperService;

import java.util.Map;

/**
 * @author zlt
 */
public interface IClientService extends ISuperService<Client> {
    Result saveClient(Client clientDto);

    /**
     * 查询应用列表
     * @param params
     * @param isPage 是否分页
     */
    PageResult<Client> listClent(Map<String, Object> params, boolean isPage);

    void delClient(long id);

    Client queryClient(String clientId, String scope);

}
