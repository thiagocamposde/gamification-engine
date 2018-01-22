package br.ufsc.tcc.gamifyEngine.service;

import br.ufsc.tcc.gamifyEngine.model.User;

public interface UserService {
	public User getUser(long userId);
	public Iterable<User> findAllUsers();
}
