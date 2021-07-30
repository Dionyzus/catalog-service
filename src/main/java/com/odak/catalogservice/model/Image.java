package com.odak.catalogservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("uri_link")
	private String uriLink;
	
	@JsonProperty("format")
	private String format;
}
