package eu.kamilsvoboda.ozodraw;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;

import static eu.kamilsvoboda.ozodraw.MainActivity.COLOR_BLUE;
import static eu.kamilsvoboda.ozodraw.MainActivity.COLOR_GREEN;
import static eu.kamilsvoboda.ozodraw.MainActivity.COLOR_RED;

public class CommandsActivity extends AppCompatActivity implements CommandListRecyclerViewAdapter.ICommandListItemListener {

    public static final String RESULT_DATA = "COMMAND";

    private ViewPager mViewPager;
    private NavigationTabBar mNavigationTabBar;

    private ArrayList<Command> mCommandDirections;
    private ArrayList<Command> mCommandSpeeds;
    private ArrayList<Command> mCommandMoves;

    private RecyclerView mCommandDirectionsList;
    private RecyclerView mCommandSpeedsList;
    private RecyclerView mCommandMovesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commands);

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

        mCommandSpeeds = new ArrayList<>();
        mCommandSpeeds.add(
                new Command(getString(R.string.command_speed_slow), Arrays.asList(COLOR_RED, Color.BLACK, COLOR_RED)));
        mCommandSpeeds.add(
                new Command(getString(R.string.command_speed_cruise), Arrays.asList(COLOR_GREEN, Color.BLACK, COLOR_GREEN)));
        mCommandSpeeds.add(
                new Command(getString(R.string.command_speed_fast), Arrays.asList(COLOR_BLUE, Color.BLACK, COLOR_BLUE)));
        mCommandSpeeds.add(
                new Command(getString(R.string.command_speed_turbo), Arrays.asList(COLOR_BLUE, COLOR_GREEN, COLOR_BLUE)));
        mCommandSpeeds.add(
                new Command(getString(R.string.command_speed_snail), Arrays.asList(COLOR_RED, COLOR_GREEN, COLOR_BLUE)));
        mCommandSpeeds.add(
                new Command(getString(R.string.command_speed_nitro), Arrays.asList(COLOR_BLUE, COLOR_GREEN, COLOR_RED)));
        mCommandSpeeds.add(
                new Command(getString(R.string.command_speed_pause), Arrays.asList(COLOR_RED, COLOR_BLUE, COLOR_RED)));

        mCommandDirections = new ArrayList<>();
        mCommandDirections.add(
                new Command(getString(R.string.command_direction_straight), Arrays.asList(COLOR_BLUE, Color.BLACK, COLOR_RED)));
        mCommandDirections.add(
                new Command(getString(R.string.command_direction_right), Arrays.asList(COLOR_BLUE, COLOR_RED, COLOR_GREEN)));
        mCommandDirections.add(
                new Command(getString(R.string.command_direction_left), Arrays.asList(COLOR_GREEN, Color.BLACK, COLOR_RED)));
        mCommandDirections.add(
                new Command(getString(R.string.command_direction_jump_straight), Arrays.asList(COLOR_GREEN, COLOR_BLUE, COLOR_GREEN)));
        mCommandDirections.add(
                new Command(getString(R.string.command_direction_jump_right), Arrays.asList(COLOR_RED, COLOR_GREEN, COLOR_RED)));
        mCommandDirections.add(
                new Command(getString(R.string.command_direction_jump_left), Arrays.asList(COLOR_GREEN, COLOR_RED, COLOR_GREEN)));
        mCommandDirections.add(
                new Command(getString(R.string.command_direction_u_turn), Arrays.asList(COLOR_BLUE, COLOR_RED, COLOR_BLUE)));

        mCommandMoves = new ArrayList<>();
        mCommandMoves.add(
                new Command("Spin", Arrays.asList(COLOR_GREEN, COLOR_RED, COLOR_GREEN, COLOR_RED)));
    }

    @Override
    public void commandClicked(Command command) {
        Intent result = new Intent();
        result.putIntegerArrayListExtra(RESULT_DATA, command.getColors());
        setResult(Activity.RESULT_OK, result);
        finish();
    }


    /**
     * Pager adapter pro jednotlivé stránky
     */
    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            final View rootView = LayoutInflater.from(
                    CommandsActivity.this).inflate(R.layout.activity_commands_list_page, null, false);

            LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());

            if (position == 0) {
                mCommandSpeedsList = rootView.findViewById(R.id.commands_list);
                mCommandSpeedsList.setHasFixedSize(true);
                mCommandSpeedsList.setLayoutManager(layoutManager);
                mCommandSpeedsList.setAdapter(new CommandListRecyclerViewAdapter(mCommandSpeeds,
                        CommandsActivity.this));
            } else if (position == 1) {
                mCommandDirectionsList = rootView.findViewById(R.id.commands_list);
                mCommandDirectionsList.setHasFixedSize(true);
                mCommandDirectionsList.setLayoutManager(layoutManager);
                mCommandDirectionsList.setAdapter(new CommandListRecyclerViewAdapter(mCommandDirections,
                        CommandsActivity.this));
            } else {
                mCommandMovesList = rootView.findViewById(R.id.commands_list);
                mCommandMovesList.setHasFixedSize(true);
                mCommandMovesList.setLayoutManager(layoutManager);
                mCommandMovesList.setAdapter(new CommandListRecyclerViewAdapter(mCommandMoves,
                        CommandsActivity.this));
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

    /**
     * Třída obsahující informace o příkazu
     */
    class Command {
        private String title;
        private ArrayList<Integer> colors;

        public Command(String title, List<Integer> colors) {
            this.title = title;
            this.colors = new ArrayList<>(colors);
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ArrayList<Integer> getColors() {
            return colors;
        }

        public void setColors(ArrayList<Integer> colors) {
            this.colors = colors;
        }
    }
}
