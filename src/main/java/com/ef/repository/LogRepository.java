package com.ef.repository;

import com.ef.entities.Arguments;
import com.ef.entities.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LogRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final String LOG_TABLE_NAME = "LOG";
    public static final String BLOCKED_IP_TABLE_NAME = "BLOCKED_IP";

    public void saveParsedLogs(List<Log> logs) {
        this.jdbcTemplate.execute("TRUNCATE " +LOG_TABLE_NAME);

        this.jdbcTemplate.batchUpdate("INSERT INTO " +LOG_TABLE_NAME +" VALUES(?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Log log = logs.get(i);
                preparedStatement.setString(1, log.getDateTime());
                preparedStatement.setString(2, log.getIp());
                preparedStatement.setString(3, log.getUserAgent());
            }

            @Override
            public int getBatchSize() {
                return logs.size();
            }
        });
    }

    public List<Log> getBlockedIps(Arguments arguments) {
        String query = "SELECT IP \n" +
                "FROM "+ LOG_TABLE_NAME + "\n" +
                "WHERE DATE_TIME >= :startDate AND DATE_TIME <= :startDate + INTERVAL :duration HOUR"+ "\n" +
                "GROUP BY IP\n" +
                "HAVING COUNT(*) > :threshold";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startDate", arguments.getStartDate());
        params.addValue("duration", "hourly".equalsIgnoreCase(arguments.getDuration()) ? "1" : "24");
        params.addValue("threshold", arguments.getThreshold());

        List<Log> blockedIPs = this.namedJdbcTemplate.query(query, params, new RowMapper<Log>() {
            @Nullable
            @Override
            public Log mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Log(null, resultSet.getString(1), null);
            }
        });

        return blockedIPs;
    }

    public void saveBlockedIps(List<Log> blockedIps, Arguments arguments) {
        this.jdbcTemplate.execute("TRUNCATE " +BLOCKED_IP_TABLE_NAME);

        this.jdbcTemplate.batchUpdate("INSERT INTO " +BLOCKED_IP_TABLE_NAME+ " VALUES(?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Log log = blockedIps.get(i);
                preparedStatement.setString(1, log.getIp());
                preparedStatement.setString(2, "The IP has been blocked due to more than "
                        +arguments.getThreshold()+ " " +arguments.getDuration()+ " requests after " +arguments.getStartDate());
            }

            @Override
            public int getBatchSize() {
                return blockedIps.size();
            }
        });
    }
}
