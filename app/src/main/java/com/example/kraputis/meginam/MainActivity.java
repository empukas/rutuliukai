package com.example.kraputis.meginam;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Random;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity implements RegistracijaFragment.OnFragmentInteractionListener {




    public void onFragmentInteraction(Uri uri){
    }
    private ProgressDialog progress;
    private String NICK;
    Locale myLocale;
    String m;
    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.PagrindinisLangas, new PradziaFragment());
        ft.commit();

        SqLite_database db = new SqLite_database(this);
        String zaidejas = db.getZAIDEJAS();
        if (zaidejas.equals("null")){
            Registracija();
        }else NICK = zaidejas;


        m = getRandomString(6).toString();
        Log.i("ziurim", "slapt : " +m);
       // m = getResources().getConfiguration().locale.toString();


        //Log.i("ziurim", "locale : " +getResources().getConfiguration().locale.toString());

    }



    public void Registracija(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.PagrindinisLangas, new RegistracijaFragment());
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    public void prisijungimas(View v){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.PagrindinisLangas, new PrisijungtiFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void prisijungti(View v){
        EditText slapyv = (EditText)findViewById(R.id.Slapyvardis_field);
        EditText slaptaz = (EditText)findViewById(R.id.Slaptazodis_field);
        String slaptText = slapyv.getText().toString();
        String slapatzText = slaptaz.getText().toString();
        slapatzText = encrypt(slapatzText,"zilvinas");

        if(slapatzText.equals("") || slaptText.equals("")){
            Toast.makeText(getApplicationContext(), "Kažkuris laukas tuščias.",
                    Toast.LENGTH_LONG).show();
        } else {

            progress = ProgressDialog.show(this, "Palaukite", "uztruks pora sec", true);

            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("NICKAS", slaptText);
            params.put("SLAPTAZODIS", slapatzText);

            client.post("http://www.ionic.96.lt/prisijungimas.php", params, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String str) {
                            // called when response HTTP status is "200 OK"
                            Log.i("atsakymas", str);
                            try {
                                JSONObject js = new JSONObject(str);
                                if (!(js.getString("DONE").equals("null")))
                                    AtgalIsRegistracijos(js.getString("DONE"));

                                else {
                                    progress.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("Suvesti blogi duomenys")
                                            .setCancelable(false)
                                            .setTitle("Uuuuups")
                                            .setPositiveButton("Koreguoti", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {

                        }
                    }
            );
        }

    }

    public void Registruotis(View v){
       EditText edit = (EditText)findViewById(R.id.Slapyvardis_field);

        progress = ProgressDialog.show(this, "Palaukite", "uztruks pora sec", true);

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("NICKAS", edit.getText().toString());
        params.put("RAKTAS", RegistracijaFragment.raktas);
        params.put("SLAPTAZODIS", RegistracijaFragment.slaptazodis);

        client.post("http://www.ionic.96.lt/registracija.php", params, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String str) {
                        // called when response HTTP status is "200 OK"
                        Log.i("atsakymas",str);
                        try {
                            JSONObject js = new JSONObject(str);
                            if (!(js.getString("DONE").equals("null")))
                               AtgalIsRegistracijos(js.getString("DONE"));

                            else{
                                progress.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("TOKS ZAIDEJAS JAU YRA!")
                                        .setCancelable(false)
                                        .setTitle("Uuuuups")
                                        .setPositiveButton("Koreguoti", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {

                    }
                }
        );

    }

    public void AtgalIsRegistracijos(String NICKAS){
        SqLite_database db = new SqLite_database(this);
        db.updateZAIDEJAS(NICKAS);
        NICK = NICKAS;
        progress.dismiss();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.PagrindinisLangas, new PradziaFragment());
        transaction.commit();
    }


    public void Zaisti(View v){
        Intent Zaidimas = new Intent(this, TheGameActivity.class);
        Zaidimas.putExtra("NICKAS", NICK);
        startActivity(Zaidimas);
    }

    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    private void setLocal(String language){
        myLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration cfg = res.getConfiguration();
        cfg.locale = myLocale;
        res.updateConfiguration(cfg,dm);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }

    public void eng(View v){
            setLocal("en");
    }

    public void ltu(View v){
        setLocal("lt");
    }

    public void go(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.PagrindinisLangas, new PradziaFragment());
        ft.commit();
    }


    public void Taskai(View v){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        TaskaiFragment fragmentDemo = TaskaiFragment.newInstance(NICK);
        transaction.replace(R.id.PagrindinisLangas, fragmentDemo);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void Kontaktai(View v){
        Intent map = new Intent(this, MapsActivity.class);
        startActivity(map);
    }

    public void Instrukcija(View v){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.PagrindinisLangas, new InstrukcijaFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    static String encrypt(String text, final String key) {
        String res = "";
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < ' ' || c > 'z') continue;
            res += (char)((c + key.charAt(j) - 2 * ' ') % 91 + ' ');
            j = ++j % key.length();
        }
        return res;
    }


}