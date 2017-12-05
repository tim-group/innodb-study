package com.example.innodb.model.records;

import com.example.innodb.model.PageType;

import java.nio.ByteBuffer;


/**
 * see https://blog.jcole.us/2013/01/03/the-basics-of-innodb-space-file-layout/
 *
 * https://github.com/jeremycole/innodb_diagrams/blob/master/images/InnoDB_Structures/FIL%20Header%20and%20Trailer.png
 *
 */
public final class FilHeader {

    private final ByteBuffer pageBuffer;

    public FilHeader(ByteBuffer pageBuffer) {
        this.pageBuffer = pageBuffer;
    }


    public int getOffset() {
        return pageBuffer.getInt(4);
    }

    public PageType getType() {
        return PageType.valueOf(pageBuffer.getShort(24));
    }

    public int getPreviousPage() {
        return pageBuffer.getInt(8);
    }

    public int getNextPage() {
        return pageBuffer.getInt(12);
    }

}
