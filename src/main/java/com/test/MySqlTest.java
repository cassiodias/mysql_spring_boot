package com.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class MySqlTest implements HealthIndicator {

    @Autowired
    private JdbcTemplate jdbc;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Health health() {
        String result = this.jdbc.execute(new ConnectionCallback<String>() {
            @Override
            public String doInConnection(Connection connection)
                    throws SQLException, DataAccessException {
                return connection.getMetaData().getDatabaseProductVersion();
            }
        });
        return Health.up().withDetail("MySql-Version", result).build();
    }

}
