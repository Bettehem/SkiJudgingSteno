package com.bettehem.skijudgingsteno;

/*
    Copyright 2015 Chris Mustola
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see http://www.gnu.org/licenses/.
 */


//Imports used for this class
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesSavingAndLoading extends Activity {

    //Variables are first created here.
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public String preferenceFilename = "Settings";
    public static final String originalPreferenceFilename = "Settings";





    //-----     Saving      ------


    public void saveString(Context context, String valueName, String value) {
        sharedPreferences = context.getSharedPreferences(preferenceFilename, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(valueName, value);

        editor.apply();

    }

    public void saveInt(Context context, String valueName, int value) {
        sharedPreferences = context.getSharedPreferences(preferenceFilename, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putInt(valueName, value);

        editor.apply();

    }

    public void saveBoolean(Context context, String valueName, boolean value) {
        sharedPreferences = context.getSharedPreferences(preferenceFilename, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putBoolean(valueName, value);

        editor.apply();

    }

    public void saveFloat(Context context, String valueName, float value) {
        sharedPreferences = context.getSharedPreferences(preferenceFilename, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putFloat(valueName, value);

        editor.apply();

    }

    public void saveLong(Context context, String valueName, long value) {
        sharedPreferences = context.getSharedPreferences(preferenceFilename, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putLong(valueName, value);

        editor.apply();

    }


    //-----     Loading      ------

    public String loadString(Context context, String valueName) {

        if (valueName != null) {
            sharedPreferences = context.getSharedPreferences(preferenceFilename, MODE_PRIVATE);

            String loadedStringValue;
            loadedStringValue = sharedPreferences.getString(valueName, "Error! Not Found!");

            return loadedStringValue;
        } else {
            return "Error! Not Found!";
        }
    }

    public int loadInt(Context context, String valueName) {

        sharedPreferences = context.getSharedPreferences(preferenceFilename, MODE_PRIVATE);

        int loadedIntValue, defaultvalue;

        defaultvalue = 0;

        loadedIntValue = sharedPreferences.getInt(valueName, defaultvalue);

        return loadedIntValue;
    }

    public boolean loadBoolean(Context context, String valueName) {

        sharedPreferences = context.getSharedPreferences(preferenceFilename, MODE_PRIVATE);

        boolean loadedBooleanValue, defaultvalue;

        defaultvalue = false;

        loadedBooleanValue = sharedPreferences.getBoolean(valueName, defaultvalue);

        return loadedBooleanValue;
    }

    public float loadFloat(Context context, String valueName) {

        sharedPreferences = context.getSharedPreferences(preferenceFilename, MODE_PRIVATE);

        float loadedFloatValue, defaultvalue;

        defaultvalue = 0;

        loadedFloatValue = sharedPreferences.getFloat(valueName, defaultvalue);

        return loadedFloatValue;
    }

    public long loadLong(Context context, String valueName) {

        sharedPreferences = context.getSharedPreferences(preferenceFilename, MODE_PRIVATE);

        long loadedLongValue, defaultvalue;

        defaultvalue = 0;

        loadedLongValue = sharedPreferences.getLong(valueName, defaultvalue);

        return loadedLongValue;
    }


    //-----     Deleting      ------

    public void deleteIndividualValue(Context context, String valueName) {
        sharedPreferences = context.getSharedPreferences(preferenceFilename, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.remove(valueName);

        editor.apply();
    }

    public void deleteAllValues(Context context) {
        sharedPreferences = context.getSharedPreferences(preferenceFilename, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.clear();

        editor.apply();
    }


    //-----     Saving Arrays      ------

    public void saveStringArray(Context context, String valueName, String[] array){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++){
            stringBuilder.append(array[i]).append(",");
        }

        sharedPreferences = context.getSharedPreferences(preferenceFilename, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(valueName, stringBuilder.toString());

        editor.apply();

    }


    //-----     Loading Arrays      ------

    public String[] loadStringArray(Context context, String valueName){
        sharedPreferences = context.getSharedPreferences(preferenceFilename, MODE_PRIVATE);

        //String loadedArray = sharedPreferences.getString(valueName, "Error! Not Found!");
		//String[] array = new String[]{loadedArray};

		String[] array = sharedPreferences.getString(valueName, "Error! Not Found!").split(",");
		
        return array;
    }

}
