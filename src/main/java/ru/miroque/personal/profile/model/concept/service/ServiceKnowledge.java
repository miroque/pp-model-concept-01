package ru.miroque.personal.profile.model.concept.service;

import java.util.List;

public interface ServiceKnowledge<IDNT, N> {

	N getKnowledge(IDNT id);
	N getParent(IDNT id);
	List<N> getChildren(IDNT id);
	
}
