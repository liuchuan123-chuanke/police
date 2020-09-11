package com.imeng.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wwj
 * 2020/3/12 18:35
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SimpleClientDto {

    private String clientId;
    private String clientSecStr;
    private String scope;

}
