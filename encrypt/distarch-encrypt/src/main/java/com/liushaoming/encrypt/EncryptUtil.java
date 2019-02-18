package com.liushaoming.encrypt;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;

public class EncryptUtil {
    private static final String SALT = "nnjskslsl@#$*&!ap";

    /**
     * 加密函数： 计算公式, code = encrypt(license)
     * @param license
     * @return
     */
    public static String encrypt(String license) {
        if (StringUtils.isEmpty(license)) {
            throw new InvalidParameterException("license can not be null or empty");
        }
        return DigestUtils.md5DigestAsHex(license.getBytes());
    }
}
