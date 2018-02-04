package br.ufsc.tcc.gamifyEngine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufsc.tcc.gamifyEngine.dao.RuleDao;
import br.ufsc.tcc.gamifyEngine.dao.RuleAttributeDao;
import br.ufsc.tcc.gamifyEngine.model.Rule;
import br.ufsc.tcc.gamifyEngine.model.RuleAttribute;

@Service
public class RuleServiceImpl implements RuleService{
	
	@Autowired
	private RuleDao ruleDao;
	
	@Autowired
	private RuleAttributeDao ruleAttributeDao;
	
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

//	@Override
//	public RuleAttribute getRuleReward(int idRule) {
//		return ruleAttributeDao.findByIdRule(idRule);
//	}
}
