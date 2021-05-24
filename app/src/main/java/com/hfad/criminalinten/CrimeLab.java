package com.hfad.criminalinten;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//It is a singleton class
public class CrimeLab {
    //Declare
    private static CrimeLab sCrimeLab;
    //Declare list of Crimes
    private List<Crime> mCrimes;


    //Если экземпляр уже существует, то get() просто возвращает
    //его. Если экземпляр еще не существует, то get() вызывает конструктор для его
    //создания.
    public static CrimeLab get(Context context){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;

    }

    //Другие классы не
    //смогут создать экземпляр CrimeLab в обход метода get()
    private CrimeLab(Context context){
        mCrimes = new ArrayList<>();


        /*
        for (int i = 0; i < 100; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
        */

    }

    //Adding new Object of Crime (in Menu)
    public void addCrime(Crime c){
        mCrimes.add(c);
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for (Crime crime : mCrimes){
            if (crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }
}
