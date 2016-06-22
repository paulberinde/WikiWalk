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

public class CollectionItemActivity extends Activity {
    Context context;
    WebView wikipediaView;
    int isWebviewVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_collection_item);
        Bundle bundle = getIntent().getBundleExtra("android.intent.extra.INTENT");
        int position = bundle.getInt("position");
        DatabaseOperations databaseOperations = DatabaseOperations.getInstance(this);
        databaseOperations.open();
        ArrayList<String> collectionItem = databaseOperations.getCollectionItem(position);
        String imgUrl = (String) collectionItem.get(0);
        String name = (String) collectionItem.get(1);
        String address = (String) collectionItem.get(2);
        final String wikiUrl = (String) collectionItem.get(3);
        String gpsLocation = (String) collectionItem.get(4);
        String buildingCategory = (String) collectionItem.get(5);
        databaseOperations.close();
        TextView fullscreenTitleView = (TextView) findViewById(R.id.fullscreenTitleView);
        fullscreenTitleView.setText(name);
        ImageView fullscreenImageView = (ImageView) findViewById(R.id.fullscreenImageView);
        Picasso
                .with(context)
                .load(imgUrl)
                .fit().centerInside()
                .into(fullscreenImageView);

        TextView addressView = (TextView) findViewById(R.id.addressView);
        addressView.setText("Adres: " + address);

        TextView locationView = (TextView) findViewById(R.id.locationView);
        locationView.setText("Locatie op de kaart: " + gpsLocation);
        TextView categoryView = (TextView) findViewById(R.id.categoryView);
        categoryView.setText("Categorie: " + buildingCategory);
        wikipediaView = (WebView) findViewById(R.id.wikipediaView);
        wikipediaView.setWebViewClient(new WebViewClient());
        wikipediaView.getSettings().setJavaScriptEnabled(true);
        wikipediaView.setVisibility(View.GONE);
        Button wikipediaButton = (Button) findViewById(R.id.wikipediaButton);

        wikipediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(wikiUrl, "Geen Wikipedia Artikel")) {
                    Toast.makeText(CollectionItemActivity.this, "Helaas, er is geen Wikipedia artikel voor dit monument", Toast.LENGTH_SHORT).show();
                }
                else {
                    wikipediaView.setVisibility(View.VISIBLE);
                    isWebviewVisible=1;
                    wikipediaView.getSettings().setJavaScriptEnabled(true);
                    wikipediaView.setWebViewClient(new WebViewClient());
                    wikipediaView.setWebViewClient(new WebViewClient());
                    wikipediaView.getSettings().setJavaScriptEnabled(true);
                    wikipediaView.loadUrl(wikiUrl);
                }

            }

        });

    }
    @Override
    public void onBackPressed() {


        if (isWebviewVisible == 0) {
            super.onBackPressed();

        }
        wikipediaView.setVisibility(View.GONE);
        isWebviewVisible=0;
        return;

    }
}

