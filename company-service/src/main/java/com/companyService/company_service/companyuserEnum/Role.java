package com.companyService.company_service.companyuserEnum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
	ADMIN, SENDER, REVIEWER;
	
	  @JsonCreator
	    public static Role fromString(String key) {
	        return key == null ? null : Role.valueOf(key.toUpperCase());
	    }

	    @JsonValue
	    public String getValue() {
	        return this.name();
	    }
}
