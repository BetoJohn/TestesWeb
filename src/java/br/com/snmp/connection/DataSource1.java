/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.snmp.connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;

/**
 *
 * @author georgejunior
 */
public class DataSource1 {

    public final static String DATASOURCE_PROPERTIES_FILE = "datasource.properties";
    private final static String CONFIG_NAME = "name";
    private final static String DRIVER_CLASS = "driverClass";
    private final static String JDBC_URL = "jdbcUrl";
    private final static String USER_NAME = "username";
    private final static String PASSWORD = "password";
    private final static String ACQUIRE_INCREMENT = "acquireIncrement";
    private final static String INITIAL_POOL_SIZE = "initialPoolSize";
    private final static String MAX_POOL_SIZE = "maxPoolSize";
    private final static String MIN_POOL_SIZE = "minPoolSize";
    private final static String MAX_STATEMENTS = "maxStatements";
    private final static String TIME_OUT = "timeout";
    private final static String AUTO_COMMIT = "autoCommitOnClose";
    private final static String IDLE_CONNECTION_TEST = "idleConnectionTestPeriod";
    private final static String MAX_CONNECTION_AGE = "maxConnectionAge";
    private final static String MAX_IDLE_TIME = "maxIdleTime";
    private final static String MAX_IDLE_TIME_EXECESS_CONNECTIONS = "maxIdleTimeExcessConnections";

    private static DataSource1 datasource;
    private ComboPooledDataSource cpds;

    private DataSource1() throws IOException, SQLException, PropertyVetoException {
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass("org.postgresql.Driver"); //loads the jdbc driver
        cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/projetosnmpdb");
        cpds.setUser("postgres");
        cpds.setPassword("postgres");

        // the settings below are optional -- c3p0 can work with defaults
        cpds.setMinPoolSize(1);
        cpds.setAcquireIncrement(1);
        cpds.setMaxPoolSize(2);
        cpds.setMaxStatements(180);
    }

    private DataSource1(Properties properties) throws Exception {
        cpds = new ComboPooledDataSource(properties.getProperty(CONFIG_NAME));
        cpds.setDriverClass(properties.getProperty(DRIVER_CLASS)); //loads the jdbc driver
        cpds.setJdbcUrl(properties.getProperty(JDBC_URL));
        cpds.setUser(properties.getProperty(USER_NAME));
        cpds.setPassword(properties.getProperty(PASSWORD));

        // the settings below are optional -- c3p0 can work with defaults
        try {
            cpds.setMinPoolSize(Integer.parseInt(properties.getProperty(MIN_POOL_SIZE)));
        } catch (Exception e) {
        }
        try {
            cpds.setAcquireIncrement(Integer.parseInt(properties.getProperty(ACQUIRE_INCREMENT)));
        } catch (Exception e) {
        }
        try {
            cpds.setMaxPoolSize(Integer.parseInt(properties.getProperty(MAX_POOL_SIZE)));
        } catch (Exception e) {
        }
        try {
            cpds.setMaxStatements(Integer.parseInt(properties.getProperty(MAX_STATEMENTS)));

        } catch (Exception e) {
        }
        try {
            cpds.setAutoCommitOnClose("true".equalsIgnoreCase(properties.getProperty(AUTO_COMMIT)));

        } catch (Exception e) {
        }
        try {
            cpds.setIdleConnectionTestPeriod(Integer.parseInt(properties.getProperty(IDLE_CONNECTION_TEST)));

        } catch (Exception e) {
        }
        try {
            cpds.setMaxConnectionAge(Integer.parseInt(properties.getProperty(MAX_CONNECTION_AGE)));

        } catch (Exception e) {
        }
        try {
            cpds.setMaxIdleTime(Integer.parseInt(properties.getProperty(MAX_IDLE_TIME)));

        } catch (Exception e) {
        }
        try {
            cpds.setMaxIdleTimeExcessConnections(Integer.parseInt(properties.getProperty(MAX_IDLE_TIME_EXECESS_CONNECTIONS)));

        } catch (Exception e) {
        }
    }

    public static Properties getPropertiesPackage() throws Exception {
        Properties props = new Properties();
        //C:\Users\carlos.macedo\Documents\NetBeansProjects\TestesWeb\build\web\
        //saber qual properties esta pegando
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        props.load(ec.getResourceAsStream("/" + DATASOURCE_PROPERTIES_FILE));

        FileInputStream fis = new FileInputStream("C:\\Users\\carlos.macedo\\Documents\\NetBeansProjects\\TestesWeb\\build\\web\\" + DATASOURCE_PROPERTIES_FILE);

        //InputStream is = new DataSource1().getClass().getResourceAsStream(DATASOURCE_PROPERTIES_FILE);
        //props.load(is);
        return props;
    }

    public static DataSource1 getInstance() throws Exception {
        if (datasource == null) {
            datasource = new DataSource1(getPropertiesPackage());
        }
        return datasource;
    }

    public static DataSource1 getInstance(Properties properties) throws Exception {
        if (datasource == null) {
            datasource = new DataSource1(properties);
        }
        return datasource;
    }

    public Connection getConnection() throws SQLException {
        return this.getCpds().getConnection();
    }

    /**
     * @return the cpds
     */
    public ComboPooledDataSource getCpds() {
        return cpds;
    }
}
