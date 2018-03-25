package br.ufsc.tcc.gamifyEngine.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufsc.tcc.gamifyEngine.model.RuleBadgeAttribute;

public interface RuleBadgeAttributeDao  extends CrudRepository<RuleBadgeAttribute, Integer> {
	
	public List<RuleBadgeAttribute> findByAttributeId(int attributeId);
}
