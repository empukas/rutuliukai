package com.example.kraputis.meginam;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TheGameActivity extends AppCompatActivity {


    ArrayList<Rutuliukas> rutuliukai = new ArrayList<Rutuliukas>();
    ArrayList<Rutuliukas2> kliutis = new ArrayList<Rutuliukas2>();
    Handler handler = new Handler();
    int laikrodis = 0;
    int s = 0;
    int l = -1;
    String NICKAS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_game);

        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
        View st = findViewById(R.id.st);
        st.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                start();
                v.setVisibility(View.GONE);
            }

        });
        View rr = findViewById(R.id.reset);
        rr.setVisibility(View.GONE);
        rr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                res();
            }

        });

        NICKAS = getIntent().getStringExtra("NICKAS");
        TextView vardas = (TextView) TheGameActivity.this.findViewById(R.id.nik);
        vardas.setText(NICKAS);
    }


    public void rutuliukai(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int h = displaymetrics.heightPixels;
        int w = displaymetrics.widthPixels;
        RelativeLayout layout=(RelativeLayout)findViewById(R.id.activity_main);
        for(int i=1;i<=36;i++){
            rutuliukai.add(new Rutuliukas(i,new ImageView(this), i+3, w, h));
        }

    }
    public void kliutis(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int h = displaymetrics.heightPixels;
        int w = displaymetrics.widthPixels;
        RelativeLayout layout=(RelativeLayout)findViewById(R.id.activity_main);
        for(int i=1;i<=36;i++){
            kliutis.add(new Rutuliukas2(i,new ImageView(this), i+10, w, h));
        }

    }
    public void res(){
        s=0;
        l=0;
        TextView levlis1 = (TextView) this.findViewById(R.id.LEVELIS);
        levlis1.setText("LEVEL 1");
        TextView s = (TextView) this.findViewById(R.id.skaitliukas);
        s.setText("Skaitliukas");
        TextView l = (TextView) this.findViewById(R.id.lifes);
        l.setText("0");
        View rr = findViewById(R.id.reset);
        start();
        rr.setVisibility(View.GONE);
    }

    public void start(){
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
        rutuliukai();
        kliutis();
        handler.post(runnableCode);
    }


    // Define the code block to be executed
    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            if(s+15 > l ) {
                final AnimatorSet set3 = new AnimatorSet();
                final TextView lifes = (TextView) TheGameActivity.this.findViewById(R.id.lifes);
                final RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
                ImageView lol;
                ImageView kl;
                if (s < 50) {


                    for (int i = 0; i <= 25; i++) {

                        if (rutuliukai.get(i).fiksuotiLaika() == true) {
                            lol = rutuliukai.get(i).getImage();
                            if (lol.getParent() != null)
                                ((ViewGroup) lol.getParent()).removeView(lol); // <- fix

                            layout.addView(lol);
                            lol.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    layout.removeView(v);
                                    TextView skaitliuks = (TextView)TheGameActivity.this.findViewById(R.id.skaitliukas);
                                    s++;
                                    skaitliuks.setText(String.format("%d", s));
                                }

                            });


                            AnimatorSet set = new AnimatorSet();
                            set.playTogether(
                                    ObjectAnimator.ofFloat(lol, "scaleX", 0.0f, 0.4f)
                                            .setDuration(2000),
                                    ObjectAnimator.ofFloat(lol, "scaleY", 0.0f, 0.4f)
                                            .setDuration(2000)
                            );
                            AnimatorSet sett = new AnimatorSet();
                            sett.playTogether(
                                    ObjectAnimator.ofFloat(lol, "scaleX", 0.4f, 0.0f)
                                            .setDuration(2000),
                                    ObjectAnimator.ofFloat(lol, "scaleY", 0.4f, 0.0f)
                                            .setDuration(2000)

                            );
                            sett.end();
                            set3.playSequentially(set, sett);
                            set3.addListener(new Animator.AnimatorListener() {

                                @Override
                                public void onAnimationStart(Animator animation) {
                                    l++;
                                    lifes.setText(String.format("%d", l));
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    if (set3.isPaused()) {
                                        set3.resume();
                                    } else {
                                    }

                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                }
                            });

                        }

                    }



                    for (int i = 0; i <= 1; i++) {

                        if (kliutis.get(i).fiksuotiLaika() == true) {


                            kl = kliutis.get(i).getImage();
                            if (kl.getParent() != null)
                                ((ViewGroup) kl.getParent()).removeView(kl); // <- fix
                            layout.addView(kl);


                            kl.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    //set3.pause();
                                    layout.removeView(v);
                                    //layout.clearAnimation();
                                    TextView skaitliuks = (TextView) TheGameActivity.this.findViewById(R.id.skaitliukas);
                                    s=s-5;
                                    skaitliuks.setText(String.format("%d", s));
                                    //set3.pause();
                                    //layout.endViewTransition(v);


                                }

                            });

                            AnimatorSet set = new AnimatorSet();
                            set.playTogether(
                                    ObjectAnimator.ofFloat(kl, "scaleX", 0.0f, 0.4f)
                                            .setDuration(2000),
                                    ObjectAnimator.ofFloat(kl, "scaleY", 0.0f, 0.4f)
                                            .setDuration(2000)
                            );
                            AnimatorSet sett = new AnimatorSet();
                            sett.playTogether(
                                    ObjectAnimator.ofFloat(kl, "scaleX", 0.4f, 0.0f)
                                            .setDuration(2000),
                                    ObjectAnimator.ofFloat(kl, "scaleY", 0.4f, 0.0f)
                                            .setDuration(2000)

                            );
                            sett.end();
                            set3.playSequentially(set, sett);
                            set3.addListener(new Animator.AnimatorListener() {

                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {

                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                }
                            });


                        }}



                    handler.postDelayed(runnableCode, 1100);

                } else if (s < 150) {
                    TextView levlis2 = (TextView) TheGameActivity.this.findViewById(R.id.LEVELIS);
                    levlis2.setText("LEVEL 2");
                    for (int i = 0; i <= 25; i++) {

                        if (rutuliukai.get(i).fiksuotiLaika() == true) {
                            lol = rutuliukai.get(i).getImage();
                            if (lol.getParent() != null)
                                ((ViewGroup) lol.getParent()).removeView(lol); // <- fix

                            layout.addView(lol);

                            lol.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    //set3.pause();
                                    layout.removeView(v);
                                    //layout.clearAnimation();
                                    TextView skaitliuks = (TextView) TheGameActivity.this.findViewById(R.id.skaitliukas);
                                    s++;
                                    skaitliuks.setText(String.format("%d", s));
                                    //set3.pause();
                                    //layout.endViewTransition(v);


                                }

                            });


                            AnimatorSet set = new AnimatorSet();
                            set.playTogether(
                                    ObjectAnimator.ofFloat(lol, "scaleX", 0.0f, 0.4f)
                                            .setDuration(1700),
                                    ObjectAnimator.ofFloat(lol, "scaleY", 0.0f, 0.4f)
                                            .setDuration(1700)
                            );
                            AnimatorSet sett = new AnimatorSet();
                            sett.playTogether(
                                    ObjectAnimator.ofFloat(lol, "scaleX", 0.4f, 0.0f)
                                            .setDuration(1700),
                                    ObjectAnimator.ofFloat(lol, "scaleY", 0.4f, 0.0f)
                                            .setDuration(1700)

                            );
                            sett.end();
                            set3.playSequentially(set, sett);
                            set3.addListener(new Animator.AnimatorListener() {

                                @Override
                                public void onAnimationStart(Animator animation) {
                                    l++;
                                    lifes.setText(String.format("%d", l));
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    if (set3.isPaused()) {
                                        set3.resume();
                                    } else {

                                    }

                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                }
                            });

                        }

                    }




                    for (int i = 0; i <= 1; i++) {

                        if (kliutis.get(i).fiksuotiLaika() == true) {


                            kl = kliutis.get(i).getImage();
                            if (kl.getParent() != null)
                                ((ViewGroup) kl.getParent()).removeView(kl);
                            layout.addView(kl);

                            kl.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {

                                    layout.removeView(v);

                                    TextView skaitliuks = (TextView) TheGameActivity.this.findViewById(R.id.skaitliukas);
                                    s=s-5;
                                    skaitliuks.setText(String.format("%d", s));



                                }

                            });
                            AnimatorSet set = new AnimatorSet();
                            set.playTogether(
                                    ObjectAnimator.ofFloat(kl, "scaleX", 0.0f, 0.4f)
                                            .setDuration(2000),
                                    ObjectAnimator.ofFloat(kl, "scaleY", 0.0f, 0.4f)
                                            .setDuration(2000)
                            );
                            AnimatorSet sett = new AnimatorSet();
                            sett.playTogether(
                                    ObjectAnimator.ofFloat(kl, "scaleX", 0.4f, 0.0f)
                                            .setDuration(2000),
                                    ObjectAnimator.ofFloat(kl, "scaleY", 0.4f, 0.0f)
                                            .setDuration(2000)

                            );
                            sett.end();
                            set3.playSequentially(set, sett);
                            set3.addListener(new Animator.AnimatorListener() {

                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {

                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                }
                            });


                        }}


                    handler.postDelayed(runnableCode, 800);
                } else {
                    PridetiTaskus();
                    TextView wi = (TextView) TheGameActivity.this.findViewById(R.id.LEVELIS);
                    wi.setText("Cleared!!");
                    handler.removeCallbacks(runnableCode);
                    Log.i("INFOOOO", "WIN WIN WINE!!!");
                    View res = findViewById(R.id.reset);
                    res.setVisibility(View.VISIBLE);
                }
                set3.start();
            }else{
                PridetiTaskus();
                View res = findViewById(R.id.reset);
                res.setVisibility(View.VISIBLE);
                TextView lo = (TextView) TheGameActivity.this.findViewById(R.id.LEVELIS);
                lo.setText("Game Over ! Try Miss Less");
                handler.removeCallbacks(runnableCode);
                Log.i("INFOOOO", "LOSED TRY AGAIN!!!");
                // finish();
            }
        }
    };


    public void PridetiTaskus(){

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("TASKAI", s);
        params.put("NICKAS", NICKAS);

        client.get("http://www.ionic.96.lt/prideti_taskus.php", params, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String str) {
                        // called when response HTTP status is "200 OK"
                        Log.i("WEBSERVER", "PRIDEJO"+s+NICKAS);
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {

                    }
                }
        );

    }

}