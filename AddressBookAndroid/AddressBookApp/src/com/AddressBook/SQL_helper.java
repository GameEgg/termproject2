package com.AddressBook;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQL_helper extends SQLiteOpenHelper{
	

	public SQL_helper(Context context) {
		super(context, "LINK_DB", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE data ( id INTEGER PRIMARY KEY AUTOINCREMENT, phone TEXT");
		db.execSQL("CREATE TABLE field ( id INTEGER, fieldName TEXT, fieldData TEXT");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}