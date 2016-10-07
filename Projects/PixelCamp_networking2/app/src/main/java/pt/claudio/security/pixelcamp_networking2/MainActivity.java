package pt.claudio.security.pixelcamp_networking2;

import android.net.Uri;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;



public class MainActivity extends AppCompatActivity {

    final class JavaScriptInterface {
        @JavascriptInterface
        public String getSomeString() {
            return "string";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(new JavaScriptInterface(), "jsinterface");
        myWebView.setWebViewClient(new WebViewClient() {
                                    public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
                                        handler.proceed() ;
                                    }
                                });
        myWebView.loadUrl("https://pixels.camp");
    }

}
