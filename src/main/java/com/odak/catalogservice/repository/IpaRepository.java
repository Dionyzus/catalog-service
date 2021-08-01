package com.odak.catalogservice.repository;

import java.util.List;
import java.util.Optional;

public interface IpaRepository<T> {

	List<T> getAll();

	T save(T entity);

	void saveMany(List<T> entities);

	Optional<T> getById(String id);

	void delete(String id);

	T update(T entity, T newEntityData);
}
