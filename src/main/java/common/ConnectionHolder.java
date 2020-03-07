package common;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionHolder {
    private static final ThreadLocal<Connection> currentConnection = new ThreadLocal<>();

    public static Connection get() {
        Connection connection = currentConnection.get();
        try {
            if (connection == null || connection.isClosed()) {
                connection = DataSourceUtils.getConnection();
                currentConnection.set(connection);
                return connection;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void clear() {
        currentConnection.remove();
    }
}
