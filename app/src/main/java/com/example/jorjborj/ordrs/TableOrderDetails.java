package com.example.jorjborj.ordrs;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.jorjborj.ordrs.R.id.contactnumber;
import static com.example.jorjborj.ordrs.R.id.events;
import static com.example.jorjborj.ordrs.R.id.start;

/**
 * Created by jorjborj on 4/24/2018.
 */

public class TableOrderDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    int day,month,year,hour,minute;
    int dayFinal,monthFinal,yearFinal,hourFinal,minuteFinal;
    EditText contactName,contactNumber,numberOfPeople,notes;
    TextView date,tblnum;
    ImageButton pickDate;
    Button confirm,cancel;
    String tableNumber;
    DatabaseHelper dataBaseHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_order_details);


        tableNumber = getIntent().getStringExtra("order_table");
        getSupportActionBar().setTitle("Table Order Details");

        dataBaseHelper = new DatabaseHelper(this);

        contactName = (EditText)findViewById(R.id.contantname);
        contactNumber = (EditText)findViewById(R.id.contantnumber);
        numberOfPeople = (EditText)findViewById(R.id.numberofpeople);
        notes = (EditText)findViewById(R.id.notes);
        date = (TextView) findViewById(R.id.date);
        pickDate = (ImageButton)findViewById(R.id.pickdate);
        tblnum = (TextView)findViewById(R.id.table_number);
        confirm = (Button)findViewById(R.id.confirm);
        cancel = (Button)findViewById(R.id.cancel);
        tblnum.setText(tableNumber);


        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TableOrderDetails.this,TableOrderDetails.this,year,month,day);
                datePickerDialog.show();

            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date theDate = Calendar.getInstance().getTime();
                String dateString = date.getText().toString();


                AddData(Integer.parseInt(tableNumber),contactName.getText().toString(),Integer.parseInt(contactNumber.getText().toString())
                        ,Integer.parseInt(numberOfPeople.getText().toString()),notes.getText().toString(),dateString);
                Intent i = new Intent(getApplicationContext(),UpcomingEventsActivity.class);

                startActivity(i);
            }

            private void AddData(int i, String s, int i1, int i2, String s1, String theDate) {

                boolean insertData = dataBaseHelper.insertEvent(i,s,i1,i2,s1,theDate);
                if(insertData){
                    Toast.makeText(getBaseContext(),"Data added", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getBaseContext(),"Failed to add data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelAlert();
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

        TimePickerDialog timePickerDialog = new TimePickerDialog(TableOrderDetails.this,TableOrderDetails.this,hour,minute,true);
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
            date.setText(time1);
            Toast.makeText(getBaseContext(), "Table #" + getIntent().getStringExtra("order_table")+ "- Date&Time: " + dayFinal + "/" + monthFinal + "/" + yearFinal + ", " + hourFinal + ":" + minutes + ".", Toast.LENGTH_SHORT).show();
        } else {
            String time = dayFinal + "/" + monthFinal + "/" + yearFinal + " " + hourFinal + ":" + minuteFinal + "";
            Toast.makeText(getBaseContext(), "Table #"+ getIntent().getStringExtra("order_table") + "- Date&Time: " + dayFinal + "/" + monthFinal + "/" + yearFinal + ", " + hourFinal + ":" + minuteFinal + ".", Toast.LENGTH_SHORT).show();
            date.setText(time);

        }

    }


    private void showCancelAlert() {

        final AlertDialog.Builder cancelAlert = new AlertDialog.Builder(TableOrderDetails.this);
        cancelAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(TableOrderDetails.this, PickOptionActivity.class);
                startActivity(i);
                finish();

            }
        });
        cancelAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        cancelAlert.setTitle("Cancel Order");
        cancelAlert.setMessage("Are you sure you want to cancel order on table " + tableNumber + " ?");
        cancelAlert.show();


    }

}
