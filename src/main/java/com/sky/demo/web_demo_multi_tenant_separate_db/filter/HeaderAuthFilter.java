package com.sky.demo.web_demo_multi_tenant_separate_db.filter;

import com.sky.demo.web_demo_multi_tenant_separate_db.auth.token.HeaderAuthToken;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.CodecUtil;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.json.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by user on 16/9/27.
 */
public class HeaderAuthFilter extends AccessControlFilter {

    private static final Logger logger = LoggerFactory.getLogger(HeaderAuthFilter.class);

    private static final String AUTHORIZATION = "Authorization";

    private Lock lock = new ReentrantLock();

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        boolean result = false;
        try {
            lock.lock();

            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
            logger.debug("request url is {},ip is {}", httpServletRequest.getRequestURI(), request.getRemoteHost());

            AuthenticationToken token = createToken(request, response);
            logger.info("client header token is {}", token);

            getSubject(request, response).login(token);
            result = true;
        } catch (UnsupportedTokenException e) {
            logger.error("authentication token is not supported by web service. " + e);
            ResponseEntity res = new ResponseEntity("Invalid Authorization Token", HttpStatus.UNAUTHORIZED);
            onLoginFailure(response, res);
        } catch (AuthenticationException e) {
            logger.error("authentication failed  " + e);
            ResponseEntity res = new ResponseEntity("Invalid Authorization Header", HttpStatus.UNAUTHORIZED);
            onLoginFailure(response, res);
        } finally {
            lock.unlock();
        }
        return result;
    }


    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String authorizationHeader = getAuthHeader(request);
        logger.info("current header is {} before to create token", authorizationHeader);

        if (StringUtils.isBlank(authorizationHeader)) {
            throw new AuthenticationException("authentication header is null before create token ");
        }

        String[] authorizationList = getPrincipalsAndCredentials(authorizationHeader, request);
        return createToken(authorizationList);
    }

    protected String getAuthHeader(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return httpRequest.getHeader(AUTHORIZATION);
    }


    protected String[] getPrincipalsAndCredentials(String authorizationHeader, ServletRequest request) {
        if (StringUtils.isBlank(authorizationHeader)) {
            return null;
        }

        try {
            String authorization = CodecUtil.decode(authorizationHeader);
            String[] authorizationList = authorization.split(":");
            if (authorizationList == null || authorizationList.length != 3) {
                HttpServletRequest req = (HttpServletRequest) request;
                logger.error("authorization list is null or length != 3,current header is {},request url is {}",
                        authorizationHeader, req.getRequestURI());
                return null;
            }
            return authorizationList;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    protected AuthenticationToken createToken(String[] authorizationList) {
        if (authorizationList == null) {
            throw new AuthenticationException("authentication header list is null when create token");
        }
        return new HeaderAuthToken(authorizationList[0], authorizationList[1], authorizationList[2]);
    }

    protected void onLoginFailure(ServletResponse response, ResponseEntity<String> res) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().write(JsonUtil.writeValueAsString(res));
    }
}