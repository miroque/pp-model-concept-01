package ru.miroque.personal.profile.model.concept.entity;


import java.util.Objects;

public class Answer {
	/**
	 * Ага, даже лог у меня тут стоит.
	 * И надо будет заморачиваться над генерацией уникального, значения.
	 */
	private Long id;
	private String name;

	public Answer() {
		this(null, null);
	}

	public Answer(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
		Answer check = (Answer) o;
		return Objects.equals(id, check.id) && Objects.equals(name, check.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public String toString() {
		return "Answer{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
