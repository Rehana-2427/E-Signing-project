package com.example.e_signing_project.authservice.model;

import java.util.Map;

public class EmailRequest {
	
	private String to;
    private String subject;
    private String body;
    private String templateName;
    private Map<String, Object> variables;
    
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public Map<String, Object> getVariables() {
		return variables;
	}
	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}
	@Override
	public String toString() {
		return "EmailRequest [to=" + to + ", subject=" + subject + ", body=" + body + "]";
	}
    
    

}
