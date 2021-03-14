package com.example.quickcash;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.databinding.Bindable;
import androidx.databinding.Observable;

import androidx.lifecycle.AndroidViewModel;

    public class SpecificTaskViewModel extends AndroidViewModel implements Observable {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());

        @Bindable
        public String headLine, description;
        @Bindable
        public String rawStartDateString, rawEndDateString, startDateString, endDateString;
        @Bindable
        public boolean urgent;
        @Bindable
        public String wage = "";

        public SpecificTaskViewModel(Application application){
            super(application);
            description = getString(R.string.DESCRIPTION_KEY, "No Description Found");
            headLine = getString(R.string.HEADLINE_KEY, "No Headline Found");
            wage = getString(R.string.WAGE_KEY, "No Wage Found");
            rawStartDateString = getString(R.string.START_DATE_KEY, "No Start Date Found");
            rawEndDateString = getString(R.string.END_DATE_KEY, "No End Date Found");
            urgent = sharedPreferences.getBoolean("URGENT", false);
        }




        @Override
        public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        }

        @Override
        public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        }

        public void parseDates() {
            //TODO Parse raw date strings to make display-able strings
            endDateString = rawEndDateString;
            startDateString = rawStartDateString;
        }


        public String getString(int keyID, String defaultString){
            return sharedPreferences.getString(getApplication().getResources().getString(keyID), defaultString);
        }

    }