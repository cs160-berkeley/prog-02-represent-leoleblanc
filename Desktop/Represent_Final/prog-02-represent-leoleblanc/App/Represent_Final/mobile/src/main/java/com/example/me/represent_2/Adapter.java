package com.example.me.represent_2;

/**
 * Created by Me on 2/28/16.
 */
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Adapter extends ArrayAdapter<String> {

    //DECLARATIONS
    int[] images;
    String[] names;
    Context c;
    LayoutInflater inflater;
//    String[] bioguideIds;
    String[] urls;

    public Adapter(Context context, String[] names, int[] images, String[] picStrings) {
        super(context, R.layout.model, names);

        this.c = context;
        this.names = names;
        this.images = images;
        urls = picStrings;
//        Log.d("id array length", String.valueOf(ids.length));
//        bioguideIds = ids;
//        Log.d("gotten here", "AFTER ASSIGNING BIOGUIDEIDS");
//        fillUrls(ids);
//        Log.d("after fillUrls", "HERE");
    }

//    public void fillUrls(String[] ids) {
//        urls = new String[ids.length];
//        Log.d("urls length", String.valueOf(urls.length));
//        for (int i = 0; i < ids.length; i++) {
////                         "https://theunitedstates.io/images/congress/[size]/[bioguide].jpg"
//            String url = "https://theunitedstates.io.images/congress/225x275/" + ids[i] + ".jpg";
//            Log.d("url is", url);
//            urls[i] = url;
//        }
//    }

    //INNER CLASS TO HOLD OUR VIEWS FOR EACH ROW
    public class ViewHolder {

        TextView name;
        ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.model, null);
        }

        //our viewHolder object
        ViewHolder holder = new ViewHolder();

        //initialize views
        holder.name = (TextView) convertView.findViewById(R.id.model_Text);
        holder.image = (ImageView) convertView.findViewById(R.id.model_ImageView);

        //Assign data
        //set the picture based upon the url
//        holder.image.setImageResource(images[position]);
        Picasso.with(c).load(urls[position]).into(holder.image);
//        Picasso.with(this.getContext()).load(urls[position]).into(holder.image);
        holder.name.setText(names[position]);


        return convertView;
    }
}
