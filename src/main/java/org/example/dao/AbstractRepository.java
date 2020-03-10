package org.example.dao;

import org.example.common.ConnectionHolder;
import org.example.common.GeneratedResultCallback;
import org.example.common.PreparedStatementSetter;
import org.example.common.RowMapper;

import java.sql.*;
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

    protected boolean executeUpdate(String sql, PreparedStatementSetter setter, GeneratedResultCallback callback) {
        Connection connection = ConnectionHolder.get();
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            try {
                setter.setValues(statement);
                int result = statement.executeUpdate();
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        callback.execute(rs);
                    }
                }
                return result > 0;
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
