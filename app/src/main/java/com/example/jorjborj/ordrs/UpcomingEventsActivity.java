package com.example.jorjborj.ordrs;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class UpcomingEventsActivity extends AppCompatActivity {

    ArrayList<UpcomingEventObject> events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events);

        ListView lv = (ListView) findViewById(R.id.eventslv);

        events = new ArrayList<>();
        initializeDummyEvents();

        lv.setAdapter(new EventsAdapter(this,R.layout.activity_upcoming_events,events));


    }

    private void initializeDummyEvents() {

        UpcomingEventObject event1 = new UpcomingEventObject(1,"George",545983177,10,"BlaBla", Calendar.getInstance().getTime());
        events.add(event1);
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

            contactName = (TextView)findViewById(R.id.contantname);
            contactNumber = (TextView)findViewById(R.id.contantnumber);
            numberOfPeople = (TextView)findViewById(R.id.numberofpeople);
            dateTime = (TextView)findViewById(R.id.dateTime);

            contactName.setText(modelsArrayList.get(position).getContactName());
            contactNumber.setText(modelsArrayList.get(position).getPhoneNum());
            numberOfPeople.setText(modelsArrayList.get(position).getNumOfPeople());
            dateTime.setText(modelsArrayList.get(position).getTimeDate().toString());

            return customView;
        }
    }
}
