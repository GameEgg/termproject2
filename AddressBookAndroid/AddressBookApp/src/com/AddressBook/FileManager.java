package com.AddressBook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;


public class FileManager {
	private SAXBuilder builder;
	String str_Path = Environment.getExternalStorageDirectory().getAbsolutePath();
	
	public FileManager(){

	}
	
	public void saveDB(DB db,String xmlName){
	    Element address;
	    Element fields;
	    Element smses;
	    Element userPhone = new Element("userPhone");
	    Element callHistory = new Element("callHistory");
	    Element root = new Element("AddressBook");
	    
	    String xmlLocation = str_Path+"/"+xmlName;

		for(String calling:db.getCallHistory()){
			callHistory.addContent(newElement("calling",calling));
		}
		userPhone.addContent(db.getUserPhone());
		root.addContent(callHistory);
		root.addContent(userPhone);
		
		for(Data data:db.getDataList()){
			address = new Element("data");
			fields = new Element("fields");
			smses = new Element("smses");
			
			address.addContent(newElement("name",data.getName()));
			address.addContent(newElement("phone",data.getPhoneNumber()));
			
			for(Field field:data.getFieldList()){
			    Element tmpField = new Element("field");
			    tmpField.addContent(newElement("fieldName",field.getFieldName()));
			    tmpField.addContent(newElement("fieldData",field.getFieldData()));
			    fields.addContent(tmpField);
			}
			for(String sms:data.getSms()){
				smses.addContent(newElement("sms",sms));
			}

			address.addContent(fields);
			address.addContent(smses);
			
			root.addContent(address);
		}
		Document document = new Document(root);
		saveXML(document,xmlLocation);
	}
	
	public DB makeDB(String xmlName){
		
		DB db = new DB();
		ArrayList<Data> dataList = new ArrayList<Data>();
		Element root;
		Document document;
	    String xmlLocation = "file://"+str_Path+"/"+xmlName;
	    File file = new File(xmlLocation);
	    
		builder = new SAXBuilder();
		try {
			Log.i("egg",xmlLocation+" : 파일을 불러옵니다1/3");
			document = builder.build(xmlLocation);
			Log.i("egg",xmlLocation+" : 파일을 불러옵니다2/3");
			root = document.getRootElement();
			Log.i("egg",xmlLocation+" : 파일을 불러옵니다3/3");
		} catch (JDOMException e) {
			root = new Element("AddressBook");
			Log.e("egg",xmlLocation+" : 파일이 깨졌습니다. 새로운 파일을 생성하겠습니다.");
			e.printStackTrace();
		} catch (IOException e) {  ////////파일이 존재하지 않는다

		    root = new Element("AddressBook");
			Log.e("egg",xmlLocation+" : 파일이 존재하지 않습니다. 새로운 파일을 생성하겠습니다.");
			e.printStackTrace();
		}


		List<Element> list = root.getChildren();
		List<Element> fields;
		List<Element> smsList;
		List<Element> callHistory;
	    
		for(Element node:list){
			if(node.getName() == "data"){
				fields = node.getChild("fields").getChildren();
				smsList = node.getChild("smses").getChildren();
			
				Data address = new Data(node.getChildText("name"), node.getChildText("phone"));
				
				for(Element field:fields){
					address.addField(field.getChildText("fieldName"), field.getChildText("fieldData"));
				}
				for(Element sms:smsList){
					address.getSms().add(sms.getText());
				}
				
				dataList.add(address);
			}
			else if(node.getName() == "userPhone"){
				db.setUserPhone(node.getText());
			}
			else if(node.getName() == "callHistory"){
				callHistory = node.getChildren();

				for(Element history:callHistory){
					db.getCallHistory().add(history.getText());
				}
			}
		}
		db.setDataList(dataList);
		
		return db;
		
	}
	
	
	
	private Element newElement(String childName,String childValue)
	{
		Element child = new Element(childName);
		child.setText(childValue);
		
		return child;
	}
	
	private void saveXML(Document document, String xmlLocation){
		XMLOutputter outputter = new XMLOutputter();

		Format format = Format.getPrettyFormat();
		format.setEncoding("euc-kr");
		outputter.setFormat(format);

		try {
			Log.i("egg",xmlLocation+" : 파일을 저장합니다.");
			outputter.output(document, new FileOutputStream(xmlLocation));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	
	public DB makeSQLDB(Context context){
		SQL_helper helper = new SQL_helper(context);
		DB db = new DB();
		SQLiteDatabase dbSQLite = helper.getReadableDatabase();
		Cursor cur = dbSQLite.rawQuery("SELECT * FROM data WHERE id = 0", null);
		
		while(cur.moveToNext()){
			db.getDataList().add(new Data(cur.getString(1),cur.getString(2)));
		}
		
		cur = dbSQLite.rawQuery("SELECT * FROM field LIMIT 1", null);
		
		while(cur.moveToNext()){
			db.getDataList().get(cur.getInt(0)).addField(cur.getString(1), cur.getString(2));
		}
		
		return db;
	}
	
	public void saveSQLDB(Context context, DB db){
		SQL_helper helper = new SQL_helper(context);
		SQLiteDatabase dbSQLite = helper.getReadableDatabase();

		dbSQLite.execSQL("DROP TABLE data");
		dbSQLite.execSQL("DROP TABLE field");

		dbSQLite.execSQL("CREATE TABLE data ( id INTEGER PRIMARY KEY AUTOINCREMENT, phone TEXT");
		dbSQLite.execSQL("CREATE TABLE field ( id INTEGER, fieldName TEXT, fieldData TEXT");
		
		int i = 0;
		for (Data data:db.getDataList()){
			dbSQLite.execSQL("INSERT INTO data ("+null+","+data.getName()+","+data.getPhoneNumber()+")");
			for(Field field:data.getFieldList()){
				dbSQLite.execSQL("INSERT INTO field ("+i+","+field.getFieldName()+","+field.getFieldData()+")");
			}
		}
	}
}