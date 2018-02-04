package br.ufsc.tcc.gamifyEngine.dao;

import org.springframework.data.repository.CrudRepository;

import br.ufsc.tcc.gamifyEngine.model.Attribute;

public interface AttributeDao  extends CrudRepository<Attribute, Long> {
	public Attribute findById(int attributeId);
}
