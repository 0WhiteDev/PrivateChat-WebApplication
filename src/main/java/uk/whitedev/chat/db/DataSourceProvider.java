package uk.whitedev.chat.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceProvider implements AutoCloseable {
    private static DataSource dataSource;

    private DataSourceProvider() { }

    @Override
    public void close() {
        try {
            if (getDataSource().getConnection() != null) {
                getDataSource().getConnection().close();
            }
        }catch (Exception e){}
    }

    public static DataSource getDataSource() throws NamingException {
        if (dataSource == null) {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env/");
            dataSource = (DataSource) envContext.lookup("jdbc/privatechat");
        }
        return dataSource;
    }
}