package com.cognitiveperformancegroup.tc3_version_11;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class  ParticipantDaySelectionActivity extends AppCompatActivity {

    boolean am_pm_isSelected = false;
    boolean selected = false;
    String daySelection;
    String amOrPM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_day_selection);

        /*************************create group number dropdown*******************************/
        final Spinner am_pm_Spinner = (Spinner) findViewById(R.id.am_pm_dropdown);
        final ArrayAdapter<String> am_pm_Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.am_pm));
        am_pm_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        am_pm_Spinner.setAdapter(am_pm_Adapter);

        am_pm_Spinner.setBackgroundColor(Color.BLACK);

        final Button day1Button = (Button) findViewById(R.id.day1Button);
        final Button day2Button = (Button) findViewById(R.id.day2Button);
        final Button day3Button = (Button) findViewById(R.id.day3Button);
        final Button day4Button = (Button) findViewById(R.id.day4Button);
        final Button previousButton = (Button) findViewById(R.id.backButton);
        final Button nextButton = (Button) findViewById(R.id.nextButton);

        am_pm_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position != 0){

                    am_pm_Spinner.setBackgroundColor(Color.parseColor("#000077"));

                }
                else{

                    am_pm_Spinner.setBackgroundColor(Color.BLACK);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        day1Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //set day selection to day 1
                daySelection = "1";
                selected = true;

                //change image to button down
                day1Button.setBackground(getDrawable(R.drawable.gold_button_down));

                //deselect other days
                day2Button.setBackground(getDrawable(R.drawable.gold_button));
                day3Button.setBackground(getDrawable(R.drawable.gold_button));
                day4Button.setBackground(getDrawable(R.drawable.gold_button));
            }
        });

        day2Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //set day selection to day 2
                daySelection = "2";
                selected = true;

                //change image to button down
                day2Button.setBackground(getDrawable(R.drawable.gold_button_down));

                //deselect other days
                day1Button.setBackground(getDrawable(R.drawable.gold_button));
                day3Button.setBackground(getDrawable(R.drawable.gold_button));
                day4Button.setBackground(getDrawable(R.drawable.gold_button));
            }
        });

        day3Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //set day selection to day 3
                daySelection = "3";
                selected = true;

                //change image to button down
                day3Button.setBackground(getDrawable(R.drawable.gold_button_down));

                //deselect other days
                day1Button.setBackground(getDrawable(R.drawable.gold_button));
                day2Button.setBackground(getDrawable(R.drawable.gold_button));
                day4Button.setBackground(getDrawable(R.drawable.gold_button));
            }
        });

        day4Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //set day selection to day 4
                daySelection = "4";
                selected = true;

                //change image to button down
                day4Button.setBackground(getDrawable(R.drawable.gold_button_down));

                //deselect other days
                day1Button.setBackground(getDrawable(R.drawable.gold_button));
                day2Button.setBackground(getDrawable(R.drawable.gold_button));
                day3Button.setBackground(getDrawable(R.drawable.gold_button));
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //store AM/PM selection in variable
                amOrPM = am_pm_Spinner.getSelectedItem().toString();

                am_pm_isSelected = true;

                for(int i=0; i<am_pm_Adapter.getCount(); i++){

                    if(am_pm_Spinner.getSelectedItemPosition() == 0){

                        am_pm_isSelected = false;

                    }

                }

                if (selected && am_pm_isSelected) {

                    //go back to activity
                    openTestSelectionPage();
                } else {

                    //prompt user to select training day
                    Toast.makeText(getApplicationContext(), "Please select the training day and session.", Toast.LENGTH_LONG).show();
                }


            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //go back to activity
                openLoginPage();
            }
        });


    }

    public void openLoginPage() {

        //Build Intent
        Intent intent = new Intent(this, LoginActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTestSelectionPage() {

        //Build Intent
        Intent intent = new Intent(this, TestSelectionActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed();
    }

    public void writeToExcel(String s, int row, int col) {

        try {

            File file = new File("participantOutput.xls");

            WritableWorkbook writableWorkbook = Workbook.createWorkbook(file);

            WritableSheet writableSheet = writableWorkbook.createSheet(
                    "Day" + daySelection, 0);



        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}