package com.example.jorjborj.ordrs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UpcomingEventsActivity extends AppCompatActivity {

    public static ArrayList<UpcomingEventObject> eventsArrayList = new ArrayList<>();
    public static EventsAdapter adapter;
    DatabaseHelper mDataBaseHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events);

        mDataBaseHelper = new DatabaseHelper(getBaseContext());
        mDataBaseHelper.getWritableDatabase();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView lv = (ListView) findViewById(R.id.eventslv);

        eventsArrayList = new ArrayList<>();

        initializeData();
        adapter = new EventsAdapter(UpcomingEventsActivity.this,R.layout.upcoming_event_row,eventsArrayList);
        lv.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
    }

    private void initializeData() {
        Cursor data = mDataBaseHelper.getEventData();

        if(data != null){
            if(data.moveToFirst()){
                do {
                    UpcomingEventObject eventsObject = new UpcomingEventObject();
                    eventsObject.setTableNum(Integer.parseInt(data.getString(1)));
                    eventsObject.setContactName(data.getString(2));
                    eventsObject.setPhoneNum(Integer.parseInt(data.getString(3)));
                    eventsObject.setNumOfPeople(Integer.parseInt(data.getString(4)));
                    eventsObject.setNotes(data.getString(5));
                    eventsObject.setTimeDate(data.getString(6));

                    eventsArrayList.add(eventsObject);
                } while (data.moveToNext());
            }
        }
    }


    public class EventsAdapter extends ArrayAdapter<UpcomingEventObject> {

        private final Context context;
        private final ArrayList<UpcomingEventObject> modelsArrayList;
        private int ctr;
        private int resource;

        public EventsAdapter(Context context, int resource, ArrayList<UpcomingEventObject> modelsArrayList) {
            super(context, resource, modelsArrayList);
            this.context = context;
            this.modelsArrayList = modelsArrayList;
            this.resource = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);
            View customView = inflater.inflate(R.layout.upcoming_event_row, parent, false);


            TextView contactName, contactNumber, numberOfPeople, dateTime;

            contactName = (TextView)customView.findViewById(R.id.contactname);
            contactNumber = (TextView)customView.findViewById(R.id.contactnumber);
            numberOfPeople = (TextView)customView.findViewById(R.id.numberofpeople);
            dateTime = (TextView)customView.findViewById(R.id.dateTime);

            contactName.setText(modelsArrayList.get(position).getContactName());
            contactNumber.setText("0"+Integer.toString(modelsArrayList.get(position).getPhoneNum()));
            numberOfPeople.setText(Integer.toString(modelsArrayList.get(position).getNumOfPeople()));
            dateTime.setText(modelsArrayList.get(position).getTimeDate().toString());

            customView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                                         UpcomingEventObject o = eventsArrayList.get(position);
                        AlertDialog alertDialog = new AlertDialog.Builder(UpcomingEventsActivity.this).create();
                        alertDialog.setTitle("Notes for event");
                        alertDialog.setMessage(o.getNotes());
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                    }
            });

            customView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    PopupMenu popup = new PopupMenu(getContext(), v);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater()
                            .inflate(R.menu.notes_settings_menu, popup.getMenu());

                    UpcomingEventObject o = eventsArrayList.get(position);
                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            UpcomingEventObject o = eventsArrayList.get(position);
                            if(item.getTitle().toString().equals("Delete")){
                                int rowId = mDataBaseHelper.getEventId(o.getContactName(),o.getPhoneNum(),o.getNumOfPeople(),o.getTimeDate());
                                mDataBaseHelper.deleteEvent(rowId);
                                Intent i = new Intent(getBaseContext(),UpcomingEventsActivity.class);
                                startActivity(i);
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            }

                            if(item.getTitle().toString().equals("Edit")){
//                            int rowId = mDataBaseHelper.getEventId(o.getContactName(),o.getPhoneNum(),o.getNumOfPeople(),o.getTimeDate());
//                            mDataBaseHelper.updateEvent(rowId,eventsArrayList.get(position).getTableNum(),eventsArrayList.get(position).getContactName(),eventsArrayList.get(position).getPhoneNum(),
//                                    eventsArrayList.get(position).getNumOfPeople(),eventsArrayList.get(position).getNotes(),eventsArrayList.get(position).getTimeDate());
//                            Intent i = new Intent(getBaseContext(),UpcomingEventsActivity.class);
//                            startActivity(i);
//                            Toast.makeText(context, "Edited", Toast.LENGTH_SHORT).show();

                                Toast.makeText(UpcomingEventsActivity.this, "Todo - edit", Toast.LENGTH_SHORT).show();

                            }

                            return true;
                        }
                    });

                    popup.show(); //showing popup menu

                    return false;
                }
            });
            return customView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addeventmenu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tableorder:
                Intent i = new Intent(UpcomingEventsActivity.this,OrderTableActivity.class);
                startActivity(i);
                finish();
                break;
            case android.R.id.home: onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(UpcomingEventsActivity.this,PickOptionActivity.class);
        startActivity(i);
        finish();
    }
}
