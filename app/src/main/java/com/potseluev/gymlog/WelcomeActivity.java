package com.potseluev.gymlog;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    SeekBar seekBarGrowth;
    RadioGroup radioGroupSex;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        //Это первый и единственный запуск
        final SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCE_FILE, MODE_PRIVATE);
        sharedPreferences.edit().putInt(Constants.WELCOME_ACTIVITY_STATUS,Constants.WELCOME_ACTIVITY_STATUS_NOT_FINISHED).apply();
//        sharedPreferences.edit().putInt(Constants.WELCOME_ACTIVITY_STATUS, Constants.WELCOME_ACTIVITY_STATUS_INITIAL).apply(); // TODO: 30.10.2017  Это для теста
        Button btnSkip = (Button) findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mViewPager.getCurrentItem() < 2) {
                    sharedPreferences.edit().putInt(Constants.WELCOME_ACTIVITY_STATUS, Constants.WELCOME_ACTIVITY_STATUS_NOT_FINISHED).apply();
                } else {
                    sharedPreferences.edit().putInt(Constants.WELCOME_ACTIVITY_STATUS, Constants.WELCOME_ACTIVITY_STATUS_FULLY_FINISHED).apply();
                }
//                sharedPreferences.edit().putInt(Constants.WELCOME_ACTIVITY_STATUS, Constants.WELCOME_ACTIVITY_STATUS_INITIAL).apply(); // TODO: 30.10.2017  Это для теста
//                updateDb();
//                int i = radioGroupSex.getCheckedRadioButtonId();
//                Log.d("mytag", String.valueOf(radioGroupSex.getCheckedRadioButtonId()));
                finish();
            }
        });

    }

    private void updateDb() {
        DBHelper mdbHelper = new DBHelper(this);
        SQLiteDatabase db = mdbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
//                radioGroupSex.getCheckedRadioButtonId();
//            cv.put(DBSchema.UserInfo.AGE,);
//            cv.put(DBSchema.UserInfo.SEX ,);
//            cv.put(DBSchema.UserInfo.GROWTH,);
//            cv.put(DBSchema.UserInfo.WEIGHT,);


        long newRowId = db.insert(DBSchema.UserInfo.TABLE_NAME,
                null,
                cv);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        RadioGroup radioGroupSex;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
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
            View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);
            TextView textView = rootView.findViewById(R.id.section_label);
            Log.d("tag", "onCreateView");
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_welcome_01, container, false);
                    final EditText editGrowth = rootView.findViewById(R.id.editGrowth);
                    final SeekBar seekBarGrowth = rootView.findViewById(R.id.seekBarGrowth);
//                    radioGroupSex = (RadioGroup) rootView.findViewById(R.id.radioGroupSex);   // TODO: 02.11.2017 Разобраться
                    seekBarGrowth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            int progress;
                            progress = seekBar.getProgress();
                            editGrowth.setText(String.valueOf(progress));
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_welcome_02, container, false);
                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.fragment_welcome_03, container, false);
                    break;
            }


            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
//            switch (position) {
//                case 1:
////                    WelcomeFragment_first fragment =  new WelcomeFragment_first();
////                    return fragment;
//                default: return null;
//            }

//                return fragment;
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
