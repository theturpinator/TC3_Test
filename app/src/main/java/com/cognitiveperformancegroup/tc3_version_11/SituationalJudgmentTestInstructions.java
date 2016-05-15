package com.cognitiveperformancegroup.tc3_version_11;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jxl.Sheet;

public class SituationalJudgmentTestInstructions extends Fragment {

    Sheet sheet;

    public SituationalJudgmentTestInstructions() {
        // Required empty public constructor
    }


    @SuppressLint("ValidFragment")
    public SituationalJudgmentTestInstructions(Sheet s){

        sheet = s;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_situational_judgment_test_instructions, container, false);

        TextView instructions = (TextView) view.findViewById(R.id.sjt_instructions_text);
        instructions.setText(sheet.getCell(1, 0).getContents());

        return view;
    }

    public static SituationalJudgmentTestInstructions newInstance(){

        SituationalJudgmentTestInstructions f = new SituationalJudgmentTestInstructions();

        return f;

    }

    public static SituationalJudgmentTestInstructions newInstance(Sheet s){

        SituationalJudgmentTestInstructions f = new SituationalJudgmentTestInstructions(s);

        return f;

    }

}
