package com.example.e_signing_project.authservice.model;

public class EmailRequest {
	
	private String to;
    private String otp;
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	@Override
	public String toString() {
		return "EmailRequest [to=" + to + ", otp=" + otp + ", getTo()=" + getTo() + ", getOtp()=" + getOtp()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	

    

}
