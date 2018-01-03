package eu.kamilsvoboda.ozodraw;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class DirectionsActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private NavigationTabBar mNavigationTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(new MyPagerAdapter());

        mNavigationTabBar = findViewById(R.id.navigation_tab_bar);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_network_check_black_24dp),
                        getResources().getColor(R.color.colorPrimary))
                        .title(getString(R.string.title_speed))
                        .badgeTitle(getString(R.string.title_speed))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_call_split_black_24dp),
                        getResources().getColor(R.color.colorPrimary))
                        .title(getString(R.string.title_direction))
                        .badgeTitle(getString(R.string.title_direction))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_gesture_black_24dp),
                        getResources().getColor(R.color.colorPrimary))
                        .title(getString(R.string.title_moves))
                        .badgeTitle(getString(R.string.title_moves))
                        .build()
        );

        mNavigationTabBar.setModels(models);
        mNavigationTabBar.setViewPager(mViewPager, 0);
    }


    /**
     * Pager adapter pro jednotlivé stránky
     */
    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            final View rootView = LayoutInflater.from(
                    DirectionsActivity.this).inflate(R.layout.activity_direction_list_page, null, false);

            if (position == 0) {
            } else if (position == 1) {
            } else {
            }

            container.addView(rootView);
            return rootView;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(final View view, final Object object) {
            return view.equals(object);
        }

        @Override
        public void destroyItem(final View container, final int position, final Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }
}
