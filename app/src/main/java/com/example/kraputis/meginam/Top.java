package com.example.kraputis.meginam;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Justineso on 2016.12.06.
 */

    public class Top {

    private String NICKAS;
    private String TASKAI;

    public Top(JSONObject object){
        try {
            NICKAS = object.getString("NICKAS");
            TASKAI = object.getString("TASKAI");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getNICKAS(){
        return NICKAS;
    }

    public  String getTASKAI(){
        return TASKAI;
    }

}
