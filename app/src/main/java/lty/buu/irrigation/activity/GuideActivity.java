package lty.buu.irrigation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.R;

public class GuideActivity extends Activity {

    List<View> views;
    View view1,view2,view3;
    Button btnjinru,btntuichu;
    ViewPager pager;
    IrrigationApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        app = (IrrigationApplication) getApplication();
        if(app.getSpf().getString("firstrun",null) != null){
            Intent it = new Intent(GuideActivity.this,LoginActivity.class);
            startActivity(it);
            finish();
        }
        LayoutInflater inflater = getLayoutInflater();
        views = new ArrayList<>();
        view1 = inflater.inflate(R.layout.guide01,null);

        view2 = inflater.inflate(R.layout.guide02,null);
        view3 = inflater.inflate(R.layout.guide03,null);
        btnjinru = (Button) view3.findViewById(R.id.btnenter);
        btntuichu = (Button) view3.findViewById(R.id.btnexit);
        btnjinru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            app.getSpf().edit().putString("firstrun","false").commit();
                Intent it = new Intent(GuideActivity.this,LoginActivity.class);
                startActivity(it);
                finish();
            }
        });
        btntuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        views.add(view1);
        views.add(view2);
        views.add(view3);
        pager = (ViewPager) findViewById(R.id.pagerview);
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }
        };
        pager.setAdapter(adapter);
    }
}
