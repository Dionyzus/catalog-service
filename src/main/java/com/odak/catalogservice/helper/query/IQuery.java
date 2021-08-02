package com.odak.catalogservice.helper.query;

public interface IQuery {

	IQuery type(String type);
	IQuery value(String value);
	IQuery limit(Integer limit);
	IQuery offset(Integer offset);
	IQuery sortBy(String sortBy);
	IQuery sortDirection(String sortDirection);
	
}
