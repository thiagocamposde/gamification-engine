package br.ufsc.tcc.gamifyEngine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufsc.tcc.gamifyEngine.dao.UserDao;
import br.ufsc.tcc.gamifyEngine.model.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	public UserServiceImpl() {
		
	}
	
	@Override
	public User getUser(int userId) {
		return userDao.findById(userId);
	}

	@Override
	public Iterable<User> findAllUsers() {
		return userDao.findAll();
	}

	@Override
	public User saveUser(User user) {
		return userDao.save(user);
	}

	@Override
	public void deleteUser(int userId) {
		this.userDao.delete(userId);
	}

	@Override
	public void getUserBadge(int userId, int badgeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUserBadge(int userId, int badgeId) {
		this.userDao.deleteUserBadge(userId, badgeId);
		
	}
}