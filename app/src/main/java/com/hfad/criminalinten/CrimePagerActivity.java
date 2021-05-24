package com.hfad.criminalinten;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

//ViewPager activty
public class CrimePagerActivity extends AppCompatActivity {
    //Declare widgets
    private ViewPager mViewPager;
    //Deaclre List of Crimes
    private List<Crime> mCrimes;
    //EXTRA ID for intent to CrimeFragment
    private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";


    //Это для CrimeListFragment
    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        //
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        //Get Reference to ViewPager
        mViewPager = (ViewPager)findViewById(R.id.crime_view_pager);
        //Get Crimes from CrimeLab
        mCrimes = CrimeLab.get(this).getCrimes();
        //We need Fragmnet Manager
        FragmentManager fragmentManager  = getSupportFragmentManager();
        //Set Adapter  to View Pager
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
        //найдите индекс отображаемого преступления; для этого переберите и проверьте идентификаторы всех преступлений.
        //Когда вы найдете экземпляр Crime, у которого поле mId совпадает
        // с crimeId в дополнении интента, измените текущий элемент по индексу найденного объекта Crime.
        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
