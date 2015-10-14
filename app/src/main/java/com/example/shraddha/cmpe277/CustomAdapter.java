package com.example.shraddha.cmpe277;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Priya on 10/11/2015.
 */
public class CustomAdapter extends ArrayAdapter<Integer> {


    private ArrayList<Integer> images;
    private ArrayList<String> texts;
    private LayoutInflater inflater;

    public CustomAdapter(Context c, ArrayList<Integer> imgs, ArrayList<String> txts) {
        super(c, R.layout.single_card, imgs);
        images = imgs;
        texts = txts;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate((R.layout.single_card), parent, false);
        }
        TextView title = (TextView) vi.findViewById(R.id.info_text);
        ImageView imgView = (ImageView) vi.findViewById(R.id.some_image);

        title.setText(texts.get(position));
        imgView.setImageResource(images.get(position));

        return vi;

    }
}


