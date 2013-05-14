package com.AddressBook;

import java.util.ArrayList;

import com.AddressBook.MainActivity.dataItemClickEventListener;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CallHistory extends Activity {
	
	private ListView callHistoryListView;
	private ArrayList<History> callHistoryList;
	private ArrayList<Integer> indexList;
	private ArrayAdapter<History> callHistoryAdapter;
	private TextView callHistory_data_date;
	
	private DB db;
	private FileManager fm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_history);
		
		callHistoryListView = (ListView)findViewById(R.id.callhistory_listView);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_call_history, menu);
		return true;
	}
	
	private void resetCallListView()
	{
		callHistoryList = new ArrayList<History>();
		
		for(int i = 0; i < db.getCallHistory().size(); ++i){
			callHistoryList.add(db.getCallHistory().get(i));
		}
		
		callHistoryAdapter = new ArrayAdapter<History>(this, R.layout.call_history_layout,callHistoryList);
		callHistoryListView.setAdapter(callHistoryAdapter);
		
		//callHistoryListView. .setOnItemClickListener();
	}
	
	@Override
	protected void onResume() {
		
		fm = new FileManager();
		db = fm.makeSQLDB(this);
		
		resetCallListView();
		// TODO Auto-generated method stub
		super.onResume();
	}

}
