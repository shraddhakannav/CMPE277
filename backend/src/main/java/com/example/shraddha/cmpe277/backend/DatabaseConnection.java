package com.example.shraddha.cmpe277.backend;

import com.google.appengine.api.utils.SystemProperty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shraddha on 10/14/15.
 */
public class DatabaseConnection {

    public static Connection connection;
    private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String CONNECTION_URL = "jdbc:mysql://2001:4860:4864:1:83e5:cfb:3eca:eecc:3307/sensordatabase";
    private static String GOOGLE_JDBC_DRIVER = "com.mysql.jdbc.GoogleDriver";
    private static String GOOGLE_CONNECTION_URL = "jdbc:google:mysql://cmpe277-109506:schema/sensordatabase";
    private static String USERNAME = "shraddha";
    private static String PASSWORD = "root";

    public static Connection getConnection() throws Exception {

        try {
            if (connection == null || connection.isClosed()) {


                String url = null;
                if (SystemProperty.environment.value() ==
                        SystemProperty.Environment.Value.Production) {
                    // Load the class that provides the new "jdbc:google:mysql://" prefix.
                    Class.forName(GOOGLE_JDBC_DRIVER);
                    url = GOOGLE_CONNECTION_URL;
                    connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
                } else {
                    // Local MySQL instance to use during development.
                    Class.forName(JDBC_DRIVER);
                    url = CONNECTION_URL;
                    connection = DriverManager.getConnection(url, "root", "root");
                }


            }
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("No SQL connection");
        }
    }


    public static List<User> listAllUsers() {
        StringBuilder sb = new StringBuilder();
        List<User> userList = new ArrayList<User>();
        try {
            String query = "select * from user";
            connection = getConnection();

            if (connection == null)
                sb.append("No connection found");
            else {
                sb.append("connection found");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                sb.append("Column number " + columnsNumber);

                while (resultSet.next()) {

                    User user = new User();
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setAddress(resultSet.getString("address"));
                    user.setPwd(resultSet.getString("pwd"));
                    user.setUser_id(resultSet.getInt("user_id"));
                    userList.add(user);
                }
                connection.commit();
                connection.close();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return userList;
    }

//    public static boolean insertSensorData(SensorData sensorData) {
//        try {
//
//            StringBuilder sb = new StringBuilder();
//            sb.append("insert into sensordata (type, latitude, longitude, value, time) values(");
//            sb.append("'" + sensorData.getSensortype() + "',");
//            sb.append(sensorData.getLatitude() + ",");
//            sb.append(  sensorData.getLongitude() + ",");
//            sb.append("'" + sensorData.getValue() + "',");
//            sb.append("" + sensorData.getTime() + ");");
//
//            String query = sb.toString();
//            connection = getConnection();
//            if (connection != null) {
//                Statement statement = connection.prepareStatement(query);
//                statement.executeUpdate(query);
//                connection.commit();
//                connection.close();
//                return true;
//            } else {
//                return false;
//            }
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//
//        return false;
//    }

    public static boolean insertUser(User user) {

        return false;
    }

    public static void deleteUser(User user) {

    }
}
