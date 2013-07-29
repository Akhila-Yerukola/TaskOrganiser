package com.example.taskorganisernew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SQLView extends Activity {
	List<HashMap<String, String>> docList;
	EventDb info;
	String hMap = null;
//Bundle savedInstanceState;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agenda_list);
//		savedInstanceState = this.savedInstanceState;
		ListView itemListView = (ListView) findViewById(R.id.listview);
		info = new EventDb(this);
		info.open();
		docList = new ArrayList();
		docList = info.getData();
		System.out.println("list length:" + docList.size());
		info.close();
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, docList,
				R.layout.sqlview, new String[] { "Event", "Date", "Time",
						"Priority" }, new int[] { R.id.doc_event,
						R.id.doc_date, R.id.doc_time, R.id.doc_priority });
		itemListView.setAdapter(simpleAdapter);
		registerForContextMenu((ListView) findViewById(R.id.listview));

		/*
		 * itemListView.setOnItemClickListener(new
		 * AdapterView.OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
		 * position, long arg3) { // TODO Auto-generated method stub
		 * 
		 * String hMap=docList.get(position).get("Event");
		 * //Toast.makeText(getApplicationContext(),
		 * hMap.get("Event")+" event is clicked", Toast.LENGTH_LONG).show();
		 * 
		 * 
		 * }
		 * 
		 * });
		 */
		itemListView
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int position, long arg3) {
						// TODO Auto-generated method stub
						hMap = docList.get(position).get("Event");
						return false;

					}
				});

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.agenda_menu, menu);
		
		
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.iDel:

			AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
			alertBox.setMessage("Are you rearly want to delete");

			alertBox.setPositiveButton("Delete",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface arg0, int arg1) {
							info.open();
							info.deleteEvent(hMap);
							info.close();

							Toast.makeText(getApplicationContext(),
									"Data Deleted Successfully",
									Toast.LENGTH_LONG).show();
							
//							SQLView.this.onCreate(savedInstanceState);
							
							Intent i = new Intent(getBaseContext(), SQLView.class);    
							startActivity(i);

						}
					});

			alertBox.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface arg0, int arg1) {
							Toast.makeText(getApplicationContext(),
									"Deletion Canceled..", Toast.LENGTH_LONG)
									.show();
						}
					});

			alertBox.show();
			
			
			return true;
			// case R.id.i:

			// return true;
		default:
			return super.onContextItemSelected(item);
		}
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent i = new Intent(this,CalendarView.class);
			startActivity(i);
		}
		return super.onKeyDown(keyCode, event);
	}

}
