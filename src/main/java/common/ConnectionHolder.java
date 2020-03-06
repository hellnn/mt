package common;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionHolder {
    private static final ThreadLocal<Connection> currentConnection = new ThreadLocal<>();

    public static Connection get() {
        if (currentConnection.get() == null) {
            Connection connection;
            try {
                connection = DataSourceUtils.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            currentConnection.set(connection);
            return connection;
        }
        return currentConnection.get();
    }

    public static void clear() {
        currentConnection.remove();
    }
}
