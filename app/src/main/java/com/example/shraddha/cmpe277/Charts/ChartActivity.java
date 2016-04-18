package com.example.shraddha.cmpe277.Charts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.shraddha.cmpe277.ModelObjects.SensorData;
import com.example.shraddha.cmpe277.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PreviewColumnChartView;

public class ChartActivity extends AppCompatActivity {

    private static final int DEFAULT_DATA = 0;

    private ColumnChartView chart;
    private ColumnChartData data;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLabels = false;
    private boolean hasLabelForSelected = false;
    private int dataType = DEFAULT_DATA;
    private PreviewColumnChartView previewChart;
    private ColumnChartData previewData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        ArrayList<SensorData> listToPlot = getIntent().getParcelableArrayListExtra("ListOfData");

        chart = (ColumnChartView) findViewById(R.id.chart);
        previewChart = (PreviewColumnChartView) findViewById(R.id.chart_preview);

        // Generate data for previewed chart and copy of that data for preview chart.
        generateDefaultData(listToPlot);

        chart.setColumnChartData(data);
        // Disable zoom/scroll for previewed chart, visible chart ranges depends on preview chart viewport so
        // zoom/scroll is unnecessary.
        chart.setZoomEnabled(false);
        chart.setScrollEnabled(false);

        previewChart.setColumnChartData(previewData);
        previewChart.setViewportChangeListener(new ViewportListener());

        previewX(false);

//        chart = (ColumnChartView) findViewById(R.id.chart);
//
//        generateDefaultData(listToPlot);

    }

    private void generateDefaultData(List<SensorData> listToPlot) {
        int numSubcolumns = 1;
        int numColumns = listToPlot.size();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {
            SensorData value1 = listToPlot.get(i);
            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue(Float.parseFloat("" + value1.getX()), ChartUtils.pickColor()));
            }

            columns.add(new Column(values));
        }

        data = new ColumnChartData(columns);
        data.setAxisXBottom(new Axis());
        data.setAxisYLeft(new Axis().setHasLines(true));

        // prepare preview data, is better to use separate deep copy for preview chart.
        // set color to grey to make preview area more visible.
        previewData = new ColumnChartData(data);
        for (Column column : previewData.getColumns()) {
            for (SubcolumnValue value : column.getValues()) {
                value.setColor(ChartUtils.DEFAULT_DARKEN_COLOR);
            }
        }

    }

    private void previewY() {
        Viewport tempViewport = new Viewport(chart.getMaximumViewport());
        float dy = tempViewport.height() / 4;
        tempViewport.inset(0, dy);
        previewChart.setCurrentViewportWithAnimation(tempViewport);
        previewChart.setZoomType(ZoomType.VERTICAL);
    }

    private void previewX(boolean animate) {
        Viewport tempViewport = new Viewport(chart.getMaximumViewport());
        float dx = tempViewport.width() / 4;
        tempViewport.inset(dx, 0);
        if (animate) {
            previewChart.setCurrentViewportWithAnimation(tempViewport);
        } else {
            previewChart.setCurrentViewport(tempViewport);
        }
        previewChart.setZoomType(ZoomType.HORIZONTAL);
    }

    private void previewXY() {
        // Better to not modify viewport of any chart directly so create a copy.
        Viewport tempViewport = new Viewport(chart.getMaximumViewport());
        // Make temp viewport smaller.
        float dx = tempViewport.width() / 4;
        float dy = tempViewport.height() / 4;
        tempViewport.inset(dx, dy);
        previewChart.setCurrentViewportWithAnimation(tempViewport);
    }

    /**
     * Viewport listener for preview chart(lower one). in {@link #onViewportChanged(Viewport)} method change
     * viewport of upper chart.
     */
    private class ViewportListener implements ViewportChangeListener {

        @Override
        public void onViewportChanged(Viewport newViewport) {
            // don't use animation, it is unnecessary when using preview chart because usually viewport changes
            // happens to often.
            chart.setCurrentViewport(newViewport);
        }

    }


//    private void generateDefaultData(ArrayList<SensorData> listToPlot) {
//        int numSubcolumns = 2;
//        int numColumns = listToPlot.size();
//        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
//        List<Column> columns = new ArrayList<Column>();
//        List<SubcolumnValue> values = null;
//
//        HashMap<Integer, Float> hourlyData = new HashMap<Integer, Float>();
//        for (int i = 0; i < numColumns; ++i) {
//
//            values = new ArrayList<SubcolumnValue>();
//            int hour = 0;
//            for (int j = 0; j < numSubcolumns; ++j) {
//                SensorData value1 = listToPlot.get(i);
//
//                try {
//                    Date date = Constants.getStandardDateFormat().parse(value1.getTime());
//                    hour = date.getHours();
//
//                    if (!hourlyData.containsKey(hour)) {
//                        Log.d("Time At: ", hour + "" + date.toString());
//                        hourlyData.put(hour, Float.parseFloat("" + value1.getX()));
//
//                        if (j == 0) {
//                            values.add(new SubcolumnValue(Float.parseFloat("" + value1.getX()), ChartUtils.COLOR_ORANGE));
//                        } else if (j == 1) {
//                            values.add(new SubcolumnValue(Float.parseFloat("" + value1.getTrustValue()), ChartUtils.COLOR_VIOLET));
//                        }
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                //values.add(new SubcolumnValue(Float.parseFloat(""+ value1.getX()), ChartUtils.pickColor()));
//            }
//            Column column = new Column(values);
//            column.setHasLabels(hasLabels);
//            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
//            columns.add(column);
//        }
//
//        data = new ColumnChartData(columns);
//
//        if (hasAxes) {
//            Axis axisX = new Axis();
//            Axis axisY = new Axis().setHasLines(true);
//            if (hasAxesNames) {
//                axisX.setName("Axis X");
//                axisY.setName("Axis Y");
//            }
//            data.setAxisXBottom(axisX);
//            data.setAxisYLeft(axisY);
//        } else {
//            data.setAxisXBottom(null);
//            data.setAxisYLeft(null);
//        }
//
//        chart.setColumnChartData(data);
//
//    }

}
