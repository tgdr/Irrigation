package lty.buu.irrigation.adapter;

import java.util.ArrayList;



import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import lty.buu.irrigation.bean.ChartPagerBean;

/**
 * 图表view适配器
 * @author zongbingwu
 *
 */
public class ChartPagerAdapter extends PagerAdapter 
{
	private static final String TAG = "<ChartPagerAdapter>";
	//图表点的数据集合
	private ArrayList<ChartPagerBean> mDataArray;
	private Context mContext;
	private final LayoutInflater mInflater;
	private ArrayList<View> array;
	public ChartPagerAdapter(Context context, ArrayList<View> array) 
	{
		mContext = context;
		this.array=array;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager)container).removeView(array.get(position));
	}
	
	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		((ViewPager)container).addView(array.get(position));
		return array.get(position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

	
}