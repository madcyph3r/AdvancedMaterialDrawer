package de.madcyph3r.example;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class SecondActivity extends ActionBarActivity {

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.secondactivity);

    }
}