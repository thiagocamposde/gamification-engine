package br.ufsc.tcc.gamifyEngine.service;

import br.ufsc.tcc.gamifyEngine.model.Attribute;

public interface AttributeService {
	public Attribute getAttribute(int attributeId);

	public void deleteAttribute(int attributeId);

	public Attribute saveAttribute(Attribute attribute);
}
