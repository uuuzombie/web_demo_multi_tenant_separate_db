package com.sky.demo.web_demo_multi_tenant_separate_db.filter;

import com.sky.demo.web_demo_multi_tenant_separate_db.auth.token.RegisterHeaderAuthToken;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.CodecUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by user on 16/9/27.
 */
public class DeviceRegisterHeaderAuthFilter extends HeaderAuthFilter {

    private static final Logger logger = LoggerFactory.getLogger(DeviceRegisterHeaderAuthFilter.class);

    @Override
    protected AuthenticationToken createToken(String[] authorizationList) {
        if (authorizationList == null) {
            throw new AuthenticationException("authentication header is null");
        }
        return new RegisterHeaderAuthToken(authorizationList[0], authorizationList[1]);
    }

    @Override
    protected String[] getPrincipalsAndCredentials(String authorizationHeader, ServletRequest request) {
        if (authorizationHeader == null) {
            return null;
        }

        try {
            String authorization = CodecUtil.decode(authorizationHeader);
            String[] authorizationList = authorization.split(":");
            if (authorizationList == null || authorizationList.length != 2) {
                logger.error("authentication header is null or size is not 2,current header is {}", authorizationList);
                return null;
            }
            return authorizationList;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
