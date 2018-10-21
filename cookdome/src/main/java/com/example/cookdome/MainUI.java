package com.example.cookdome;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fragment.ClassIficationFragment;
import com.example.fragment.CollectionFragment;
import com.example.fragment.MainFragment;
import com.example.fragment.SearchFragment;
import com.example.view.PagerEnabledSlidingPaneLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainUI extends AppCompatActivity implements View.OnClickListener{

    TextView title_tv;
    ViewPager viewPager;
    ImageView main_btn,classification_btn,search_btn,collection_btn;
    ImageView [] imageViews ;
    Button exit_btn;
    FragmentPagerAdapter fragmentPagerAdapter;

    DrawerLayout drawerLayout;

    List<Fragment> fragments;

    float scale;

    public static final String [] titles = new String[]{"菜谱大全","菜谱分类","搜索","收藏"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initDP();
        initView();
        //initData();
    }

    public void initView(){
        drawerLayout = findViewById(R.id.drawer_menu);
        exit_btn = findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title_tv = findViewById(R.id.title_tv);
        viewPager = findViewById(R.id.viewpager);
        main_btn = findViewById(R.id.main_btn);
        classification_btn = findViewById(R.id.classification_btn);
        search_btn = findViewById(R.id.search_btn);
        collection_btn = findViewById(R.id.collection_btn);
        imageViews = new ImageView[]{main_btn,classification_btn,search_btn,collection_btn};
        main_btn.setOnClickListener(this);
        classification_btn.setOnClickListener(this);
        search_btn.setOnClickListener(this);
        collection_btn.setOnClickListener(this);
        //初始化ViewPager
        fragments = new ArrayList<>();
        fragments.add(new MainFragment());
        fragments.add(new ClassIficationFragment());
        fragments.add(new SearchFragment());
        fragments.add(new CollectionFragment());
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                title_tv.setText(titles[position]);
                for(int i = 0 ; i < imageViews.length ; i++){
                    if(i==position){
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,Math.round(scale*42),1);
                        imageViews[i].setLayoutParams(params);
                    }else {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,Math.round(scale*32),1);
                        imageViews[i].setLayoutParams(params);
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void initData(){

    }

    public void initDP(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        scale = metrics.density;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_btn:
                viewPager.setCurrentItem(0);
                break;
            case R.id.classification_btn:
                viewPager.setCurrentItem(1);
                break;
            case R.id.search_btn:
                viewPager.setCurrentItem(2);
                break;
            case R.id.collection_btn:
                viewPager.setCurrentItem(3);
                break;
        }
    }

    public void openMenu(View v) {
        drawerLayout.openDrawer(Gravity.LEFT);
    }
}
