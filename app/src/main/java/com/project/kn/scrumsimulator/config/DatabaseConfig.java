package com.project.kn.scrumsimulator.config;

import android.annotation.SuppressLint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressLint("DefaultLocale")
public class DatabaseConfig {

    private static Connection connection = null;

    // For Local PostgreSQL
    private static final String host = "192.168.31.179";

    private static final String database = "scrum";
    private static final int port = 5432;
    private static final String user = "user";
    private static final String pass = "password";
    private static final String url;
    private static final String urlPattern = "jdbc:postgresql://%s:%d/%s";

    static {
        url = String.format(urlPattern, host, port, database);
    }

//    public DatabaseConfig()
//    {
//        this.url = String.format(this.url, this.host, this.port, this.database);
//        connect();
//        //this.disconnect();
//        System.out.println("connection status:" + status);
//    }

//    private void connect()
//    {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run()
//            {
//                try
//                {
//                    Class.forName("org.postgresql.Driver");
//                    connection = DriverManager.getConnection(url, user, pass);
//                    status = true;
//                    System.out.println("connected:" + status);
//                }
//                catch (Exception e)
//                {
//                    status = false;
//                    System.out.print(e.getMessage());
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();
//        try
//        {
//            thread.join();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            this.status = false;
//        }
//    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, pass);
                return connection;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public Connection getExtraConnection()
    {
        Connection c = null;
        try
        {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, pass);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return c;
    }
}

