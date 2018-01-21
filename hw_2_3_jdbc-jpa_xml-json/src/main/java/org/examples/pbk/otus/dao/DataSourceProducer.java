package org.examples.pbk.otus.dao;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

public class DataSourceProducer {

    @Resource(name = "jdbc/OracleDS")
    private DataSource dataSource;

    @Produces
    @ApplicationScoped
    public DataSource getDataSource() {
        return dataSource;
    }
}
