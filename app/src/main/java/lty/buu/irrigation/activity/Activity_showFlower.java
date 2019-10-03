package lty.buu.irrigation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import lty.buu.irrigation.R;
import lty.buu.irrigation.adapter.CardData;
import lty.buu.irrigation.adapter.RecyclerViewAdapter;

public class Activity_showFlower extends Activity {
    String datas;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private ArrayList<CardData> dataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datas = getIntent().getStringExtra("data");
        dataList = new ArrayList<>();
        //  Log.e("hhhhh",datas);
        initData();

    }

    private void initData() {
        Log.e("666666666", "666666666");
        final ArrayList image_url = new ArrayList();
        final ArrayList name = new ArrayList();
        final ArrayList description = new ArrayList();
        final ArrayList score = new ArrayList();
        final ArrayList tv_num = new ArrayList();
        try {
            JSONObject object = new JSONObject(datas);
            //     Log.e("777777","77777777");

            JSONArray array = object.getJSONArray("result");
            if (array.length() == 1 && array.getJSONObject(0).getDouble("score") == 0) {

                finish();
            } else {
                //   Log.e("88888","88888          "+array.length());
                //  Log.e("datasarray",array.toString());
                int youxiaoitem = 0;
                for (int i = 0; i < array.length(); i++) {
                    if (!array.getJSONObject(i).getJSONObject("baike_info").has("image_url")) {
                        continue;
                    }
                    youxiaoitem++;
                    //   Log.e("aaaaaa","aaaaaa");
                    tv_num.add(youxiaoitem);
                    //     Log.e("bbbbbbbb","bbbbbbbbbb");
                    score.add(new DecimalFormat(".0").format(array.getJSONObject(i).getDouble("score") * 100));
                    //  Log.e("cccccccc","cccccccccc");
                    name.add(array.getJSONObject(i).getString("name"));
                    //   Log.e("dddddddddd","dddddddd");
                    String imgurl = array.getJSONObject(i).getJSONObject("baike_info").getString("image_url");
                    String descri = array.getJSONObject(i).getJSONObject("baike_info").getString("description");
                    image_url.add(imgurl);
                    //   Log.e("eeeee",imgurl);
                    description.add(descri);
                    //    Log.e("fffffff","fffffff");
                }
                for (int i = 0; i < tv_num.size(); i++) {
                    // Log.e("idididid",_id.get(i).toString());
                    dataList.add(new CardData(image_url.get(i).toString(), name.get(i).toString(), description.get(i).toString(), score.get(i).toString(), tv_num.get(i).toString(), true));
                    //   Log.e("99999","99999"+array.length());
                }

                setContentView(R.layout.activity_cardview_layout);
                recyclerView = findViewById(R.id.recycler_view);
                RecyclerView.LayoutManager linearManager = new LinearLayoutManager(this);
                mAdapter = new RecyclerViewAdapter(Activity_showFlower.this, dataList);
                //      Log.e("nnnnnnnnnn","222222222");
                //设置Adapter
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(linearManager);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                this.finish();
                break;

        }
        return super.onKeyDown(keyCode, event);
    }
}
