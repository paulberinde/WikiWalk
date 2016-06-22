package nl.mprog.wikiwalk;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Objects;

/**
 * WikiWalk - CollectionItemActivity.Java
 * Student: Paul Berinde-Tampanariu
 * This Class  contains the methods required to create the application's CollectionItem View for a
 * single unlocked monuments.  OnCreate the layout is populated (from DB and the Web) with data and
 * in case the button is pressed a Wikipedia article will be presented in a view if the link exists.
 * There is an override for the back button which allows for data usage reduction in case of a
 * mistake.
 **/
public class CollectionItemActivity extends Activity {

    Context context;
    WebView wikipediaView;
    int isWebViewVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Parameters for Fullscreen layout
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_collection_item);

        // Processing of bundle
        Bundle bundle = getIntent().getBundleExtra("android.intent.extra.INTENT");
        int position = bundle.getInt("position");

        // Instantiate database operations and open db.
        DatabaseOperations databaseOperations = DatabaseOperations.getInstance(this);
        databaseOperations.open();

        // Request collectionItem information and break down returned ArrayList.
        ArrayList<String> collectionItem = databaseOperations.getCollectionItem(position);
        String imgUrl = collectionItem.get(0);
        String name = collectionItem.get(1);
        String address = collectionItem.get(2);
        final String wikiUrl = collectionItem.get(3);
        String gpsLocation = collectionItem.get(4);
        String buildingCategory = collectionItem.get(5);
        databaseOperations.close();

        //Set title TextView and value.
        TextView fullscreenTitleView = (TextView) findViewById(R.id.fullscreenTitleView);
        fullscreenTitleView.setText(name);

        //Set image into fullscreenImageView via Picasso
        ImageView fullscreenImageView = (ImageView) findViewById(R.id.fullscreenImageView);
        Picasso
                .with(context)
                .load(imgUrl)
                .fit().centerInside()
                .into(fullscreenImageView);
        // Set the remaining views and values
        TextView addressView = (TextView) findViewById(R.id.addressView);
        addressView.setText(getString(R.string.collection_item_address) + address);
        TextView locationView = (TextView) findViewById(R.id.locationView);
        locationView.setText(getString(R.string.collection_item_gps) + gpsLocation);
        TextView categoryView = (TextView) findViewById(R.id.categoryView);
        categoryView.setText(getString(R.string.collection_item_category) + buildingCategory);
        wikipediaView = (WebView) findViewById(R.id.wikipediaView);
        wikipediaView.setWebViewClient(new WebViewClient());
        wikipediaView.setVisibility(View.GONE);

        // Set button and onclickListener
        Button wikipediaButton = (Button) findViewById(R.id.wikipediaButton);
        wikipediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebView(wikiUrl);
            }

        });
    }
    /**
     * This override of OnBackPressed is here to ensure that when pressing back with the webView
     * the app returns to the collection item by making the webView invisible. This has the added
     * benefit of keeping the page in memory unless the user returns to the CollectionView.
    **/
    @Override
    public void onBackPressed(){
            if (isWebViewVisible == 0) {
                super.onBackPressed();

            }
            else {
                wikipediaView.setVisibility(View.GONE);
                isWebViewVisible = 0;
                return;
            }
    }



    /**
      * This Class makes the WebView visible and loads the url it was given as a parameter or
      * gives a toast to the user letting him know there is no article.
     **/
    public void openWebView(String wikiUrl) {
        if (Objects.equals(wikiUrl, "Geen Wikipedia Artikel")) {
            Toast.makeText(CollectionItemActivity.this, R.string.no_wiki_toast, Toast.LENGTH_SHORT).show();
        }

        else {
            wikipediaView.setVisibility(View.VISIBLE);
            isWebViewVisible=1;
            wikipediaView.getSettings().setJavaScriptEnabled(true);
            wikipediaView.setWebViewClient(new WebViewClient());
            wikipediaView.setWebViewClient(new WebViewClient());
            wikipediaView.loadUrl(wikiUrl);
        }

    }

}

