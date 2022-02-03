package com.example.logo_ui_design_injava2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.logo_ui_design_injava2.R;
import com.example.logo_ui_design_injava2.adapter.ItemAdapter;
import com.example.logo_ui_design_injava2.model.ItemModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn_account;
    LinearLayout l1lists;
    RelativeLayout relativeSheet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        l1lists = findViewById(R.id.l1lists);
        initRecyclerView(getData());

        initViews();
    }

    private void initViews() {
        relativeSheet = findViewById(R.id.relative_sheet);
        btn_account = findViewById(R.id.getStarted);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(relativeSheet);
        btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
    }

    private void initRecyclerView(List<ItemModel> list) {
        RecyclerView rv = getRecyclerView();
        ItemAdapter adapter = new ItemAdapter(this,list);
        rv.setAdapter(adapter);
        initBottomDotc(adapter.getItemCount(),rv);
    }

    private RecyclerView getRecyclerView() {

        RecyclerView rv = new RecyclerView(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rv.setLayoutManager(layoutManager);
        l1lists.addView(rv);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rv);
        return rv;
    }

    private List<ItemModel> getData() {
        List<ItemModel> list = new ArrayList<>();
        for (int i = 0;i<3;i++) {
            ItemModel model = new ItemModel();
            list.add(model);
        }
        return list;
    }


    private void initBottomDotc(int itemCount,RecyclerView rv) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && itemCount > 0) {
             LinearLayout l1 = getBottomDotsLayout(itemCount);
            rv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                 @Override
                 public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                     int firstVisible = ((LinearLayoutManager)rv.getLayoutManager()).findFirstVisibleItemPosition();
                     int lastVisible = ((LinearLayoutManager)rv.getLayoutManager()).findLastVisibleItemPosition();
                     int total = 0;
                     for (int j=firstVisible;j<=lastVisible;j++) {
                         total++;
                     }
                     transitionDots(l1,lastVisible,total);
                 }
             });
        }

    }

    private void transitionDots(LinearLayout l1, int lastVisibleIndex, int totalVisibleItems) {
        for (int i = 0;i<l1.getChildCount();i++) {
            if (l1.getChildAt(i) instanceof TextView) {
                l1.getChildAt(i).setBackgroundResource(R.drawable.indicator1);
            }
        }

        for (int j = 0;j<totalVisibleItems;j++) {
            if (lastVisibleIndex>=0) {
                l1.getChildAt(lastVisibleIndex).setBackgroundResource(R.drawable.indicator0);
                lastVisibleIndex--;
            }
        }
    }

    private LinearLayout getBottomDotsLayout(int count) {
        LinearLayout l1 = new LinearLayout(this);
         l1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
         l1.setOrientation(LinearLayout.HORIZONTAL);
         l1.setGravity(Gravity.CENTER);
         for (int i = 0;i<count;i++) {
             TextView tv = new TextView(this);
             LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(25,25);
             params.setMargins(10,10,10,10);
             tv.setLayoutParams(params);
             tv.setBackgroundResource(R.drawable.indicator1);
             l1.addView(tv);
         }
        l1lists.addView(l1);
        return l1;
    }
}