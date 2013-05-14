package com.AddressBook;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
	ImageButton btn_call;
	ImageButton btn_sms;
	ImageButton btn_deleteData;
	
	private FileManager FM;
	private DB db;
	private String Mode;
	private int DbIndex;
	private RelativeLayout field;
	private ArrayList<RelativeLayout> fieldArray;
	
	private EditActivity me;
	private LinearLayout dynamiclayout; // field ºÎºÐ
	private int fieldCount;
	private final int dynamic_fieldname = 0x6000;
	private final int dynamic_fieldinfo = 0x7000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		clickLisener = new editAddressbookClickLisener();
		me = this;
		
		Mode = getIntent().getExtras().getString("Mode");
		DbIndex = -1;
		initializeViewObject();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
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
		
		dynamiclayout = (LinearLayout)findViewById(R.id.dynamicArea);
		dynamiclayout.setOrientation(LinearLayout.VERTICAL);
		
		btn_call = (ImageButton)findViewById(R.id.button_call);
		btn_call.setOnClickListener(clickLisener);
		
		btn_sms = (ImageButton)findViewById(R.id.button_sms);
		btn_sms.setOnClickListener(clickLisener);
		
		btn_deleteData = (ImageButton)findViewById(R.id.edit_deleteData);
		btn_deleteData.setOnClickListener(clickLisener);
		btn_deleteData.setVisibility(View.GONE);
		
		fieldCount = 0; // field °¹¼ö³ªÅ¸³»´Â°Å ÃÊ±âÈ­
		fieldArray = new ArrayList<RelativeLayout>();
		
		FM = new FileManager();
		db = FM.makeSQLDB(me);
		
		Log.i("egg","edit Å½»ö 1 : db±æÀÌ" + db.getDataList().size());
		if(Mode.equalsIgnoreCase("edit"))
		{
			btn_deleteData.setVisibility(View.VISIBLE);

			Log.i("egg","edit Å½»ö 2");
			DbIndex = getIntent().getExtras().getInt("Index");

			Log.i("egg","edit Å½»ö 2-1 DbIndex : "+DbIndex);
			editText_name.setText(db.getDataList().get(DbIndex).getName());

			Log.i("egg","edit Å½»ö 2-2");
			editText_phone.setText(db.getDataList().get(DbIndex).getPhoneNumber());

			Log.i("egg","edit Å½»ö 3:forÁ÷Àü");
			for(int i = 0; i < db.getDataList().get(DbIndex).getFieldList().size();i++)
			{
				Log.i("egg","edit Å½»ö 3:for¾È");
				fieldCount++;
				
				field = new RelativeLayout(me);
				
				EditText field_name = new EditText(me);
				EditText field_info = new EditText(me);
				
				field_name.setId(dynamic_fieldname + fieldCount);
				field_name.setText(db.getDataList().get(DbIndex).getFieldList().get(i).getFieldName());
				field_name.setLines(1);
				field_name.setSingleLine(true);
				
				field_info.setId(dynamic_fieldinfo + fieldCount);
				field_info.setText(db.getDataList().get(DbIndex).getFieldList().get(i).getFieldData());
				field_info.setLines(1);
				field_info.setSingleLine(true);
				
				field.addView(field_name);
				field.addView(field_info);
				
				RelativeLayout.LayoutParams field_name_editText = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				field_name_editText.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				field_name_editText.width = 260;
				
				
				RelativeLayout.LayoutParams field_info_editText = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				field_info_editText.addRule(RelativeLayout.RIGHT_OF, field_name.getId());
				field_info_editText.width = 340;
				
				field_name.setLayoutParams(field_name_editText);
				field_info.setLayoutParams(field_info_editText);
				
				fieldArray.add(field);
				
				dynamiclayout.addView(field);
			}
		}
		
		else
		{
			;
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
				
				if(DbIndex == -1)//add ÀÓ
				{
					if(editText_name.getText().length() != 0 && editText_phone.getText().length() != 0)
					{
						AddData();
					}
					else
					{
						Toast.makeText(EditActivity.this, "name or phone is null", Toast.LENGTH_SHORT).show();
					}
					me.finish();
				} 
				else
				{
					if(editText_name.getText().length() != 0 && editText_phone.getText().length() != 0)
					{
						EditData();
					}
					else
					{
						Toast.makeText(EditActivity.this, "name or phone is null", Toast.LENGTH_SHORT).show();
					}
					me.finish();
				}
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
				
				if(fieldCount > 0)
				{
					fieldArray.get(fieldCount - 1).removeAllViews();
					fieldArray.remove(fieldCount - 1);
					fieldCount--;
				}
				
				break;
				
			case R.id.button_call:
				
				if(editText_phone.getText().length() != 0)
				{
					Intent callintent = new Intent(Intent.ACTION_CALL);
					callintent.setData(Uri.parse("tel:" + editText_phone.getText())); //
					startActivity(callintent); //editText_phone.getText().toString()
				}
				else
				{
					Toast.makeText(EditActivity.this, "Please Input Phone Number!", Toast.LENGTH_SHORT).show();
				}
				break;
				
			case R.id.button_sms:
				
				if(editText_phone.getText().length() != 0)
				{
					try {
					     Intent sendIntent = new Intent(Intent.ACTION_VIEW);
					     sendIntent.putExtra("address", editText_phone.getText().toString());
					     sendIntent.putExtra("sms_body", "¾È³çÇÏ¼¼¿ä Á¶±³´Ô");
					     sendIntent.setType("vnd.android-dir/mms-sms");
					     startActivity(sendIntent);

					} catch (Exception e) {
						Toast.makeText(getApplicationContext(),
							"SMS faild, please try again later!",
							Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
				}
				
				else
				{
					Toast.makeText(EditActivity.this, "Please Input Phone Number!", Toast.LENGTH_SHORT).show();
				}
				
				break;
				
			case R.id.edit_deleteData:
				
				if(DbIndex != -1)
				{
					db.getDataList().remove(DbIndex);
					FM.saveSQLDB(me,db);
				}
				me.finish();
				break;


			default:
				break;
			}
		}
		
		private void EditData()
		{
			Field field;
			db.getDataList().get(DbIndex).setName(editText_name.getText().toString());
			db.getDataList().get(DbIndex).setPhoneNumber(editText_phone.getText().toString());
			db.getDataList().get(DbIndex).getFieldList().clear();
			for(int i = 1; i <= fieldCount; i++ )
			{
				EditText fieldname = (EditText)findViewById(dynamic_fieldname + i);
				EditText fieldinfo = (EditText)findViewById(dynamic_fieldinfo + i);
				field = new Field(fieldname.getText().toString(), fieldinfo.getText().toString());
				db.getDataList().get(DbIndex).getFieldList().add(field);
			}
			FM.saveSQLDB(me,db);
			
		}
		
		private void AddData()
		{
			Data newaddressInfo = new Data(editText_name.getText().toString(), editText_phone.getText().toString());
			for(int i = 1 ; i <= fieldCount; i++)
			{
				EditText fieldname = (EditText)findViewById(dynamic_fieldname + i);
				EditText fieldinfo = (EditText)findViewById(dynamic_fieldinfo + i);
				Field field = new Field(fieldname.getText().toString(), fieldinfo.getText().toString());
				db.getDataList().get(DbIndex).getFieldList().add(field);
			}
			db.getDataList().add(newaddressInfo);
			FM.saveSQLDB(me,db);
		}
		
		private void AddField()
		{
			fieldCount++;
			
			field = new RelativeLayout(me);
			
			EditText field_name = new EditText(me);
			EditText field_info = new EditText(me);
			
			field_name.setId(dynamic_fieldname + fieldCount);
			field_name.setLines(1);
			field_name.setSingleLine(true);
			
			field_info.setId(dynamic_fieldinfo + fieldCount);
			field_info.setLines(1);
			field_info.setSingleLine(true);
			
			field.addView(field_name);
			field.addView(field_info);
			
			RelativeLayout.LayoutParams field_name_editText = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			field_name_editText.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			field_name_editText.width = 260;
			
			RelativeLayout.LayoutParams field_info_editText = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			field_info_editText.addRule(RelativeLayout.RIGHT_OF, field_name.getId());
			//field_name_editText.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			//field_name_editText.addRule(RelativeLayout.ALIGN_RIGHT);
			field_info_editText.width = 340;
			
			field_name.setLayoutParams(field_name_editText);
			field_info.setLayoutParams(field_info_editText);
			
			fieldArray.add(field);
			
			dynamiclayout.addView(field);
		}
	}
}
