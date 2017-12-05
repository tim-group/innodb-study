package com.example.innodb.setup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public final class CreateTables {


    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/innodbstudy";

        try (Connection connection = DriverManager.getConnection(url)) {
            createTable(connection, "t1");
            generateRows(connection,"t1");

            createTable(connection, "t2");
        }

    }

    private static void generateRows(Connection connection, String tableName) throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.execute("insert into " + tableName +
                    "(pk, v1)" +
                    " VALUES " +
                    "(1, 9472), " +
                    "(2, 452), " +
                    "(3, 69463)"
            );
        }
    }

    private static void createTable(Connection connection, String tableName) throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.execute("create table if not exists " + tableName + "(" +
                    "pk int primary key, " +
                    "v1 int not null " +
                    ") ROW_FORMAT=COMPACT");
        }
    }



}
