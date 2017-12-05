package com.example.innodb.model.records;


import java.nio.ByteBuffer;

/**
 * see https://blog.jcole.us/2013/01/10/the-physical-structure-of-records-in-innodb/
 */
public final class DataRecord {

    private final ByteBuffer pageBuffer;
    private final int offset;

    /**
     *
     * @param pageBuffer page pageBuffer
     * @param offset the start of the data record. N.B. the record header reads backwards from this offset, and the data forwards.
     */
    public DataRecord(ByteBuffer pageBuffer, int offset) {
        this.pageBuffer = pageBuffer;
        this.offset = offset;
    }

    public RecordHeader getRecordHeader() {
        return new RecordHeader(pageBuffer, offset);
    }


    public int getPageOffset() {
        return offset;
    }

}
