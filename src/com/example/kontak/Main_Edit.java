package com.example.kontak;

import com.example.database.Db_SQLite;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main_Edit extends Activity implements OnClickListener {

	private Db_SQLite db;
	
	private EditText t1,t2;
	private Button bt1,bt2;
	
	private int id;
	
	protected void onCreate(Bundle b){
		super.onCreate(b);
		setContentView(R.layout.activity_edit);
		
		db = new Db_SQLite(this);
		db.open();
		
		id = Main_view.id_pos;
		
		t1 = (EditText) findViewById(R.id.txt_name_edit);
		t2 = (EditText) findViewById(R.id.txt_nomber_edit);
		bt1 = (Button) findViewById(R.id.btnSave_edit);
		bt2 = (Button) findViewById(R.id.btnView_edit);
		
		bt1.setOnClickListener(this);
		bt2.setOnClickListener(this);
		
		tampilData();
	}
	
	private void tampilData(){
		Cursor csr = db.getSingleContact(id);
		t1.setText(csr.getString(csr.getColumnIndexOrThrow(Db_SQLite.nama)));
		t2.setText(csr.getString(csr.getColumnIndexOrThrow(Db_SQLite.nomor)));
	}
	
	@Override
	public void onClick(View v) {
		
		int id = v.getId();
		if(id == R.id.btnSave_edit){
			
			db.updateData(t1.getText().toString(), t2.getText().toString(), this.id);
			Toast.makeText(getBaseContext(), "Succesfully Updated...", Toast.LENGTH_LONG).show();
			tampilData();
			
		}else{
			
			Intent i = new Intent(v.getContext(), Main_view.class);
			startActivityForResult(i, 0);
			Main_Edit.this.finish();
		}
		
	}

}
