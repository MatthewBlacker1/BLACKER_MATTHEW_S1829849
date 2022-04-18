package org.me.gcu.matthew_blacker_coursework_1;
//Matthew Blacker_MPD_CW_S1829849
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity implements OnClickListener
{
//Declaring of relevant variables
    private Button incidentBtn;
    private Button plannedRoadworksBtn;
    private Button roadworksBtn;
    private String result = "";
    private String result2 = "";
    private String urlSource="https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private String urlSource2="https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private String urlSource3="https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private ListView ListIncidents;
    private ListView ListPlannedRoadworks;
    private ListView ListRoadworks;
    private ArrayAdapter<Incidents> arrayAdapterCurrentIncidents;
    private ArrayAdapter<Roadworks> arrayAdapterRoadworks;
    private ArrayAdapter<PlannedRoadWorks> arrayAdapterPlannedRoadworks;
    private List<Incidents> incidents;
    private List<Roadworks> roadworks;
    private List<PlannedRoadWorks> plannedRoadWorks;
    private TextView acknowledgement;
    private EditText search;
    private EditText search2;
    private EditText search3;


//Setup of assigning variables to activity_main.xml
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if(!isNetworkAvailable()==true)
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Internet Connection Alert")
                    .setMessage("Please Check Your Internet Connection")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).show();
        }
        else if(isNetworkAvailable()==true)
        {
            Toast.makeText(MainActivity.this,
                    "Welcome", Toast.LENGTH_LONG).show();
        }






        acknowledgement = (TextView) findViewById(R.id.acknowledgement);

        incidentBtn = (Button) findViewById(R.id.incidentsButton);
        incidentBtn.setOnClickListener(this);
        ListIncidents = (ListView) findViewById(R.id.dataRoad);
        ListIncidents.setVisibility(View.GONE);

        roadworksBtn = (Button)findViewById(R.id.roadworksButton);
        roadworksBtn.setOnClickListener(this);
        ListRoadworks = (ListView)findViewById(R.id.dataRoad3);
        ListRoadworks.setVisibility(View.GONE);


        plannedRoadworksBtn = (Button)findViewById(R.id.plannedRoadworksButton);
        plannedRoadworksBtn.setOnClickListener(this);
        ListPlannedRoadworks = (ListView)findViewById(R.id.dataRoad2);
        ListPlannedRoadworks.setVisibility(View.GONE);

        search = (EditText)findViewById(R.id.editText3);
        search2 = (EditText)findViewById(R.id.editText2);
        search3 = (EditText)findViewById(R.id.editText1);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (MainActivity.this).arrayAdapterCurrentIncidents.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }


        });

        search2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (MainActivity.this).arrayAdapterPlannedRoadworks.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Log.e("MyTag","in onCreate");

     search.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             (MainActivity.this).arrayAdapterRoadworks.getFilter().filter(charSequence);
         }

         @Override
         public void afterTextChanged(Editable editable) {

         }
     });



    }




//if statements to specify which feed and url source is being used based on the button the user presses
    public void startProgress() {
        if (incidentBtn.isPressed()) {
            result = "";
            new Thread(new Task(urlSource2)).start();
            ListIncidents.setVisibility(View.VISIBLE);
            ListPlannedRoadworks.setVisibility(View.GONE);
            ListRoadworks.setVisibility(View.GONE);
            result2 = "";
            result2 = "Incidents displayed";
        }
        if (roadworksBtn.isPressed()) {
            result = "";
            new Thread(new Task(urlSource3)).start();
            ListIncidents.setVisibility(View.GONE);
            ListPlannedRoadworks.setVisibility(View.GONE);
            ListRoadworks.setVisibility(View.VISIBLE);
            result2 = "";
            result2 = "roadworks displayed";
        }
        if (plannedRoadworksBtn.isPressed()) {
            result = "";
            result2 = "";
            new Thread(new Task(urlSource)).start();
            ListIncidents.setVisibility(View.GONE);
            ListPlannedRoadworks.setVisibility(View.VISIBLE);
            ListRoadworks.setVisibility(View.GONE);

            result2 = "roadworks planned displayed";
        }

    }//



//Starts the app
    @Override
    public void onClick(View v)
    {
            startProgress();
            Log.e("Good", "Good to go");
    }







    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                Log.e("MyTag","after ready");
                //
                // Now read the data. Make sure that there are no specific hedrs
                // in the data file that you need to ignore.
                // The useful data that you need is in each of the item entries
                //
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    Log.e("MyTag",inputLine);

                }
                in.close();

                //Starting parser based on each category
                //Connecting array of items to the app layout
                XmlParser parser = new XmlParser();
                incidents = parser.parseIncidents(result);
                arrayAdapterCurrentIncidents = new ArrayAdapter<Incidents>(MainActivity.this, android.R.layout.simple_list_item_1, incidents);

                roadworks = parser.parseRoadWorks(result);
                arrayAdapterRoadworks = new ArrayAdapter<Roadworks>(MainActivity.this, android.R.layout.simple_list_item_1, roadworks);

                plannedRoadWorks = parser.parsePlannedRoadWorks(result);
                arrayAdapterPlannedRoadworks = new ArrayAdapter<PlannedRoadWorks>(MainActivity.this, android.R.layout.simple_list_item_1, plannedRoadWorks);
            }

            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception in run");
            }

            //
            // Now that you have the xml data you can parse it
            //

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !



            //Displays the formatted data to three separate List Views
            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    ListIncidents.setAdapter(arrayAdapterCurrentIncidents);
                    ListRoadworks.setAdapter(arrayAdapterRoadworks);
                    ListPlannedRoadworks.setAdapter(arrayAdapterPlannedRoadworks);

                    //Acknowledgement given of data being shown
                    acknowledgement.setText(result2);
                }
            });
        }

    }
    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {

                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {

                        return true;
                    }
                }
            }
        }

        return false;

    }
}




