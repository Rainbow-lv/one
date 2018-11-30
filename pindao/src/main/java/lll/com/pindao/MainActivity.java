package lll.com.pindao;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import lll.com.pindao.fragment.BlankFragment;
import lll.com.pindao.fragment.Frag_01;
import lll.com.pindao.fragment.Frag_02;
import lll.com.pindao.fragment.Frag_03;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private TabLayout tab;
    private Button button;
    ArrayList<Fragment> fraglist = new ArrayList<Fragment>();
    ArrayList<ChannelBean> channlist = new ArrayList<ChannelBean>();
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找控件
        pager = findViewById(R.id.pager);
        tab = findViewById(R.id.tab);
        button = findViewById(R.id.but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ChanneelActivity提供的大可自己的方法
                ChannelActivity.startChannelActivity(MainActivity.this,channlist);
            }
        });
        channlist.add(new ChannelBean("推荐",true));
        channlist.add(new ChannelBean("热点",true));
        channlist.add(new ChannelBean("娱乐",false));
        channlist.add(new ChannelBean("要闻",false));
        channlist.add(new ChannelBean("体育",false));
        channlist.add(new ChannelBean("时尚",false));
        for (int i=0;i<channlist.size();i++){
            if (channlist.get(i).isSelect()){
                String name = channlist.get(i).getName();
                tab.addTab(tab.newTab().setText(name));
                if (i == 0){
                    fraglist.add(new Frag_01());
                }else if (i == 1){
                    fraglist.add(new Frag_02());
                } else if (i == 2){
                    fraglist.add(new Frag_03());
                }else {
                    fraglist.add(new BlankFragment());
                }
            }

        }
        //设置适配器
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(myPagerAdapter);
        tab.setupWithViewPager(pager);
    }
    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fraglist.get(i);
        }

        @Override
        public int getCount() {
            return fraglist.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return channlist.get(position).getName();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String json = data.getStringExtra(ChannelActivity.RESULT_JSON_KEY);

        Type type = new TypeToken<ArrayList<ChannelBean>>() {}.getType();

        Gson gson = new Gson();
        channlist = gson.fromJson(json, type);
        tab.removeAllTabs();
        fraglist.clear();
        for (int i =0;i<channlist.size();i++){
            ChannelBean channelBean = channlist.get(i);

            if (channelBean.isSelect()){
                String tabName = channlist.get(i).getName();
                Log.e("AAAAAAAAA", "------------"+tabName);
                tab.addTab(tab.newTab().setText(tabName));

                if ("推荐".equals(channelBean.getName())){
                    fraglist.add(new Frag_01());
                }else if ("热点".equals(channelBean.getName())){
                    fraglist.add(new Frag_02());
                } else if ("娱乐".equals(channelBean.getName())){
                    fraglist.add(new Frag_03());
                }else {
                    fraglist.add(new BlankFragment());
                }
            }
        }
        myPagerAdapter.notifyDataSetChanged();
    }
}
