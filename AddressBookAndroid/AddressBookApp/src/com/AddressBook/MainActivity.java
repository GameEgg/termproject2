package com.AddressBook;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
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
	//private DataAdapter dataAdapter;
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
	
		

		Log.i("egg","∆ƒ¿œ ¿‘√‚∑¬ ∫Œ∫– Ω√¿€");
		fm = new FileManager();
		db = fm.makeDB("db.xml");

		//db.getDataList().add(new Data("øˆ≈ÕπÈ", "123123123"));
		//db.getDataList().add(new Data("∞‘¿”ø°±◊", "01027649277"));
		//db.getDataList().add(new Data("≥ﬂ≥ﬂ≥ﬂ≥ﬂ", "123456"));
		//db.getDataList().add(new Data("≈ª¡÷ªÛ√∂", "44444444"));
		db.getDataList().add(new Data("RuneEgg", "222222222"));
		db.getDataList().add(new Data("GameEgg", "111111111"));
		db.getDataList().add(new Data("MadLife", "131313"));
		fm.saveDB(db, "db.xml");
		
		dataAdapter = new ArrayAdapter<Data>(this, R.layout.name_textview,db.getDataList());
		dataListView.setAdapter(dataAdapter);
		
		dataListView.setOnItemClickListener(new dataItemClickEventListener());
		Log.i("egg","∏ÆΩ∫≥  µÓ∑œ øœ∑·");
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
			Log.i("egg","¥≠∑Ø¡¸");
			Intent editIntent = new Intent(MainActivity.this,EditActivity.class);
			editIntent.putExtra("Mode", "edit");
			editIntent.putExtra("Index", index);
			
			startActivity(editIntent);//
		}
	
	}
	
	
}
