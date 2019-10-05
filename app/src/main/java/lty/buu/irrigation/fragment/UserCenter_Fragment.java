package lty.buu.irrigation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;



import org.json.JSONObject;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.R;
import lty.buu.irrigation.activity.CameraAnlysisActivity;
import lty.buu.irrigation.defui.ItemView;


public class UserCenter_Fragment extends Fragment {
    private ImageView mHBack;
    private ImageView mHHead;
    private ImageView mUserLine;
    private TextView mUserName;
    private TextView mUserVal;
    private ItemView mScanButton;
    private ItemView mSex;
    private ItemView mSignName;
    private ItemView mPass;
    private ItemView mPhone;
    private ItemView mAbout;
    private ItemView mclassinfo;
    JSONObject userinfo;
    IrrigationApplication app;
    public static String TAG = "UserCenter_Fragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usercenter_content, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        app= (IrrigationApplication) getActivity().getApplication();
        initView();
        setData();
    }

    private void setData() {

                        //app.setStuid(userinfo.getString("id"));
                        // tvsign.setText(userinfo.getString("introduction"));
                        mUserName.setText(app.getUsername());
                     //   mNickName.setRightDesc(app.getIpstr());
                        String phone = app.getUsername();
                        mSignName.setRightDesc(app.getIpstr());
                        mUserVal.setText("130" + "****" + "0892");
                   //     mSex.setRightDesc("男");
                        //设置背景磨砂效果
                        Glide.with(getActivity()).load(R.drawable.appicon)
                                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity())).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .into(mHBack);
                        //设置圆形图像
                        Glide.with(getActivity()).load(R.drawable.appicon)
                                .bitmapTransform(new CropCircleTransformation(getActivity())).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .into(mHHead);

                    //    mNickName.setRightDesc("hahaha");
                     //   mclassinfo.setRightDesc("hahaha");


        mAbout.setRightDesc("1.0.1");
        mAbout.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
                Toast.makeText(getActivity(), "已经是最新版了哦～", Toast.LENGTH_SHORT).show();
            }
        });


        //设置用户名整个item的点击事件
        mScanButton.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
                Intent it = new Intent(getActivity(), CameraAnlysisActivity.class);
                startActivity(it);
            }
        });
        //修改用户名item的左侧图标
        //   mNickName.setLeftIcon(R.drawable.ic_phone);
        //
        //  mNickName.setLeftTitle("修改后的用户名");
        //   mNickName.setRightDesc("名字修改");
        mScanButton.setShowRightArrow(false);
        mScanButton.setShowBottomLine(false);

        //设置用户名整个item的点击事件
        mScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity()
                        , "我是onclick事件显示的", Toast.LENGTH_SHORT).show();
            }
        });

        mSex.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
                getFragmentManager().beginTransaction().replace(R.id.mycontent,new PumpFragment()).commit();
            }
        });
        mclassinfo.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {

            }
        });
        mSignName.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
                LayoutInflater factory = LayoutInflater.from(getActivity());//提示框
//                final View view = factory.inflate(R.layout.dialog_intro, null);//这里必须是final的
//                final EditText etintro = view.findViewById(R.id.etIntro);//获得输入框对象
//
//                new AlertDialog.Builder(getActivity())
//                        .setTitle("请输入新的个性签名")//提示框标题
//                        .setView(view)
//                        .setPositiveButton("确定",//提示框的两个按钮
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {
//                                        if (etintro.getText().equals("") || etintro.getText() == null || etintro.getText().length() == 0) {
//                                            Toast.makeText(getActivity(), "个性签名输入不合法,本次设置无效", Toast.LENGTH_SHORT).show();
//                                            return;
//                                        } else {
//                                            try {
//                                                final JSONObject object = new JSONObject();
//                                                object.put("introduction", etintro.getText().toString());
//                                                //事件
//                                                OkHttpUtils.patch().addHeader("Authorization", "Bearer " + app.getToken())
//                                                        .requestBody(new RequestBody() {
//                                                            @Override
//                                                            public MediaType contentType() {
//                                                                return MediaType.parse("application/json; charset=utf-8");
//                                                            }
//
//                                                            @Override
//                                                            public void writeTo(BufferedSink sink) throws IOException {
//                                                                sink.outputStream().write(object.toString().getBytes());
//                                                            }
//
//                                                        })
//                                                        .url(BaseRequest.BASEURL + "users/" + app.getStuid() + "/intro").build()
//                                                        .execute(new StringCallback() {
//                                                            @Override
//                                                            public void onError(Call call, Exception e, int id) {
//                                                                //   Log.e(TAG, e.toString());
//                                                            }
//
//                                                            @Override
//                                                            public void onResponse(String response, int id) {
//                                                                // Log.e(TAG, response.toString());
//                                                                mSignName.setRightDesc(etintro.getText().toString());
//                                                            }
//
//                                                        });
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//
//
//                                    }
//                                }).setNegativeButton("取消", null).setCancelable(false).create().show();

            }
        });
        mPhone.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
                Toast.makeText(getActivity(), "请联系作者985094108@qq.com修改", Toast.LENGTH_SHORT).show();
            }
        });
        mPass.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
//                Toast.makeText(getActivity(), "即将推出", Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().replace(R.id.mycontent,new frag_irr_knowledge()).commit();

            }
        });
    }

    private void initView() {
        //顶部头像控件
        mHBack = getView().findViewById(R.id.h_back);
        mHHead = getView().findViewById(R.id.h_head);
        mUserLine = getView().findViewById(R.id.user_line);
        mUserName = getView().findViewById(R.id.user_name);
        mUserVal = getView().findViewById(R.id.user_val);
        //下面item控件
        mScanButton = getView().findViewById(R.id.scanbutton);
        mSex = getView().findViewById(R.id.sex);
        mSignName = getView().findViewById(R.id.signName);
        mPass = getView().findViewById(R.id.pass);
        mPhone = getView().findViewById(R.id.phone);
        mAbout = getView().findViewById(R.id.about);
        mclassinfo = getView().findViewById(R.id.classinfo);
        mclassinfo.setShowRightArrow(false);
        mclassinfo.setShowBottomLine(false);
    }
}
