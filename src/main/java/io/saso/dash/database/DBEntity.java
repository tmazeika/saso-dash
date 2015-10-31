package io.saso.dash.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBEntity
{
    void fill(ResultSet resultSet) throws SQLException;

    int getId();

    String getTableName();
}