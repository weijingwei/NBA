package com.ibm.nbaopt.lvm.visualizer.domain;

import java.util.List;

public class Resource {
	private String resource_uri_pattern;
	private String resource_id;
	private List<String> auth_codes;

	public String getResource_uri_pattern() {
		return resource_uri_pattern;
	}

	public void setResource_uri_pattern(String resource_uri_pattern) {
		this.resource_uri_pattern = resource_uri_pattern;
	}

	public String getResource_id() {
		return resource_id;
	}

	public void setResource_id(String resource_id) {
		this.resource_id = resource_id;
	}

	public List<String> getAuth_codes() {
		return auth_codes;
	}

	public void setAuth_codes(List<String> auth_codes) {
		this.auth_codes = auth_codes;
	}

}
