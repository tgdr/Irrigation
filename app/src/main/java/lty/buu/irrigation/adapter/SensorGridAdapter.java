
package lty.buu.irrigation.adapter;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lty.buu.irrigation.R;
import lty.buu.irrigation.bean.SensorBean;

/**
 * 传感器数据值适配器
 * @author 李天宇
 *
 */
public class SensorGridAdapter extends ArrayAdapter<SensorBean>
{

	private  class ViewHolder
	{
		LinearLayout bgLayout;//背景布局
		TextView nameTextView; //显示传感器名称
        ImageView stautsImageView;//显示传感器状态
        TextView setValeuTextView;//显示传感器的阀值
        TextView valueTextView;//显示传感器的值
	}
	//传感器值的集合
	private ArrayList<SensorBean> mDataArray;
	private Context mContext;
	private final LayoutInflater mInflater;
	
	public SensorGridAdapter(Context context, ArrayList<SensorBean> array)
    {
    	super(context, 0, array);
    	mContext = context;
    	mDataArray = array;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
	
	public int getCount()
	{
		return mDataArray.size();
	}
	
	public long getItemId(int position) 
    {
        return position;
    }
	
	@Override
	public SensorBean getItem(int position) 
	{
		return mDataArray.get(position);
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{
		try
		{
			SensorBean itemInfo = (SensorBean) getItem(position);
			ViewHolder holder = null;
			if (convertView == null)
			{
				convertView = mInflater.inflate(R.layout.sensor_grid_item, null);
				holder = new ViewHolder();
				//界面初始化
				holder.bgLayout= (LinearLayout)convertView.findViewById(R.id.sensor_item_layout);
				holder.nameTextView= (TextView)convertView.findViewById(R.id.sensor_name_text1);
				holder.stautsImageView= (ImageView) convertView.findViewById(R.id.status_image);
				holder.setValeuTextView= (TextView)convertView.findViewById(R.id.set_value_text);
				holder.valueTextView= (TextView)convertView.findViewById(R.id.sensor_value_text);
				convertView.setTag(holder);

			}
			else
			{
				holder = (ViewHolder)convertView.getTag();
			}

				holder.nameTextView.setText(itemInfo.getName());
				holder.valueTextView.setText(""+itemInfo.getValue());

				//显示设定值内容
				String setStr = mContext.getString(R.string.set_value);
				final int invalidMin = Integer.MIN_VALUE;
				int minV = itemInfo.getMinValue();
				int maxV = itemInfo.getMaxValue();
				if(minV>invalidMin && maxV>invalidMin){
					setStr += minV+"~"+maxV;
				} else if(minV>invalidMin) {
					setStr += ">"+minV;
				} else if(maxV>invalidMin) {
					setStr += "<"+maxV;
				}
				holder.setValeuTextView.setText(setStr);

				//根据传感器的值来判断当前是否应该告警
				boolean isWarning = false;
				int value = itemInfo.getValue();
				if(value>invalidMin && ((minV>invalidMin || maxV>invalidMin)))
				{
					if(minV>invalidMin && value<minV){
						isWarning = true;
					}
					if(maxV>invalidMin && value>maxV){
						isWarning = true;
					}
				}
				if(isWarning){
					//如果需要告警，背景则显示红色
					holder.stautsImageView.setImageResource(R.drawable.nomal);

				} else {
					//不必告警则显示绿色
					holder.stautsImageView.setImageResource(R.drawable.jinggao);
				}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return convertView;
	}
}
