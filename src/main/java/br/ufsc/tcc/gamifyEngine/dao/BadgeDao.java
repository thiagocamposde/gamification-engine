package br.ufsc.tcc.gamifyEngine.dao;

import org.springframework.data.repository.CrudRepository;

import br.ufsc.tcc.gamifyEngine.model.Badge;

public interface BadgeDao  extends CrudRepository<Badge, Long> {
	public Badge findById(int badgeId);
}
