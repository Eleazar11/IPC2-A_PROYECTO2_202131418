/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.publirevistasapi.database;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
/**
 *
 * @author 3198935960914 - Eleazar Colop
 */
public class DataSourceDBSingleton {
    
    

    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/revistas_online";
    private static final String USER = "root";
    private static final String PASSWORD = "123";
    private static DataSourceDBSingleton UNICA_INSTANCIA_DE_DATASOURCE;
    
    private DataSource datasource;

    // Constructor privado para evitar múltiples instancias
    private DataSourceDBSingleton() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            PoolProperties p = new PoolProperties();
            p.setUrl(URL_MYSQL);
            p.setDriverClassName("com.mysql.cj.jdbc.Driver");
            p.setUsername(USER);
            p.setPassword(PASSWORD);
            p.setJmxEnabled(true);
            p.setTestWhileIdle(false);
            p.setTestOnBorrow(true);
            p.setValidationQuery("SELECT 1");
            p.setTestOnReturn(false);
            p.setValidationInterval(30000);
            p.setTimeBetweenEvictionRunsMillis(30000);
            p.setMaxActive(100);
            p.setInitialSize(10);
            p.setMaxWait(10000);
            p.setRemoveAbandonedTimeout(60);
            p.setMinEvictableIdleTimeMillis(30000);
            p.setMinIdle(10);
            p.setLogAbandoned(true);
            p.setRemoveAbandoned(true);
            p.setJdbcInterceptors(
                    "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                    + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
            datasource = new DataSource(p);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener la única instancia (Singleton)
    public static DataSourceDBSingleton getInstance() {
        if (UNICA_INSTANCIA_DE_DATASOURCE == null) {
            UNICA_INSTANCIA_DE_DATASOURCE = new DataSourceDBSingleton();
        }

        return UNICA_INSTANCIA_DE_DATASOURCE;
    }

    // Método para obtener una conexión del pool
    public Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }
}
