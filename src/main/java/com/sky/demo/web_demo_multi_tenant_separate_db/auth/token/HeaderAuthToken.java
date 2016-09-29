package com.sky.demo.web_demo_multi_tenant_separate_db.auth.token;

import com.google.common.base.Objects;

/**
 * Created by user on 16/9/29.
 */
public class HeaderAuthToken extends RegisterHeaderAuthToken {

    private static final long serialVersionUID = -3943230690614221979L;
    private String ipAddress;

    public HeaderAuthToken(String timestamp, String token, String ipAddress) {
        super(timestamp, token);
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("ipAddress", ipAddress)
                .toString();
    }
}
