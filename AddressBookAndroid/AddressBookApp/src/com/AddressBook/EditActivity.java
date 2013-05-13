package com.AddressBook;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
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
	Button btn_call;
	Button btn_sms;
	
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
		DbIndex = -1;
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
		
		dynamiclayout = (LinearLayout)findViewById(R.id.dynamicArea);
		dynamiclayout.setOrientation(LinearLayout.VERTICAL);
		
		btn_call = (Button)findViewById(R.id.button_call);
		btn_call.setOnClickListener(clickLisener);
		
		btn_sms = (Button)findViewById(R.id.button_sms);
		btn_sms.setOnClickListener(clickLisener);
		
		fieldCount = 0; // field 갯수나타내는거 초기화
		fieldArray = new ArrayList<RelativeLayout>();
		
		FM = new FileManager();
		db = FM.makeDB("db.xml");
		
		if(Mode.equalsIgnoreCase("edit"))
		{
			DbIndex = getIntent().getExtras().getInt("Index");
			
			editText_name.setText(db.getDataList().get(DbIndex).getName());
			
			editText_phone.setText(db.getDataList().get(DbIndex).getPhoneNumber());
			
			for(int i = 0; i < db.getDataList().get(DbIndex).getFieldList().size();i++)
			{
				fieldCount++;
				
				field = new RelativeLayout(me);
				
				EditText field_name = new EditText(me);
				EditText field_info = new EditText(me);
				
				field_name.setId(dynamic_fieldname + fieldCount);
				field_name.setText(db.getDataList().get(DbIndex).getFieldList().get(i).getFieldName());
				field_info.setId(dynamic_fieldinfo + fieldCount);
				field_info.setText(db.getDataList().get(DbIndex).getFieldList().get(i).getFieldData());
				
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
				
				Toast.makeText(EditActivity.this, "save btn", Toast.LENGTH_SHORT).show();
				if(DbIndex == -1)//add 임
				{
					AddData();
				}
				else
				{
					EditData();
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
				Toast.makeText(EditActivity.this, "call btn", Toast.LENGTH_SHORT).show();
				Intent callintent = new Intent(Intent.ACTION_CALL);
				callintent.setData(Uri.parse("tel:" + db.getDataList().get(DbIndex).getPhoneNumber())); //
				startActivity(callintent); //editText_phone.getText().toString()
				break;
				
			case R.id.button_sms:
				
				try {
				     Intent sendIntent = new Intent(Intent.ACTION_VIEW);
				     sendIntent.putExtra("address", editText_phone.getText().toString());
				     sendIntent.putExtra("sms_body", "안녕하세요 조교님");
				     sendIntent.setType("vnd.android-dir/mms-sms");
				     startActivity(sendIntent);

				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
						"SMS faild, please try again later!",
						Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}


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
			FM.saveDB(db, "db.xml");//
			me.finish();
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
			FM.saveDB(db, "db.xml");
			me.finish();
		}
		
		private void AddField()
		{
			fieldCount++;
			
			field = new RelativeLayout(me);
			
			EditText field_name = new EditText(me);
			EditText field_info = new EditText(me);
			
			field_name.setId(dynamic_fieldname + fieldCount);
			field_info.setId(dynamic_fieldinfo + fieldCount);
			
			field.addView(field_name);
			field.addView(field_info);
			//field.addView(btn_delete_field);
			
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
}
