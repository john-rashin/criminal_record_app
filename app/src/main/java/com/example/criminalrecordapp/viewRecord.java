package com.example.criminalrecordapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class viewRecord extends AppCompatActivity {

    ListView listView;
    SearchView searchView;
    DBHelper dbHelper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Display data
        listView = findViewById(R.id.listview);
        dbHelper = new DBHelper(this);
        ArrayList<DBHelper.CriminalRecord> criminalRecords = dbHelper.getAllCriminalRecords();
        CriminalRecordAdapter adapter = new CriminalRecordAdapter(this, criminalRecords);
        listView.setAdapter(adapter);


       //Search Data from criminal records.
        ArrayList<DBHelper.CriminalRecord> defaultData;
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                    return false;
            }
        });

        //Intent to edit activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the data for the clicked item
                DBHelper.CriminalRecord item = (DBHelper.CriminalRecord) parent.getItemAtPosition(position);


                // Create an Intent to open the edit activity
                Intent intent = new Intent(viewRecord.this, EditActivity.class);

                // Add the specific data to the Intent as extras
                intent.putExtra("id", item.getId());
                intent.putExtra("name", item.getName());
                intent.putExtra("age", item.getAge());
                intent.putExtra("date", item.getDate());
                intent.putExtra("offenseType", item.getOffenseType());
                intent.putExtra("offenseDescription", item.getOffenseDescription());
                intent.putExtra("municipality", item.getMunicipality());
                intent.putExtra("province", item.getProvince());
                intent.putExtra("stationName", item.getStationName());
                intent.putExtra("stationAddress", item.getStationAddress());

                // Start the edit activity
                startActivity(intent);
            }
        });

    }
    static class ViewHolder {
        TextView textId;
        TextView textName;
        TextView textAge;
        TextView textDate;
        TextView textOffenseType;
        TextView textOffenseDescription;
        TextView textMunicipality;
        TextView textProvince;
        TextView textStationName;
        TextView textStationAddress;
    }

}