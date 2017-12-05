package com.example.innodb.model.records;


import java.nio.ByteBuffer;

/**
 * see https://github.com/jeremycole/innodb_diagrams/blob/master/images/InnoDB_Structures/Record%20Header.png
 */
public final class RecordHeader {

    private final ByteBuffer pageBuffer;
    private final int offsetToBottomOfRecordHeader;

    public RecordHeader(ByteBuffer pageBuffer, int offsetToBottomOfRecordHeader) {
        this.pageBuffer = pageBuffer;
        this.offsetToBottomOfRecordHeader = offsetToBottomOfRecordHeader;
    }

    public short getNextRecordOffset() {
        return pageBuffer.getShort(offsetToBottomOfRecordHeader - 2);
    }

    public RecordType getRecordType() {
        return RecordType.values()[(pageBuffer.getShort(offsetToBottomOfRecordHeader - 4) & 0b0000000000000111)];
    }

    public short getOrder() {
        return (short)(pageBuffer.getShort(offsetToBottomOfRecordHeader - 4) & 0b1111111111111000);
    }

    public short getNumberOfRecordsOwned() {
        return (short)(pageBuffer.getShort(offsetToBottomOfRecordHeader - 5) & 0b00001111);
    }

    public short getInfoFlags() {
        return (short)(pageBuffer.getShort(offsetToBottomOfRecordHeader - 5) & 0b11110000);
    }

    public enum RecordType {
        CONVENTIONAL,
        NODE_POINTER,
        INFIMUM,
        SUPREMUM;
    }

}
