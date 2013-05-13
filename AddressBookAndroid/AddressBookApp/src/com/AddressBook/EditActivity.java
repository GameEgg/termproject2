package com.AddressBook;

import java.util.ArrayList;

import android.R.drawable;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class EditActivity extends Activity {
	
	editAddressbookClickLisener clickLisener;
	
	ImageButton btn_back;
	ImageButton btn_save;
	ImageButton btn_picture;
	
	EditText editText_name;
	EditText editText_phone;
	
	ImageButton btn_fieldAdd;
	ImageButton btn_fieldDelete;
	
	EditText editText_fieldName;
	EditText editText_fieldInfo;
	
	Button btn_test;
	
	private FileManager FM;
	private DB db;
	private String Mode;
	private int DbIndex;
	private RelativeLayout field;
	private ArrayList<RelativeLayout> fieldArray;
	private ArrayList<RelativeLayout> fieldArray2;
	
	private EditActivity me;
	private LinearLayout dynamiclayout; // field 부분
	private int fieldCount;
	private final int dynamic_fieldname = 0x6000;
	private final int dynamic_fieldinfo = 0x7000;
	private final int dynamic_fielddeletebtn = 0x8000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		clickLisener = new editAddressbookClickLisener();
		me = this;
		
		Mode = getIntent().getExtras().getString("Mode");
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		initializeViewObject();
	}
	
	private void initializeViewObject()
	{
		btn_back = (ImageButton)findViewById(R.id.edit_backbtn);
		btn_back.setOnClickListener(clickLisener);
		btn_save = (ImageButton)findViewById(R.id.edit_savebtn);
		btn_save.setOnClickListener(clickLisener);
		btn_picture = (ImageButton)findViewById(R.id.edit_Picture);
		btn_picture.setOnClickListener(clickLisener);
		
		editText_name = (EditText)findViewById(R.id.editText_Name);
		editText_phone = (EditText)findViewById(R.id.editText_Phone);
		
		btn_fieldAdd = (ImageButton)findViewById(R.id.edit_fieldAddbtn);
		btn_fieldAdd.setOnClickListener(clickLisener);
		btn_fieldDelete = (ImageButton)findViewById(R.id.edit_fieldDeletebtn);
		btn_fieldDelete.setOnClickListener(clickLisener);
		
		editText_fieldName = (EditText)findViewById(R.id.editText_fieldName);
		editText_fieldInfo = (EditText)findViewById(R.id.editText_fieldInfo);
		
		dynamiclayout = (LinearLayout)findViewById(R.id.dynamicArea);
		dynamiclayout.setOrientation(LinearLayout.VERTICAL);
		
		fieldCount = 1; // field 갯수나타내는거 초기화
		fieldArray = new ArrayList<RelativeLayout>();
		
		FM = new FileManager();
		db = FM.makeDB("dm.xml");
		
		if(Mode.equalsIgnoreCase("edit"))
		{
			DbIndex = getIntent().getExtras().getInt("Index");
			editText_name.setText(db.getDataList().get(DbIndex).getName());
			editText_phone.setText(db.getDataList().get(DbIndex).getPhoneNumber());
			for(int i = 0;i<db.getDataList().get(DbIndex).getFieldList().size();)
			{
				
			}
		}
		else
		{
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit, menu);
		return true;
	}
	
	class editAddressbookClickLisener implements View.OnClickListener
	{
		public editAddressbookClickLisener()
		{
			
		}
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) 
			{
			case R.id.edit_backbtn:
				Toast.makeText(EditActivity.this, "back btn", Toast.LENGTH_SHORT).show();
				me.finish();
				break;
				
			case R.id.edit_savebtn:
				Toast.makeText(EditActivity.this, "save btn", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.edit_Picture:
				Toast.makeText(EditActivity.this, "picture btn", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.edit_fieldAddbtn:
				Toast.makeText(EditActivity.this, "field Add btn", Toast.LENGTH_SHORT).show();
				AddField();
				break;
				
			case R.id.edit_fieldDeletebtn:
				Toast.makeText(EditActivity.this, "field Delete btn", Toast.LENGTH_SHORT).show();
				fieldArray.get(fieldCount - 1).removeAllViews();
				fieldArray.remove(fieldCount - 1);
				fieldCount--;
				break;
				
			default:
				break;
			}
		}
		
		private void AddField()
		{
			fieldCount++;
			
			field = new RelativeLayout(me);
			
			EditText field_name = new EditText(me);
			EditText field_info = new EditText(me);
			ImageButton btn_delete_field = new ImageButton(me);
			
			field_name.setId(dynamic_fieldname + fieldCount);
			field_info.setId(dynamic_fieldinfo + fieldCount);
//			btn_delete_field.setId(dynamic_fielddeletebtn + fieldCount);
//			btn_delete_field.setImageResource(R.drawable.edit_fielddeletebtn); //getResources().getDrawable(R.id.edit_fieldDeletebtn)
//			btn_delete_field.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					//ImageButton deletebtn = new ImageButton(findViewById());
//					
//				}
//			});
			btn_delete_field.setBackgroundDrawable(null);
			
			field.addView(field_name);
			field.addView(field_info);
			//field.addView(btn_delete_field);
			
			RelativeLayout.LayoutParams field_name_editText = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			field_name_editText.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			field_name_editText.width = 260;
			
			RelativeLayout.LayoutParams field_info_editText = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			field_info_editText.addRule(RelativeLayout.RIGHT_OF, field_name.getId());
			field_info_editText.width = 340;
			
//			RelativeLayout.LayoutParams field_delete_btn = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//			field_delete_btn.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//			field_delete_btn.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//			field_delete_btn.setMargins(0, 0, -24, 0);
			
			field_name.setLayoutParams(field_name_editText);
			field_info.setLayoutParams(field_info_editText);
			//btn_delete_field.setLayoutParams(field_delete_btn);
			
			fieldArray.add(field);
			
			dynamiclayout.addView(field);
		}
	}
	
	class Applyfield extends BaseAdapter
	{
		Context maincon;
		LayoutInflater Inflator;
		int layout;
		ArrayList<RelativeLayout> arSrc;
		
		public Applyfield(Context context,int alayout,ArrayList<RelativeLayout> fieldArray) {
			maincon = context;
			Inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arSrc = fieldArray;
			layout = alayout;
			// TODO Auto-generated constructor stub
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
			final int pos = position;
			
			if(convertView == null)
			{
				convertView = Inflator.inflate(layout, parent,false);
			}
			
			EditText fieldname = (EditText)convertView.findViewById(R.id.editText_fieldName);
			
			EditText fieldinfo = (EditText)convertView.findViewById(R.id.editText_fieldInfo);
			
			ImageButton fieldDeleteBtn = (ImageButton)convertView.findViewById(R.id.edit_fieldDeletebtn);
			fieldDeleteBtn.setImageResource(R.drawable.edit_fielddeletebtn);
			
			// TODO Auto-generated method stub
			return null;
		}
	}

}
