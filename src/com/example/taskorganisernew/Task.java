package com.example.taskorganisernew;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TabHost;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class Task extends Activity implements OnClickListener,
		OnItemSelectedListener {

	Button onSave, cancel;
	EditText eventDetails;
	RadioGroup priorityGroup;
	String priority, date, time, event;

	Spinner spinner;
	String priorities[] = { "High", "Medium", "Low" };
	EventDb entry;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_layout);
		TimePicker tpTime = (TimePicker) findViewById(R.id.tp_time);
		int hour = tpTime.getCurrentHour();
		int minute = tpTime.getCurrentMinute();
		date = CalendarView.date;
		time = hour + ":" + minute + ":00";
		onSave = (Button) findViewById(R.id.bSave);
		cancel = (Button) findViewById(R.id.bCancel);
		eventDetails = (EditText) findViewById(R.id.etEvent);
		entry = new EventDb(Task.this);
		onSave.setOnClickListener(this);
		cancel.setOnClickListener(this);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, priorities);
		spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setOnItemSelectedListener(this);
		spinner.setAdapter(adapter);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent cal = new Intent(this, CalendarView.class);
		switch (v.getId()) {

		case R.id.bSave:
			boolean work = true;
			try {
				event = eventDetails.getText().toString();
				eventDetails.setHint("Enter your event details");

				entry.open();
				entry.createEntry(event, date, time, priority);
				entry.close();
			} catch (Exception e) {
				work = false;
				Dialog d = new Dialog(this);
				d.setTitle("Event Addition");
				TextView tv = new TextView(this);
				tv.setText("\n \n Fail");
				d.setContentView(tv);
				d.show();
			}

			Toast.makeText(this, "Event Added!", Toast.LENGTH_SHORT).show();
			startActivity(cal);
			break;
		case R.id.bCancel:
			Toast.makeText(this, "Event Addition cancelled!", Toast.LENGTH_SHORT).show();
			startActivity(cal);
			break;
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

		int s = spinner.getSelectedItemPosition();
		switch (s) {
		case 0:
			priority = "High";
			break;
		case 1:
			priority = "Medium";
			break;
		case 2:
			priority = "Low";
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		priority = "High";

	}

	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");

	}

}
