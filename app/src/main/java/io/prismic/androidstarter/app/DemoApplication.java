package io.prismic.androidstarter.app;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.prismic.Api;
import io.prismic.android.Prismic;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class DemoApplication extends Application {

  public Prismic prismic = new Prismic(this, "https://lesbonneschoses.prismic.io/api");

  @Override
  public void onCreate() {
    prismic.init();
  }

}
