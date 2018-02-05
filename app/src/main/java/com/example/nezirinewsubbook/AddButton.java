package com.example.nezirinewsubbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddButton extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_button);
    }

    public  String name;
    public String date;
    public String charge;
    public String comment;
    boolean buttonState = false;

    //updates the state of the button that confirms the selected date
    public void trueButton(View view) {
        if (buttonState == true) {
            this.buttonState = false;
        } else {
            this.buttonState = true;
        }

        //Create a datePicker view and set its format
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear() - 1900;


        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(year, month, day);
        this.date = dateFormatter.format(d);

    }

    public void theEndButton(View view) {

        //Converts the user input into strings and prompts the user to fill out the required fields
        this.name = ((EditText) findViewById(R.id.name)).getText().toString();
        this.charge = ((EditText) findViewById(R.id.charge)).getText().toString();
        this.comment = ((EditText) findViewById(R.id.comment)).getText().toString();

        if ((this.name.equals("")) | (!this.buttonState) | (this.charge.equals(""))) {
            Toast.makeText(getApplicationContext(), "Please enter some values in the * fields", Toast.LENGTH_SHORT).show();
        } else {
            intentMethod();

        }
    }

    //Send the data received from the user to the MainActivity and finish the acitivity
    public void intentMethod(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("name", this.name);
        returnIntent.putExtra("date", this.date);
        returnIntent.putExtra("charge", this.charge);
        returnIntent.putExtra("comment", this.comment);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}
