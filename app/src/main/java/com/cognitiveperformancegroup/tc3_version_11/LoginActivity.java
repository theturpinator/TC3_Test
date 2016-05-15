package com.cognitiveperformancegroup.tc3_version_11;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class LoginActivity extends AppCompatActivity {

    String researcherID;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        password = (EditText) findViewById(R.id.password);

        //connect instance of Spinner in activity with Spinner in xml layout
        final Spinner researcherID_Spinner = (Spinner) findViewById(R.id.researcher_id_dropdown);

        //create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> researcherID_Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.planets2_array));

        //specify the layout to use when the list of choices appears
        researcherID_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //apply the adapter to the spinner
        researcherID_Spinner.setAdapter(researcherID_Adapter);

        /*************************do the same for participant dropdown*******************************/
        final Spinner participantID_Spinner = (Spinner) findViewById(R.id.participant_id_dropdown);
        ArrayAdapter<String> participantID_Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.planets_array));
        participantID_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        participantID_Spinner.setAdapter(participantID_Adapter);

        final Button researcherLoginButton = (Button) findViewById(R.id.researcherButton);
        final Button participantLoginButton = (Button) findViewById(R.id.participantButton);

        researcherLoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                researcherID = researcherID_Spinner.getSelectedItem().toString();


                //read login information from file
                //if login information matches one from file
                if (isLoginValid(researcherID, password.getText().toString())) {

                    //change image to button down
                    researcherLoginButton.setBackground(getDrawable(R.drawable.gold_button_down));

                    //go to Scenario Selection Page
                    openScenarioSelectionPage();

                }
                else {

                    //else
                    //alert user and prompt for correct login information
                    Toast.makeText(getApplicationContext(), "Researcher ID and password combination is invalid.", Toast.LENGTH_LONG).show();

                }


            }

        });

        participantLoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (participantID_Spinner.getSelectedItemPosition() == 0) {

                    Toast.makeText(getApplicationContext(), "Please select a Participant ID.", Toast.LENGTH_LONG).show();

                }
                else {

                    //change to button down
                    participantLoginButton.setBackground(getDrawable(R.drawable.gold_button_down));

                    //go to Participant Day Selection Page
                    openParticipantDaySelectionPage();
                }
            }

        });
    }

    public void openScenarioSelectionPage() {

        //Build Intent
        Intent intent = new Intent(this, ScenarioSelectionActivity.class);

        //pass value to next activity
        intent.putExtra("domain", researcherID);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openParticipantDaySelectionPage() {

        //Build Intent
        Intent intent = new Intent(this, ParticipantDaySelectionActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public boolean isLoginValid(String user, String pass) {

        try
        {
            InputStream is = getAssets().open("Login.xls");

            //create Workbook object
            Workbook workbook = Workbook.getWorkbook(is);

            //create Sheet object of first sheet
            Sheet sheet = workbook.getSheet(0);

            int numRows = sheet.getRows();
            int numCol = sheet.getColumns();

            TextView[] temp = new TextView[numCol];
            LinearLayout[] linearLayout = new LinearLayout[numCol];
            RadioGroup[] groups = new RadioGroup[numCol];
            final RadioButton[] buttons = new RadioButton[(numRows-1)*(numCol)];

            for (int i = 0; i < numRows; i++)
            {
                //Toast.makeText(getApplicationContext(), "user: " + sheet.getCell(0, i).getContents().toString() + "\npw: " + sheet.getCell(1, i).getContents().toString(), Toast.LENGTH_LONG).show();


                if (sheet.getCell(0, i).getContents().toString().equals(user) && sheet.getCell(1, i).getContents().toString().equals(pass)) {

                    return true;
                }
            }
        }
        catch (IOException e) {

            e.printStackTrace();
        }
        catch (BiffException e) {

            e.printStackTrace();
        }

        return false;

    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed();
    }
}


