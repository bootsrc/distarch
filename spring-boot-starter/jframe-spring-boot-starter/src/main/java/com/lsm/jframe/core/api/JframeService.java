package com.lsm.jframe.core.api;

public class JframeService {
    private String prefix;
    private String suffix;

    public JframeService(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
    public String wrap(String word) {
        return prefix + word + suffix;
    }
}
