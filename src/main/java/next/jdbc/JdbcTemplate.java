package next.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JdbcTemplate {

    public abstract void update(String query) throws SQLException;

    public abstract void setValues(PreparedStatement pstmt) throws SQLException;
}
