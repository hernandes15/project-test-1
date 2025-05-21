package com.project.test.enums;


public enum Message {
	  SUCCESS("Success!"),
	  NOTFOUND("Data Not Found!"),
	  ERROR("Error in services!"),
	  CREATED("Data successfully created!"),
	  UPDATED("Data successfully updated!"),
	  UNAVAILABLE("Service Unavailable!"),
	  UNAUTHORIZED("Auth Error!");


	  private final String value;

	  private Message(String value) {
	    this.value = value;
	  }

	  public String getValue() {
	    return value;
	  }
}
