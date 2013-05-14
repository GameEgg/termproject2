package com.AddressBook;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

class HistoryAdapter extends BaseAdapter{
	private Context maincon;
	LayoutInflater Inflater;
	ArrayList<History> arSrc;
	int layout;
	
	public HistoryAdapter(Context context,int alayout, ArrayList<History> aarSrc) {
		// TODO Auto-generated constructor stub
		maincon = context;
		Inflater = (LayoutInflater)maincon.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		
		ImageView callhis_person = (ImageView)convertView.findViewById(R.id.call_person_img);
		//callhis_person.setImageResource(R.id.call_person_img);
		
		TextView data_date = (TextView)convertView.findViewById(R.id.call_data);
		data_date.setText(arSrc.get(pos).getData() + "   " + arSrc.get(pos).getDate());
		
		ImageButton call_btn = (ImageButton)convertView.findViewById(R.id.call_callbtn);
		call_btn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent callintent = new Intent(Intent.ACTION_CALL);
				callintent.setData(Uri.parse("tel:" + arSrc.get(pos).getData())); //
				maincon.startActivity(callintent); //editText_phone.getText().toString()
			}
		});

		return convertView;
	}
}
