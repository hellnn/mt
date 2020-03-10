package org.example.service;

import org.example.common.ConnectionHolder;
import org.example.common.TransactionCallback;

import java.sql.Connection;
import java.sql.SQLException;

public class AbstractService {
    protected void doInTransaction(TransactionCallback callback) {
        try (Connection connection = ConnectionHolder.get()) {
            connection.setAutoCommit(false);
            try {
                callback.execute();
            } catch (RuntimeException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
                ConnectionHolder.clear();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
