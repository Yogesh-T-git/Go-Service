package com.user.goservice.Database;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GetDataFromDatabase extends AsyncTask<Void, Void, ResultSet> {

    public String query = "";
    public String retrieve = "get", update = "set";
    public String queryType = retrieve;
    public ResultSet resultSet = null;

    public void setQuery(String query, String queryType) {
        this.query = query;
        this.queryType = queryType;
    }

    public void setQuery(String query) {
        this.query = query;

    }

    @Override
    protected ResultSet doInBackground(Void... voids) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://192.168.43.8:3306/goservicedb",
                            "admin", "1234");
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);


        } catch (Exception e) {
            Log.e("Error", e.getLocalizedMessage());
        }

        return resultSet;
    }
}
