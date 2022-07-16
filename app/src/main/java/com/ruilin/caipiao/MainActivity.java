package com.ruilin.caipiao;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ruilin.caipiao.ui.CaipiaoFragment;
import com.ruilin.caipiao.ui.PasswordListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.vp_fragments)
    ViewPager vpFragments;
    @BindView(R.id.rb_caipiao)
    RadioButton rbCaipiao;
    @BindView(R.id.rb_pwd)
    RadioButton rbPwd;
    @BindView(R.id.rg_tab)
    RadioGroup rgTab;

    private MainFragmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mAdapter.addFragment(CaipiaoFragment.getInstance(),"caipiao");
        mAdapter.addFragment(PasswordListFragment.getInstance(), "pwd");

        vpFragments.setAdapter(mAdapter);
        vpFragments.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (0 == position) {
                    rbCaipiao.setChecked(true);
                } else {
                    rbPwd.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (group.isPressed()) return;
                if (checkedId == R.id.rb_caipiao) {
                    vpFragments.setCurrentItem(0);
                } else {
                    vpFragments.setCurrentItem(1);
                }
            }
        });
    }

    public class MainFragmentAdapter extends FragmentStatePagerAdapter {

        private static final String t = "MainFragmentAdapter";

        private List<Fragment> list = new ArrayList<Fragment>();
        private List<String> listTitle = new ArrayList<String>();

        public MainFragmentAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public Fragment getItem(int position) {
            //Log.d(t, "getItem position=" + position);
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
		/*FragmentTitleInterface fti = (FragmentTitleInterface)list.get(position);

		//Log.d(t, "fti.getTitle()=" + fti.getTitle());
        return fti.getTitle();*/

            return listTitle.get(position);
        }
        @Override
        public Parcelable saveState() {
            return null;
        }

        public void addFragment(Fragment fm, String title){
            list.add(fm);
            listTitle.add(title);
        }

        public void remove(int index){
            if (index < list.size()){
                list.remove(index);
                listTitle.remove(index);
            }
        }
    }

}