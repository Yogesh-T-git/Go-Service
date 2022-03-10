package com.user.goservice.Database;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database extends AsyncTask<Void, Void, Void> {

    public String query = "";
    public String retrieve = "get", update = "set";
    public String queryType = retrieve;
    public int flag = 0;
    public ResultSet resultSet = null;

    public void setQuery(String query, String queryType) {
        this.query = query;
        this.queryType = queryType;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://192.168.43.8:3306/goservicedb",
                            "admin", "1234");
            Statement statement = connection.createStatement();

            if (queryType.equals(update)) {
                flag = statement.executeUpdate(query);
            }
            if (queryType.equals(retrieve)) {
                resultSet = statement.executeQuery(query);

            }


        } catch (Exception e) {
            Log.e("Error DB", e.getLocalizedMessage());
        }

        return null;
    }
}
