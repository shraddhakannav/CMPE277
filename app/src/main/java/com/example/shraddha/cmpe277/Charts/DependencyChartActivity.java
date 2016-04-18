package com.example.shraddha.cmpe277.Charts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.shraddha.cmpe277.ModelObjects.SensorData;
import com.example.shraddha.cmpe277.R;
import com.example.shraddha.cmpe277.Utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

public class DependencyChartActivity extends AppCompatActivity {

    public final static String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec",};

    public final static String[] days = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun",};
    public final static String[] times = new String[]{"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "01:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00",};

    private LineChartView chartTop;
    private ColumnChartView chartBottom;

    private LineChartData lineData;
    private ColumnChartData columnData;
    private HashMap<String, List<SensorData>> hashMap;
    private HashMap<Integer, List<SensorData>> columnWiseKey;
    private String variable;
    private String unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dependency_chart);

        variable = getIntent().getStringExtra("VARIABLE");
        unit = getIntent().getStringExtra("UNIT");

        hashMap = Constants.getDataForVariable();
        columnWiseKey = new HashMap<Integer, List<SensorData>>();

        // *** TOP LINE CHART ***
        chartTop = (LineChartView) findViewById(R.id.chart_top);

        // Generate and set data for line chart
        generateInitialLineData();

        // *** BOTTOM COLUMN CHART ***

        chartBottom = (ColumnChartView) findViewById(R.id.chart_bottom);

        generateColumnData();
    }

    private void generateColumnData() {

        int numSubcolumns = 1;
        int numColumns = hashMap.keySet().size();

        Iterator<String> keys = hashMap.keySet().iterator();

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;

        for (int i = 0; keys.hasNext(); i++) {
            String key = keys.next();
            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {

                List<SensorData> dataValues = hashMap.get(key);
//                for (SensorData data: dataValues) {
//                    data.getX();
//                }
                values.add(new SubcolumnValue(Float.parseFloat(dataValues.get(0).getX() + ""), ChartUtils.pickColor()));
            }

            axisValues.add(new AxisValue(i).setLabel(key.substring(5, 11)));
            columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
            columnWiseKey.put(i, hashMap.get(key));
        }

        columnData = new ColumnChartData(columns);

        columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true).setMaxLabelChars(5).setName("Days").setTextColor(ChartUtils.DEFAULT_DARKEN_COLOR));
        columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(2).setName("Avg(" + variable + ")").setTextColor(ChartUtils.DEFAULT_DARKEN_COLOR));

        chartBottom.setColumnChartData(columnData);

        // Set value touch listener that will trigger changes for chartTop.
        chartBottom.setOnValueTouchListener(new ValueTouchListener());

        // Set selection mode to keep selected month column highlighted.
        chartBottom.setValueSelectionEnabled(true);

        chartBottom.setZoomType(ZoomType.HORIZONTAL);

    }

    /**
     * Generates initial data for line chart. At the begining all Y values are equals 0. That will change when user
     * will select value on column chart.
     */
    private void generateInitialLineData() {
        int numValues = times.length;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<PointValue> values = new ArrayList<PointValue>();

        for (int i = 0; i < numValues; ++i) {
            values.add(new PointValue(i, 0));
            axisValues.add(new AxisValue(i).setLabel(times[i])); //list.get(i).getTime().substring(11, 15)));
        }

        Line line = new Line(values);
        line.setColor(ChartUtils.COLOR_GREEN).setCubic(true);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        lineData = new LineChartData(lines);
        lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true).setName("Time of Day").setTextColor(ChartUtils.DEFAULT_DARKEN_COLOR));
        lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3).setName(variable + "(" + unit + ")").setTextColor(ChartUtils.DEFAULT_DARKEN_COLOR));

        chartTop.setLineChartData(lineData);

        // For build-up animation you have to disable viewport recalculation.
        chartTop.setViewportCalculationEnabled(true);

        // And set initial max viewport and current viewport- remember to set viewports after data.
//        Viewport v = new Viewport(0, 110, 6, 0);
//        chartTop.setMaximumViewport(v);
//        chartTop.setCurrentViewport(v);

        chartTop.setZoomType(ZoomType.HORIZONTAL);
    }

    private void generateLineData(int color, List<SensorData> list) {
        // Cancel last animation if not finished.
        chartTop.cancelDataAnimation();

        // Modify data targets
        Line line = lineData.getLines().get(0);// For this example there is always only one line.
        line.setColor(color).setHasPoints(false);

        List<PointValue> listToRemove = new ArrayList<>();
        List<AxisValue> axisToRemove = new ArrayList<>();

        for (int i = 0; i < line.getValues().size(); i++) {
            PointValue value = line.getValues().get(i);
            listToRemove.add(value);
            AxisValue axis = lineData.getAxisXBottom().getValues().get(i);
            axisToRemove.add(axis);
        }

        line.getValues().removeAll(listToRemove);
        lineData.getAxisXBottom().getValues().removeAll(axisToRemove);

        int i = 0;
        for (i = 0; i < list.size(); i++) {
            if (i < line.getValues().size()) {
                PointValue value = line.getValues().get(i);
                value.setTarget(value.getX(), Float.parseFloat("" + list.get(i).getX()));
            } else {
                if (list.get(i).getX() != -9999.99)
                line.getValues().add(new PointValue(i, Float.parseFloat("" + list.get(i).getX())));
                else line.getValues().add(new PointValue(i, Float.parseFloat("0.0")));
                lineData.getAxisXBottom().getValues().add(new AxisValue(i).setLabel(list.get(i).getTime().substring(11, 15)));
            }
        }

        chartTop.startDataAnimation(300);
    }

    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            List<SensorData> listToPlot = columnWiseKey.get(columnIndex);
            generateLineData(value.getColor(), listToPlot);
        }

        @Override
        public void onValueDeselected() {
            generateInitialLineData();
        }
    }
}
