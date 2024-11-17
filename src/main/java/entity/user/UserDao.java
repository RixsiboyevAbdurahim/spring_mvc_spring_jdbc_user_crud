package entity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void save(User user) {
        String sql = "insert into users (username, password) values(?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
    }

    public User getUserById(int id) {
        String sql = "select * from users where id=?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{id}, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"));
            }
        });
        return users.isEmpty() ? null : users.get(0);
    }

    public List<User> getAllUsers() {
        String sql = "select * from users order by id desc";
        List<User> userList = jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"));
            }
        });
        return userList;
    }

    public void delete(int id) {
        String sql = "delete from users where id=?";
        jdbcTemplate.update(sql, id);
    }

    public void update(User user) {
        String sql = "update users set username=?, password=? where id=?";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getId());
    }
}
