package br.ufsc.tcc.gamifyEngine.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import br.ufsc.tcc.gamifyEngine.model.LogEvent;

public interface LogEventDao extends CrudRepository<LogEvent, Integer> {
//	public LogEvent fingLogByUserAndRule (int userId, int ruleId);

	@Query("select log from LogEvent log where log.rule.id = ?1 AND log.user.id = ?2")
	public List<LogEvent> findByUserAndRule(int userId, int ruleId);
	
	@Query("select log from LogEvent log where log.id = ?1")
	public LogEvent findById(int logId);
	
	@Query("select log from LogEvent log where log.user.id = ?1")
	public List<LogEvent> getUserLogs(int userId);
	
	@Transactional
	@Modifying
	@Query(value= "DELETE FROM log_event where rule_id = ?1 AND user_id = ?2", nativeQuery=true)
	public void deleteRuleLogsFromUser(int ruleId, int userId);
	
}
