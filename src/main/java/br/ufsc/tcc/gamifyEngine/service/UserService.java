package br.ufsc.tcc.gamifyEngine.service;

import br.ufsc.tcc.gamifyEngine.model.User;

public interface UserService {
	public User getUser(int userId);
	public Iterable<User> findAllUsers();
	public User saveUser(User user);
	public void deleteUser(int userId);
}
