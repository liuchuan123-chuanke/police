package com.imeng.common.utils;

import java.util.UUID;

public class UUIDUtils {

    public static String getRandomNum() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
