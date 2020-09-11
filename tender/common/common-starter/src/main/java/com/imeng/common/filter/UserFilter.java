package com.imeng.common.filter;

import com.imeng.common.context.UserContextHolder;
import com.imeng.common.model.SimpleUserDto;
import com.imeng.common.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户信息过滤 userId
 *
 * @author tw
 * 2020/1/17 15:43
 */

@ConditionalOnClass(Filter.class)
@Slf4j
public class UserFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            User user;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof User) {
                    user = (User)authentication.getPrincipal();
                    UserContextHolder.setUser(new SimpleUserDto(user.getId()+"",user.getUserName(),"",""));
                }
            }

            filterChain.doFilter(request, response);
        } finally {
            UserContextHolder.clear();
        }
    }
}

