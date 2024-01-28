package com.example.dg_bank;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.*;
import java.util.Objects;

public class SQLServerConnect {
    Connection conn;
    private static final String url = "jdbc:postgresql://horton.db.elephantsql.com/";
    private static final String username = "tvbospfe";
    private static final String password = "856nhEo741c_VLLWeIwMbInbQyE1AMbN";
    @SuppressLint("NewApi")

    public SQLServerConnect()
    {
        getConn();
    }

    protected void finalize() throws Throwable {
        try {
            conn.close();
        } finally {
            // Call the superclass finalize method
            super.finalize();
        }
    }

    public void getConn() {
        StrictMode.ThreadPolicy a = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, username, password);
        }
        catch (Exception e) {
            Log.e("Error = ",e.getMessage());
            conn = null;
        }
    }

    public boolean checkConn(Context context)
    {
        if(conn==null)
        {
            Toast.makeText(context, "Can't connect to the server", Toast.LENGTH_SHORT).show();
            getConn();
            return false;
        }
        return true;
    }

    public int getNextID(String tableName) {
        int nextID = 1; // Default value if the table is empty or no rows are found.
        try {
            String selectQuery = "SELECT COALESCE(MAX(ID), 0) FROM "+tableName;
            Statement statement = conn.createStatement();

            // Execute the select query
            ResultSet resultSet = statement.executeQuery(selectQuery);
            if (resultSet.next()) {
                nextID = resultSet.getInt(1) + 1;
            }

            // Close the result set, prepared statement, and connection
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nextID;
    }

    public String getName(String id)
    {
        try {
            String query = "SELECT FirstName, SecondName FROM Personal_Info WHERE User_ID = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(id));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String tmp = rs.getString(1) + " " + rs.getString(2);
                return tmp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "null";
    }

    public String getBalance(String id)
    {
        try {
            String query = "SELECT Balance FROM Personal_Info WHERE User_ID = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(id));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String tmp = rs.getString(1);
                return tmp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "null";
    }

    public boolean setBalance(String id, String amount)
    {
        try {
            String query = "UPDATE Personal_Info SET Balance = ? WHERE User_ID = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(2, Integer.parseInt(id));
            statement.setInt(1, Integer.parseInt(amount));
            statement.executeUpdate();
            if(Objects.equals(getBalance(id), amount))
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String getValue(String TableName, String ColumnName, String id)
    {
        try {
            String query;
            if(TableName.equals("Application_User"))
                query = "SELECT " + ColumnName + " FROM " + TableName + " WHERE ID = ?";
            else
                query = "SELECT " + ColumnName + " FROM " + TableName + " WHERE User_ID = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(id));
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
//                    Toast.makeText(context, rs.getString(1), Toast.LENGTH_SHORT).show();
                String tmp = rs.getString(1);
                return tmp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "null";
    }

    public boolean setValue(String TableName, String ColumnName, String id, String value)
    {
        try {
            String query;
            if(TableName.equals("Application_User"))
            {
                query = "UPDATE "+TableName+" SET "+ColumnName+" = '"+value+"' WHERE ID = ?";
            }
            else
            {
                query = "UPDATE "+TableName+" SET "+ColumnName+" = '"+value+"' WHERE User_ID = ?";
            }
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(id));
            int rowsAffected = statement.executeUpdate();

            // Check if the update was successful
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean insertRow(String TableName, String[] ColumnNames, String[] values)
    {
        try {
            StringBuilder query = new StringBuilder("INSERT INTO " + TableName + "(");
            for(int i=0;i< ColumnNames.length;i++)
            {
                query.append(ColumnNames[i]);
                if(i== ColumnNames.length-1)
                {
                    query.append(") Values (");
                    continue;
                }
                query.append(", ");
            }
            for (int i=0;i< ColumnNames.length;i++)
            {
                query.append("'");
                query.append(values[i]);
                query.append("'");
                if(i==ColumnNames.length-1)
                {
                    query.append(")");
                    continue;
                }
                query.append(", ");
            }
            Statement statement = conn.createStatement();
            int rowsAffected = statement.executeUpdate(query.toString());

            // Check if the update was successful
            if (rowsAffected > 0) {
                statement.execute("COMMIT");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String[] getRow(String tableName, String[] columns, String[] defaultValue) {
        String[] resultArray = null;
        try {
            String columnNames = String.join(", ", columns);
            String query = String.format("SELECT %s FROM %s WHERE %s = '%s';", columnNames, tableName, defaultValue[0], defaultValue[1]);
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                resultArray = new String[columns.length];
                for (int i = 0; i < columns.length; i++) {
                    resultArray[i] = resultSet.getString(columns[i]);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultArray;
    }

    public boolean doesExist(String TableName, String ColumnName, String value)
    {
        int count = 0;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count_value FROM "+TableName+" WHERE "+ColumnName+" = '"+value+"'");
            if(resultSet.next())
                count = resultSet.getInt("count_value");
            if(count>0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet getQuery(String query)
    {
        ResultSet resultSet = null;
        try
        {
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return resultSet;
    }

    public boolean updateQuery(String query)
    {
        try
        {
            Statement statement = conn.createStatement();
            int rows = statement.executeUpdate(query);
            if(rows > 0)
            {
                statement.execute("COMMIT");
                return true;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
