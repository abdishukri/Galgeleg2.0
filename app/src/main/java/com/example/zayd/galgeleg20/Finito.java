package com.example.zayd.galgeleg20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.zayd.galgeleg20.MainActivity.galgelogik;


public class Finito extends AppCompatActivity implements View.OnClickListener {
    private TextView text2;
    private ImageView image;
    private EditText edit2;
    private Button tilbageKnap;

    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private int spilletgammel = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finito);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String b = getIntent().getStringExtra("Vundet");
        int spilletgammel = sharedPreferences.getInt("spillet", 0);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        text2 = (TextView) findViewById(R.id.text2);
        image = (ImageView) findViewById(R.id.img2);
        tilbageKnap = (Button) findViewById(R.id.tilbageKnap);

        tilbageKnap.setOnClickListener(this);





        if (galgelogik.erSpilletTabt()) {
            text2.setText("Du har tabt prøv igen++++, ordet var: " + galgelogik.getOrdet());
            image.setImageResource(R.drawable.unlike);


        } else if (galgelogik.erSpilletVundet()) {

            edit2.setText("hurraaaaa du har Vundet " + galgelogik.getOrdet());
            image.setImageResource(R.drawable.like);
            spilletgammel = sharedPreferences.getInt("Spillet", 1 + spilletgammel);
            spilletgammel++;
            sharedPreferences.edit().putInt("Spillet", spilletgammel).apply();


        }

    }


    @Override
    public void onClick(View v) {


        if (v == tilbageKnap){

            Intent galgeIntent = new Intent(Finito.this, MainActivity.class);
           Finito.this.startActivity(galgeIntent);


        }

    }
}