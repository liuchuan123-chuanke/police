package com.imeng.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wwj
 * 2020/3/12 18:32
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SimpleUserDto {

    private String userId;
    private String userName;
    private String role;
    private String fullName;

}
