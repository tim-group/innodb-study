package com.example.innodb.model;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.Long.MAX_VALUE;

public final class TableFileReader {

    private static final int PAGE_SIZE_IN_BYTES = 16 * 1024;

    public static Stream<Page> loadPages(String fileName) throws Exception {
        RandomAccessFile file = new RandomAccessFile(fileName, "r");
        long tableSpaceSize = file.length();

        FileChannel channel = file.getChannel();
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());

        return StreamSupport.stream(new PageSpliterator(buffer, tableSpaceSize), false).onClose(() -> closeQuietly(channel));
    }

    private static void closeQuietly(FileChannel channel) {
        try {
            channel.close();
        } catch (IOException e) { /* swallow */ }
    }

    private static final class PageSpliterator implements Spliterator<Page> {

        private final MappedByteBuffer buffer;
        private final long tableSpaceSize;

        private int offset;

        private PageSpliterator(MappedByteBuffer buffer, long tableSpaceSize) {
            this.buffer = buffer;
            this.tableSpaceSize = tableSpaceSize;
            this.offset = 0;
        }

        @Override
        public boolean tryAdvance(Consumer<? super Page> action) {
            if (offset >= this.tableSpaceSize) {
                return false;
            }
            buffer.position(offset);
            ByteBuffer pageBuffer = buffer.slice();
            pageBuffer.limit(PAGE_SIZE_IN_BYTES);
            offset += PAGE_SIZE_IN_BYTES;

            action.accept(Page.from(pageBuffer));
            return true;
        }

        @Override
        public Spliterator<Page> trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return MAX_VALUE;
        }

        @Override
        public int characteristics() {
            return ORDERED | NONNULL | DISTINCT;
        }

    }
}
