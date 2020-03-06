package dao;

import common.ConnectionHolder;
import common.PreparedStatementSetter;
import common.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRepository<T> {
    private static final String LOCK_SQL_SUFFIX = " order by id for update";

    protected List<T> executeQuery(String sql, Object[] args, RowMapper<T> mapper, boolean lock) {
        Connection connection = ConnectionHolder.get();
        String resultSql = lock ? sql + LOCK_SQL_SUFFIX : sql;
        try (PreparedStatement statement = connection.prepareStatement(resultSql)) {
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            List<T> results = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    results.add(mapper.map(rs));
                }
                return results;
            } finally {
                if (connection.getAutoCommit()) {
                    connection.close();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean executeUpdate(String sql, PreparedStatementSetter setter) {
        Connection connection = ConnectionHolder.get();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try {
                setter.setValues(statement);
                return statement.executeUpdate() > 0;
            } finally {
                if (connection.getAutoCommit()) {
                    connection.close();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
