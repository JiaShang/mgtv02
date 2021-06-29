package com.shang.mgtv02.service;

import com.shang.mgtv02.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpt implements UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public String getAllUsers(){
        return jdbcTemplate.queryForObject("select count(1) from users", String.class);
    }

    @Override
    public List<Map<String, Object>> findAll() {
        String sql = "select * from users";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    @Override
    public User getByName(String name) {
        String sql = "select * from users where name = ? ";
        //创建一个新的BeanPropertyRowMapper对象
        RowMapper<User> rowMapper=new BeanPropertyRowMapper<User>(User.class);

        //将name绑定到sql语句中，通过RowMapper返回一个Object类型的单行记录
        return this.jdbcTemplate.queryForObject(sql, rowMapper, name);
    }

    /**
     * 插入用户-防止sql注入-可以返回该条记录的主键
     * @param user
     * @return
     */
    @Override
    public int addUser(User user) {
        String sql = "insert into user(id,name,password,email) values(null,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int resRow = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql,new String[]{"id"});
                ps.setString(1,user.getName());
                ps.setString(2,user.getPassword());
                ps.setString(3,user.getEmail());
                return ps;
            }
        },keyHolder);
        System.out.println("操作记录数："+resRow+" 主键："+keyHolder.getKey());
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    @Override
    public int deleteUser(User user) {
        String sql = "delete from users where name = ?";
        return jdbcTemplate.update(sql,user.getName());
    }

    @Override
    public int updateUser(User user) {
        String sql = "update user set name=?,password=? where name=?";
        int res = jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1,user.getName());
                preparedStatement.setString(2,user.getPassword());
                preparedStatement.setString(3,user.getEmail());
                preparedStatement.setInt(4,user.getId());
            }
        });
        return res;
    }

    @Override
    public int isHasUser(String name) {
        String sql = "select * from user where name=?";
        List<User> user = jdbcTemplate.query(sql, new Object[]{name}, new UserRowMapper());
        if (user!=null && user.size()>0){
            return 1;
        } else {
            return 0;
        }
    }
}
