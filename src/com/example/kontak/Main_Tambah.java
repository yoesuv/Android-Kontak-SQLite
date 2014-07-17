package com.example.kontak;

import com.example.database.Db_SQLite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main_Tambah extends Activity implements OnClickListener {
	
	private Db_SQLite db;
	
	private EditText t1,t2;
	private Button bt1,bt2,bt3;
	
	private String nama,nomor;

	protected void onCreate(Bundle b){
		super.onCreate(b);
		setContentView(R.layout.activity_tambah);
		
		db = new Db_SQLite(this);
		db.open();		
		
		t1 = (EditText) findViewById(R.id.txt_name);
		t2 = (EditText) findViewById(R.id.txt_number);
		bt1 = (Button) findViewById(R.id.btnSave);
		bt2 = (Button) findViewById(R.id.btnBack);
		bt3 = (Button) findViewById(R.id.btnView_tambah);
		
		bt1.setOnClickListener(this);
		bt2.setOnClickListener(this);
		bt3.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		int id = v.getId();
		
		if(id == R.id.btnSave){
			
			nama = t1.getText().toString();
			nomor = t2.getText().toString();
			
			if(nama.equals("")){
				Toast.makeText(getBaseContext(), "Nama Kosong", Toast.LENGTH_SHORT).show();
				return;
			}
			
			if(nomor.equals("")){
				Toast.makeText(getBaseContext(), "Nomor Kosong", Toast.LENGTH_SHORT).show();
				return;
			}
			
			db.insertData(nama, nomor);
			
			Toast.makeText(getBaseContext(), "Kontak Baru Tersimpan", Toast.LENGTH_SHORT).show();
			
			t1.setText("");
			t2.setText("");
			
		}else if(id == R.id.btnView_tambah){
			
			Intent i = new Intent(v.getContext(), Main_view.class);
			startActivityForResult(i, 0);
			Main_Tambah.this.finish();
			
		}else{
			
			Intent i = new Intent(v.getContext(), MainActivity.class);
			startActivityForResult(i, 0);
			Main_Tambah.this.finish();
		}
	}

}
