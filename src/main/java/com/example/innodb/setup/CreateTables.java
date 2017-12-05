package com.example.innodb.setup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Random;

public final class CreateTables {


    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/innodbstudy";

        try (Connection connection = DriverManager.getConnection(url)) {
            createTable(connection, "t1");
            generateRows(connection,"t1", 3);

            createTable(connection, "t2");
            generateRows(connection,"t2", 1000);

            createTable(connection, "t3");
            generateRows(connection,"t3", 100000);
        }

    }

    private static void generateRows(Connection connection, String tableName, int count) throws Exception {
        Random random = new Random();
        try (Statement statement = connection.createStatement()) {
            for(int i = 0; i < count; i++) {
                random.nextInt();
                statement.execute("insert into " + tableName + "(pk, v1) VALUES (" + i + ", " + random.nextInt() + ")");
            }
        }
    }

    private static void createTable(Connection connection, String tableName) throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.execute("drop table if exists " + tableName);
            statement.execute("create table if not exists " + tableName + "(" +
                    "pk int primary key, " +
                    "v1 int not null " +
                    ") ROW_FORMAT=COMPACT");
        }
    }



}
