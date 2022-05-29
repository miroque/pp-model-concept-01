package ru.miroque.personal.profile.model.concept.dao;

import javax.enterprise.inject.Produces;
import java.io.File;

public class FileFactory {

	@Produces
	public File filePath(){
		return new File("./payload/default.ppml");
	}
}
