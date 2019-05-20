package com.example.lab4listviewadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lab4listviewadapter.Fragments.FormFragment;
import com.example.lab4listviewadapter.Fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new MainFragment())
                .commit();
    }
}
