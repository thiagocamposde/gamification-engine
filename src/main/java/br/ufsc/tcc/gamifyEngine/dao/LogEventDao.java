package br.ufsc.tcc.gamifyEngine.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.ufsc.tcc.gamifyEngine.model.LogEvent;

public interface LogEventDao extends CrudRepository<LogEvent, Long> {
//	public LogEvent fingLogByUserAndRule (int userId, int ruleId);

	@Query("select log from LogEvent log where log.rule.id = ?1 AND log.user.id = ?2")
	public List<LogEvent> findByUserAndRule(int userId, int ruleId);
}
