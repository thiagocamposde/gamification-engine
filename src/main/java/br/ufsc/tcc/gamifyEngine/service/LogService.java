package br.ufsc.tcc.gamifyEngine.service;

import java.util.List;

import br.ufsc.tcc.gamifyEngine.model.LogEvent;

public interface LogService {
	public List<LogEvent> getLogByUserAndRule(int userId, int ruleId);

	public void insertLog(LogEvent logEvent);

	public void deleteLog(int logId);

	public LogEvent getLog(int logId);

	public List<LogEvent> getUserLogs(int userId);

	public LogEvent saveLog(LogEvent logEvent);

	public void deleteRuleLogsFromUser(int ruleId, int userId);
}
