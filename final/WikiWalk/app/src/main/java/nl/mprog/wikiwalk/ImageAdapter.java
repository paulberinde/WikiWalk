package nl.mprog.wikiwalk;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;
import java.util.List;
/**
 * WikiWalk - ImageAdapter.Java
 * Student: Paul Berinde-Tampanariu
 * This Class is an extension of the ArrayAdapter used to populate a GridView with Images.
 * The plug-in Picasso is used to get images from URLS and their lifecycle management.
 * Uses a separate layout for a single Image.
 **/

public class ImageAdapter extends ArrayAdapter {

    private Context context;
    private LayoutInflater inflater;
    protected List<String> imageUrls;

    public ImageAdapter(Context context, List imageUrls) {
        super(context, R.layout.list_item, imageUrls);

        this.context = context;
        inflater = LayoutInflater.from(context);
        this.imageUrls = imageUrls;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        Picasso
                .with(context)
                .load((imageUrls.get(position))).fit().centerCrop()
                .into((ImageView) convertView);



        return convertView;
    }

}
