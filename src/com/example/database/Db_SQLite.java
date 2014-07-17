package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Db_SQLite {
	
	private static final String db_name= "Db_Kontak";
	private static final int db_ver = 1;
	
	public static final String nm_tabel = "tb_kontak";
	public static final String id = "_id";
	public static final String nama = "nama";
	public static final String nomor = "nomor";
	
	private static final String TAG = "ContactDBAdapter";
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	
	private final static String db_create = "CREATE TABLE tb_kontak(_id INTEGER PRIMARY KEY,nama VARCHAR NOT NULL,nomor VARCHAR NOT NULL);";
	
	private final Context context;
	
	private static class DatabaseHelper extends SQLiteOpenHelper{
		

		public DatabaseHelper(Context context) {
			super(context, db_name,null, db_ver);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(db_create);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d(TAG, "Upgrade Database");
			db.execSQL("DROP TABLE IF EXISTS "+nm_tabel);
			onCreate(db);
		}
		
	}
	
	public Db_SQLite(Context context){
		this.context = context;
	}
	
	public Db_SQLite open() throws SQLException{
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		dbHelper.close();
	}
	
	// INSERT DATA BARU
	public void insertData(String b,String c){
		ContentValues cv = new ContentValues();
		cv.put(nama, b);
		cv.put(nomor, c);
		db.insert(nm_tabel, null, cv);
	}
	
	//UPDATE DATA
	public void updateData(String a,String b,int idnya){
		ContentValues cv = new ContentValues();
		cv.put(nama, a);
		cv.put(nomor, b);		
		db.update(nm_tabel, cv, id + "="+idnya, null);
	}
	
	//DELETE DATA
	public boolean deleteData(int idnya){
		return db.delete(nm_tabel, id+"="+idnya, null)>0;
	}
	
	
	public Cursor getAllContact(){
		return db.query(nm_tabel,new String[]{id,nama,nomor},null,null,null,null,null);
	}
	
	public Cursor getSingleContact(int idd){
		
		Cursor cur = db.query(nm_tabel, new String[] {id,nama,nomor}, id+ "="+ idd, null, null, null, null);
		if(cur!=null){
			cur.moveToFirst();
		}
		return cur;
	}
	

}
