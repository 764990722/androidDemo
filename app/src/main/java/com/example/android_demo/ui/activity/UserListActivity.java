package com.example.android_demo.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.android_demo.R;
import com.example.android_demo.databinding.ActivityUserlistBinding;
import com.example.android_demo.repository.Network;
import com.example.android_demo.repository.Scheduler;
import com.example.android_demo.repository.Subs;
import com.example.android_demo.repository.entity.receive.RUserList;
import com.example.android_demo.repository.net.AppValue;
import com.example.android_demo.ui.base.BaseActivity;
import com.example.android_demo.utils.Base64Utils;
import com.example.android_demo.utils.JSON;
import com.example.android_demo.utils.Text;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by: PeaceJay
 * Created date: 2021/03/30.
 * Description: 注册
 */
public class UserListActivity extends BaseActivity<ActivityUserlistBinding> {

    private static final String TAG = UserListActivity.class.getSimpleName();
    private BaseQuickAdapter<RUserList.ListBean, BaseViewHolder> mAdapter;
    private List<RUserList.ListBean> list = new ArrayList<>();

    @Override
    protected int onLayout() {
        return R.layout.activity_userlist;
    }

    @Override
    protected void onObject() {
        binding.setActivity(this);
    }

    @Override
    protected void onView() {

    }

    @Override
    protected void onData() {
        rvAdapter();//交易查询界面
        binding.smartRefreshLayout.setEnableAutoLoadMore(true);//是否底部自动上拉加载
        binding.smartRefreshLayout.setEnableLoadMore(true);//是否启用上拉加载
        binding.smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(false);//内容不满一页的时候，是否可以上拉加载更多
        binding.smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(500);
                viewUserList();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewUserList();
    }

    private void viewUserList() {
        Network.api().queryUser()
                .compose(Scheduler.apply())
                .subscribe(new Subs<RUserList>(2) {
                    @Override
                    public void onSuccess(RUserList data) {
                        Log.i(TAG, "onSuccess: " + JSON.toJson(data));
                        if (data!=null){
                            list.clear();
                            list.addAll(data.getList());
                            if (mAdapter != null) {
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }


    private void rvAdapter() {
        binding.rvTransaction.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaseQuickAdapter<RUserList.ListBean, BaseViewHolder>(R.layout.item_user_cont, list) {
            @Override
            protected void convert(BaseViewHolder helper, RUserList.ListBean item) {
                TextView tv_name = helper.getView(R.id.tv_name);
                TextView tv_phone = helper.getView(R.id.tv_phone);
                ImageView image_view = helper.getView(R.id.image_view);

                Glide.with(getContext())
                        .load(item.getHerdImage())
                        .apply(new RequestOptions().centerCrop().error(R.mipmap.ic_logo))
                        .into(image_view);

                tv_name.setText(item.getUsername());
                tv_phone.setText(String.format("%s %s",item.getPhone(),item.getPassword()));
            }
        };
        binding.rvTransaction.setFocusable(false);//解决显示位置
        binding.rvTransaction.setNestedScrollingEnabled(false);
        binding.rvTransaction.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(UserListActivity.this,UpDataActivity.class)
                        .putExtra("userData",list.get(position)));
            }
        });
    }




}