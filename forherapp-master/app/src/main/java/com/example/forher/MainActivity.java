package com.example.forher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    public CardView cardReq;
    public CardView cardHelp;
    public CardView cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardHelp=(CardView) findViewById(R.id.cHelp);
        cardReq=(CardView) findViewById(R.id.cReq);
        cardLogout=(CardView) findViewById(R.id.cLogout);

        cardReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),add.class));
            }
        });
        cardHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),search.class));
            }
        });
        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut(); //logout
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
    @Override
    public void onBackPressed()
    {

    }

}