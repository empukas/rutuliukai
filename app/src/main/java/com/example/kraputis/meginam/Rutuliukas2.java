package com.example.kraputis.meginam;

import android.media.Image;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by kraputis on 11/15/16.
 */

public class Rutuliukas2 {
    private int id;
    private ImageView image;
    private int laikasPasirodymui;
    private int praejoLaiko = 0;
    private int w;
    private int h;

    public Rutuliukas2(int id, ImageView image, int laikasPasirodymui, int w, int h){
        this.id = id;
        this.image = image;
        this.image.setImageResource(R.drawable.test2);
        Random randomGenerator = new Random();
        this.laikasPasirodymui = laikasPasirodymui;
        this.w = w;
        this.h = h;
    }

    public ImageView getImage(){

        return image;
    }

    public int getLaikas(){
        return laikasPasirodymui;
    }

    public int getId(){
        return id;
    }

    public Boolean fiksuotiLaika(){
        ++praejoLaiko;
        if(laikasPasirodymui == praejoLaiko){
            praejoLaiko = 0;
            Random randomGenerator = new Random();
            this.image.setY((float)randomGenerator.nextInt(h-200)); //-676
            this.image.setX((float)randomGenerator.nextInt(w-200)); //-480
            return true;
        }
        return false;
    }

}