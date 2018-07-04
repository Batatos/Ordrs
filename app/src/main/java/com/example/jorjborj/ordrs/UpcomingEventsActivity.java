package com.example.jorjborj.ordrs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class UpcomingEventsActivity extends AppCompatActivity {

    public static ArrayList<UpcomingEventObject> eventsArrayList = new ArrayList<>();
    public static EventsAdapter adapter;
    DatabaseHelper mDataBaseHelper;
    int day,month,year,hour,minute;
    int dayFinal,monthFinal,yearFinal,hourFinal,minuteFinal;
    TextView date,tblnum;

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


            TextView contactName, contactNumber, numberOfPeople, dateTime,tablenum;

            tablenum = (TextView)customView.findViewById(R.id.tablenum);
            contactName = (TextView)customView.findViewById(R.id.contactname);
            contactNumber = (TextView)customView.findViewById(R.id.contactnumber);
            numberOfPeople = (TextView)customView.findViewById(R.id.numberofpeople);
            dateTime = (TextView)customView.findViewById(R.id.dateTime);

            tablenum.setText(modelsArrayList.get(position).getTableNum()+"");
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
                                showCancelAlert(o);
                            }

                            if(item.getTitle().toString().equals("Edit")){

//                            int rowId = mDataBaseHelper.getEventId(o.getContactName(),o.getPhoneNum(),o.getNumOfPeople(),o.getTimeDate());
//                            mDataBaseHelper.updateEvent(rowId,eventsArrayList.get(position).getTableNum(),eventsArrayList.get(position).getContactName(),eventsArrayList.get(position).getPhoneNum(),
//                                    eventsArrayList.get(position).getNumOfPeople(),eventsArrayList.get(position).getNotes(),eventsArrayList.get(position).getTimeDate());
//                            Intent i = new Intent(getBaseContext(),UpcomingEventsActivity.class);
//                            startActivity(i);
//                            Toast.makeText(context, "Edited", Toast.LENGTH_SHORT).show();
                                int rowId = mDataBaseHelper.getEventId(o.getContactName(),o.getPhoneNum(),o.getNumOfPeople(),o.getTimeDate());

                                EditEventDialog eed = new EditEventDialog(UpcomingEventsActivity.this,rowId,o.getTableNum(), o.getContactName(),o.getPhoneNum(),o.getNumOfPeople(),o.getNotes(),o.getTimeDate());
                                    eed.show();
//                                Toast.makeText(UpcomingEventsActivity.this, "Todo - edit", Toast.LENGTH_SHORT).show();

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

    private void showCancelAlert(final UpcomingEventObject o) {

        final AlertDialog.Builder cancelAlert = new AlertDialog.Builder(UpcomingEventsActivity.this);
        cancelAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int rowId = mDataBaseHelper.getEventId(o.getContactName(),o.getPhoneNum(),o.getNumOfPeople(),o.getTimeDate());
                mDataBaseHelper.deleteEvent(rowId);
                Intent i = new Intent(getBaseContext(),UpcomingEventsActivity.class);
                startActivity(i);
                Toast.makeText(getBaseContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        cancelAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        cancelAlert.setTitle("Delete Item");
        cancelAlert.setMessage("Are you sure you want to delete item?");
        cancelAlert.show();


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





    public class EditEventDialog extends Dialog implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

        int tableNum,phoneNum,numOfPeople,rowId;
        String contactName,notes,date;

        int day,month,year,hour,minute;
        int dayFinal,monthFinal,yearFinal,hourFinal,minuteFinal;
        EditText contactName1,contactNumber,numberOfPeople,notes1;
        TextView date1,tblnum;
        ImageButton pickDate;
        Button confirm,cancel;
        String tableNumber;
        DatabaseHelper dataBaseHelper;


        public EditEventDialog(@NonNull Context context) {
            super(context);
        }

        public EditEventDialog(@NonNull Context context, int rowId, int tableNum,String contactName, int phoneNum, int numOfPeople, String notes, String date) {
            super(context);
            this.tableNum=tableNum;
            this.contactName=contactName;
            this.phoneNum=phoneNum;
            this.numOfPeople=numOfPeople;
            this.notes=notes;
            this.date=date;
            this.rowId=rowId;
        }

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.update_table_order_details);



            contactName1 = (EditText)findViewById(R.id.contantname);
            contactNumber = (EditText)findViewById(R.id.contantnumber);
            numberOfPeople = (EditText)findViewById(R.id.numberofpeople);
            notes1 = (EditText)findViewById(R.id.notes);
            date1 = (TextView) findViewById(R.id.date);
            pickDate = (ImageButton)findViewById(R.id.pickdate);
            tblnum = (TextView)findViewById(R.id.table_number);
            confirm = (Button)findViewById(R.id.confirm);
            cancel = (Button)findViewById(R.id.cancel);


            contactName1.setText(contactName);
            contactNumber.setText(Integer.toString(phoneNum));
            numberOfPeople.setText(Integer.toString(numOfPeople));
            notes1.setText(notes);
            date1.setText(date);
            tblnum.setText(Integer.toString(tableNum));


            pickDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Calendar c = Calendar.getInstance();
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(UpcomingEventsActivity.this,UpcomingEventsActivity.EditEventDialog.this,year,month,day);
                    datePickerDialog.show();

                }
            });

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                            mDataBaseHelper.updateEvent(rowId,Integer.parseInt(tblnum.getText().toString()),contactName1.getText().toString(),Integer.parseInt(contactNumber.getText().toString()),Integer.parseInt(numberOfPeople.getText().toString()),notes1.getText().toString(),date1.getText().toString());
                            Intent i = new Intent(getBaseContext(),UpcomingEventsActivity.class);
                            startActivity(i);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(UpcomingEventsActivity.this, "Edited", Toast.LENGTH_SHORT).show();

                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        }


        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yearFinal = year;
            monthFinal = month+1;
            dayFinal = dayOfMonth;

            Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(UpcomingEventsActivity.this,UpcomingEventsActivity.EditEventDialog.this,hour,minute,true);
            timePickerDialog.show();

        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hourFinal = hourOfDay;
            minuteFinal = minute;
            if (minuteFinal < 10) {
                //cosmetics
                String minutes = "0";
                minutes += Integer.toString(minuteFinal);
                String time1 = dayFinal + "/" + monthFinal + "/" + yearFinal + " " + hourFinal + ":" + minutes + "";
                date1.setText(time1);
            } else {
                String time = dayFinal + "/" + monthFinal + "/" + yearFinal + " " + hourFinal + ":" + minuteFinal + "";
                date1.setText(time);

            }

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
