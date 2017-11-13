package com.example.zayd.galgeleg20;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{



    public  static Galgelogik galgelogik = new Galgelogik();

    private TextView info, ordDR;
    private Button spilKnap, Genstart;
    private EditText text;
    private ImageView img;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info = (TextView) findViewById(R.id.info);
        img = (ImageView) findViewById(R.id.img);
        text = (EditText) findViewById(R.id.editText);
        spilKnap = (Button) findViewById(R.id.spilKnap);
        Genstart = (Button) findViewById(R.id.genstart);
        ordDR = (TextView) findViewById(R.id.textInfoDR);
        listView = (ListView) findViewById(R.id.listViewDR);



        ordDR.setText("fra DR´s server....");

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    galgelogik.hentOrdFraDr();
                    return "korrekt hentet fra DR´s server";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "blev ikke hentet korrekt" + e;
                }

            }

            @Override
            protected void onPostExecute(Object resultat) {
                ordDR.setText("resultat: \n " + resultat);

            }
        }.execute();

        info.setText("Velkommen til mit spændende spil" + "\n Du skal gætte dette ord: " + galgelogik.getSynligtOrd() +
                "\n Skrive et bogstav herunder og tryk spil.\n");
        String velkomst = getIntent().getStringExtra("velkomst");
        if (velkomst != null)
            info.append(velkomst);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, galgelogik.muligeOrd);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);

        text.setHint("Skriv et bogstav her!!:");
        spilKnap.setText("spil");
        spilKnap.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_play, 0, 0, 0);
        Genstart.setText("Genstart");

        spilKnap.setOnClickListener(this);
        Genstart.setOnClickListener(this);

        spilKnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("State", String.valueOf(galgelogik.getAntalForkerteBogstaver()));


                //Toast.makeText(MainActivity.this, galgelogik.getAntalForkerteBogstaver(), Toast.LENGTH_SHORT);


                galgelogik.gætBogstav(String.valueOf(text.getText()));

                String bogstav = text.getText().toString();
                if (bogstav.length() != 1)

                {
                    text.setError("Skriv et bogstav");
                    return;
                }

                switch (galgelogik.getAntalForkerteBogstaver()) {
                    case 1:
                        img.setImageResource(R.drawable.forkert1);
                        break;

                    case 2:
                        img.setImageResource(R.drawable.forkert2);
                        break;

                    case 3:
                        img.setImageResource(R.drawable.forkert3);
                        break;

                    case 4:
                        img.setImageResource(R.drawable.forkert4);
                        break;


                    case 5:
                        img.setImageResource(R.drawable.forkert5);
                        break;
                    case 6:
                        img.setImageResource(R.drawable.forkert6);
                        break;
                }
                text.setText("");
                text.setError(null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    spilKnap.animate().rotationBy(2* 360).setInterpolator(new DecelerateInterpolator());


                }
                opdaterSkærm();

            }
       });
}
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        opdaterSkærm();


    }

    @Override
    public void onClick(View v) {
        ordDR.setVisibility(ordDR.GONE);
        listView.setVisibility(listView.GONE);

        if (v == Genstart) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

        if (v == spilKnap) {

            String bogstav = text.getText().toString();
            if (bogstav.length() != 1) {
                text.setError("Skriv et bogstav");
                return;
            }
            galgelogik.getAntalForkerteBogstaver();
            switch (galgelogik.getAntalForkerteBogstaver()) {

                case 1:
                    img.setImageResource(R.drawable.forkert1);
                    break;

                case 2:
                    img.setImageResource(R.drawable.forkert2);
                    break;

                case 3:
                    img.setImageResource(R.drawable.forkert3);
                    break;

                case 4:
                    img.setImageResource(R.drawable.forkert4);
                    break;


                case 5:
                    img.setImageResource(R.drawable.forkert5);
                    break;
                case 6:
                    img.setImageResource(R.drawable.forkert6);
                    break;

            }
            text.setText("");
            text.setError(null);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                spilKnap.animate().rotationBy(2* 360).setInterpolator(new DecelerateInterpolator());
            }
            opdaterSkærm();
        }
    }
    private void opdaterSkærm() {
        info.setText("Gæt ordet: "+ galgelogik.getSynligtOrd());
        info.append("\n\n Du har "+ galgelogik.getAntalForkerteBogstaver() + "forkerte" + galgelogik.getBrugteBogstaver());


        if (galgelogik.erSpilletVundet()){
            info.append("\n Du har vundet ");
            Intent intent = new Intent(this, Finito.class);
            intent.putExtra("Vundet","Du har vundet");
            startActivity(intent);
            galgelogik.nulstil();

        }

        if (galgelogik.erSpilletTabt()){
            info.setText("Du har tabt prøv igen, ordet var: "+ galgelogik.getOrdet());
            Intent intent = new Intent(this, Finito.class);
            intent.putExtra("Vundet","Du har vundet");
            startActivity(intent);
        }












    }



}

