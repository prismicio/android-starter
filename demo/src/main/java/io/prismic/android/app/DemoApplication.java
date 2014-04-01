package io.prismic.android.app;

import android.app.Application;
import io.prismic.android.Prismic;

public class DemoApplication extends Application {

  public Prismic prismic = new Prismic(this, "https://lesbonneschoses.prismic.io/api");

  @Override
  public void onCreate() {
    prismic.init();
  }

}
