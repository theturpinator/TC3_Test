package com.cognitiveperformancegroup.tc3_version_11;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ScenarioSelectionActivity extends AppCompatActivity {

    String domain;
    String scenario;
    boolean groupSelected = false;
    boolean dateSelected = false;
    private String group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario_selection);

        //get domain from intent, store in variable
        domain = getIntent().getExtras().getString("domain");


        /*************************create group number dropdown*******************************/
        final Spinner groupNumberSpinner = (Spinner) findViewById(R.id.group_number_dropdown);
        ArrayAdapter<String> groupNumberAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.groups_array));
        groupNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupNumberSpinner.setAdapter(groupNumberAdapter);

        groupNumberSpinner.setBackgroundColor(Color.BLACK);

        groupNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {

                    group = groupNumberSpinner.getSelectedItem().toString();
                    groupSelected = true;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final EditText dateEditText = (EditText) findViewById(R.id.editText);

        final Calendar myCalendar = Calendar.getInstance();


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dateEditText.setText(sdf.format(myCalendar.getTime()));
            }

        };

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ScenarioSelectionActivity.this, R.style.CalendarTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                dateSelected = true;

            }
        });





        final Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                              //go to previous page
                                              openLoginActivity();

                                          }
                                      }
        );

        //create Button objects and connect them to xml buttons
        final Button vbsB1button = (Button) findViewById(R.id.vbsB1button);
        final Button vbsB2button = (Button) findViewById(R.id.vbsB2button);
        final Button liveM1button = (Button) findViewById(R.id.liveM1button);
        final Button liveM2button = (Button) findViewById(R.id.liveM2button);
        final Button liveM3button = (Button) findViewById(R.id.liveM3button);

        vbsB1button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (groupSelected && dateSelected) {
                    //store scenario selection in string variable
                    scenario = "B1";

                    //change image to button down
                    vbsB1button.setBackground(getDrawable(R.drawable.gold_button_down));

                    if (domain.equals("ASA1") || domain.equals("ASA2")) {

                        //change activity
                        openASA(7, 0);
                    }
                    else if (domain.equals("TDT1") || domain.equals("TDT2")) {

                        //change activity
                        openTD(12, 0);
                    }
                    else if (domain.equals("RPE1") || domain.equals("RPE2")) {

                        //change activity
                        openRPE(17, 0);
                    }
                    else if (domain.equals("TC31") || domain.equals("TC32")) {

                        //change activity
                        openTC3(22, 0);
                    }
                    else if (domain.equals("Research1") || domain.equals("Research2")) {

                        //change activity
                        openASA(7, 0);
                    }
                    else if (domain.equals("VIP1")) {

                        //prompt user to choose domain

                        //change activity to selected domain page
                        openASA(7, 0);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please select the scenario and date.", Toast.LENGTH_LONG).show();

                }

            }
        });

        vbsB2button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (groupSelected && dateSelected) {
                    //store scenario selection in string variable
                    scenario = "B2";

                    //change image to button down
                    vbsB2button.setBackground(getDrawable(R.drawable.gold_button_down));

                    if (domain.equals("ASA1") || domain.equals("ASA2")) {

                        //change activity
                        openASA(8, 0);
                    }
                    else if (domain.equals("TDT1") || domain.equals("TDT2")) {

                        //change activity
                        openTD(13, 0);
                    }
                    else if (domain.equals("RPE1") || domain.equals("RPE2")) {

                        //change activity
                        openRPE(18, 0);
                    }
                    else if (domain.equals("TC31") || domain.equals("TC32")) {

                        //change activity
                        openTC3(23, 0);
                    }
                    else if (domain.equals("Research1") || domain.equals("Research2")) {

                        //change activity
                        openASA(7, 0);
                    }
                    else if (domain.equals("VIP1")) {

                        //prompt user to choose domain

                        //change activity to selected domain page
                        openASA(7, 0);
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(), "Please select the scenario and date.", Toast.LENGTH_LONG).show();
                }

            }
        });

        liveM1button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (groupSelected && dateSelected) {
                    //store scenario selection in string variable
                    scenario = "M1";

                    //change image to button down
                    liveM1button.setBackground(getDrawable(R.drawable.red_button_down));

                    if (domain.equals("ASA1") || domain.equals("ASA2")) {

                        //change activity
                        openASA(9, 0);
                    }
                    else if (domain.equals("TDT1") || domain.equals("TDT2")) {

                        //change activity
                        openTD(14, 0);
                    }
                    else if (domain.equals("RPE1") || domain.equals("RPE2")) {

                        //change activity
                        openRPE(19, 0);
                    }
                    else if (domain.equals("TC31") || domain.equals("TC32")) {

                        //change activity
                        openTC3(24, 0);
                    }
                    else if (domain.equals("Research1") || domain.equals("Research2")) {

                        //change activity
                        openASA(7, 0);
                    }
                    else if (domain.equals("VIP1")) {

                        //prompt user to choose domain

                        //change activity to selected domain page
                        openASA(7, 0);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please select the scenario and date.", Toast.LENGTH_LONG).show();
                }


            }
        });

        liveM2button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (groupSelected && dateSelected) {
                    //store scenario selection in string variable
                    scenario = "M2";

                    //change image to button down
                    liveM2button.setBackground(getDrawable(R.drawable.white_button_down));

                    if (domain.equals("ASA1") || domain.equals("ASA2")) {

                        //change activity
                        openASA(10, 0);
                    }
                    else if (domain.equals("TDT1") || domain.equals("TDT2")) {

                        //change activity
                        openTD(15, 0);
                    }
                    else if (domain.equals("RPE1") || domain.equals("RPE2")) {

                        //change activity
                        openRPE(20, 0);
                    }
                    else if (domain.equals("TC31") || domain.equals("TC32")) {

                        //change activity
                        openTC3(25, 0);
                    }
                    else if (domain.equals("Research1") || domain.equals("Research2")) {

                        //change activity
                        openASA(7, 0);
                    }
                    else if (domain.equals("VIP1")) {

                        //change activity to selected domain page
                        openASA(7, 0);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please select the scenario and date.", Toast.LENGTH_LONG).show();
                }

            }
        });

        liveM3button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (groupSelected && dateSelected) {
                    //store scenario selection in string variable
                    scenario = "M3";

                    //change image to button down
                    liveM3button.setBackground(getDrawable(R.drawable.blue_button_down));

                    if (domain.equals("ASA1") || domain.equals("ASA2")) {

                        //change activity
                        openASA(11, 0);
                    }
                    else if (domain.equals("TDT1") || domain.equals("TDT2")) {

                        //change activity
                        openTD(16, 0);
                    }
                    else if (domain.equals("RPE1") || domain.equals("RPE2")) {

                        //change activity
                        openRPE(21, 0);
                    }
                    else if (domain.equals("TC31") || domain.equals("TC32")) {

                        //change activity
                        openTC3(26, 0);
                    }
                    else if (domain.equals("Research1") || domain.equals("Research2")) {

                        //change activity
                        openASA(7, 0);
                    }
                    else if (domain.equals("VIP1")) {

                        //change activity to selected domain page
                        openASA(7, 0);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please select the scenario and date.", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    public void openASA(int sheetNum, int rowNum) {

        //build intent
        Intent intent = new Intent(this, ASATargetsActivity.class);

        intent.putExtra("group", group);

        intent.putExtra("domain", domain);

        intent.putExtra("scenario", scenario);

        intent.putExtra("rowNum", rowNum);

        intent.putExtra("sheetNum", sheetNum);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTD(int sheetNum, int rowNum) {

        //build intent
        Intent intent = new Intent(this, TDTargetsActivity.class);

        intent.putExtra("group", group);

        intent.putExtra("domain", domain);

        intent.putExtra("scenario", scenario);

        intent.putExtra("rowNum", rowNum);

        intent.putExtra("sheetNum", sheetNum);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openRPE(int sheetNum, int rowNum) {

        //build intent
        Intent intent = new Intent(this, RPETargetsActivity.class);

        intent.putExtra("group", group);

        intent.putExtra("domain", domain);

        intent.putExtra("scenario", scenario);

        intent.putExtra("rowNum", rowNum);

        intent.putExtra("sheetNum", sheetNum);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTC3(int sheetNum, int rowNum) {

        //build intent
        Intent intent = new Intent(this, TC3TargetsActivity.class);

        intent.putExtra("group", group);

        intent.putExtra("domain", domain);

        intent.putExtra("scenario", scenario);

        intent.putExtra("rowNum", rowNum);

        intent.putExtra("sheetNum", sheetNum);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed();
    }

    public void openLoginActivity() {

        //build intent
        Intent intent = new Intent(this, LoginActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();

    }
}
