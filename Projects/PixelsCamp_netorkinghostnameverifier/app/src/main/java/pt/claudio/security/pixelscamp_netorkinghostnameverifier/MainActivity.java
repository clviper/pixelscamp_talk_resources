package pt.claudio.security.pixelscamp_netorkinghostnameverifier;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.io.Reader;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.xml.parsers.SAXParserFactory;

class loadURL extends AsyncTask<String, Void, String> {

    private Exception exception;

    protected String doInBackground(String... urls) {
        try {

            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }

            });
            String data;
            URL url = new URL("https://wrong.host.badssl.com/");
            URLConnection urlConnection = url.openConnection();
            InputStream in = urlConnection.getInputStream();
            BufferedReader reader =new BufferedReader(new InputStreamReader(in, "UTF-8"));
            while ((data = reader.readLine()) != null) {
                Log.d("px", data);
            }
            return "";
        } catch (Exception e) {
            Log.d("px ",e.toString());
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
