package ru.miroque.personal.profile.model.concept.entity;


import java.util.Objects;
import java.util.UUID;

public class Knowledge {

	private UUID id;
	private String name;

	public Knowledge() {
		this(null, null);
	}

	public Knowledge(UUID id, String name) {
		this.id = id;
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Knowledge knowledge = (Knowledge) o;
		return Objects.equals(id, knowledge.id) && Objects.equals(name, knowledge.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public String toString() {
		return "Knowledge{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
