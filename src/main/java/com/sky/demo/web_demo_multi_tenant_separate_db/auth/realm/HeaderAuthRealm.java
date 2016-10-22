package com.sky.demo.web_demo_multi_tenant_separate_db.auth.realm;

import com.google.common.base.Preconditions;
import com.sky.demo.web_demo_multi_tenant_separate_db.auth.token.HeaderAuthToken;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.service.TenantService;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.SHAUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by user on 16/9/27.
 */
public class HeaderAuthRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(HeaderAuthRealm.class);

    @Resource
    private TenantService tenantService;

    @Override
    public boolean supports(AuthenticationToken token) {
        logger.debug("current authentication token is {}", token);
        return token.getClass().equals(HeaderAuthToken.class);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //no authorization
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        HeaderAuthToken headerAuthToken = (HeaderAuthToken) token;
        logger.debug("..... begin to validate token....");
        String timestamp = headerAuthToken.getTimestamp();
        String tokenStr = headerAuthToken.getToken();
        String deviceId = headerAuthToken.getDeviceId();

        logger.debug(".....header info in client request: timestamp is {},token is {},device uuid is {}....", new Object[]{timestamp, tokenStr, deviceId});
        if (StringUtils.isBlank(timestamp) || StringUtils.isBlank(tokenStr) || StringUtils.isBlank(deviceId)) {
            throw new AuthenticationException("token is empty");
        }

        try {
            TenantForm tenantForm = tenantService.queryByDeviceId(deviceId);
            Preconditions.checkNotNull(tenantForm, "tenant is null");

            String deviceToken = tenantForm.getDeviceToken();
            logger.debug("device token is {}", deviceToken);

            String tokenCheck = SHAUtil.encrypt(timestamp + deviceToken + deviceId);
            logger.debug("begin to validate, encrypt token is {}, auth token is {}", tokenCheck, tokenStr);
            if (!tokenCheck.equals(tokenStr)) {
                throw new AuthenticationException("Invalid token");
            }
        } catch (AuthenticationException e) {
            logger.error("encrypt token error" + e);
            throw new AuthenticationException("encrypt token error");
        } catch (Exception e) {
            logger.error("some error happen when check token" + e);
            throw new AuthenticationException("check token error");
        }

        return new SimpleAuthenticationInfo(headerAuthToken.getTimestamp(), headerAuthToken.getToken(), this.getName());
    }
}
