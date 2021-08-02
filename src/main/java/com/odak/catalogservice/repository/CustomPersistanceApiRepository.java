package com.odak.catalogservice.repository;

import java.util.List;
import java.util.Optional;

/**
 * Persistence API enabling data handling.
 *
 * @author ivano
 *
 * @param <T> - required data type.
 */
public interface CustomPersistanceApiRepository<T> {

	/**
	 * Gets all available collection data.
	 *
	 * @return list containing data.
	 */
	List<T> getAll();

	/**
	 * Creates new collection entry.
	 * 
	 * @param entity - new collection entry data.
	 * @return added collection entry if operation was successful.
	 */
	T save(T entity);

	/**
	 * Adds multiple entries to collection.
	 *
	 * @param entities - list of new entries.
	 */
	void saveMany(List<T> entities);

	/**
	 * Gets entry if provided id exists.
	 *
	 * @param id - String value of requested entry.
	 * @return requested entry or null if entry does not exist.
	 */
	Optional<T> getById(String id);

	/**
	 * Deletes entry if provided id exists.
	 *
	 * @param id - String value of entry to be deleted.
	 */
	void delete(String id);

	/**
	 * Updates entry with provided new entry details.
	 *
	 * @param entity - entity to be updated.
	 * @param newEntityData - entry containing updated values.
	 * @return - updated entry if operation was successful.
	 */
	T update(T entity, T newEntityData);
}
