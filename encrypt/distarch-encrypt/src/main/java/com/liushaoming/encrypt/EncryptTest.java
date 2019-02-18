package com.liushaoming.encrypt;

/**
 * 右键--run as java application此class即可调试
 * 使用md5加盐加密。不可逆加密。
 */
public class EncryptTest {
    public static void main(String[] args){
        testEncrypt();
    }

    private static void testEncrypt() {
        System.out.println("You should save code in deploy database. And sell license to customers.");

        for (int i=0;i< 5; i++) {
            String license = UUIDUtil.newUuid();
            String code = EncryptUtil.encrypt(license);

            System.out.println("license_" + i + "=" + license + ", code=" + code);
        }
    }
}
