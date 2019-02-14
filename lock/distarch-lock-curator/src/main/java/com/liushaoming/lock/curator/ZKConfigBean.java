package com.liushaoming.lock.curator;

public class ZKConfigBean {
    private String lockRoot;
    private int sessionTimeout;
    private String connectStr;
    private int connectTimeout;

    public String getLockRoot() {
        return lockRoot;
    }

    public void setLockRoot(String lockRoot) {
        this.lockRoot = lockRoot;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public String getConnectStr() {
        return connectStr;
    }

    public void setConnectStr(String connectStr) {
        this.connectStr = connectStr;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}
