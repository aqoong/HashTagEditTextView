package com.aqoong.lib.hashtagedittextview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    HashTagEditTextView testTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testTextView = findViewById(R.id.text);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] test = testTextView.getInsertTag();
                Log.d("Main", test!=null ? test.toString() : "");
            }
        });
    }
}
