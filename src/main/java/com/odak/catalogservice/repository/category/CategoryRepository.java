package com.odak.catalogservice.repository.category;

import java.util.List;

import com.odak.catalogservice.model.Category;
import com.odak.catalogservice.repository.CustomPersistanceApiRepository;

/**
 * Category repository abstraction available for additional persistence methods.
 *
 * @author ivano
 *
 */
public interface CategoryRepository extends CustomPersistanceApiRepository<Category> {
	/**
	 * Gets category data by name or multiple name values.
	 *
	 * @param categories - single or multiple category values
	 * @return list containing {@link Category}.
	 */
	List<Category> getByNames(String... categories);

	/**
	 * Gets single category by name.
	 *
	 * @param category - category name.
	 * @return {@link Category} instance.
	 */
	Category getByName(String category);
}
