package pt.claudio.security.pixelscamp_networksecurityconfig;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


class loadURL extends AsyncTask<String, Void, String> {

    private Exception exception;

    protected String doInBackground(String... urls) {
        try {
            String data;
            //Change to URL to http to confirm that the output no longer occurs.
            URL url = new URL("http://http.badssl.com/");
            URLConnection urlConnection = url.openConnection();
            InputStream in = urlConnection.getInputStream();
            BufferedReader reader =new BufferedReader(new InputStreamReader(in, "UTF-8"));
            while ((data = reader.readLine()) != null) {
                Log.d("px_output:", data);
            }
            return "";
        } catch (Exception e) {
            Log.d("px_error:",e.toString());
            return "";
        }
    }

    protected void onPostExecute() {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new loadURL().execute();

    }
}
