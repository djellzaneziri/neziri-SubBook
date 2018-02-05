package com.example.nezirinewsubbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

//Receives the data that was sent from AddButton and edits it using the new data
public class EditItem extends AddButton {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent newIntent = getIntent();
        EditText name = findViewById(R.id.name);
        DatePicker date = findViewById(R.id.datePicker);
        EditText charge = findViewById(R.id.charge);
        EditText comment = findViewById(R.id.comment);

        String[] parts = newIntent.getStringExtra("date").split("-");
        int year = Integer.valueOf(parts[0]);
        int month= Integer.valueOf(parts[1]) - 1;
        int day = Integer.valueOf(parts[2]);

        name.setText(newIntent.getStringExtra("name"));
        date.updateDate(year, month, day);
        charge.setText(newIntent.getStringExtra("charge"));
        comment.setText(newIntent.getStringExtra("comment"));


    }

    @Override
    //Overrides the intentMethod from AddButton and takes the new data back
    public void intentMethod(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("name", this.name);
        returnIntent.putExtra("date", this.date);
        returnIntent.putExtra("charge", this.charge);
        returnIntent.putExtra("comment", this.comment);
        returnIntent.putExtra("index", getIntent().getIntExtra("index", 0));
        setResult(Activity.RESULT_OK, returnIntent);

        finish();
    }
}

