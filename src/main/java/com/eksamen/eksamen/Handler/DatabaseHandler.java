package com.eksamen.eksamen.Handler;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://35.205.120.189/rodovre";
    private static final String USERNAME = "free";
    private static final String PASSWORD = "test1234";
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static Statement statement;
    private static ResultSet resultSet;

    private static DatabaseHandler ourInstance = new DatabaseHandler();
    public static DatabaseHandler getInstance() {
        return ourInstance;
    }

    private DatabaseHandler() {
        try {
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(DATABASE_URL, USERNAME,PASSWORD);
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found!");
        } catch (SQLException e) {
            System.out.println("Couldn't connect to server");
        }
    }

    public void insert(String table, String[] columnNames, ArrayList values) {
        //Name of table
        String sql = "INSERT INTO "+table+" (";

        //Columns
        for(String column : columnNames) {
            sql += column+",";
        }

        //Remove last comma and insert end parenthesis
        sql = sql.substring(0, sql.length()-1)+") VALUES(";

        //Prepare for values
        for(String column: columnNames) {
            sql += "?,";
        }

        //Remove last comma and insert end parenthesis
        sql = sql.substring(0, sql.length()-1)+")";

        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);

            if(values.get(0).getClass() == ArrayList.class) {
                for(Object arrayValues: values) {
                    int counter = 1;

                    for(Object value : (ArrayList)arrayValues) {
                        if(value.getClass() == Integer.class) {
                            preparedStatement.setInt(counter, (Integer)value);
                        } else if(value.getClass() == String.class) {
                            preparedStatement.setString(counter, (String)value);
                        }
                        counter++;
                    }

                    preparedStatement.executeUpdate();
                    connection.commit();
                }
            } else {
                int counter = 1;

                for(Object value : values) {
                    if(value.getClass() == Integer.class) {
                        preparedStatement.setInt(counter, (Integer)value);
                    } else if(value.getClass() == String.class) {
                        preparedStatement.setString(counter, (String)value);
                    }
                    counter++;
                }

                preparedStatement.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to execute! Sorry!");
        }
    }

    public void update() {

    }

    public void delete() {

    }
    public void select() {

    }

    public ArrayList<String> getColumns(String table) {
        ArrayList<String> columns = new ArrayList<>();

        try {
            resultSet = statement.executeQuery("SHOW COLUMNS FROM "+table);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int counter = 1;

            while(resultSet.next()) {
                System.out.println(resultSetMetaData.getColumnTypeName(counter));
                counter++;
            }
        } catch (SQLException e) {
            System.out.println("Something went really wrong!");
        }

        return columns;
    }

    public void close() {
        try {
            if(preparedStatement != null) { preparedStatement.close(); }
            if(statement != null) { statement.close(); }
            if(connection != null) { connection.close(); }
        } catch (SQLException e) {
            System.out.println("Unable to close connection! No connection established!");
        }
    }
}
