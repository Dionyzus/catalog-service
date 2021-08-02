package com.odak.catalogservice.helper.query;

/**
 * Interface used to build query with provided request parameters. Reads users
 * request parameters and provides query configuration
 *
 * @author ivano
 *
 */
public interface IQuery {

	/**
	 * Adds query type to request parameters.
	 *
	 * @param type - String value representing query type.
	 * @return {@link IQuery}.
	 */
	IQuery type(String type);

	/**
	 * Adds value to search data with.
	 *
	 * @param type - String value representing query value required to execute
	 *             search.
	 * @return {@link IQuery}.
	 */
	IQuery value(String value);

	/**
	 * Adds page limit, limit to how many data is presented to user.
	 *
	 * @param limit - String value representing page limit/size.
	 * @return {@link IQuery}.
	 */
	IQuery limit(Integer limit);

	/**
	 * Adds offset to displayed data, with zero index convention.
	 *
	 * @param offset - String value representing pages offset.
	 * @return {@link IQuery}.
	 */
	IQuery offset(Integer offset);

	/**
	 * Adds field to sort by data.
	 *
	 * @param sortBy - String representing field to sort by.
	 * @return {@link IQuery}.
	 */
	IQuery sortBy(String sortBy);

	/**
	 * If sort field is provided, sorts data according to provided direction.
	 * Available directions ascending (ASC) and descending (DESC).
	 * 
	 * @param sortDirection - String representing sort direction.
	 * @return {@link IQuery}
	 */
	IQuery sortDirection(String sortDirection);

}
