package com.example.journeymanager.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journeymanager.R;
import com.example.journeymanager.activities.LocationInfo;
import com.example.journeymanager.objects.CityDescription;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class ParseWikiArticleTask extends AsyncTask<String, Integer, String> {
    private final Activity mActivity;
    private TextView extractoInformacionCiudad;
    private final ObjectMapper mapper = new ObjectMapper();
    private CityDescription cityDescription;
    private ProgressDialog pDialog;
    private String mUrl;
    private final String WikiJSONurl = "http://es.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exintro=&titles=";

    public ParseWikiArticleTask(Activity activity) {
        mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(mActivity);
        pDialog.setCancelable(false);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("Procesando...");
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            mUrl = urls[0];
            cityDescription = mapper.readValue(new URL(WikiJSONurl + mUrl), CityDescription.class);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return "ok";
    }


    @Override
    protected void onPostExecute(String result) throws NullPointerException {
        if (result.equalsIgnoreCase("ok")) {
            try {
                extractoInformacionCiudad = (TextView) mActivity.findViewById(R.id.informacionCiudad);
                extractoInformacionCiudad.setText(cityDescription.getOther().toString());
                Intent intent = new Intent(mActivity, LocationInfo.class);
                intent.putExtra("info", extractoInformacionCiudad.getText());
                mActivity.startActivity(intent);

                pDialog.dismiss();
            } catch (NullPointerException e) {
                Toast.makeText(mActivity, "Error al intentar obtener los datos de la web", 1000).show();
                mActivity.finish();
            }
        } else {
            Toast.makeText(mActivity, "Error al intentar obtener los datos de la web", 1000).show();
            mActivity.finish();
        }
    }

}
