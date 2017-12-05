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

    private final ByteBuffer pageBuffer;

    public IndexHeader(ByteBuffer pageBuffer) {
        this.pageBuffer = pageBuffer;
    }

    public short getLevel() {
        return pageBuffer.getShort(64);
    }

    public int getIndexId() {
        return pageBuffer.getInt(66);
    }

    public short getNumberOfRecords() {
        return pageBuffer.getShort(54);
    }

    public RowFormat getRowFormat() {
        return (pageBuffer.getShort(42) & 1<<15) == 0 ? REDUNDANT : COMPACT;
    }

    public enum RowFormat {
        REDUNDANT,
        COMPACT;
    }

}
