package com.sky.demo.web_demo_multi_tenant_separate_db.auth.token;

import com.google.common.base.Objects;

/**
 * Created by user on 16/9/29.
 */
public class HeaderAuthToken extends RegisterHeaderAuthToken {

    private static final long serialVersionUID = -3943230690614221979L;
    private String deviceId;

    public HeaderAuthToken(String timestamp, String token, String deviceId) {
        super(timestamp, token);
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("deviceId", deviceId)
                .toString();
    }
}
