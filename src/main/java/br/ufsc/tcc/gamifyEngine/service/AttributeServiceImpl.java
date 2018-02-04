package br.ufsc.tcc.gamifyEngine.service;

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

}
