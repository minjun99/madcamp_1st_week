package com.example.madcamp_1st_week;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class TabActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Adding Fragments
        adapter.AddFragment(new FragmentContacts(), R.drawable.image_contact);
        adapter.AddFragment(new FragmentGallery(), R.drawable.image_gallery);
        adapter.AddFragment(new FragmentAlpha(), R.drawable.ic_account_circle);

        // adapter Setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        for(int i = 0; i < viewPager.getAdapter().getCount(); i++){
            tabLayout.getTabAt(i).setIcon(adapter.getFragmentInfo(i).getIconResId());
        }
    }
}
