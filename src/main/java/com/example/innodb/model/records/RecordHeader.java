package com.example.innodb.model.records;


import java.nio.ByteBuffer;

import static com.example.innodb.model.records.IndexHeader.RowFormat.COMPACT;
import static com.example.innodb.model.records.IndexHeader.RowFormat.REDUNDANT;

/**
 * see https://github.com/jeremycole/innodb_diagrams/blob/master/images/InnoDB_Structures/Record%20Header.png
 */
public final class RecordHeader {

    private final ByteBuffer buffer;
    private final int offsetToBottomOfRecordHeader;

    public RecordHeader(ByteBuffer buffer) {
        this(buffer, 5);
    }

    public RecordHeader(ByteBuffer buffer, int offsetToBottomOfRecordHeader) {
        this.buffer = buffer;
        this.offsetToBottomOfRecordHeader = offsetToBottomOfRecordHeader;
    }

    public short getNextRecordOffset() {
        return buffer.getShort(offsetToBottomOfRecordHeader - 2);
    }

    public short getRecordType() {
        return (short)(buffer.getShort(offsetToBottomOfRecordHeader - 4) & 0b0000000000000111);
    }

    public short getOrder() {
        return (short)(buffer.getShort(offsetToBottomOfRecordHeader - 4) & 0b1111111111111000);
    }

    public short getNumberOfRecordsOwned() {
        return (short)(buffer.getShort(offsetToBottomOfRecordHeader - 5) & 0b00001111);
    }

    public short getInfoFlags() {
        return (short)(buffer.getShort(offsetToBottomOfRecordHeader - 5) & 0b11110000);
    }

}
