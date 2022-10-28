package ru.miroque.personal.profile.model.concept.dao.implementation;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.xml.xpath.XPathExpressionException;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import ru.miroque.personal.profile.model.concept.dao.DaoKnowledge;
import ru.miroque.personal.profile.model.concept.entity.Knowledge;
import ru.miroque.personal.profile.model.concept.exception.ExceptionNotPersisted;

@Default
@ApplicationScoped
public class DaoKnowledgeJson implements DaoKnowledge {
	@Inject
	Logger log;
	private final File storagePath;

	@Inject
	public DaoKnowledgeJson(File fileJson) {
		this.storagePath = fileJson;
	}

	@Override
	public Collection<Knowledge> findByName(String name) throws XPathExpressionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createOrUpdate(Knowledge item) throws ExceptionNotPersisted {
		save(item);

	}

	@Override
	public void createOrUpdate(Knowledge parent, Knowledge item) throws ExceptionNotPersisted {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Knowledge> findAllAtRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	private void save(Knowledge item) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		try {
			writer.writeValue(storagePath, item);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//new file(path of your file) 
	}

}
