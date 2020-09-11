package com.imeng.web.config;

import com.imeng.common.exception.DefaultExceptionAdvice;
import com.imeng.common.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author zlt
 * @date 2018/12/22
 */
@ControllerAdvice
public class ExceptionAdvice extends DefaultExceptionAdvice {

}
