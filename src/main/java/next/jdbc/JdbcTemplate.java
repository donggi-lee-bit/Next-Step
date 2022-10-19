package next.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import next.model.User;

public abstract class JdbcTemplate {

    public abstract void update(User user) throws SQLException;

    public abstract void setValues(User user, PreparedStatement pstmt) throws SQLException;

    public abstract String createQuery();
}
