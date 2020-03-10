package org.example.common;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface GeneratedResultCallback {
    void execute(ResultSet rs) throws SQLException;
}
