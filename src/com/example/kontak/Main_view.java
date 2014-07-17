package com.example.kontak;


import com.example.database.Db_SQLite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class Main_view extends Activity implements OnItemClickListener {
	
	public static final String key_ID = "_id";

	private ListView listv;
	private Button btn1;
	
	private Db_SQLite db;
	
	public static int id_pos;
	
	private String no_hp;
	
	protected void onCreate(Bundle b){
		super.onCreate(b);
		setContentView(R.layout.activity_view);
		
		db = new Db_SQLite(this);
		db.open();
		
		listv = (ListView) findViewById(R.id.listView1);
		listv.setOnItemClickListener(this);
		
		registerForContextMenu(listv);
		
		btn1 = (Button) findViewById(R.id.btnBack_view);
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), MainActivity.class);
				startActivityForResult(i, 0);
				Main_view.this.finish();
			}
		});
		
		loadData();
		
	}
	
	private void loadData(){
		Cursor cur = db.getAllContact();
		
		String[] from = new String[]{Db_SQLite.nama,Db_SQLite.id,Db_SQLite.nomor};
		int[] to = new int[]{R.id.txt_listnama,R.id.txt_urut,R.id.txt_listnomor};
		
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.list_adapter, cur, from, to);
		sca.notifyDataSetChanged();
		listv.setAdapter(sca);
	}
	
	public void onCreateContextMenu(ContextMenu menu,View tampil,ContextMenuInfo mnInfo){
		super.onCreateContextMenu(menu, tampil, mnInfo);
		menu.setHeaderTitle("Menu");
		MenuInflater mif = getMenuInflater();
		mif.inflate(R.menu.context_menu, menu);
		
	}
	
	public boolean onContextItemSelected(MenuItem item){		
				
		AdapterContextMenuInfo mnIf = (AdapterContextMenuInfo) item.getMenuInfo();
		
		
		switch(item.getItemId()){
		case R.id.item1:	
			//Send SMS
			id_pos = (int) mnIf.id;
			Cursor csr = db.getSingleContact(id_pos);
			no_hp = csr.getString(csr.getColumnIndexOrThrow(Db_SQLite.nomor));
			
			Intent itt = new Intent("android.intent.action.VIEW");
			Uri data = Uri.parse("sms:"+no_hp);
			itt.setData(data);
			startActivityForResult(itt, 0);
			
		break;
		case R.id.item2:
			//Edit			
			id_pos = (int) mnIf.id;
			Intent it = new Intent(Main_view.this, Main_Edit.class);
			startActivityForResult(it, 0);
			Main_view.this.finish();
			
		break;
		case R.id.item3:
			//Delete
			id_pos = (int) mnIf.id;
			deletee(id_pos);

		break;
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int pos, long id) {
		// List view item on Click
		//Bundle b = new Bundle();
		//b.putLong(key_ID, id);
		//Toast.makeText(getBaseContext(), "Listview Click  "+id+" "+pos, Toast.LENGTH_SHORT).show();
		//db.close(); 
	}
	
	private void deletee(final int z){
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle("DELETE").setCancelable(false)
		.setIcon(R.drawable.contacts)
		.setMessage("Hapus Kontak ?")
		.setPositiveButton("YA", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				db.deleteData(z);
				loadData();
			}
		})
		.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dlg, int arg1) {				
				dlg.dismiss();
			}
		}).show();
		
	}
	
}
