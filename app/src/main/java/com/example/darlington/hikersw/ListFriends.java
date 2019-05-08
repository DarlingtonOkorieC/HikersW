package com.example.darlington.hikersw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class ListFriends extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_friends);

        Toast.makeText(this, getIntent().getStringExtra("name"), Toast.LENGTH_SHORT).show();
        

    }
}
