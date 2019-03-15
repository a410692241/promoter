
package com.linayi.enums;

public enum Sex {
	MALE("男"),
	FEMALE("女");

	private String sexName;
	private Sex(String sexName) {
		this.sexName = sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getSexName() {
		return this.sexName;
	}
}
