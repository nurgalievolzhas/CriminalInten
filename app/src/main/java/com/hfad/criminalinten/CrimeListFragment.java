package com.hfad.criminalinten;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {
    //Declare Views
    private RecyclerView mCrimeRecyclerView;

    //Declare Adapter
    private CrimeAdapter mAdapter;

    //переменную для хранения признака видимости подзаголовка.
    private boolean mSubtitleVisible;

    //
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    //onCreate-----------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    //onCreateView--------------------------
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Определяем представление
        View view = inflater.inflate(R.layout.fragment_crime_list,container,false);
        //Ссылка на Recycler view
        mCrimeRecyclerView = (RecyclerView)view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //
        if (savedInstanceState!=null){
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        //
        updateUI();

        return view;
    }
    //onResume()--------------------------------
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    //onSaveInstanceState-------------------------


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE,mSubtitleVisible);
    }

    //MENU-----------------------
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);

        //Обновление MenuItem
        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }
        else{
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }
    //Reaction for Menu selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(),crime.getId());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //метод updateSubtitle(), который будет задавать подзаголовок
    //с количеством преступлений на панели инструментов
    private void updateSubtitle(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format,crimeCount);

        //Отображение или сокрытие подзаголовка
        if (!mSubtitleVisible){
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }


    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        //Вывод обновленного состояния
        updateSubtitle();
    }
















    //класс ViewHolder, который будет заполнять ваш макет
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //Widgets
        private TextView  mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;
        //
        private Crime mCrime;


        public CrimeHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_crime,parent,false));

            //Для щелчков
            itemView.setOnClickListener(this);
            //Извлечение представлений в конструкторе
            mTitleTextView = (TextView)itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView)itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView)itemView.findViewById(R.id.crime_solved);
        }

        //метод bind(Crime). Он будет вызываться
        //каждый раз, когда в CrimeHolder должен отображаться новый объект Crime. Сначала добавляется метод bind(Crime)
        public void bind(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        //Когда щелкаешь
        @Override
        public void onClick(View view){
            //Toast.makeText(getActivity(),mCrime.getTitle() + " Clicked",Toast.LENGTH_SHORT).show();
            //Запускается CrimeActivity
            Intent intent = CrimePagerActivity.newIntent(getActivity(),mCrime.getId());
            startActivity(intent);
            /*
            Intent intent = new Intent(getActivity(), CrimeActivity.class);
            startActivity(intent);*/
        }
    }












    //
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        //Declare needed fields. Data about crimes goes to adapter
        private List<Crime> mCrimes;

        //Constructor
        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        //Метод onCreateViewHolder вызывается виджетом RecyclerView,
        // когда ему требуется новое представление для отображения элемента. В этом методе мы создаем
        //объект LayoutInflater и используем его для создания нового объекта CrimeHolder.
        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

}
