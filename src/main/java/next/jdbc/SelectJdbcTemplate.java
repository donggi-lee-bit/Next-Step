package next.jdbc;

import core.jdbc.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import next.model.User;

public abstract class SelectJdbcTemplate {

    public abstract void setValues(PreparedStatement pstmt) throws SQLException;

    public abstract Object mapRow(ResultSet resultSet) throws SQLException;

    public Object queryForObject(String sql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Connection con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            setValues(pstmt);
            rs = pstmt.executeQuery();
            User user = (User) mapRow(rs);

            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public List<User> query(String sql) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            return (List<User>) mapRow(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
