package io.prismic.android.app;

import android.graphics.Bitmap;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import io.prismic.*;
import io.prismic.android.Prismic;

import java.util.Map;


public class MainActivity extends ActionBarActivity {

  private DemoApplication app;

  private Map<String, String> bookmarks;
  private String[] bookmarksArray;

  private DrawerLayout mDrawerLayout;
  private ActionBarDrawerToggle mDrawerToggle;
  private ListView mDrawerList;
  private View mLoading;
  private WebView mWebView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    mDrawerList = (ListView) findViewById(R.id.left_drawer);
    mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);
    mDrawerLayout.setDrawerListener(mDrawerToggle);
    mLoading = findViewById(R.id.loading);
    mWebView = (WebView)findViewById(R.id.webView);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);

    app = (DemoApplication)getApplication();
    app.prismic.registerListener(apiListener);
  }

  private Prismic.Listener<Api> apiListener = new Prismic.Listener<Api>() {
    @Override
    public void onSuccess(Api result) {
      Log.v("prismic", "API is ready");
      bookmarks = app.prismic.getBookmarks();
      bookmarksArray = bookmarks.keySet().toArray(new String[0]);
      loadBookmark("about");
      mDrawerList.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.drawer_list_item, bookmarksArray));
    }
  };

  private void loadBookmark(String key) {
    app.prismic.getBookmark(key, new Prismic.Listener<Document>() {
      @Override
      public void onSuccess(Document result) {
        String html = result.asHtml(new BlankLinkResolver());
        mWebView.loadDataWithBaseURL(null, html, null, "UTF-8", null);
      }
      @Override
      public void onError(Api.Error error) {
        setLoading(false);
      }
    });
  }

  private void setLoading(Boolean loading) {
    if (loading) {
      mLoading.setVisibility(View.VISIBLE);
      mWebView.setVisibility(View.GONE);
    } else {
      mLoading.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
      mWebView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
      mLoading.setVisibility(View.GONE);
      mWebView.setVisibility(View.VISIBLE);
    }
  }

  private class DrawerItemClickListener implements ListView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
      loadBookmark(bookmarksArray[position]);
      setTitle(bookmarksArray[position]);
      mDrawerLayout.closeDrawer(mDrawerList);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (mDrawerToggle.onOptionsItemSelected(item)) {
      return true;
    }
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

}
