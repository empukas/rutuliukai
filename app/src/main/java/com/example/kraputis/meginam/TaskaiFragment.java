package com.example.kraputis.meginam;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskaiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskaiFragment extends Fragment {
    private ProgressDialog progress;
    String NICKAS;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public TaskaiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment TaskaiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskaiFragment newInstance(String param1) {
        TaskaiFragment fragment = new TaskaiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            NICKAS = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Listas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_taskai, container, false);
    }

    public void Listas() {

        progress = ProgressDialog.show(getContext(), "Palaukite", "uztruks pora sec.", true);


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("NICKAS", NICKAS);

        client.get("http://www.ionic.96.lt/top5.php", params, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String str) {
                        // called when response HTTP status is "200 OK"
                        ArrayList<Top> topas = new ArrayList<Top>();
                        try {
                            JSONArray jsonObjects = new JSONArray(str);
                            for (int i = 0; i < 5; i++) {
                                topas.add(new Top(jsonObjects.getJSONObject(i)));
                            }
                            String tt = jsonObjects.getJSONObject(5).getString("TASKAI");
                            Log.i("taskai", Integer.toString(jsonObjects.length()));
                            TextView taskai = (TextView) getView().findViewById(R.id.field_taskai);
                            taskai.setText(tt);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                        AtnaujintiList(topas);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                    }
                }
        );

    }
    public void AtnaujintiList(ArrayList<Top> duomenys){
        TopAdapter adapter = new TopAdapter(getContext(), duomenys);
        ListView listView = (ListView) getView().findViewById(R.id.TOP_list);
        listView.setAdapter(adapter);
        progress.dismiss();
    }

}
