package com.example.shraddha.cmpe277.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shraddha.cmpe277.ModelObjects.SensorDataSource;
import com.example.shraddha.cmpe277.R;

import com.example.shraddha.cmpe277.Utils.Constants;
import com.example.shraddha.cmpe277.VariableActivity;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

  private Activity activity;
  private List<SensorDataSource> sources;
  public String startTime;
  public String endTime;

  public ListViewAdapter(List<SensorDataSource> sources, Activity activity) {
    this.sources = sources;
    this.activity = activity;
  }

  @Override public int getCount() {
    return sources.size();
  }

  @Override public Object getItem(int position) {
    return sources.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;
    if (convertView == null) {
      LayoutInflater inflater = activity.getLayoutInflater();
      convertView = inflater.inflate(R.layout.source_list_item, parent, false);
      viewHolder = new ViewHolder();
      viewHolder.institutionName = (TextView) convertView.findViewById(R.id.institution_name);
      viewHolder.datasetId = (TextView) convertView.findViewById(R.id.data_set_id);
      viewHolder.startEndTime = (TextView) convertView.findViewById(R.id.start_time);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    final SensorDataSource source = sources.get(position);
    if (source != null) {
      viewHolder.institutionName.setText(source.getInstitution());
      viewHolder.datasetId.setText(source.getSourceId());
      //startTime = Constants.formatDate(source.getStartTime());
      //endTime = Constants.formatDate(source.getEndTime());
      viewHolder.startEndTime.setText(source.getStartTime() + "  to  " + source.getEndTime());
    }



    return convertView;
  }

  public static class ViewHolder {
    public TextView institutionName;
    public TextView datasetId;
    public TextView startEndTime;
  }
}
