package com.simple.common.util;

import java.util.UUID;

/**
 * @author: lingjun.jlj
 * @date: 2019/5/5 13:41
 * @description:
 */
public class IDUtils {

    public static String generateId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(generateId());
    }
}