package dao.impl;

import dao.AbstractJdbcTemplate;
import dao.interfaces.UserDao;
import model.User;

public class UserDaoImpl extends AbstractJdbcTemplate<User> implements UserDao {
	public UserDaoImpl() {
		super(User.class, "USERS");
	}
}
