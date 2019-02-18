package com.liushaoming.encrypt;

import java.util.UUID;

public class UUIDUtil {


//    public static void main(String[] args) {
//        String uuid =  newUuid();
//        System.out.println("uuid=" + uuid);
//    }

    /**
     * UUID是由一个十六位的数字组成,表现出来的形式例如
     * 668eee3274eb4873a1b921b7f9bb85d6
     * @return
     */
    public static String newUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
