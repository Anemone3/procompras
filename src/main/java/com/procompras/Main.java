package com.procompras;

import com.procompras.engine.RappiSpider;
import com.procompras.engine.SeleniumBrowser;

import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException {
        String url = "jdbc:postgresql://[::1]:5432/procompras";
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
        Connection conn = DriverManager.getConnection(url, props);

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT NOW()");

        if (rs.next()) {
            System.out.println(rs.getString(1));
        }

        SeleniumBrowser seleniumBrowser = new SeleniumBrowser();
        RappiSpider spider = new RappiSpider(seleniumBrowser);
        spider.execute();
    }
}