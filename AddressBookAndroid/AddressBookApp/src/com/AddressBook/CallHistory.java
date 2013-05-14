package com.AddressBook;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CallHistory extends Activity {
	
	private ListView callHistoryListView;
	private ArrayList<History> callHistoryList;
	private ArrayList<Integer> indexList;
	
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
		
		History tmphis = new History("5월 14일 10시 30분","01040179173");
		callHistoryList.add(tmphis);
		tmphis = new History("5월 14일 10시 31분","01040179173");
		callHistoryList.add(tmphis);
		tmphis = new History("5월 14일 10시 32분","01040179173");
		callHistoryList.add(tmphis);
		
		//callHistoryList.add(new History("2013년 5월 14일 9시 54분", "임경모 01040179173 수신"));
		
		for(int i = 0; i < db.getCallHistory().size(); ++i){
			callHistoryList.add(db.getCallHistory().get(i));
		}
		
		HistoryAdapter callHistoryAdapter = new HistoryAdapter(this, R.layout.call_history_layout, callHistoryList);
		callHistoryListView.setAdapter(callHistoryAdapter);
	}
		
	@Override
	protected void onResume() {
		
		fm = new FileManager();
		db = fm.makeSQLDB(this);
		
		resetCallListView();
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	class HistoryAdapter extends BaseAdapter{
		Context maincon;
		LayoutInflater Inflater;
		ArrayList<History> arSrc;
		int layout;
		
		public HistoryAdapter(Context context,int alayout,ArrayList<History> aarSrc) {
			// TODO Auto-generated constructor stub
			maincon = context;
			Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arSrc = aarSrc;
			layout = alayout;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arSrc.size();
		}
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return arSrc.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			final int pos = position;
			if(convertView == null)
			{
				convertView = Inflater.inflate(layout,parent, false);
			}
			ImageView callhis_person = (ImageView)findViewById(R.id.call_person_img);
			callhis_person.setImageResource(R.id.call_person_img);
			
			TextView data_date = (TextView)findViewById(R.id.call_data);
			data_date.setText(arSrc.get(pos).getData() + "   " + arSrc.get(pos).getDate());
			
			ImageButton call_btn = (ImageButton)findViewById(R.id.call_callbtn);
			call_btn.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent callintent = new Intent(Intent.ACTION_CALL);
					callintent.setData(Uri.parse("tel:" + arSrc.get(pos).getData())); //
					startActivity(callintent); //editText_phone.getText().toString()
				}
			});
			
			
			return convertView;
		}
	}

}
