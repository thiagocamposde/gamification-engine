package br.ufsc.tcc.gamifyEngine.service;

import br.ufsc.tcc.gamifyEngine.model.Badge;

public interface BadgeService {
	public Badge getBadge(int badgeId);

	public void deleteBadge(int badgeId);

	public Badge saveBadge(Badge badge);

	public Iterable<Badge> findAllbadges();
}
