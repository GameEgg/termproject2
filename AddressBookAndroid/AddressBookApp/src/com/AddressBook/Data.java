package com.AddressBook;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class Data implements Comparable{
	private String name;
	private String phoneNumber;
	private ArrayList<String> sms;
	private ArrayList<Field> fieldList;
	
	public Data(String iname, String iphoneNumber)
	{
		name = iname;
		phoneNumber = "";
		fieldList = new ArrayList<Field>();
		sms = new ArrayList<String>();
		//

		Pattern pattern = Pattern.compile("\\d");

		char[] inputArr = iphoneNumber.toCharArray();
		for(int i = 0; i < iphoneNumber.length(); ++i){
			String s = String.valueOf(inputArr[i]);
			
			phoneNumber += pattern.matcher(s).matches()?s:"";
		}
	}
	
	public String getName() {
		return name;
	}
	public String getPhoneNumber() {
		String upgradeNumber = new String();
		
		if(phoneNumber.length() == 11){
			upgradeNumber = new String(phoneNumber.substring(0, 3)+"-"+phoneNumber.substring(3, 7)+"-"+phoneNumber.substring(7, 11));
		}
		else if(phoneNumber.length() == 10){
			upgradeNumber = new String(phoneNumber.substring(0, 3)+"-"+phoneNumber.substring(3, 6)+"-"+phoneNumber.substring(6, 10));
		}
		else if(phoneNumber.length() == 9){
			upgradeNumber = new String(phoneNumber.substring(0, 2)+"-"+phoneNumber.substring(2, 5)+"-"+phoneNumber.substring(5, 9));
		}
		else if(phoneNumber.length() == 8){
			upgradeNumber = new String(phoneNumber.substring(0, 4)+"-"+phoneNumber.substring(4, 8));
		}
		else {
			upgradeNumber = phoneNumber;
		}
		
		return upgradeNumber;
	}
	public boolean addField(String fieldName,String fieldData)
	{
		for(Field field:fieldList){
			if(field.getFieldName().endsWith(fieldName)){
				return false;
			}
		}
		fieldList.add(new Field(fieldName, fieldData));
		return true;
	}
	
	public ArrayList<Field> getFieldList()
	{
		return fieldList;
	}
	
	public void editField()
	{
		
	}
	
	public void removeField()
	{
		
	}

	public ArrayList<String> getSms() {
		return sms;
	}

	public void setSms(ArrayList<String> sms) {
		this.sms = sms;
	}
	
	public void editByIndex(int index, String data, String fieldName){
		switch(index){
		case 1:
			name = data;
			break;
		case 2:
			phoneNumber = data;
			break;
		default:
			if(index > 2 && index < fieldList.size()+3){
				fieldList.get(index-3).setFieldData(data);
				fieldList.get(index-3).setFieldName(fieldName);
			}
		}
	}

	@Override
	public int compareTo(Object o) {
		Data target = (Data)o;
		return (name.compareTo(target.getName()));
	}
	
	@Override
	public String toString(){
		return name;
	}
	
}

