package br.ufsc.tcc.gamifyEngine.service;

import java.util.List;

import br.ufsc.tcc.gamifyEngine.model.Attribute;

public interface AttributeService {
	public Attribute getAttribute(int attributeId);

	public void deleteAttribute(int attributeId);

	public Attribute saveAttribute(Attribute attribute);

	public Iterable<Attribute> findAllAttributes();
}
