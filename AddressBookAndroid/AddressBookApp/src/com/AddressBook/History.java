package com.AddressBook;

public class History {
	private String data;
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	private String date;
	public History(String date, String data)
	{
		this.data = data;
		this.date = date;
	}
}
