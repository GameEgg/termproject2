package com.AddressBook;

import java.util.ArrayList;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private ImageButton addButton;
	private EditText searchingText;
	private ListView dataListView;
	
	private ArrayList<View> buttonViewList;
	private ArrayList<Data> dataList;
	private ArrayList<Integer> indexList;
	private ArrayAdapter<Data> dataAdapter;
	
	
	private DB db;
	private FileManager fm;
	
	

	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);

		addButton = (ImageButton)findViewById(R.id.addButton);
		searchingText = (EditText)findViewById(R.id.searchingText);
		dataListView = (ListView)findViewById(R.id.dataListView);

		searchingText.addTextChangedListener(new searching());
		Log.i("egg","리스너 등록 완료");
	}
	
	
	private void resetListView()
	{
		Log.i("egg","reset시작!");
		Log.i("egg", searchingText.getText().toString());
		indexList = db.findListByString(searchingText.getText().toString());
		dataList = new ArrayList<Data>();
		
		for(int i = 0; i < indexList.size(); ++i){
			dataList.add(db.getDataList().get(indexList.get(i)));
		}
		
		dataAdapter = new ArrayAdapter<Data>(this, R.layout.name_textview,dataList);
		dataListView.setAdapter(dataAdapter);
		
		dataListView.setOnItemClickListener(new dataItemClickEventListener());
		addButton.setOnClickListener(new addButtonClickEventListener());
	}
	
	
	@Override
	protected void onResume() {
		
		fm = new FileManager();
		//db = fm.makeDB("db.xml");->
		db = fm.makeSQLDB(this);
		
		resetListView();

		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	class dataItemClickEventListener implements AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View parent, int index,
				long arg3) {
			Log.i("egg","눌러짐 : "+indexList.get(index));
			Intent editIntent = new Intent(MainActivity.this,EditActivity.class);
			editIntent.putExtra("Mode", "edit");
			editIntent.putExtra("Index", indexList.get(index));
			startActivity(editIntent);
		}
	
	}
	class addButtonClickEventListener implements android.view.View.OnClickListener{

		@Override
		public void onClick(View arg0) {
			Log.i("egg","눌러짐 : add");
			Intent editIntent = new Intent(MainActivity.this,EditActivity.class);
			editIntent.putExtra("Mode", "add");
			startActivity(editIntent);
		}
	}
	class searching implements TextWatcher{
		@Override
		public void afterTextChanged(Editable arg0) {
			Log.i("egg","검색 작동!");
			resetListView();
			
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
}
