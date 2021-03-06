package br.ufsc.tcc.gamifyEngine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufsc.tcc.gamifyEngine.dao.BadgeDao;
import br.ufsc.tcc.gamifyEngine.model.Badge;

@Service
public class BadgeServiceImpl implements BadgeService{
	@Autowired
	private BadgeDao badgeDao;
	
	@Override
	public Badge getBadge(int badgeId) {
		return badgeDao.findById(badgeId);
	}

	@Override
	public void deleteBadge(int badgeId) {
		this.badgeDao.delete(badgeId);
	}

	@Override
	public Badge saveBadge(Badge badge) {
		return this.badgeDao.save(badge);
	}

	@Override
	public Iterable<Badge> findAllbadges() {
		return this.badgeDao.findAll();
	}
}
