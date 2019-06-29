package com.example.madcamp_1st_week;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

   public class FragmentInfo {
        private int iconResId;
        //private String text;
        private Fragment fragment;

        public FragmentInfo(Fragment fragment, int iconResId) {
            this.iconResId = iconResId;
            //this.text = text;
            this.fragment = fragment;
        }

        public int getIconResId() {
            return iconResId;
        }

//        public String getTitleText() {
//            return text;
//        }

        public Fragment getFragment() {
            return fragment;
        }
    }

    private final List<FragmentInfo> fragmentInfoList = new ArrayList<FragmentInfo>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentInfoList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragmentInfoList.size();
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return fragmentInfoList.get(position).getTitleText();
//    }

    public void AddFragment(Fragment fragment, int iconResId) {
        FragmentInfo info = new FragmentInfo(fragment,iconResId);
        fragmentInfoList.add(info);
    }

    public FragmentInfo getFragmentInfo(int position) {
        return fragmentInfoList.get(position);
    }
}
