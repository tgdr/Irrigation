package lty.buu.irrigation.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.R;
import lty.buu.irrigation.http.HttpRequest;
import lty.buu.irrigation.http.REQNAME;


public class frag_realtimesensor extends Fragment implements View.OnClickListener {
    IrrigationApplication app;
    private TextView tvTemp;
    private LineChart weatherLine;
    private Button btnRefresh;
    private TextView tvLight;
    private TextView tvLightValues;
    private TextView tvsd;
    private TextView tvHumidityValues;
    private TextView tvwdzt;
    private TextView tvTempValues;
    private TextView tvsbzt;
    private TextView tvSpread;
    private TextView tvSoilValues;
    private ViewPager pager;
    private TextView tvTip;
    private TextView tvPmFg;
    private TextView tvTempFg;
    private TextView tvHumidityFg;
    private TextView tvCoFg;

    private TextView tv_light_hint;
    private TextView tv_humidity_hint;
    private TextView tv_temp_hint;

    private TextView tv_soil_hint;

    private Timer timer;
    private TimerTask timerTask;
    private int tempValues;
    private static List<Integer> pmList;
    private static List<Integer> soilList;
    private static List<Integer> tempList;
    private static List<Integer> lightList;
    private static List<Integer> humidityList;
    private static Handler soilHandler;
    private static Handler tempHandler;
    private static Handler lightHandler;
    private static Handler humidityHandler;
    private List<Integer> maxList;
    private List<Integer> minList;
    private FragmentPagerAdapter pagerAdapter;
    private List<Fragment> data;
    private String[] tips = {"光照度", "温度", "湿度", "土壤湿度"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_6, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        app = (IrrigationApplication) getActivity().getApplication();
        tvTemp = (TextView) view.findViewById(R.id.tv_temp);
        weatherLine = (LineChart) view.findViewById(R.id.weatherLine);
        btnRefresh = (Button) view.findViewById(R.id.btn_refresh);
        tvLight = (TextView) view.findViewById(R.id.tv_light);
        tvLightValues = (TextView) view.findViewById(R.id.tv_light_values);
        tvsd = (TextView) view.findViewById(R.id.tv_sd);
        tvHumidityValues = (TextView) view.findViewById(R.id.tv_humidity_values);
        tvwdzt = (TextView) view.findViewById(R.id.tv_wdzt);
        tvTempValues = (TextView) view.findViewById(R.id.tv_temp_values);
        tvsbzt = (TextView) view.findViewById(R.id.tv_sbzt);

        tvSpread = (TextView) view.findViewById(R.id.tv_spread);
        tvSoilValues = (TextView) view.findViewById(R.id.tv_soil_values);
        pager = (ViewPager) view.findViewById(R.id.pager);
        tvTip = (TextView) view.findViewById(R.id.tv_tip);
        tvPmFg = (TextView) view.findViewById(R.id.tv_pm_fg);
        tvTempFg = (TextView) view.findViewById(R.id.tv_temp_fg);
        tvHumidityFg = (TextView) view.findViewById(R.id.tv_humidity_fg);
        tvCoFg = (TextView) view.findViewById(R.id.tv_co_fg);

        tv_light_hint= (TextView) view.findViewById(R.id.tv_light_hint);
        tv_humidity_hint= (TextView) view.findViewById(R.id.tv_humidity_hint);
        tv_temp_hint= (TextView) view.findViewById(R.id.tv_temp_hint);
        tv_soil_hint= (TextView) view.findViewById(R.id.tv_soil_hint);



        btnRefresh.setOnClickListener(this);
        tvPmFg.setOnClickListener(this);
        tvTempFg.setOnClickListener(this);
        tvHumidityFg.setOnClickListener(this);
        tvCoFg.setOnClickListener(this);

        tempValues = 24;
        pmList = new ArrayList<Integer>();
        soilList = new ArrayList<Integer>();
        tempList = new ArrayList<Integer>();
        lightList = new ArrayList<Integer>();
        humidityList = new ArrayList<Integer>();

        initLine();
        setLineData(tempValues);

        data = new ArrayList<Fragment>();
        data.add(new Light());
        data.add(new TempFragment());
        data.add(new HumidityFragment());
        data.add(new CoFragment());
        pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return data.get(position);
            }

            @Override
            public int getCount() {
                return data.size();
            }
        };
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvTip.setText(tips[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_refresh:
                tvTemp.setText("室温："+tempValues + "°");
                setLineData(tempValues);
                break;
            case R.id.tv_pm_fg:
                pager.setCurrentItem(0);
                break;
            case R.id.tv_temp_fg:
                pager.setCurrentItem(1);
                break;
            case R.id.tv_humidity_fg:
                pager.setCurrentItem(2);
                break;
            case R.id.tv_co_fg:
                pager.setCurrentItem(3);
                break;
        }
    }

    public void initLine() {
        weatherLine.getXAxis().setEnabled(false);
        weatherLine.getAxisLeft().setEnabled(false);
        weatherLine.getAxisRight().setEnabled(false);
        weatherLine.getLegend().setEnabled(false);

        weatherLine.setDescription("");
        maxList = new ArrayList<Integer>();
        maxList.add(22);
        maxList.add(24);
        maxList.add(25);

        maxList.add(25);
        maxList.add(25);
        maxList.add(22);
        minList = new ArrayList<Integer>();
        minList.add(14);
        minList.add(15);
        minList.add(16);

        minList.add(17);
        minList.add(16);
        minList.add(16);

    }

    public void setLineData(int values) {
        maxList.set(1, values + 4);
        minList.set(1, values - 4);

        List<String> xVals = new ArrayList<String>();
        List<Entry> yVals1 = new ArrayList<Entry>();
        List<Entry> yVals2 = new ArrayList<Entry>();
        for (int i = 0; i < minList.size(); i++) {
            xVals.add(i + "");
            yVals1.add(new Entry(maxList.get(i), i));
            yVals2.add(new Entry(minList.get(i), i));
        }
        LineDataSet dataSet1 = new LineDataSet(yVals1, "");
        dataSet1.setDrawCircleHole(false);
        dataSet1.setColor(ColorTemplate.getHoloBlue());
        dataSet1.setCircleSize(2);
        dataSet1.setValueTextSize(12);
        dataSet1.setValueTextColor(ColorTemplate.VORDIPLOM_COLORS[4]);
        dataSet1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return (int) v + "°";
            }
        });
        LineDataSet dataSet2 = new LineDataSet(yVals2, "");
        dataSet2.setDrawCircleHole(false);
        dataSet2.setColor(ColorTemplate.getHoloBlue());
        dataSet2.setCircleSize(2);
        dataSet2.setValueTextSize(12);
        dataSet2.setValueTextColor(ColorTemplate.VORDIPLOM_COLORS[4]);
        dataSet2.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return (int) v + "°";
            }
        });
        List<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(dataSet1);
        dataSets.add(dataSet2);
        LineData data = new LineData(xVals, dataSets);
        weatherLine.setData(data);
        weatherLine.invalidate();
    }

    @Override
    public void onResume() {
        super.onResume();
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };
        timer.schedule(timerTask, 0, 4500);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null)
            timer.cancel();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getAllSense();
        }
    };


    public void sendValues(List<Integer> list, int values, Handler handler) {
        if (list.size() == 20) {
            list.remove(19);
            list.add(0, values);
        } else {
            list.add(values);
        }
        if (handler != null)
            handler.sendEmptyMessage(1);
    }

    public void setValues(TextView tv, int values, int max, int warn, String str1, String str2, String str3) {
        if (values > max) {
            tv.setText(str1);
        } else if (values <= max && values >= warn) {
            tv.setText(str2);
        } else {
            tv.setText(str3);
        }
    }
    public void getAllSense() {
        try {
            HttpRequest.request(REQNAME.GetRealTimeSensorValues, new JSONObject().put("Username", app.getUserpermission()), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    if (TextUtils.isEmpty(jsonObject.toString()))
                        return;
                    try {
                        if(jsonObject.getString("result").equals("ok")) {
                           // int pm = jsonObject.getInt("pm2.5");
                          //  int co = jsonObject.getInt("co2");
                            tempValues = jsonObject.getInt("Irrtemperature");
                            int light = jsonObject.getInt("Irrlight");
                            int humidity = jsonObject.getInt("Irrhumidity");
                            int soil = jsonObject.getInt("Irrsoilhum");
        Log.e("light",light+"");
                           // tvsbzt.setText("(" + pm + ")");
                            tvSoilValues.setText("(" + soil + ")");
                            tvTempValues.setText("(" + tempValues + ")");
                            tvLightValues.setText("(" + light + ")");
                            tvHumidityValues.setText("(" + humidity + ")");

                            setValues(tvLight, light, 3500, 2000, "强", "中", "弱");
                            setValues(tvsd, humidity, 80, 40, "潮", "中", "干");
                            setValues(tvwdzt, tempValues, 35, 15, "热", "宜", "冷");
                           // setValues(tvsbzt, pm, 250, 125, "不宜", "一般", "适宜");
                         //   setValues(tvSpread, co, 8000, 4000, "快", "良", "优");

                            setValues(tv_light_hint, light, 3500, 2000, "紫外线较强，注意防晒", "紫外线一般，较适宜", "多给作物照射阳光");
                            setValues(tv_humidity_hint, humidity, 80, 40, "湿度高", "湿度中等", "湿度较低");
                            setValues(tv_temp_hint, tempValues, 35, 15, "温度较高,需调节", "温度适宜", "温度较低，注意防寒");
                        //    setValues(tv_pm_hint, pm, 250, 125, "天气糟糕，不宜运动", "可以适当运动", "非常适合运动");
                            setValues(tv_soil_hint, soil, 70, 30, "作物生产最佳湿度", "不需要浇水", "需要浇水");


                       //     sendValues(pmList, pm, pmHandler);
                            sendValues(soilList, soil, soilHandler);
                            sendValues(lightList, light, lightHandler);
                            sendValues(tempList, tempValues, tempHandler);
                            sendValues(humidityList, humidity, humidityHandler);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e("", volleyError.toString());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(time);
    }

    public static class Light extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_6_pm, container, false);
        }

        private BarChart barChart;
        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            barChart = (BarChart) view.findViewById(R.id.barChart);

            barChart.setDrawBarShadow(false);
            barChart.setDescription("");

            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(12);
            xAxis.setDrawGridLines(false);

            barChart.getAxisRight().setEnabled(false);
            YAxis leftAxis = barChart.getAxisLeft();
            leftAxis.setTextSize(12);
            leftAxis.setAxisMaxValue(8000);
            leftAxis.setAxisMinValue(0);

            barChart.getLegend().setEnabled(false);

            setData(pmList);

            lightHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    setData(lightList);
                }
            };
        }

        public void setData(List<Integer> list) {
            long time = System.currentTimeMillis();
            List<String> xValues = new ArrayList<String>();
            List<BarEntry> yValues = new ArrayList<BarEntry>();
            for (int i = 0; i < 20; i++) {
                xValues.add(getTime(time));
                time -= 4500;
                if (i >= list.size()) {
                    yValues.add(new BarEntry(0, i));
                } else {
                    yValues.add(new BarEntry(list.get(i), i));
                }
            }
            BarDataSet dataSet = new BarDataSet(yValues, "");
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    if (v == 0)
                        return "";
                    return (int) v + "";
                }
            });
            dataSet.setValueTextSize(14);
            dataSet.setValueTextColor(ColorTemplate.getHoloBlue());

            BarData data = new BarData(xValues, dataSet);

            barChart.setData(data);
            barChart.invalidate();
        }
    }

    public static class TempFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_6_temp, container, false);
        }

        private LineChart lineChart;

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            lineChart = (LineChart) view.findViewById(R.id.lineChart);
            lineChart.setDescription("");

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(12);
            xAxis.setDrawGridLines(false);

            lineChart.getAxisRight().setEnabled(false);
            YAxis leftAxis = lineChart.getAxisLeft();
            leftAxis.setTextSize(12);
            leftAxis.setAxisMaxValue(50);
            leftAxis.setAxisMinValue(0);
            lineChart.getLegend().setEnabled(false);

            setData(tempList);

            tempHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    setData(tempList);
                }
            };
        }

        public void setData(List<Integer> list) {
            long time = System.currentTimeMillis();
            List<String> xValues = new ArrayList<String>();
            List<Entry> yValues = new ArrayList<Entry>();
            for (int i = 0; i < 20; i++) {
                xValues.add(getTime(time));
                time -= 4500;
                if (i >= list.size()) {
                    yValues.add(new BarEntry(0, i));
                } else {
                    yValues.add(new Entry(list.get(i), i));
                }
            }
            LineDataSet dataSet = new LineDataSet(yValues, "");
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    if (v == 0)
                        return "";
                    return (int) v + "";
                }
            });
            dataSet.setValueTextSize(14);
            dataSet.setValueTextColor(ColorTemplate.getHoloBlue());
            dataSet.setDrawCircleHole(false);
            dataSet.setColor(ColorTemplate.getHoloBlue());
            dataSet.setCircleSize(4);
            dataSet.setLineWidth(2);

            LineData data = new LineData(xValues, dataSet);

            lineChart.setData(data);
            lineChart.invalidate();
        }
    }

    public static class HumidityFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_6_humidity, container, false);
        }

        private LineChart lineChart;

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            lineChart = (LineChart) view.findViewById(R.id.lineChart);
            lineChart.setDescription("");

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(12);
            xAxis.setDrawGridLines(false);

            lineChart.getAxisRight().setEnabled(false);
            YAxis leftAxis = lineChart.getAxisLeft();
            leftAxis.setTextSize(12);
            leftAxis.setAxisMaxValue(600);
            leftAxis.setAxisMinValue(0);
            lineChart.getLegend().setEnabled(false);

            setData(humidityList);

            humidityHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    setData(humidityList);
                }
            };
        }

        public void setData(List<Integer> list) {
            long time = System.currentTimeMillis();
            List<String> xValues = new ArrayList<String>();
            List<Entry> yValues = new ArrayList<Entry>();
            for (int i = 0; i < 20; i++) {
                xValues.add(getTime(time));
                time -= 4500;
                if (i >= list.size()) {
                    yValues.add(new BarEntry(0, i));
                } else {
                    yValues.add(new Entry(list.get(i), i));
                }
            }
            LineDataSet dataSet = new LineDataSet(yValues, "");
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    if (v == 0)
                        return "";
                    return (int) v + "";
                }
            });
            dataSet.setValueTextSize(14);
            dataSet.setValueTextColor(ColorTemplate.getHoloBlue());
            dataSet.setDrawCircleHole(false);
            dataSet.setColor(ColorTemplate.getHoloBlue());
            dataSet.setCircleSize(4);
            dataSet.setLineWidth(2);

            LineData data = new LineData(xValues, dataSet);

            lineChart.setData(data);
            lineChart.invalidate();
        }
    }

    public static class CoFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_6_co, container, false);
        }

        private LineChart lineChart;

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            lineChart = (LineChart) view.findViewById(R.id.lineChart);
            lineChart.setDescription("");

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(12);
            xAxis.setDrawGridLines(false);

            lineChart.getAxisRight().setEnabled(false);
            YAxis leftAxis = lineChart.getAxisLeft();
            leftAxis.setTextSize(12);
            leftAxis.setAxisMaxValue(600);
            leftAxis.setAxisMinValue(0);
            lineChart.getLegend().setEnabled(false);

            setData(soilList);

            soilHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    setData(soilList);
                }
            };
        }

        public void setData(List<Integer> list) {
            long time = System.currentTimeMillis();
            List<String> xValues = new ArrayList<String>();
            List<Entry> yValues = new ArrayList<Entry>();
            for (int i = 0; i < 20; i++) {
                xValues.add(getTime(time));
                time -= 4500;
                if (i >= list.size()) {
                    yValues.add(new BarEntry(0, i));
                } else {
                    yValues.add(new Entry(list.get(i), i));
                }
            }
            LineDataSet dataSet = new LineDataSet(yValues, "");
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    if (v == 0)
                        return "";
                    return (int) v + "";
                }
            });
            dataSet.setValueTextSize(14);
            dataSet.setValueTextColor(ColorTemplate.getHoloBlue());
            dataSet.setDrawCircleHole(false);
            dataSet.setColor(ColorTemplate.getHoloBlue());
            dataSet.setCircleSize(4);
            dataSet.setLineWidth(2);
            dataSet.setDrawFilled(true);
            dataSet.setFillColor(ColorTemplate.getHoloBlue());

            LineData data = new LineData(xValues, dataSet);

            lineChart.setData(data);
            lineChart.invalidate();
        }
    }
}
