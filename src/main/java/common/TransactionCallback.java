package common;

import java.sql.SQLException;

@FunctionalInterface
public interface TransactionCallback {
    void execute() throws SQLException;
}
