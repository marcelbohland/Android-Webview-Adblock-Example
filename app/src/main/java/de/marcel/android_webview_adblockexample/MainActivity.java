package de.marcel.android_webview_adblockexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    StringBuilder blocklist;
    WebView view;
    String loddnormallist= "0"; //if you want to use a filterlist without "::::" at the beginning. please change to 1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        load();//Load Blocklist


        //Load Webview, etc.
        view = (WebView) findViewById(R.id.webView2);
        view.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        view.getSettings().setBuiltInZoomControls(true);
        view.getSettings().setSupportZoom(true);
        view.getSettings().setDisplayZoomControls(false);
        view.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        view.setScrollbarFadingEnabled(true);
        view.setLongClickable(true);
        view.getSettings().setJavaScriptEnabled(true);
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        view.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        view.getSettings().setDomStorageEnabled(true);
        view.getSettings().setAppCacheEnabled(true);
        view.getSettings().setSavePassword(true);
        view.getSettings().setSaveFormData(true);
        view.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        view.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        view.getSettings().setEnableSmoothTransition(true);
        view.setWebViewClient(new WebViewClient());
        view.getSettings().setMediaPlaybackRequiresUserGesture(true);
        view.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        view.loadUrl("https://www.google.com");




        //WebviewClient for blocking
        view.setWebViewClient(new WebViewClient() {


            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                    ByteArrayInputStream EMPTY3 = new ByteArrayInputStream("".getBytes());
                    String kk53 = String.valueOf(blocklist);//Load blocklist
                    if (kk53.contains(":::::" + request.getUrl().getHost())) {// If blocklist equals url = Block
                        return new WebResourceResponse("text/plain", "utf-8", EMPTY3);//Block
                    }
                return super.shouldInterceptRequest(view, request);
            }
        });
    }




    public void license(View v){
        view.loadUrl("file:///android_asset/license.txt");
    }//Button1
    public void tf(View v)
    {
        view.loadUrl("https://ads-blocker.com/testing/");
    }//Button2







    private void load(){//Blocklist loading
        String strLine2="";
        blocklist = new StringBuilder();

        InputStream fis2 = this.getResources().openRawResource(R.raw.adblockserverlist);//Storage location
        BufferedReader br2 = new BufferedReader(new InputStreamReader(fis2));
        if(fis2 != null) {
            try {
                while ((strLine2 = br2.readLine()) != null) {
                    if(loddnormallist.equals("0")){
                    blocklist.append(strLine2);//if ":::::" exists in blocklist | Line for Line
                    blocklist.append("\n");
                    }
                    if(loddnormallist.equals("1")){
                        blocklist.append(":::::"+strLine2);//if ":::::" not exists in blocklist | Line for Line
                        blocklist.append("\n");
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
