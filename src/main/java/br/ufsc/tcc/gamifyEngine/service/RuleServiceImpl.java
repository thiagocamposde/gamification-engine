package br.ufsc.tcc.gamifyEngine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufsc.tcc.gamifyEngine.dao.RuleDao;
import br.ufsc.tcc.gamifyEngine.dao.RuleLevelDao;
import br.ufsc.tcc.gamifyEngine.dao.RuleAttributeDao;
import br.ufsc.tcc.gamifyEngine.dao.RuleBadgeDao;
import br.ufsc.tcc.gamifyEngine.model.Rule;
import br.ufsc.tcc.gamifyEngine.model.RuleAttribute;
import br.ufsc.tcc.gamifyEngine.model.RuleBadge;
import br.ufsc.tcc.gamifyEngine.model.RuleLevel;

@Service
public class RuleServiceImpl implements RuleService{
	
	@Autowired
	private RuleDao ruleDao;
	
	@Autowired
	private RuleAttributeDao ruleAttributeDao;
	
	@Autowired
	private RuleBadgeDao ruleBadgeDao;

	@Autowired
	private RuleLevelDao ruleLevelDao;
	
	public RuleServiceImpl() {
			
	}
	
	@Override
	public Rule getRule(int ruleId) {
		return ruleDao.findById(ruleId);
	}

	@Override
	public Iterable<Rule> findAllRules() {
		return ruleDao.findAll();
	}

	@Override
	public RuleAttribute getRuleAttribute(int ruleAttributeId) {
		return ruleAttributeDao.findById(ruleAttributeId);
	}
	
	@Override
	public RuleBadge getRuleBadge(int ruleBadgeId) {
		return ruleBadgeDao.findById(ruleBadgeId);
	}

	@Override
	public RuleAttribute getRuleAttributeByRule(Rule ruleId) {
		return ruleAttributeDao.findByRule(ruleId);
	}

	@Override
	public RuleLevel getRuleLevel(int ruleLevelId) {
		return ruleLevelDao.findById(ruleLevelId);
	}
}
