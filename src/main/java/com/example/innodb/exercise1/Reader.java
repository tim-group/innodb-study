package com.example.innodb.exercise1;

import com.example.innodb.model.Page;
import com.example.innodb.model.PageType;
import com.example.innodb.model.TableFileReader;
import com.example.innodb.model.records.IndexHeader;
import com.example.innodb.model.records.IndexSystemRecord;
import com.example.innodb.model.records.RecordHeader;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Reader {

    public static void main(String[] args) throws Exception {

        final AtomicInteger cnt = new AtomicInteger();

        try (Stream<Page> pages = TableFileReader.loadPages("src/main/resources/t1.ibd")) {
            pages.forEachOrdered(page -> {
                System.out.println("page " + cnt.getAndIncrement());
                System.out.println("\tNumber " + page.getPageNumber());
                System.out.println("\tType " + page.getType());
                System.out.println("\tPrevious " + page.getPreviousPage());
                System.out.println("\tNext " + page.getNextPage());
            });
        }

        try (Stream<Page> pages = TableFileReader.loadPages("src/main/resources/t1.ibd")) {
            Page firstIndexPage = pages.filter(page -> page.getType() == PageType.INDEX).findFirst().get();



            // see https://blog.jcole.us/2013/01/07/the-physical-structure-of-innodb-index-pages/
            IndexHeader indexHeader = firstIndexPage.getRecord(IndexHeader.class, IndexHeader.PAGE_OFFSET);
            System.out.println("Number of records in index: " + indexHeader.getNumberOfRecords());

            IndexSystemRecord indexSystemRecord = firstIndexPage.getRecord(IndexSystemRecord.class, IndexSystemRecord.PAGE_OFFSET);


            System.out.println(indexSystemRecord.getInfimum().getNextRecordOffset());

            RecordHeader recordHeader = new RecordHeader(firstIndexPage.pageBuffer(), 120 + indexSystemRecord.getInfimum().getNextRecordOffset());
            System.out.println(recordHeader.getRecordType());

        }
    }

}
