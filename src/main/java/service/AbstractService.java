package service;

import common.ConnectionHolder;
import common.TransactionCallback;

import java.sql.Connection;
import java.sql.SQLException;

public class AbstractService {
    protected void doInTransaction(TransactionCallback callback) {
        try (Connection connection = ConnectionHolder.get()) {
            connection.setAutoCommit(false);
            try {
                callback.execute();
            } catch (SQLException e) {
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
                ConnectionHolder.clear();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
