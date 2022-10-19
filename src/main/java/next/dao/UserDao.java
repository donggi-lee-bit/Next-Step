package next.dao;

import core.jdbc.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import next.jdbc.JdbcTemplate;
import next.jdbc.SelectJdbcTemplate;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        JdbcTemplate insertJdbcTemplate = new JdbcTemplate() {
            @Override
            public void update(String query) throws SQLException {
                Connection con = null;
                PreparedStatement pstmt = null;
                try {
                    con = ConnectionManager.getConnection();
                    pstmt = con.prepareStatement(query);
                    setValues(pstmt);
                    pstmt.executeUpdate();
                } finally {
                    if (pstmt != null) {
                        pstmt.close();
                    }

                    if (con != null) {
                        con.close();
                    }
                }
            }

            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
        };
        insertJdbcTemplate.update(sql);
    }

    public void update(User user) throws SQLException {
        String sql = "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";
        JdbcTemplate updateJdbcTemplate = new JdbcTemplate() {
            @Override
            public void update(String query) throws SQLException {
                Connection con = null;
                PreparedStatement pstmt = null;
                try {
                    con = ConnectionManager.getConnection();
                    pstmt = con.prepareStatement(query);
                    setValues(pstmt);
                    pstmt.executeUpdate();
                } finally {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                }
            }

            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }
        };
        updateJdbcTemplate.update(sql);
    }

    public User findByUserId(String userId) throws SQLException {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }

            @Override
            public Object mapRow(ResultSet resultSet) throws SQLException {
                User user = null;
                if (resultSet.next()) {
                    user = new User(resultSet.getString("userId"), resultSet.getString("password"), resultSet.getString("name"),
                        resultSet.getString("email"));
                }
                return user;
            }
        };
        return (User) selectJdbcTemplate.queryForObject(sql);
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT userId, password, name, email FROM USERS";
        SelectJdbcTemplate jdbcTemplate = new SelectJdbcTemplate() {
            @Override
            public void setValues(PreparedStatement pstmt) {

            }

            @Override
            public Object mapRow(ResultSet resultSet) throws SQLException {
                List<User> users = new ArrayList<>();
                if (resultSet.next()) {
                    User user = new User(resultSet.getString("userId"), resultSet.getString("password"),
                        resultSet.getString("name"), resultSet.getString("email"));
                    users.add(user);
                }
                return users;
            }
        };
        return jdbcTemplate.query(sql);
    }
}
