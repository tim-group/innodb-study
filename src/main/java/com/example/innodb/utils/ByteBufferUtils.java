package com.example.innodb.utils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class ByteBufferUtils {

    public static String readAsciiString(ByteBuffer buffer, int offset, int length) {
        byte[] str = new byte[length];
        buffer.position(offset);
        buffer.get(str);
        return new String(str, StandardCharsets.US_ASCII);
    }
}
