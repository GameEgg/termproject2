package com.AddressBook;

public class Field {
	private String fieldName;
	private String fieldData;
	
	public Field(String name, String data){
		fieldName = name;
		fieldData = data;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldData() {
		return fieldData;
	}

	public void setFieldData(String fieldData) {
		this.fieldData = fieldData;
	}
	
	
}
