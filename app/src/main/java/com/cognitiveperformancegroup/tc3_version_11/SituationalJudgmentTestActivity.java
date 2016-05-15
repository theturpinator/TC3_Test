package com.cognitiveperformancegroup.tc3_version_11;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class SituationalJudgmentTestActivity extends AppCompatActivity implements SituationalJudgmentTestProblem.CompleteListener{

    private boolean isComplete = false;
    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private Button menuButton;
    private int page;
    Fragment instructions, context, problem;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situational_judgment_test);

        bundle = getIntent().getExtras();

        page = 0;

        loadNextProblem();

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

        final Button saveAndExit = (Button) findViewById(R.id.sjt_save_and_exit);

        saveAndExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder instructions = new AlertDialog.Builder(SituationalJudgmentTestActivity.this, R.style.TC3AlertDialog);
                final TextView instructionsText = new TextView(SituationalJudgmentTestActivity.this);

                instructionsText.setWidth(600);
                instructionsText.setPadding(20, 20, 20, 20);
                instructions.setView(instructionsText);
                instructions.setTitle("Measure Finished");
                instructionsText.setGravity(Gravity.TOP | Gravity.LEFT);
                instructionsText.setText("You have indicated that you have finished the measure. Would you like to Save and Exit?");

                instructions.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent();

                        intent.setClass(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);

                    }

                });

                instructions.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });

                instructions.show();

            }
        });

        final Button instructions = (Button) findViewById(R.id.sjt_instructions);

        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder instructions = new AlertDialog.Builder(SituationalJudgmentTestActivity.this, R.style.TC3AlertDialog);
                final TextView instructionsText = new TextView(SituationalJudgmentTestActivity.this);

                instructionsText.setWidth(600);
                instructionsText.setPadding(20, 20, 20, 20);
                instructions.setView(instructionsText);
                instructions.setTitle("Instructions");
                instructionsText.setGravity(Gravity.TOP | Gravity.LEFT);
                instructionsText.setText("Situational Judgment Test - The purpose of this test is measure of your ability to apply concepts from Squad Overmatch instruction and training to solve problems in context. Each item consists of three parts. Be sure to answer each part of the item on the score sheet. All items are related to the following situation, so you should be familiar with the situation and refer back. You may refer to the situation when responding to an item. Read the problem and select a response from the list provided.  The purpose of this test is to assess your problem solving abilities. In this test, there is no correct answer. Your scores will be compared to a baseline. This test should take about 30 minutes to complete.\n\n Rate (from 1 to 4) the effectiveness of the actions you would take from the list below, where 1 is the most effective and 4 is the least effective.");

                instructions.setPositiveButton("OK", null);


                instructions.show();

            }
        });





        final Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener()
                                      {
                                          @Override
                                          public void onClick(View v) {
                                              page--;
                                              if(page == -1){

                                                  openTeamPerformancePage();
                                              }
                                              else {
                                                  isComplete = false;
                                                  loadNextProblem();
                                              }
                                          }
                                      }
        );

        final Button nextButton = (Button) findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener()
                                      {
                                          @Override
                                          public void onClick(View v) {

                                              //If the page is 0 or 1, we do not need to check for completion (these pages are instructions)
                                              if(page < 2){

                                                  page ++;
                                                  loadNextProblem();

                                              }else if(true) { //isComplete == true <-- change this after demo

                                                  if (page < 5) {

                                                      page++;
                                                      loadNextProblem();
                                                      isComplete = false;

                                                  } else {

                                                      openTestSelectionPage();

                                                  }
                                              }else{

                                                  AlertDialog.Builder mustComplete = new AlertDialog.Builder(SituationalJudgmentTestActivity.this, R
                                                          .style
                                                          .TC3AlertDialog2);
                                                  final TextView mustCompleteText = new TextView(SituationalJudgmentTestActivity.this);

                                                  mustCompleteText.setWidth(600);
                                                  mustCompleteText.setPadding(20, 20, 20, 20);
                                                  mustComplete.setView(mustCompleteText);
                                                  mustComplete.setTitle("Error");
                                                  mustCompleteText.setGravity(Gravity.TOP | Gravity.LEFT);
                                                  mustCompleteText.setText("You must complete the test before proceeding.");

                                                  mustComplete.setPositiveButton("OK", null);


                                                  mustComplete.show();

                                              }


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

    private void loadNextProblem() {

        try {

            InputStream is = getAssets().open("Tests.xls");

            //create Workbook object
            Workbook workbook = Workbook.getWorkbook(is);

            //create Sheet object of first sheet
            Sheet sheet = workbook.getSheet(5);


            switch (page) {

                case 0:

                    instructions = SituationalJudgmentTestInstructions.newInstance(sheet);
                    getSupportFragmentManager().beginTransaction().replace(R.id.sjtContent, instructions).commit();
                    break;

                case 1:
                    context = SituationalJudgmentTestContext.newInstance(sheet);
                    getSupportFragmentManager().beginTransaction().replace(R.id.sjtContent, context).commit();
                    break;

                case 2:

                    problem = SituationalJudgmentTestProblem.newInstance(sheet, page-1);
                    getSupportFragmentManager().beginTransaction().replace(R.id.sjtContent, problem).commit();
                    break;

                case 3:

                    problem = SituationalJudgmentTestProblem.newInstance(sheet, page-1);
                    getSupportFragmentManager().beginTransaction().replace(R.id.sjtContent, problem).commit();
                    break;

                case 4:

                    problem = SituationalJudgmentTestProblem.newInstance(sheet, page-1);
                    getSupportFragmentManager().beginTransaction().replace(R.id.sjtContent, problem).commit();
                    break;

                case 5:

                    problem = SituationalJudgmentTestProblem.newInstance(sheet, page-1);
                    getSupportFragmentManager().beginTransaction().replace(R.id.sjtContent, problem).commit();
                    break;

            }

        }
        catch (IOException e) {

            e.printStackTrace();
        }
        catch (BiffException e) {

            e.printStackTrace();
        }
    }

    @Override
    public boolean onComplete(boolean complete) {

        isComplete = complete;

        return true;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {

            String selection = ((TextView) view).getText().toString();

            if (selection.equalsIgnoreCase("Knowledge and Comprehension Test")) {

                //open Knowledge and Comprehension Post Test page
                openKnowledgeComprehensionPostTestPage();
            }
            else if(selection.equalsIgnoreCase("Home")){

                //ope home page
                openTestSelectionPage();

            }
            else if(selection.equalsIgnoreCase("Baseline Skills Survey")){

                //ope home page
                openBaselineSkillsSurvery();

            }
            else if(selection.equalsIgnoreCase("Situational Judgment Test")){

                openSituationalJudgmentTestPage();

            }
            else if (selection.equalsIgnoreCase("Team Action Processes")) {

                //open Team Action Processes page
                openTeamActionProcessPage();

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

            drawerLayout.closeDrawer(drawerListView);

        }
    }

    public void openBaselineSkillsSurvery(){

        Intent intent = new Intent(this, BaselineSkillsSurveyActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("SJTCOMPLETE", true);

        startActivity(intent);

    }

    public void openTestSelectionPage() {

        //Build Intent
        Intent intent = new Intent(this, TestSelectionActivity.class);


        intent.putExtras(bundle);
        intent.putExtra("SJTCOMPLETE", true);


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openSituationalJudgmentTestPage() {

        //build intent
        Intent intent = new Intent(this, SituationalJudgmentTestActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("SJTCOMPLETE", true);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }


    public void openKnowledgeComprehensionPostTestPage() {

        //build intent
        Intent intent = new Intent(this, KnowledgeComprehensionPostTestActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("SJTCOMPLETE", true);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTeamActionProcessPage() {

        //build intent
        Intent intent = new Intent(this, TeamActionProcessActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("SJTCOMPLETE", true);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTeamCohesionMeasurePage() {

        //build intent
        Intent intent = new Intent(this, TeamCohesionMeasureActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("SJTCOMPLETE", true);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTeamEfficacyMeasurePage() {

        //build intent
        Intent intent = new Intent(this, TeamEfficacyMeasureActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("SJTCOMPLETE", true);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTeamPerformancePage() {

        //build intent
        Intent intent = new Intent(this, TeamPerformanceActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("SJTCOMPLETE", true);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

}
