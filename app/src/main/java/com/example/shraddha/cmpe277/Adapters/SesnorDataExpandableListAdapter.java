package com.example.shraddha.cmpe277.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.shraddha.cmpe277.ModelObjects.SensorData;
import com.example.shraddha.cmpe277.R;
import com.example.shraddha.cmpe277.Utils.Constants;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Shraddha on 4/17/16.
 */
public class SesnorDataExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<SensorData>> _listDataChild;

    public SesnorDataExpandableListAdapter(Context context, List<String> listDataHeader,
                                           HashMap<String, List<SensorData>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        final SensorData childText = (SensorData) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater =
                    (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_data_item, null);
        }
        String formattedDate = childText.getTime();
        try {
            Date date = Constants.getStandardDateFormat().parse(childText.getTime());
            formattedDate = Constants.getPrettyLongDateFormat().format(date.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        TextView txtX = (TextView) convertView.findViewById(R.id.txtVwX);
        TextView txtUnit = (TextView) convertView.findViewById(R.id.txtVwUnit);
        TextView txtTime = (TextView) convertView.findViewById(R.id.txtVwTime);
        TextView txtTrust = (TextView) convertView.findViewById(R.id.txtVwTrust);
        txtX.setText("" + childText.getX());
        txtTime.setText(formattedDate);
        txtTrust.setText(String.format("%.3f", childText.getTrustValue()));
        txtUnit.setText(childText.getUnit());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater =
                    (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle.substring(0, 15));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
