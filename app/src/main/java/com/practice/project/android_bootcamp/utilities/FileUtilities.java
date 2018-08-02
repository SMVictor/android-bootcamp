package com.practice.project.android_bootcamp.utilities;

import android.content.Context;

import com.practice.project.android_bootcamp.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtilities {

    private Context mContext;
    private List<String> fourSquareFileData = new ArrayList<>();

    public FileUtilities(Context context) {
        mContext = context;
        readFourSquareDataFile();
    }

    private void readFourSquareDataFile(){

        InputStream is = mContext.getResources().openRawResource(R.raw.four_square_data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String data;

        if (is != null){

            try {

                while ((data = reader.readLine()) != null){

                    fourSquareFileData.add(data);
                }
                is.close();

            }catch (Exception e){

                e.printStackTrace();
            }
        }
    }

    public String getFourSquareData(String dataName) {

        String value = "";

        for (String data : fourSquareFileData) {
            if (data.contains(dataName)){
                String[] split = data.split(":");
                value = split[1];
                break;
            }
        }

        return value;
    }
}
