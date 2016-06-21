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
