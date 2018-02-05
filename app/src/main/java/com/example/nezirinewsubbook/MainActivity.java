package com.example.nezirinewsubbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Subscription> names = new ArrayList<Subscription>();
    ListView listView;
    SubscriptionAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemsAdapter = new SubscriptionAdapter(this, names);

        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        //Create an onClickListener that Edits the text when clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Create a new intent that transfers data from the MainActivity to the EditItem class
                Intent returnIntent = new Intent(MainActivity.this, EditItem.class);
                Subscription sub = names.get(i);

                //Take the data we need from the constructor and send it to the EditItem activity
                returnIntent.putExtra("name", sub.getName());
                returnIntent.putExtra("date", sub.getDate());
                returnIntent.putExtra("charge", sub.getCharge());
                returnIntent.putExtra("comment", sub.getComment());
                returnIntent.putExtra("index", i);

                startActivityForResult(returnIntent, 2);
            }
        });


        //Create a long click listener that deletes the text when held clicked for a period of time
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                //remove the pressed item from the ArrayList and notify the adapter
                names.remove(i);
                itemsAdapter.notifyDataSetChanged();
                updateTotal();
                return true;
            }
        });
        //Call loadlist that loads the data from the file ;
        loadList();
    }

    //Load the data into the app as soon as it starts running
    protected void onStart() {
        super.onStart();
    }

    //Save the data from the app when the app is closed
    protected void onStop() {
        super.onStop();
        saveList();
    }

    @Override

    //Receive all the data back from the activities previously called from Main Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //The data that was sent from the AddButton activity
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                //Gets the data, adds the new subscription to the list, and updates the adapter
                String newName = data.getStringExtra("name");
                String newDate = data.getStringExtra("date");
                String newComment = data.getStringExtra("comment");
                String newCharge = data.getStringExtra("charge");
                names.add(new Subscription(newName, newDate, newCharge, newComment));
                itemsAdapter.notifyDataSetChanged();
                updateTotal();
            }
        }

        //Gets back the data that was sent from the EditItem class
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                String newName = data.getStringExtra("name");
                String newDate = data.getStringExtra("date");
                String newComment = data.getStringExtra("comment");
                String newCharge = data.getStringExtra("charge");

                //Get the position of the current subscription and update it with the new values
                names.set(data.getIntExtra("index", 1), new Subscription(newName, newDate, newCharge, newComment));

                //Notify the adapter
                itemsAdapter.notifyDataSetChanged();
                //Update the total price
                updateTotal();
            }
        }
    }


    //Start the AddButton activity after a Button is pressed
    public void AddSubscription(View view) {
        Intent i = new Intent(this, AddButton.class);
        startActivityForResult(i, 1);
    }


    //Save the list into a file using the Gson library
    public void saveList() {
        try {
            File newfile = new File(getFilesDir(), "fileString");

            if (!newfile.exists()) {
                newfile.createNewFile();
            }
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(newfile));
            writer.write(new Gson().toJson(names));
            writer.close();
        } catch (IOException ioE) {
            Log.e("MainActivity", "Exception occurred", ioE);
        }
    }

    //Load the list from a file
    public void loadList() {
        try {
            File newfile = new File(getFilesDir(), "fileString");
            if (!newfile.exists()) {
                return;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(newfile)));
            StringBuilder stringBuilder = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append('\n' + line);
                line = reader.readLine();
            }
            reader.close();
            ArrayList<Subscription> subscriptions = new Gson().fromJson(stringBuilder.toString(), new TypeToken<List<Subscription>>() {
            }.getType());
            this.names.clear();
            this.names.addAll(subscriptions);
            itemsAdapter.notifyDataSetChanged();
            updateTotal();
        } catch (IOException IO) {
            Log.e("MainActivity", "Exception occurred", IO);
        }
    }

    //Updates the total price of the subscriptions and changes the text of the price
    public void updateTotal() {
        TextView totalTextView = findViewById(R.id.totalCharge);
        double price = 0;
        for (Subscription sub : names) {
            price += Double.valueOf(sub.getCharge());
        }
        totalTextView.setText("Total: " + price);
    }

}
