package com.example.valeriaserber.trainingapp.activities;

import java.util.Locale;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valeriaserber.trainingapp.R;
import com.example.valeriaserber.trainingapp.TrainingApplication;
import com.example.valeriaserber.trainingapp.fragments.NewsFragment;
import com.example.valeriaserber.trainingapp.fragments.ProfileFragment;
import com.example.valeriaserber.trainingapp.model.User;
import com.example.valeriaserber.trainingapp.utilities.RestError;
import com.example.valeriaserber.trainingapp.utilities.UserUtility;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class UserActivity extends ActionBarActivity
        implements ActionBar.TabListener, ProfileFragment.OnPictureSelectedListener {

    private static final int TAB_COUNT = 2;

    private String mObjectId;
    private User mUser;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setUi();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.topbar_logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        getUser();
    }

    private void setUi() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
    }

    private void setListeners(final ActionBar actionBar) {
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setCustomView(mSectionsPagerAdapter.getCustomView(i))
                            .setTabListener(this));
        }
    }

    private void getUser() {
        User session = UserUtility.getUserData(this);
        mObjectId = session.getObjectId();

        TrainingApplication.sUserService.getUser(mObjectId, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                mUser = user;
                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                mViewPager.setAdapter(mSectionsPagerAdapter);
                setListeners(getSupportActionBar());
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getResponse() != null) {
                    RestError body = (RestError) error.getBodyAs(RestError.class);
                    switch (body.code) {
                        case 101:
                            showToast(getString(R.string.invalid_login));
                            return;
                        default:
                            showToast(getString(R.string.network_error));
                            return;
                    }
                }
            }
        });
    }

    private void showToast(CharSequence text){
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            UserUtility.removeUserData(getApplicationContext());
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onPictureSelected(String picture) {
        Intent intent = new Intent(this, PictureActivity.class);
        intent.putExtra(PictureActivity.PICTURE_SOURCE, picture);
        startActivity(intent);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new NewsFragment();
                case 1:
                    return new ProfileFragment().newInstance(mUser);
            }
            return null;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.news).toUpperCase(l);
                case 1:
                    return getString(R.string.profile).toUpperCase(l);
            }
            return null;
        }

        public int getIcon(int position) {
            switch (position) {
                case 0:
                    return R.drawable.news_button;
                case 1:
                    return R.drawable.profile_button;
            }
            return -1;
        }

        public View getCustomView(int position) {
            View tabView = getLayoutInflater().inflate(R.layout.tabs_user, null);
            TextView tabText = (TextView) tabView.findViewById(R.id.tab_title);
            ImageView tabImage = (ImageView) tabView.findViewById(R.id.tab_icon);
            tabText.setText(getPageTitle(position));
            tabImage.setImageDrawable(getResources().getDrawable(getIcon(position)));
            return tabView;
        }
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_user, container, false);
            return rootView;
        }
    }
}