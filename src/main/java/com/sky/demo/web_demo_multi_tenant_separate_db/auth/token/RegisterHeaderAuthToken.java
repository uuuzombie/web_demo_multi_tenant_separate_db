package com.sky.demo.web_demo_multi_tenant_separate_db.auth.token;

import com.google.common.base.Objects;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by user on 16/9/29.
 */
public class RegisterHeaderAuthToken implements AuthenticationToken {

    private static final long serialVersionUID = 5831691470383080653L;
    private String timestamp;
    private String token;

    public RegisterHeaderAuthToken(String timestamp, String token) {
        this.timestamp = timestamp;
        this.token = token;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        // principal 身份，即主体的标识属性
        return timestamp;
    }

    @Override
    public Object getCredentials() {
        // credentials 证明/凭证，即只有主体知道的安全值
        return token;
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("timestamp", timestamp)
                .add("token", token)
                .toString();
    }
}
