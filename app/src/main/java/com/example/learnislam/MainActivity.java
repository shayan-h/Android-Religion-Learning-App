package com.example.learnislam;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.app.TabActivity;
import android.content.Intent;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import static android.view.View.*;
//AppCompatActivity


@Metadata(
        mv = {1, 4, 0},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014¨\u0006\u0007"},
        d2 = {"Lcom/example/learnislam/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "LearnIslam.app"}
)
public final class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button button;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        // Tabs
        tabLayout = findViewById(R.id.bottomTab);
        viewPager = findViewById(R.id.viewPager);

        mAuth = FirebaseAuth.getInstance();

        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("WtW"));
        tabLayout.addTab(tabLayout.newTab().setText("Tafseer"));
        tabLayout.addTab(tabLayout.newTab().setText("Quran"));
        tabLayout.addTab(tabLayout.newTab().setText("Profile"));



        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        home_frag home = new home_frag();
                        return home;
                    case 1:
                        wordToWord_frag wtw = new wordToWord_frag();
                        return wtw;
                    case 2:
                        tafseer_frag tafseer = new tafseer_frag();
                        return tafseer;
                    case 3:
                        quran_frag quran = new quran_frag();
                        return quran;
                    case 4:
                        profile_frag profile = new profile_frag();
                        return profile;
                    default:
                        return null;
                }
                //return null;
            }

            @Override
            public int getCount() {
                return tabLayout.getTabCount();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // WTW button (test)
        button = (Button) findViewById(R.id.buttonTest);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setActivated(true);
                openWordToWordActivity();
            }
        });

        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.inflateMenu(R.menu.menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.logoutMenu) {
                    Logout();
                }
                return false;
            }
        });
    }


    private void Logout() {
        mAuth.signOut();
        finish();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.menu, menu);
        toolbar.inflateMenu(R.menu.menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.logoutMenu: {
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public void openWordToWordActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}