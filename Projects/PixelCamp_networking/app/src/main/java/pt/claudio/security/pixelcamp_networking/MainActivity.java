package pt.claudio.security.pixelcamp_networking;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity {


    private static final HostnameVerifier DUMMY_VERIFIER = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doRequest requests = new doRequest();
        requests.execute();
    }

    private class doRequest extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void...params) {
            String result = null;
            StringBuffer sb = new StringBuffer();
            InputStream is = null;

            TrustManager mySuperCustomTrustManager = new X509TrustManager() {

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
            };


            try {
                SSLContext sslc = SSLContext.getInstance("TLS");
                sslc.init(null, new TrustManager[] { mySuperCustomTrustManager },new SecureRandom());
                //URL url = new URL("https://pixels.camp/");
                URL url = new URL("https://self-signed.badssl.com/");
                //URLConnection urlConnection = url.openConnection();
                HttpsURLConnection urlConnection =
                        (HttpsURLConnection)url.openConnection();
                urlConnection.setSSLSocketFactory(sslc.getSocketFactory());
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String inputLine = "";
                while ((inputLine = br.readLine()) != null) {
                    sb.append(inputLine);
                }
                Log.d("PixelCamp_Output",sb.toString());
                return null;
            }catch(Exception e)
            {
                Log.e("PixelCamp_Error",e.toString());
                return null;
            }
        }

    }
}
