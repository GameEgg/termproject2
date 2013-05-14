package com.AddressBook;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQL_helper extends SQLiteOpenHelper{
	

	public SQL_helper(Context context) {
		super(context, "LINK", null, 1);//
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("egg","onCreate »£√‚µ ");
		db.execSQL("CREATE TABLE data ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT );");
		db.execSQL("CREATE TABLE field ( id INTEGER PRIMARY KEY AUTOINCREMENT, dataID INTEGER, fieldName TEXT, fieldData TEXT );");
		db.execSQL("CREATE TABLE callhistory ( id INTEGER PRIMARY KEY AUTOINCREMENT, dataID INTEGER, fieldName TEXT, fieldData TEXT );");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int old, int now) {
		db.execSQL("DROP TABLE IF EXISTS data");
		db.execSQL("DROP TABLE IF EXISTS field");
		onCreate(db);
		
	}

}
