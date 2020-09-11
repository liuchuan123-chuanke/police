package com.imeng.web.dto;

import cn.hutool.core.util.StrUtil;
import com.imeng.common.model.User;
import com.imeng.common.model.base.BaseDto;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class UserDto extends BaseDto {

    private Long  roleId;
    private Long  orgId;
    private String userName;
    private String nickName;
    private String telPhone;
    private boolean disable;
    private String  orgName;
    private String  addr;
    private String  roleName;
    private String  password;

    public User convertToUser(PasswordEncoder encoder){
        User user = new User();
        user.setId(getId());
        if(StrUtil.isNotBlank(password)){
            //密码加密
            user.setPwd(encoder.encode(password));
        }
        user.setUserName(userName);
        user.setNickName(nickName);
        user.setDisable(disable);
        user.setPhone1(telPhone);
        return user;
    }
}
