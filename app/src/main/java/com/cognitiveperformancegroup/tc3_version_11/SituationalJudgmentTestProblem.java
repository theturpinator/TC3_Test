package com.cognitiveperformancegroup.tc3_version_11;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;


public class SituationalJudgmentTestProblem extends Fragment {

    public interface CompleteListener{

        boolean onComplete(boolean complete);

    }

    boolean isComplete = false;
    CompleteListener complete;
    TextView problemTitle;
    TextView problemScenario;
    TextView hr;
    private String question;
    private int numRows;
    private int numCol;
    private int page; //Used for the switch statement in the "Next" Action listener to determine which frame should populate next
    private Sheet sheet;


    LinearLayout.LayoutParams problemTitleLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
            .WRAP_CONTENT);
    LinearLayout.LayoutParams problemScenarioLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
            .WRAP_CONTENT);
    LinearLayout.LayoutParams spinnerLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 40);
    LinearLayout.LayoutParams questionLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
            .WRAP_CONTENT);
    LinearLayout.LayoutParams answerLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
            .WRAP_CONTENT);

    LinearLayout.LayoutParams answerContainerLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
            .WRAP_CONTENT);

    public SituationalJudgmentTestProblem() {

    }

    @SuppressLint("ValidFragment")
    public SituationalJudgmentTestProblem(Sheet s, int p) {
        sheet = s;
        page = p;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_situational_judgment_test_problem, container, false);


        problemTitle = new TextView(getContext());
        problemScenario = new TextView(getContext());
        hr = new TextView(getContext());

        problemTitleLP.setMargins(10, 10, 10, 10);
        problemScenarioLP.setMargins(10, 10, 10, 10);
        questionLP.setMargins(0, 20, 0, 5);
        spinnerLP.setMargins(0, 5, 5, 5);
        answerLP.setMargins(5, 5, 0, 5);



        numRows = sheet.getRows();
        numCol = sheet.getColumns();

        final Spinner[][] spinners = new Spinner[3][4];
        final TextView[][] answers = new TextView[3][4];
        final LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.sjtproblemcontainer);
        LinearLayout[][] answerContainers = new LinearLayout[3][4];
        TextView[] questions = new TextView[3];
        List<String> list = new ArrayList<>();

        list.add("Select");

        for(int m=0; m<4; m++) {
            list.add(""+(m+1));
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        view = loadNextProblem(view, questions, spinners, answers, mainLayout, answerContainers, dataAdapter);
        loadNextProblem(page, questions, answers);

        return view;
    }


    private void loadNextProblem(int problemNum, TextView[] questions, TextView[][] answers){

        int startIndex = 0;
        int finishIndex;

        switch(problemNum){
            case 1:
                startIndex = 3;
                break;
            case 2:
                startIndex = 7;
                break;
            case 3:
                startIndex = 11;
                break;
            case 4:
                startIndex = 15;
                break;
        }

        finishIndex = startIndex + 3;

        problemTitle.setText(sheet.getCell(0, startIndex).getContents());
        problemScenario.setText(sheet.getCell(1, startIndex).getContents());

        int indexCol = 0;

        for (int i = startIndex+1; i < finishIndex+1; i++) {

            question = sheet.getCell(0, i).getContents();

            questions[indexCol].setText(question);

            for (int j = 1; j < 5; j++) {

                int indexRow = j-1;

                answers[indexCol][indexRow].setText(sheet.getCell(j, i).getContents());

            }

            indexCol++;

        }

    }

    private View loadNextProblem(View view, TextView[] questions, final Spinner[][] spinners, TextView[][] answers, LinearLayout mainLayout, LinearLayout[][] answerContainers, ArrayAdapter<String> dataAdapter){


        for (int i = 0; i < 4; i++) {

            if(i == 0){

                problemTitle.setLayoutParams(problemTitleLP);
                problemTitle.setText("Test");//Get title from excel file

                mainLayout.addView(problemTitle);

                //Create TextView for Problem Scenario
                problemScenario.setLayoutParams(problemScenarioLP);
                problemScenario.setText("Test");//Get scenario from excel file

                mainLayout.addView(problemScenario);
                hr.setHeight(2);
                hr.setBackgroundColor(Color.WHITE);

                mainLayout.addView(hr);

                problemTitle.setText("test");
                problemTitle.setTextAppearance(getContext(), android.R.style.TextAppearance_Large);
                problemTitle.setTypeface(Typeface.DEFAULT_BOLD);
                problemScenario.setText("test");
                problemScenario.setTextAppearance(getContext(), android.R.style.TextAppearance_Large);


            }else{

                questions[i-1] = new TextView(getContext());
                questions[i-1].setLayoutParams(questionLP);
                questions[i-1].setText("test");
                questions[i-1].setTextAppearance(getContext(), android.R.style.TextAppearance_Large);

                mainLayout.addView(questions[i-1]);

                final int indexRow = i-1;

                for (int j = 0; j < 4; j++) {

                    final int indexCol = j;

                    answerContainers[indexRow][indexCol] = new LinearLayout(getContext());
                    answerContainers[indexRow][indexCol].setLayoutParams(answerContainerLP);
                    answerContainers[indexRow][indexCol].setOrientation(LinearLayout.HORIZONTAL);

                    spinners[indexRow][indexCol] = new Spinner(getContext());
                    spinners[indexRow][indexCol].setBackgroundColor(Color.parseColor("#F8A219"));
                    spinners[indexRow][indexCol].setGravity(Gravity.CENTER);
                    spinners[indexRow][indexCol].setAdapter(dataAdapter);
                    spinners[indexRow][indexCol].setLayoutParams(spinnerLP);
                    spinners[indexRow][indexCol].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            boolean hasSameValue = false;


                            if (id != 0) {

                                for (int k = 0; k < 4; k++) {

                                    if (k != indexCol && spinners[indexRow][k].getSelectedItemPosition() == position) {

                                        AlertDialog.Builder instructions = new AlertDialog.Builder(getContext(), R.style.TC3AlertDialog2);
                                        final TextView instructionsText = new TextView(getContext());

                                        instructionsText.setWidth(600);
                                        instructionsText.setPadding(20, 20, 20, 20);
                                        instructions.setView(instructionsText);
                                        instructions.setTitle("Error");
                                        instructionsText.setGravity(Gravity.TOP | Gravity.LEFT);
                                        instructionsText.setText("This option has already been selected. Each option may only be used once. Please make another selection.");

                                        instructions.setPositiveButton("OK", null);


                                        instructions.show();

                                        spinners[indexRow][indexCol].setSelection(0);


                                    }

                                }

                                isComplete = true; //Initialize to true, change to false if not complete

                                //Check if test is complete
                                for (int i = 0; i < spinners.length; i++) {
                                    for (int j = 0; j < spinners[i].length; j++) {

                                        if (spinners[i][j].getSelectedItemPosition() == 0) {
                                            isComplete = false;
                                        }

                                    }
                                }

                                if (isComplete == true) {

                                    complete.onComplete(isComplete);

                                }

                            }
                        }


                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }

                    });

                    answers[indexRow][indexCol] = new TextView(getContext());
                    answers[indexRow][indexCol].setLayoutParams(answerLP);
                    answers[indexRow][indexCol].setText("Test");
                    answers[indexRow][indexCol].setTextSize(18);

                    answerContainers[indexRow][indexCol].addView(spinners[indexRow][indexCol]);
                    answerContainers[indexRow][indexCol].addView(answers[indexRow][indexCol]);

                    mainLayout.addView(answerContainers[indexRow][indexCol]);
                }

            }

        }

        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        complete = (CompleteListener) context;
    }

    public static SituationalJudgmentTestProblem newInstance(Sheet s, int p){

        SituationalJudgmentTestProblem f = new SituationalJudgmentTestProblem(s, p);

        return f;

    }

}
