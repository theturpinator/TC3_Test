package com.cognitiveperformancegroup.tc3_version_11;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class BaselineSkillsSurveyActivity extends AppCompatActivity {

    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private Button menuButton;
    private int numRows;
    private int numCol;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_action_process);

        bundle = getIntent().getExtras();

        TextView title = (TextView) findViewById(R.id.title);

        title.setText("Baseline Skills Survey");

        try
        {
            InputStream is = getAssets().open("Tests.xls");

            //create Workbook object
            Workbook workbook = Workbook.getWorkbook(is);

            //create Sheet object of first sheet
            Sheet sheet = workbook.getSheet(6);

            numRows = sheet.getRows();
            numCol = sheet.getColumns();

            TextView[] temp = new TextView[numCol];
            LinearLayout[] linearLayout = new LinearLayout[numCol];
            RadioGroup[] groups = new RadioGroup[numCol];
            final RadioButton[] buttons = new RadioButton[(numRows-1)*(numCol)];

            for (int i = 0; i < numCol; i++)
            {
                linearLayout[i] = (LinearLayout) findViewById(R.id.middle);
                groups[i] = new RadioGroup(this);
                groups[i].setOrientation(RadioGroup.HORIZONTAL);
                temp[i] = new TextView(this);
                temp[i].setText((i + 1) + ". " + sheet.getCell(i, 0).getContents());
                temp[i].setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Large);
                temp[i].setPadding(15,20,0,0);
                linearLayout[i].addView(temp[i]);


                for (int j = 0; j < numRows; j++)
                {
                    if (j > 0) {
                        buttons[(i*4)+(j-1)] = new RadioButton(this);
                        buttons[(i*4)+(j-1)].setButtonDrawable(new StateListDrawable());
                        buttons[(i*4)+(j-1)].setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.apptheme_btn_radio_holo_dark), null, null);
                        //buttons[(i*4)+(j-1)].setCompoundDrawablesWithIntrinsicBounds(0,android.R.drawable.btn_radio,0,0);
                        buttons[(i * 4) + (j - 1)].setText(sheet.getCell(i, j).getContents());
                        buttons[(i*4)+(j- 1)].setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Large);
                        buttons[(i * 4) + (j - 1)].setWidth(150);
                        buttons[(i*4)+(j- 1)].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        buttons[(i*4)+(j- 1)].setPadding(10, 0, 0, 0);
                        groups[i].addView(buttons[(i*4)+(j-1)]);
                        groups[i].setPadding(50, 0, 0, 0);
                    }
                }

                linearLayout[i].addView(groups[i]);

            }

            //Code for bottom "Conclusion" signature
            LinearLayout.LayoutParams hrlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
            View hr = new View(this);
            hr.setLayoutParams(hrlp);
            hr.setBackgroundColor(Color.WHITE);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 10, 10, 10);
            TextView conclusion = new TextView(this);
            conclusion.setLayoutParams(lp);
            conclusion.setGravity(Gravity.CENTER_HORIZONTAL);
            conclusion.setTextSize(20);
            conclusion.setText("This concludes the test. Once you've completed answering all of the questions, please press \"Next\" to proceed to the next test.");
            linearLayout[numCol-1].addView(hr);
            linearLayout[numCol-1].addView(conclusion);

        }
        catch (IOException e) {

            e.printStackTrace();
        }
        catch (BiffException e) {

            e.printStackTrace();
        }





        menuButton = (Button) findViewById(R.id.menu_button);

        //disable fading when opening drawer menu
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));

        //get list items from strings.xml
        drawerListViewItems = getResources().getStringArray(R.array.items);

        //get ListView defined in activity_main.xml
        drawerListView = (ListView) findViewById(R.id.left_drawer);

        //set the adapter for the list view
        drawerListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_items, drawerListViewItems));

        drawerListView.setOnItemClickListener(new DrawerItemClickListener());


        final Button instructions = (Button) findViewById(R.id.tap_instructions);

        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder instructions = new AlertDialog.Builder(BaselineSkillsSurveyActivity.this, R.style.TC3AlertDialog);
                final TextView instructionsText = new TextView(BaselineSkillsSurveyActivity.this);

                instructionsText.setWidth(600);
                instructionsText.setPadding(20, 20, 20, 20);
                instructions.setView(instructionsText);
                instructions.setTitle("Instructions");
                instructionsText.setGravity(Gravity.TOP | Gravity.LEFT);
                instructionsText.setText("Baseline Skills Survey - The baseline skills survey asks participants to assess their current level of skills in TC3, ASA, R/PE, TD, and IAAR. It will be collected twice: on the first day of the experiment before training begins and at the end of experiment. The results will be used to compare their perceived level of knowledge and skills with their actual levels of knowledge and skills\n" +
                        "\n" +
                        "Read the following items and rate your current ability level.     Use the scale below to indicate your current level of advanced situational awareness skills. ");

                instructions.setPositiveButton("OK", null);


                instructions.show();

            }
        });


        final Button saveAndExit = (Button) findViewById(R.id.tap_save_and_exit);

        saveAndExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), LoginActivity.class);

                startActivity(intent);

            }
        });

        final Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener()
                                      {
                                          @Override
                                          public void onClick(View v) {

                                              //go to previous page
                                              openTestSelectionPage();
                                          }
                                      }
        );

        final Button nextButton = (Button) findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener()
                                      {
                                          @Override
                                          public void onClick(View v) {

                                              //go to next page
                                              openKnowledgeComprehensionPostTestPage();
                                          }
                                      }
        );

        menuButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //open navigation drawer
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                drawerLayout.openDrawer(drawerListView);

            }
        });
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {

            String selection = ((TextView) view).getText().toString();

            if (selection.equalsIgnoreCase("Knowledge and Comprehension Test")) {

                //open Knowledge and Comprehension Post Test page
                openKnowledgeComprehensionPostTestPage();
            }
            else if (selection.equalsIgnoreCase("Team Action Processes")) {

                //open Team Action Processes page
                openTeamActionProcessPage();

            }
            else if(selection.equalsIgnoreCase("Baseline Skills Survey")){

                //ope home page
                openBaselineSkillsSurvery();

            }
            else if(selection.equalsIgnoreCase("Home")){

                //ope home page
                openTestSelectionPage();

            }
            else if (selection.equalsIgnoreCase("Team Cohesion Measure")) {

                //open Team Cohesion Measure page
                openTeamCohesionMeasurePage();
            }
            else if (selection.equalsIgnoreCase("Team Efficacy Measure")) {

                //open Team Efficacy Measure page
                openTeamEfficacyMeasurePage();
            }
            else if (selection.equalsIgnoreCase("Team Performance Measure")) {

                //open Team Performance Measure page
                openTeamPerformancePage();
            }
            else if(selection.equalsIgnoreCase("Situational Judgment Test")){

                openSituationalJudgmentTestPage();

            }

            drawerLayout.closeDrawer(drawerListView);

        }
    }

    public void openBaselineSkillsSurvery(){

        Intent intent = new Intent(this, BaselineSkillsSurveyActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("BSSCOMPLETE", true);

        startActivity(intent);

    }

    public void openTestSelectionPage() {

        //Build Intent
        Intent intent = new Intent(this, TestSelectionActivity.class);



        intent.putExtras(bundle);
        intent.putExtra("BSSCOMPLETE", true);



        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openKnowledgeComprehensionPostTestPage() {

        //build intent
        Intent intent = new Intent(this, KnowledgeComprehensionPostTestActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("BSSCOMPLETE", true);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openSituationalJudgmentTestPage() {

        //build intent
        Intent intent = new Intent(this, SituationalJudgmentTestActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("BSSCOMPLETE", true);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTeamActionProcessPage() {

        //build intent
        Intent intent = new Intent(this, TeamActionProcessActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("BSSCOMPLETE", true);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTeamCohesionMeasurePage() {

        //build intent
        Intent intent = new Intent(this, TeamCohesionMeasureActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("BSSCOMPLETE", true);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTeamEfficacyMeasurePage() {

        //build intent
        Intent intent = new Intent(this, TeamEfficacyMeasureActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("BSSCOMPLETE", true);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTeamPerformancePage() {

        //build intent
        Intent intent = new Intent(this, TeamPerformanceActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("BSSCOMPLETE", true);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed();
    }



}
