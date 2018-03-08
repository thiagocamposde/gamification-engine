package br.ufsc.tcc.gamifyEngine.service;

import java.util.List;

import br.ufsc.tcc.gamifyEngine.model.LogEvent;

public interface LogService {
	public List<LogEvent> getLogByUserAndRule(int userId, int ruleId);
}
