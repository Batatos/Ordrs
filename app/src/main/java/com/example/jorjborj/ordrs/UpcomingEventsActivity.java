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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView lv = (ListView) findViewById(R.id.eventslv);

        events = new ArrayList<>();
        initializeDummyEvents();

        lv.setAdapter(new EventsAdapter(UpcomingEventsActivity.this,R.layout.upcoming_event_row,events));


    }

    private void initializeDummyEvents() {

        UpcomingEventObject event1 = new UpcomingEventObject(1,"George Aoun",545983177,12,"BlaBla", Calendar.getInstance().getTime());
        UpcomingEventObject event2 = new UpcomingEventObject(1,"Ahed Istaiteh",545922731,10,"BlaBla", Calendar.getInstance().getTime());
        UpcomingEventObject event3 = new UpcomingEventObject(1,"Areej Salameh",543010759,2,"BlaBla", Calendar.getInstance().getTime());
        UpcomingEventObject event4 = new UpcomingEventObject(1,"Luna Zreik",505452311,25,"BlaBla", Calendar.getInstance().getTime());
        UpcomingEventObject event5 = new UpcomingEventObject(1,"David Cohen",545123327,8,"BlaBla", Calendar.getInstance().getTime());
        UpcomingEventObject event6 = new UpcomingEventObject(1,"Avi Harari",545922477,30,"BlaBla", Calendar.getInstance().getTime());
        UpcomingEventObject event7 = new UpcomingEventObject(1,"Roey Levi",525544111,2,"BlaBla", Calendar.getInstance().getTime());
        UpcomingEventObject event8 = new UpcomingEventObject(1,"Omer Avner",505343131,40,"BlaBla", Calendar.getInstance().getTime());
        UpcomingEventObject event9 = new UpcomingEventObject(1,"Firas Odeh",525019921,16,"BlaBla", Calendar.getInstance().getTime());
        UpcomingEventObject event10 = new UpcomingEventObject(1,"Charbel Aoun",545987161,2,"BlaBla", Calendar.getInstance().getTime());

        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);
        events.add(event6);
        events.add(event7);
        events.add(event8);
        events.add(event9);
        events.add(event10);

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
            contactNumber.setText(Integer.toString(modelsArrayList.get(position).getPhoneNum()));
            numberOfPeople.setText(Integer.toString(modelsArrayList.get(position).getNumOfPeople()));
            dateTime.setText(modelsArrayList.get(position).getTimeDate().toString());

            return customView;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
