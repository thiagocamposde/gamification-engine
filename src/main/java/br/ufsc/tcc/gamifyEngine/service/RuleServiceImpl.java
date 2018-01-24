package br.ufsc.tcc.gamifyEngine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufsc.tcc.gamifyEngine.dao.RuleDao;
import br.ufsc.tcc.gamifyEngine.dao.RuleRewardDao;
import br.ufsc.tcc.gamifyEngine.model.Rule;
import br.ufsc.tcc.gamifyEngine.model.RuleReward;

@Service
public class RuleServiceImpl implements RuleService{
	
	@Autowired
	private RuleDao ruleDao;
	
	@Autowired
	private RuleRewardDao ruleRewardDao;
	
	public RuleServiceImpl() {
			
	}
	
	@Override
	public Rule getRule(long ruleId) {
		return ruleDao.findById(ruleId);
	}

	@Override
	public Iterable<Rule> findAllRules() {
		return ruleDao.findAll();
	}

	@Override
	public RuleReward getRuleReward(long ruleId) {
		return ruleRewardDao.findById(ruleId);
	}
}
