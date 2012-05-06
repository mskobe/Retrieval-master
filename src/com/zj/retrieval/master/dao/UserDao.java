package com.zj.retrieval.master.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.zj.retrieval.master.User;

public class UserDao {
	private SimpleJdbcTemplate sqlClient;
	
	public void setDataSource(DataSource dataSource) {
		sqlClient = new SimpleJdbcTemplate(dataSource);
	}
	public int addUser(User user) {
		String sql = "insert into `user` values(:id, :name, :password, :isActive)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		return sqlClient.update(sql, param);
	}
	public List<User> getAllNotActiveUser() {
		String sql = "select * from `user` where `isActive`=0";
		ParameterizedBeanPropertyRowMapper<User> rm = 
				ParameterizedBeanPropertyRowMapper.newInstance(User.class);
		return sqlClient.query(sql, rm);
	}
	public List<User> getAllUser() {
		String sql = "select * from `user`";
		ParameterizedBeanPropertyRowMapper<User> rm = 
			ParameterizedBeanPropertyRowMapper.newInstance(User.class);
		return sqlClient.query(sql, rm);
	}
	public List<User> getUserById(String id) {
		String sql = "select * from `user` where `id`=?";
		ParameterizedBeanPropertyRowMapper<User> rm =
			ParameterizedBeanPropertyRowMapper.newInstance(User.class);
		return sqlClient.query(sql, rm, id);
	}
	public List<User> getUserByName(String name) {
		String sql = "select * from `user` where `name`=?";
		ParameterizedBeanPropertyRowMapper<User> rm =
			ParameterizedBeanPropertyRowMapper.newInstance(User.class);
		return sqlClient.query(sql, rm, name);
	}
	public int deleteById(String id) {
		String sql = "delete from `user` where `id`=?";
		return sqlClient.update(sql, id);
	}
	public int updateUserPWD(User oldUser) {
		String sql = "update `user` set `password`=:password where `id`=:id";
		SqlParameterSource sps = new BeanPropertySqlParameterSource(oldUser);
		return sqlClient.update(sql, sps);
	}
	public boolean verifySu(String postUserName, String postUserPassword) {
		String sql = "select count(*) from `su` where `name`=? and `password`=?";
		return sqlClient.queryForInt(sql, postUserName, postUserPassword) > 0;
	}
	public boolean verifyUser(String name, String pwd) {
		String sql = "select count(*) from `user` where `name`=? and `password`=? and isActive=1";
		return sqlClient.queryForInt(sql, name, pwd) > 0;
	}
	public boolean verifyUserOrSu(String name, String pwd) {
		return verifySu(name, pwd) || verifyUser(name, pwd);
	}
	public void activeUser(String id) {
		String sql = "update `user` set `isActive`=1 where `id`=?";
		sqlClient.update(sql, id);
	}
}
