package br.ufsc.tcc.gamifyEngine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufsc.tcc.gamifyEngine.dao.AttributeDao;
import br.ufsc.tcc.gamifyEngine.model.Attribute;

@Service
public class AttributeServiceImpl implements AttributeService{
	@Autowired
	private AttributeDao attributeDao;

	@Override
	public Attribute getAttribute(int attributeId) {
		return attributeDao.findById(attributeId);
	}

	@Override
	public void deleteAttribute(int attributeId) {
		this.attributeDao.delete(attributeId);
	}

	@Override
	public Attribute saveAttribute(Attribute attribute) {
		return this.attributeDao.save(attribute);
	}

	@Override
	public Iterable<Attribute> findAllAttributes() {
		return this.attributeDao.findAll();
	}
}
