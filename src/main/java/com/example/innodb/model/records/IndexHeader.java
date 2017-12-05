package com.example.innodb.model.records;

import java.nio.ByteBuffer;

import static com.example.innodb.model.records.IndexHeader.RowFormat.COMPACT;
import static com.example.innodb.model.records.IndexHeader.RowFormat.REDUNDANT;


/**
 * see https://blog.jcole.us/2013/01/07/the-physical-structure-of-innodb-index-pages/
 *
 * https://github.com/jeremycole/innodb_diagrams/blob/master/images/InnoDB_Structures/INDEX%20Header.png
 *
 */
public final class IndexHeader {

    public static final int PAGE_OFFSET = 38;

    private final ByteBuffer buffer;

    public IndexHeader(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public short getLevel() {
        return buffer.getShort(64 - PAGE_OFFSET);
    }

    public int getIndexId() {
        return buffer.getInt(66 - PAGE_OFFSET);
    }

    public short getNumberOfRecords() {
        return buffer.getShort(54 - PAGE_OFFSET);
    }

    public RowFormat getRowFormat() {
        return (buffer.getShort(42 - PAGE_OFFSET) & 1<<15) == 0 ? REDUNDANT : COMPACT;
    }

    public enum RowFormat {
        REDUNDANT,
        COMPACT;
    }

}
