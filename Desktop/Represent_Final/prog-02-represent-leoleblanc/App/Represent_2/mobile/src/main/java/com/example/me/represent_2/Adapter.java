package com.example.me.represent_2;

/**
 * Created by Me on 2/28/16.
 */
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.ImageView;

public class Adapter extends ArrayAdapter<String> {

    //DECLARATIONS
    int[] images;
    String[] names;
    Context c;
    LayoutInflater inflater;

    public Adapter(Context context, String[] names, int[] images) {
        super(context, R.layout.model, names);

        this.c = context;
        this.names = names;
        this.images = images;
    }

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
        final ViewHolder holder = new ViewHolder();

        //initialize views
        holder.name = (TextView) convertView.findViewById(R.id.model_Text);
        holder.image = (ImageView) convertView.findViewById(R.id.model_ImageView);

        //Assign data
        holder.image.setImageResource(images[position]);
        holder.name.setText(names[position]);


        return convertView;
    }
}
