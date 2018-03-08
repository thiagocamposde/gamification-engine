package br.ufsc.tcc.gamifyEngine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufsc.tcc.gamifyEngine.dao.RuleDao;
import br.ufsc.tcc.gamifyEngine.dao.RuleLevelDao;
import br.ufsc.tcc.gamifyEngine.dao.LogEventDao;
import br.ufsc.tcc.gamifyEngine.dao.RuleAttributeDao;
import br.ufsc.tcc.gamifyEngine.dao.RuleBadgeDao;
import br.ufsc.tcc.gamifyEngine.model.LogEvent;
import br.ufsc.tcc.gamifyEngine.model.Rule;
import br.ufsc.tcc.gamifyEngine.model.RuleAttribute;
import br.ufsc.tcc.gamifyEngine.model.RuleBadge;
import br.ufsc.tcc.gamifyEngine.model.RuleLevel;

@Service
public class LogServiceImpl implements LogService{
	
	@Autowired
	private LogEventDao logEventDao;
	
	public LogServiceImpl() {
			
	}
	
	@Override
	public List<LogEvent>getLogByUserAndRule(int userId, int ruleId) {
		return this.logEventDao.findByUserAndRule(userId, ruleId);
	}
}
