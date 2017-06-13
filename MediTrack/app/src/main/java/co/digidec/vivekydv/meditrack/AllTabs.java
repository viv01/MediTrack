package co.digidec.vivekydv.meditrack;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by vivekya on 6/2/2017.
 */
public class AllTabs extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;
    TabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_tabs);

        pref = this.getSharedPreferences("MediTrackPref", 0);
        editor = pref.edit();
        if(!pref.contains("sospresent")) {
            editor.putInt("sospresent",0);
        }
        editor.commit();

        //Adding toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Today"));
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("Settings"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        adapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
        tabLayout.setupWithViewPager(viewPager);


        int page=0;

        if(pref.contains("sospresent")){
            if(pref.getInt("sospresent",0)==0){
                page = getIntent().getIntExtra("selectedTab", 2);
            }else{
                page = getIntent().getIntExtra("selectedTab", 0);
            }
        }
        viewPager.setCurrentItem(page);

    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
