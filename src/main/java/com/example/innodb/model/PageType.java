package com.example.innodb.model;

import java.util.Arrays;

public enum PageType {
    ALLOCATED(0),
    UNDO_LOG(2),
    INODE(3),
    IBUF_FREE_LIST(4),
    IBUF_BITMAP(5),
    SYS(6),
    TRX_SYS(7),
    FSP_HDR(8),
    XDES(9),
    BLOB(10),
    ZBLOB2(12),
    INDEX(17855);

    private short value;

    PageType(int value) {
        this.value = (short) value;
    }

    public static PageType valueOf(short value) {
        return Arrays.stream(values())
                     .filter(x -> x.value == value)
                     .findFirst()
                     .orElseThrow(() -> new IllegalStateException("Unknown page type " + value));
    }
}
