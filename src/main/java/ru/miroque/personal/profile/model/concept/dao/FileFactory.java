package ru.miroque.personal.profile.model.concept.dao;

import org.jboss.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileFactory {
	@Inject
	Logger log;

	@Produces
	public File filePath(){
		Path pathDefaultSubdirectory = Path.of("./payload/");
		Path pathDefaultFile = Path.of("default.ppml");
		Path pathDefaultCombinedFull = pathDefaultSubdirectory.resolve(pathDefaultFile);

		if (Files.exists(pathDefaultSubdirectory) && Files.isDirectory(pathDefaultSubdirectory)){
			log.info("Directory exists!");
		} else {
			log.warn("Directory DOES NOT EXISTS!");
			try {
				Files.createDirectories(pathDefaultSubdirectory);
				log.info("Directory CREATED!");
			} catch (IOException e) {
				log.error("directory not created due error", e);
				throw new RuntimeException(e);
			}
		}

		if (Files.exists(pathDefaultCombinedFull)) {
			log.infof("File exists, return one: %s", pathDefaultCombinedFull.toString());
			return pathDefaultFile.toFile();
		} else {
			log.warn("File does not exists!");
			Path pathNewCreatedFile = null;
			try {
				String content = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
								"<personal-profile xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"personal-profile.xsd\">" +
								" <identity> </identity>" +
								" <data> </data>" +
								"</personal-profile>";
				pathNewCreatedFile = Files.write(pathDefaultCombinedFull, content.getBytes(StandardCharsets.UTF_8));
				log.info("File CREATED!");
			} catch (IOException e) {
					log.info("file not created due error", e);
					throw new RuntimeException(e);
			}
			log.infof("return new created one: %s", pathDefaultCombinedFull.toString());
			return pathNewCreatedFile.toFile();
		}
	}
}
