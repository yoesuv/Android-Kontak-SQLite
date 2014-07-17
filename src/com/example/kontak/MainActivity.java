package com.example.kontak;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
//import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	
	private Button bt1,bt2,bt3,bt4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bt1 = (Button) findViewById(R.id.btnLihat);
		bt2 = (Button) findViewById(R.id.btnTambah);
		bt3 = (Button) findViewById(R.id.btnAbout);
		bt4 = (Button) findViewById(R.id.btnExit);
		
		bt1.setOnClickListener(this);
		bt2.setOnClickListener(this);
		bt3.setOnClickListener(this);
		bt4.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		int id = v.getId();
		if(id == R.id.btnLihat){
			
			Intent it = new Intent(v.getContext(), Main_view.class);
			startActivityForResult(it, 0);
			MainActivity.this.finish();
			
		}else if(id == R.id.btnTambah){
			
			Intent it = new Intent(v.getContext(), Main_Tambah.class);
			startActivityForResult(it, 0);
			MainActivity.this.finish();
			
		}else if(id == R.id.btnAbout){
			
			showAbout();
			
		}else{
			//Exit
			tutup();
		}
	}
	
	private void showAbout(){
		TextView judul = new TextView(this);
		judul.setText("ABOUT");
		judul.setGravity(Gravity.CENTER);
		judul.setTextColor(Color.WHITE);
		judul.setTextSize(25);
		judul.setPadding(0, 5, 0, 5);
		
		LayoutInflater lif = LayoutInflater.from(this);
		View v = lif.inflate(R.layout.activity_about, null);
		Builder b = new AlertDialog.Builder(this);
		b.setView(v).setCustomTitle(judul)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dlg, int arg1) {
				dlg.dismiss();
			}
		});
		b.create().show();
	}
	
	private void tutup(){
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle("EXIT").setCancelable(false)
		.setIcon(R.drawable.contacts)
		.setMessage("Exit From Application ?")
		.setPositiveButton("YES",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				System.exit(0);
			}
		})
		.setNegativeButton("NO",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface di, int arg1) {
				di.dismiss();
			}
		}).show();
	}

}
