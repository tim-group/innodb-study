package com.example.innodb.model;

import com.example.innodb.model.records.FilHeader;

import java.nio.ByteBuffer;

/**
 * see https://blog.jcole.us/2013/01/03/the-basics-of-innodb-space-file-layout/
 *
 * https://github.com/jeremycole/innodb_diagrams/blob/master/images/InnoDB_Structures/Basic%20Page%20Overview.png
 *
 */
public final class Page {

    private final ByteBuffer buffer;
    private final FilHeader filHeader;

    public Page(ByteBuffer buffer) {
        this.buffer = buffer;
        this.filHeader = new FilHeader(buffer);
    }

    public static Page from(ByteBuffer buffer) {
        return new Page(buffer);
    }

    public FilHeader getFilHeader() {
        return filHeader;
    }

    public int getPageNumber() {
        return filHeader.getOffset();
    }

    public PageType getType() {
        return filHeader.getType();
    }

    public int getPreviousPage() {
        return filHeader.getPreviousPage();
    }

    public int getNextPage() {
        return filHeader.getNextPage();
    }

    public ByteBuffer pageBuffer() {
        return buffer;
    }
}
