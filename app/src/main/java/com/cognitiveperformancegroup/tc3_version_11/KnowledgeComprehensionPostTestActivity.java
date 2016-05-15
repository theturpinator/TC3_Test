package com.cognitiveperformancegroup.tc3_version_11;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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


public class KnowledgeComprehensionPostTestActivity extends AppCompatActivity {

    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private Button menuButton;
    private int numRows;
    private int numCol;
    private LinearLayout imageLayout;
    private Button question14_image;
    private Button question15_image;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_comprehension_post_test);

        bundle = getIntent().getExtras();

        try
        {
            InputStream is = getAssets().open("Tests.xls");

            //create Workbook object
            Workbook workbook = Workbook.getWorkbook(is);

            //create Sheet object of first sheet
            Sheet sheet = workbook.getSheet(0);

            numRows = sheet.getRows();
            numCol = sheet.getColumns();

            TextView[] temp = new TextView[numCol];
            LinearLayout[] linearLayout = new LinearLayout[numCol];
            RadioGroup[] groups = new RadioGroup[numCol];
            final RadioButton[] buttons = new RadioButton[(numRows-1)*(numCol)];

            for (int i = 0; i < numCol; i++) {

                linearLayout[i] = (LinearLayout) findViewById(R.id.middle);

                if(i == 0 || i == 15 || i == 30 || i == 45 || i == 54){

                    LinearLayout.LayoutParams tvlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
                            .WRAP_CONTENT);
                    tvlp.setMargins(20, 10, 0, 10);

                    TextView test = new TextView(this);
                    test.setTextSize(25);
                    test.setTypeface(Typeface.DEFAULT_BOLD);
                    test.setTextColor(Color.parseColor("#F8A219"));
                    test.setLayoutParams(tvlp);


                    LinearLayout.LayoutParams hrlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
                    hrlp.setMargins(0, 20, 0, 0);
                    View hr = new View(this);
                    hr.setBackgroundColor(Color.WHITE);
                    hr.setLayoutParams(hrlp);

                    switch(i){

                        case 0:

                            test.setText("Advanced Situational Awareness");
                            break;

                        case 15:

                            linearLayout[i].addView(hr);
                            test.setText("Tactical Combat Casualty Care");
                            break;

                        case 30:

                            linearLayout[i].addView(hr);
                            test.setText("Resilience and Performance Enhancement");
                            break;

                        case 45:

                            linearLayout[i].addView(hr);
                            test.setText("Team Development & IAAR ");
                            break;

                        case 54:

                            linearLayout[i].addView(hr);
                            test.setText("Team Development");
                            break;


                    }



                    linearLayout[i].addView(test);


                }

                groups[i] = new RadioGroup(this);
                groups[i].setOrientation(RadioGroup.VERTICAL);
                temp[i] = new TextView(this);
                temp[i].setText((i + 1) + ". " + sheet.getCell(i, 0).getContents());
                temp[i].setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Large);
                temp[i].setPadding(15, 20, 0, 0);
                linearLayout[i].addView(temp[i]);

                if (i == 13) {

                    imageLayout = new LinearLayout(this);
                    imageLayout.setOrientation(LinearLayout.VERTICAL);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    layoutParams.setMargins(150, 20, 150, 20);

                    question14_image = new Button(this);
                    question14_image.setBackground(getDrawable(R.drawable.question14pic));

                    imageLayout.addView(question14_image, layoutParams);

                    linearLayout[i].addView(imageLayout);
                }

                if (i == 14) {
                    imageLayout = new LinearLayout(this);
                    imageLayout.setOrientation(LinearLayout.VERTICAL);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    layoutParams.setMargins(150, 20, 150, 20);

                    question15_image = new Button(this);
                    question15_image.setBackground(getDrawable(R.drawable.question15pic));

                    imageLayout.addView(question15_image, layoutParams);

                    linearLayout[i].addView(imageLayout);
                }


                for (int j = 0; j < numRows; j++) {
                    if (j > 0) {
                        buttons[(i * 4) + (j - 1)] = new RadioButton(this);
                        buttons[(i * 4) + (j - 1)].setText(sheet.getCell(i, j).getContents());
                        buttons[(i * 4) + (j - 1)].setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Large);
                        buttons[(i * 4) + (j - 1)].setButtonDrawable(new StateListDrawable());
                        buttons[(i * 4) + (j - 1)].setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.apptheme_btn_radio_holo_dark), null, null, null);

                        groups[i].addView(buttons[(i * 4) + (j - 1)]);
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


        final Button instructions = (Button) findViewById(R.id.kct_instructions);

        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder instructions = new AlertDialog.Builder(KnowledgeComprehensionPostTestActivity.this, R.style.TC3AlertDialog);
                final TextView instructionsText = new TextView(KnowledgeComprehensionPostTestActivity.this);

                instructionsText.setWidth(600);
                instructionsText.setPadding(20, 20, 20, 20);
                instructions.setView(instructionsText);
                instructions.setTitle("Instructions");
                instructionsText.setGravity(Gravity.TOP | Gravity.LEFT);
                instructionsText.setText("Knowledge and Comprehension Test -  A multiple choice test will assess participant knowledge and comprehension of the five topics areas presented during classroom instruction (TC3, ASA, R/PE, TD, and IAAR). In the experimental condition, a pre-test will be administered prior to start of classroom instruction on the first day. A post-test for TC3 and ASA will be administered at the end of day one, and a post-test for R/PE, TD, and IAAR will be administered at the end of day two. In the control condition, a pre-test will be administered before the first live exercise, and a post test will be administered after the second live scenario exercise.\n" +
                        "\n" +
                        "The purpose of this test is to assess your current understanding of information and concepts that were introduced during the Squad Overmatch (SOvM) course.    Please read the item and select the best response.  If you do not know, go on to the next question.   Enter all of your responses on the score sheet provided.  This test should take about 30 minutes to complete.");

                instructions.setPositiveButton("OK", null);


                instructions.show();

            }
        });

        final Button saveAndExit = (Button) findViewById(R.id.kct_save_and_exit);

        saveAndExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder instructions = new AlertDialog.Builder(KnowledgeComprehensionPostTestActivity.this, R.style.TC3AlertDialog);
                final TextView instructionsText = new TextView(KnowledgeComprehensionPostTestActivity.this);

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

        final Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener()
                                      {
                                          @Override
                                          public void onClick(View v) {

                                              //go to previous page
                                              openBaselineSkillsSurvery();

                                          }
                                      }
        );

        final Button nextButton = (Button) findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener()
                                      {
                                          @Override
                                          public void onClick(View v) {

                                              //go to previous page
                                              openTeamActionProcessPage();
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


    public void openBaselineSkillsSurvery(){

        Intent intent = new Intent(this, BaselineSkillsSurveyActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("KCTCOMPLETE", true);

        startActivity(intent);

    }
    public void openTestSelectionPage() {

        //Build Intent
        Intent intent = new Intent(this, TestSelectionActivity.class);


        intent.putExtras(bundle);
        intent.putExtra("KCTCOMPLETE", true);


        startActivity(intent);

        //should users be allowed to go back to this page?
        //finish();
    }

    public void openSituationalJudgmentTestPage() {

        //build intent
        Intent intent = new Intent(this, SituationalJudgmentTestActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("KCTCOMPLETE", true);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {

            String selection = ((TextView) view).getText().toString();

            if (selection.equalsIgnoreCase("Knowledge and Comprehension Post Test")) {

                //open Knowledge and Comprehension Post Test page
                openKnowledgeComprehensionPostTestPage();
            }
            else if(selection.equalsIgnoreCase("Baseline Skills Survey")){

                //ope home page
                openBaselineSkillsSurvery();

            }
            else if(selection.equalsIgnoreCase("Home")){

                //ope home page
                openTestSelectionPage();

            }
            else if (selection.equalsIgnoreCase("Team Action Processes")) {

                //open Team Action Processes page
                openTeamActionProcessPage();
            }
            else if (selection.equalsIgnoreCase("Team Cohesion Measure")) {

                //open Team Cohesion Measure page
                openTeamCohesionMeasurePage();
            }
            else if(selection.equalsIgnoreCase("Situational Judgment Test")){

                openSituationalJudgmentTestPage();

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

    public void openKnowledgeComprehensionPostTestPage() {

        //build intent
        Intent intent = new Intent(this, KnowledgeComprehensionPostTestActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("KCTCOMPLETE", true);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTeamActionProcessPage() {

        //build intent
        Intent intent = new Intent(this, TeamActionProcessActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("KCTCOMPLETE", true);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTeamCohesionMeasurePage() {

        //build intent
        Intent intent = new Intent(this, TeamCohesionMeasureActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("KCTCOMPLETE", true);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTeamEfficacyMeasurePage() {

        //build intent
        Intent intent = new Intent(this, TeamEfficacyMeasureActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("KCTCOMPLETE", true);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTeamPerformancePage() {

        //build intent
        Intent intent = new Intent(this, TeamPerformanceActivity.class);

        intent.putExtras(bundle);
        intent.putExtra("KCTCOMPLETE", true);

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