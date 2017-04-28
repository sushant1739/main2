package com.bluetooth.bluetooth;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import android.util.Log;
import android.widget.Toast;

import com.bluetooth.bluetooth.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Handler;

public class MainActivity extends Activity implements LocationListener{
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    public static Client wifi_client = null;
    Button btn_click;
    TextView txtLat;
    ListView listView;
    public static String lat;
    public static String lng;
    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;
    public static ArrayAdapter<String> adapter;
    public static ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView) findViewById(R.id.listView);
       // btn_click=(Button)findViewById(R.id.btn_click);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, this);
        /*btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // onLocationChanged(Location location) //method to turn on
            }
        });*/
    }
    // Create ArrayAdapter using the planet list.
    //listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, planetList);
    @Override
    public void onLocationChanged(Location location) {

        lat=Double.toString(location.getLatitude());
        lng=Double.toString(location.getLongitude());
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
        adapter.add("Latitude:" + MainActivity.lat + ", Longitude:" + MainActivity.lng);
        listView.setAdapter(adapter);
        try{
            // CALL GetText method to make post method call
            msg("calling client");
            wifi_client = new Client();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    wifi_client.execute();
                }
            });
        }
        catch(Exception ex)
        {

        }
    }
    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }
    public  void  GetText(String lat,String lng) throws UnsupportedEncodingException {

        // Show response on activity


    }
    public void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

}
class Client extends AsyncTask<Void, Void, Void> {

    protected Void doInBackground(Void... arg0) {
        // Create data variable for sent values to server
/*
        String data = URLEncoder.encode("lat", "UTF-8")
                + "=" + URLEncoder.encode(lat, "UTF-8");

        data += "&" + URLEncoder.encode("lng", "UTF-8") + "="
                + URLEncoder.encode(lng, "UTF-8");

        data += "&" + URLEncoder.encode("carno", "UTF-8")
                + "=" + URLEncoder.encode("UP45B8681", "UTF-8");*/

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {
            String data="lat="+MainActivity.lat+"&lng="+MainActivity.lng+"&carno=UP45B8681";
            URL url = new URL("http://computronicslab.com/iot/vtsApp/add.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();
        }
        catch(Exception ex)
        {

        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {}
        }

        return null;
    }

    private void msg(String s) {

    }
}




