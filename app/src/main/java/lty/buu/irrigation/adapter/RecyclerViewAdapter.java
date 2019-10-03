package lty.buu.irrigation.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.haozhang.lib.SlantedTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lty.buu.irrigation.R;
import lty.buu.irrigation.defui.FoldTextView;
import lty.buu.irrigation.defui.SpannableFoldTextView;


/**
 * Created by lty on 2019-10-03.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CardViewHolder> {
    private Context context;
    Map piliangdata;
    private ArrayList<CardData> datalist;
    View view;
    ArrayList marjor, minor;
    Handler handler, handlerphoto;

    public RecyclerViewAdapter() {


    }



    /*
     * 带参构造函数，传入上下文和需要绑定的数据集合
     * */
    public RecyclerViewAdapter(Context cx, ArrayList datalist) {
        this.context = cx;
        this.datalist = datalist;


    }


    /*
     *创建ViewHolder，持有布局映射
     * */
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //通过布局加载器获取到CardView布局view
        view = LayoutInflater.from(context).inflate(R.layout.activity_cardview_content, parent, false);
        //通过获取到的布局view实例化一个自己实现的CardViewHolder
        CardViewHolder cardViewHolder = new CardViewHolder(view);
        //返回一个已绑定布局的viewHolder，避免重复findViewById()


        return cardViewHolder;
    }


    /*
     *onBindViewHolder方法做药作用就是将数据集绑定到布局view，以及添加一些事件点击监听
     * */
    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.CardViewHolder holder, final int position) {

        final int pos = position;


        holder.tv_card_num.setText(datalist.get(position).getTv_num()+"/"+datalist.size());
        holder.tv_card_name.setText(datalist.get(position).getName());

        Glide.with(context)
                //加载网址
                .load(datalist.get(position).getImage_url())
                //设置占位图
                .placeholder(R.mipmap.ic_launcher)
                //加载错误图
                .error(R.mipmap.ic_launcher)
                //磁盘缓存的处理
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img);
        holder.tv_card_score.setText(datalist.get(position).getScore()+"分");
        if (Float.valueOf(datalist.get(position).getScore())>15) {
            holder.cardView.setCardBackgroundColor(Color.argb(20, 50, 160, 0));
            //holder.cardImage.setImageResource(R.mipmap.form_checkbox_checked);

        }  else {
            holder.cardView.setCardBackgroundColor(Color.argb(20, 160, 50, 0));
         //   holder.cardImage.setImageResource(R.mipmap.icon_wait);
        }
        holder.tv_desc.setParentClick(true);
        holder.tv_desc.setText(datalist.get(position).getDescription());
//        holder.tv_desc.setFoldText("展开");
//        holder.tv_desc.setExpandText("fdfsdfsdfsdf");

                        //  Log.e("drtdrgtdfgcfgd",datalist.get(position).getCourseposition());
//        holder.tvcourseposition.setText(datalist.get(position).getCourseposition());
//        holder.tvcourseteacher.setText(datalist.get(position).getTeachername());

                        //给整个cardview添加点击事件
                        holder.cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });


    }





    /*
     * 返回recyclerview数据项的个数
     * */
    @Override
    public int getItemCount() {
        return datalist.size();
    }


    /*
     * 自定义实现viewholder类
     * */
    class CardViewHolder extends RecyclerView.ViewHolder {

        SlantedTextView tv_card_name;
        SpannableFoldTextView tv_desc;
        TextView tv_card_num,tv_card_score;
        ImageView img;
CardView cardView;

        public CardViewHolder(View itemView) {
            super(itemView);
            tv_card_name = itemView.findViewById(R.id.tv_card_name);
            cardView = itemView.findViewById(R.id.cv1);
            tv_card_num = itemView.findViewById(R.id.tv_card_num);
            tv_card_score = itemView.findViewById(R.id.tv_card_score);
            img = itemView.findViewById(R.id.card_img);
            tv_desc= itemView.findViewById(R.id.tv_card_des);
        }
    }

}