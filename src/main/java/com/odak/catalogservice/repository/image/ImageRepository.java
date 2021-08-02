package com.odak.catalogservice.repository.image;

import java.util.List;

import com.odak.catalogservice.model.Image;
import com.odak.catalogservice.repository.CustomPersistanceApiRepository;

/**
 * Image repository abstraction available for additional persistence methods.
 *
 * @author ivano
 *
 */
public interface ImageRepository extends CustomPersistanceApiRepository<Image> {
	/**
	 * Gets list of links for a provided catalog item.
	 *
	 * @param catalogItemName - name of a catalog item
	 * @return list containing image links.
	 */
	public List<String> getUriLinksByCatalogItemName(String catalogItemName);
}
