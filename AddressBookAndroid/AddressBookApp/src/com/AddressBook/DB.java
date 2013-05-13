package com.AddressBook;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class DB {
	private ArrayList<Data> dataList;
	private String userPhone;
	private ArrayList<String> callHistory;
	
	public DB(){
		dataList = new ArrayList<Data>();
		callHistory = new ArrayList<String>();
	}
	
	public ArrayList<Data> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<Data> dataList) {
		this.dataList = dataList;
	}

	public String getUserPhone() {

		if(userPhone == null) return null;
		
		
		String upgradeNumber = new String();
		
		if(userPhone.length() == 11){
			upgradeNumber = new String(userPhone.substring(0, 3)+"-"+userPhone.substring(3, 7)+"-"+userPhone.substring(7, 11));
		}
		else if(userPhone.length() == 10){
			upgradeNumber = new String(userPhone.substring(0, 3)+"-"+userPhone.substring(3, 6)+"-"+userPhone.substring(6, 10));
		}
		else if(userPhone.length() == 9){
			upgradeNumber = new String(userPhone.substring(0, 2)+"-"+userPhone.substring(2, 5)+"-"+userPhone.substring(5, 9));
		}
		else if(userPhone.length() == 8){
			upgradeNumber = new String(userPhone.substring(0, 4)+"-"+userPhone.substring(4, 8));
		}
		else {
			upgradeNumber = userPhone;
		}
		
		return upgradeNumber;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = new String();
		Pattern pattern = Pattern.compile("\\d");

		char[] inputArr = userPhone.toCharArray();
		for(int i = 0; i < userPhone.length(); ++i){
			String s = String.valueOf(inputArr[i]);
			
			this.userPhone += pattern.matcher(s).matches()?s:"";
		}
	}

	public ArrayList<String> getCallHistory() {
		return callHistory;
	}

	public void setCallHistory(ArrayList<String> callHistory) {
		this.callHistory = callHistory;
	}
	public boolean userPhoneExist(){
		if(userPhone == null){
			return false;
		}
		else {
			return true;
		}
	}
	public ArrayList<Integer> findListByString(String string){
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i < dataList.size(); ++i){
			if(dataList.get(i).getName().toLowerCase().contains(string.toLowerCase())){
				list.add(i);
				continue;
			}
			else if(dataList.get(i).getName().toLowerCase().contains(string.toLowerCase())){
				list.add(i);
				continue;
			}
			else {
				for(Field field:dataList.get(i).getFieldList()){
					if(field.getFieldData().toLowerCase().contains(string.toLowerCase())){
						list.add(i);
						break;
					}
				}
				continue;
			}
		}
		
		
		return list;
	}
	
	public ArrayList<Integer> findSmsExistList()
	{
		ArrayList<Integer> smsExistListIndex = new ArrayList<Integer>();
		
		for(int i = 0; i < dataList.size() ;i++)
		{
			if(dataList.get(i).getSms().size()!=0)
			{
				smsExistListIndex.add(i);
			}
		}
		return smsExistListIndex;
	}
}