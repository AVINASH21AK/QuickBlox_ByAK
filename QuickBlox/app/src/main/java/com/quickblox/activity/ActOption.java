package com.quickblox.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quickblox.R;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.fragment.FragLogin;
import com.quickblox.fragment.FragSignUp;
import com.quickblox.utils.App;
import com.quickblox.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActOption extends BaseActivity{

    String TAG = "ActOption";

    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) public ViewPager viewPager;

    public String strFrom = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            ViewGroup.inflate(this, R.layout.act_option, ll_SubMainContainer);

            ButterKnife.bind(this);

            QuickBlox_InitializeFramework();
            initialize();
            clickEvent();
            setupViewPager();


            tabLayout.setupWithViewPager(viewPager);
            changeTabsFont();


            //-- Width of Indicator Line margin left & Right & to show wrap content to text
            wrapTabIndicatorToTitle(tabLayout, 0, 60);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void changeTabsFont() {
        //https://stackoverflow.com/questions/31067265/change-the-font-of-tab-text-in-android-design-support-tablayout/31067431#31067431
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(App.getFont_Bold());
                }
            }
        }
    }






    public void initialize() {
        try {

             /*----- BaseActivity -----*/
            rl_baseToolbar.setVisibility(View.GONE);


            /*----- This Activity -----*/



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickEvent() {
        try {



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-- Start Setup ViewPager

    public void setupViewPager() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragLogin(), "Login");
        adapter.addFragment(new FragSignUp(), "Register");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount()); //-used to call all apis for all fragments.
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        public final List<Fragment> mFragmentList = new ArrayList<>();
        public final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    //-- Indicator Line margin left & Right & to show wrap content to text
    public void wrapTabIndicatorToTitle(TabLayout tabLayout, int externalMargin, int internalMargin) {
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            int childCount = ((ViewGroup) tabStrip).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View tabView = tabStripGroup.getChildAt(i);
                tabView.setMinimumWidth(0);
                tabView.setPadding(0, tabView.getPaddingTop(), 0, tabView.getPaddingBottom());
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    if (i == 0) {
                        // left
                        setMargin(layoutParams, externalMargin, internalMargin);
                    } else if (i == childCount - 1) {
                        // right
                        setMargin(layoutParams, internalMargin, externalMargin);
                    } else {
                        // internal
                        setMargin(layoutParams, internalMargin, internalMargin);
                    }
                }
            }

            tabLayout.requestLayout();
        }
    }

    public void setMargin(ViewGroup.MarginLayoutParams layoutParams, int start, int end) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.setMarginStart(start);
            layoutParams.setMarginEnd(end);
        } else {
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        }
    }

    //-- End Setup ViewPager




    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
