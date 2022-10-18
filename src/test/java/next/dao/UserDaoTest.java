package next.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDaoTest {

    @BeforeEach
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void insert() throws SQLException {
        User expected = new User("userId", "password", "name", "javajigi@email.com");
        UserDao userDao = new UserDao();
        userDao.insert(expected);
        User actual = userDao.findByUserId(expected.getUserId());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void findAll() throws SQLException {
        UserDao userDao = new UserDao();
        List<User> users = userDao.findAll();
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    void update() throws SQLException {
        User user = new User("userId", "password", "name", "javajigi@email.com");
        UserDao userDao = new UserDao();
        userDao.insert(user);
        User findUser = userDao.findByUserId(user.getUserId());
        assertThat(user).isEqualTo(findUser);

        user.update(new User("userId", "password2", "name2", "sanjigi@email.com"));
        userDao.update(user);
        findUser = userDao.findByUserId(user.getUserId());
        assertThat(user).isEqualTo(findUser);
    }
}
