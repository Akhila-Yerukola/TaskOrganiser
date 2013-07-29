package com.example.taskorganisernew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class EventDb {

	public static final String KEY_DATE = "_date";
	public static final String KEY_TIME = "_time";
	public static final String KEY_EVENT = "_event";
	public static final String KEY_PRIORITY = "_priority";

	private static final String DATABASE_NAME = "MyEventsDb";
	private static final String DATABASE_TABLE = "MyEvents";
	private static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private  Context ourContext;
	private static SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper  {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(" + KEY_EVENT
					+ " TEXT NOT NULL, " + KEY_DATE + " TEXT, " + KEY_TIME
					+ " TEXT, " + KEY_PRIORITY
					+ " VARCHAR(10));" ); 
			//db.execSQL("create table MyEvents(_event text not null, _date text, _time text, _priority varchar(10));");

			

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			// TODO Auto-generated method stub
			ourDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(ourDatabase);

		}

	}

	public EventDb(Context c) {
		this.ourContext = c;
		ourHelper = new DbHelper(ourContext);
	}
	
	
	public EventDb open() throws SQLException {
		ourDatabase= ourHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		ourHelper.close();
	}


	public long createEntry(String event, String date, String time,
			String priority) {
		// TODO Auto-generated method stub
		
		ContentValues cv = new ContentValues();
		cv.put(KEY_EVENT, event);
		cv.put(KEY_DATE, date);
		cv.put(KEY_TIME, time);
		cv.put(KEY_PRIORITY, priority);
		 return ourDatabase.insert(DATABASE_TABLE, null, cv);
		
	}


//	public String getData() {
	public List getData() {
		// TODO Auto-generated method stub
		List docList = new ArrayList();
		String[] columns = new String[]{ KEY_EVENT, KEY_DATE, KEY_TIME, KEY_PRIORITY};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String result= "";
		System.out.println("no of records: "+c.getCount());
		int iEvent = c.getColumnIndex(KEY_EVENT);
		int iDate = c.getColumnIndex(KEY_DATE);
		int iTime = c.getColumnIndex(KEY_TIME);
		int iP = c.getColumnIndex(KEY_PRIORITY);
		
		
		for(c.moveToFirst(); !c.isAfterLast();c.moveToNext()){
//			result = result + c.getString(iEvent) + " " + c.getString(iDate) + " " + c.getString(iTime) + " " + c.getString(iP) + "\n" ;
			HashMap temp = new HashMap();
			temp.put("Event",c.getString(iEvent) );
			temp.put("Date",c.getString(iDate) );
			temp.put("Time",c.getString(iTime) );
			temp.put("Priority",c.getString(iP) );
			docList.add(temp);
			
			
		}
		return docList;
	}


	public void deleteEvent(String hMap) {
		// TODO Auto-generated method stub
		ourDatabase.delete(DATABASE_TABLE,"_event='"+hMap+"'",null);
		
		
		
	}
}

