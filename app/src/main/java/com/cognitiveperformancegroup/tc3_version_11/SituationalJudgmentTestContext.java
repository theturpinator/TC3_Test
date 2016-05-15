package com.cognitiveperformancegroup.tc3_version_11;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jxl.Sheet;


public class SituationalJudgmentTestContext extends Fragment {

    Sheet sheet;

    public SituationalJudgmentTestContext() {

    }

    @SuppressLint("ValidFragment")
    public SituationalJudgmentTestContext(Sheet s) {
        // Required empty public constructor

        sheet = s;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_situational_judgment_test_context, container, false);

        TextView context = (TextView) view.findViewById(R.id.context);
        TextView situation = (TextView) view.findViewById(R.id.situation);

        context.setText(sheet.getCell(1, 1).getContents());
        situation.setText(sheet.getCell(1, 2).getContents());

        return view;
    }

    public static SituationalJudgmentTestContext newInstance(Sheet s){

        SituationalJudgmentTestContext f = new SituationalJudgmentTestContext(s);

        return f;

    }

}
