package com.example.innodb.model.records;

import com.example.innodb.utils.ByteBufferUtils;
import com.google.common.base.Preconditions;

import java.nio.ByteBuffer;


/**
 * see https://blog.jcole.us/2013/01/07/the-physical-structure-of-innodb-index-pages/
 *
 * https://github.com/jeremycole/innodb_diagrams/blob/master/images/InnoDB_Structures/INDEX%20System%20Records.png
 *
 */
public final class IndexSystemRecord {

    private final ByteBuffer buffer;

    public IndexSystemRecord(ByteBuffer buffer) {
        this.buffer = buffer;
        Preconditions.checkArgument(ByteBufferUtils.readAsciiString(buffer, 99, 8).equals("infimum\0"));
        Preconditions.checkArgument(ByteBufferUtils.readAsciiString(buffer, 112, 8).equals("supremum"));
    }

    public RecordHeader getSupremum() {
        return new RecordHeader(buffer, 112);
    }

    public RecordHeader getInfimum() {
        return new RecordHeader(buffer, 99);
    }

    public int getRecordEndPageOffset() {
        return 120;
    }

}
