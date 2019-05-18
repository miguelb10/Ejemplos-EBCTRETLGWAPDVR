package com.app.mg.pruebadewebsocket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.mg.pruebadewebsocket.Activities.ControlActivity;
import com.app.mg.pruebadewebsocket.Activities.WSAndControlActivity;

public class MainActivity extends AppCompatActivity {

    Button btnWSC;
    Button btnC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWSC = findViewById(R.id.btnWSC);
        btnC = findViewById(R.id.btnC);

        btnWSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WSAndControlActivity.class);
                startActivity(intent);
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ControlActivity.class);
                startActivity(intent);
            }
        });
    }
}
