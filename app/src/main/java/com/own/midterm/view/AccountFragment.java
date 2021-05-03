package com.own.midterm.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.own.midterm.R;
import com.own.midterm.base.BaseFragment;
import com.own.midterm.base.CallBack;
import com.own.midterm.util.MyJSON.MyJSON;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.own.midterm.util.MyJSON.MyJSON.SERVER_LOC;
import static com.own.midterm.util.Other.makeStatusBarTransparent;

public class AccountFragment extends BaseFragment {

    private ImageView img;

    private RelativeLayout layout;

    private TextView uid_text;

    private TextView num;

    private String uid="123456";

    private String head="touxiang.png";

    private RelativeLayout demoLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences getAc = getContext().getSharedPreferences("account",MODE_PRIVATE);
        uid = getAc.getString("uid","123456");

        final SharedPreferences sp = getContext().getSharedPreferences(uid,MODE_PRIVATE);
        MyJSON myJSON = new MyJSON(getContext(), new CallBack() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFail() {

            }
        });
        myJSON.request("Selflostproperty.php"+"?uid=",uid);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void initView() {
        makeStatusBarTransparent(getActivity());
        layout=getView().findViewById(R.id.lay4);
        layout.setOnClickListener(this);
        num=getView().findViewById(R.id.fb);
        uid_text=getView().findViewById(R.id.uid_tx);
        uid_text.setText("uid:"+uid);
        img= Objects.requireNonNull(getView()).findViewById(R.id.tx_img);
        demoLayout = Objects.requireNonNull(getView()).findViewById(R.id.lay5);
        demoLayout.setOnClickListener(this);
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform();
        Glide.with(Objects.requireNonNull(getContext())).load(SERVER_LOC+"/zixi/LostImage/"+head)
                .apply(mRequestOptions).into(img);
    }

    @Override
    public void initListener() {
    }

    @Override
    public int getContentViewID() {
        return R.layout.account_layout;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        super.onClick(v);
        switch (v.getId()){
            case R.id.lay4:
                intent = new Intent(v.getContext(),MyPostActivity.class);
                v.getContext().startActivity(intent);
                break;
            case R.id.lay5:
                intent = new Intent(v.getContext(), DemoActivity.class);
                v.getContext().startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void destroy() {
    }

}
