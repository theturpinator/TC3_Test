package com.cognitiveperformancegroup.tc3_version_11;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TestSelectionActivity extends AppCompatActivity {

    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_selection);

        bundle = getIntent().getExtras();

        if(getIntent().getExtras() != null){

            boolean bssIsComplete = getIntent().getBooleanExtra("BSSCOMPLETE", false);
            boolean kctIsComplete = getIntent().getBooleanExtra("KCTCOMPLETE", false);
            boolean tapIsComplete = getIntent().getBooleanExtra("TAPCOMPLETE", false);
            boolean tcmIsComplete = getIntent().getBooleanExtra("TCMCOMPLETE", false);
            boolean temIsComplete = getIntent().getBooleanExtra("TEMCOMPLETE", false);
            boolean tpmIsComplete = getIntent().getBooleanExtra("TPMCOMPLETE", false);
            boolean sjtIsComplete = getIntent().getBooleanExtra("SJTCOMPLETE", false);

            if(bssIsComplete){

                ImageView bssCheckButton = (ImageView) findViewById(R.id.bss_complete_checkmark);

                bssCheckButton.setBackground(getDrawable(R.drawable.observed_checkmark_green));

            }

            if(kctIsComplete){

                ImageView kctCheckButton = (ImageView) findViewById(R.id.kct_complete_checkmark);

                kctCheckButton.setBackground(getDrawable(R.drawable.observed_checkmark_green));

            }

            if(tapIsComplete){

                ImageView tapCheckButton = (ImageView) findViewById(R.id.tap_complete_checkmark);

                tapCheckButton.setBackground(getDrawable(R.drawable.observed_checkmark_green));

            }

            if(tcmIsComplete){

                ImageView tcmCheckButton = (ImageView) findViewById(R.id.tcm_complete_checkmark);

                tcmCheckButton.setBackground(getDrawable(R.drawable.observed_checkmark_green));

            }

            if(temIsComplete){

                ImageView temCheckButton = (ImageView) findViewById(R.id.tem_complete_checkmark);

                temCheckButton.setBackground(getDrawable(R.drawable.observed_checkmark_green));

            }

            if(tpmIsComplete){

                ImageView tpmCheckButton = (ImageView) findViewById(R.id.tpm_complete_checkmark);

                tpmCheckButton.setBackground(getDrawable(R.drawable.observed_checkmark_green));

            }

            if(sjtIsComplete){

                ImageView sjtCheckButton = (ImageView) findViewById(R.id.sjt_complete_checkmark);

                sjtCheckButton.setBackground(getDrawable(R.drawable.observed_checkmark_green));

            }

            if(bssIsComplete && kctIsComplete && tapIsComplete && tcmIsComplete && temIsComplete && tpmIsComplete && sjtIsComplete){

                AlertDialog.Builder finishedAlert = new AlertDialog.Builder(TestSelectionActivity.this, R.style.TC3AlertDialog2);
                final TextView finishedAlertText = new TextView(TestSelectionActivity.this);

                finishedAlertText.setWidth(600);
                finishedAlertText.setPadding(20, 20, 20, 20);
                finishedAlert.setView(finishedAlertText);
                finishedAlert.setTitle("Thank You!");
                finishedAlertText.setGravity(Gravity.TOP | Gravity.LEFT);
                finishedAlertText.setText("You have finished all of the tests. Thank you for participating!");

                finishedAlert.setPositiveButton("OK", null);


                finishedAlert.show();

            }



        }

        final Button saveAndExit = (Button) findViewById(R.id.testselection_save_and_exit);

        saveAndExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder instructions = new AlertDialog.Builder(TestSelectionActivity.this, R.style.TC3AlertDialog);
                final TextView instructionsText = new TextView(TestSelectionActivity.this);

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


        //create buttons
        final Button nextButton = (Button) findViewById(R.id.nextButton);
        final Button backButton = (Button) findViewById(R.id.backButton);
        final Button menuButton = (Button) findViewById(R.id.menu_button);

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //open Knowledge and Comprehension Post Test
                openBaselineSkillsSurvery();

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //open Knowledge and Comprehension Post Test
                openParticipantDaySelectionPage();

            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //open navigation drawer
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                drawerLayout.openDrawer(drawerListView);

            }
        });
    }



    public void openKnowledgeComprehensionPostTestPage() {

        //build intent
        Intent intent = new Intent(this, KnowledgeComprehensionPostTestActivity.class);

        if(getIntent().getExtras() == null){

            intent.putExtra("BSSCOMPLETE", false);
            intent.putExtra("KCTCOMPLETE", false);
            intent.putExtra("TAPCOMPLETE", false);
            intent.putExtra("TCMCOMPLETE", false);
            intent.putExtra("TEMCOMPLETE", false);
            intent.putExtra("TPMCOMPLETE", false);
            intent.putExtra("SJTCOMPLETE", false);

        }else{

            intent.putExtras(bundle);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openParticipantDaySelectionPage() {

        //Build Intent
        Intent intent = new Intent(this, ParticipantDaySelectionActivity.class);

        if(getIntent().getExtras() == null){

            intent.putExtra("BSSCOMPLETE", false);
            intent.putExtra("KCTCOMPLETE", false);
            intent.putExtra("TAPCOMPLETE", false);
            intent.putExtra("TCMCOMPLETE", false);
            intent.putExtra("TEMCOMPLETE", false);
            intent.putExtra("TPMCOMPLETE", false);
            intent.putExtra("SJTCOMPLETE", false);

        }else{

            intent.putExtras(bundle);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTeamActionProcessPage() {

        //build intent
        Intent intent = new Intent(this, TeamActionProcessActivity.class);

        if(getIntent().getExtras() == null){

            intent.putExtra("BSSCOMPLETE", false);
            intent.putExtra("KCTCOMPLETE", false);
            intent.putExtra("TAPCOMPLETE", false);
            intent.putExtra("TCMCOMPLETE", false);
            intent.putExtra("TEMCOMPLETE", false);
            intent.putExtra("TPMCOMPLETE", false);
            intent.putExtra("SJTCOMPLETE", false);

        }else{

            intent.putExtras(bundle);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTeamCohesionMeasurePage() {

        //build intent
        Intent intent = new Intent(this, TeamCohesionMeasureActivity.class);

        if(getIntent().getExtras() == null){

            intent.putExtra("BSSCOMPLETE", false);
            intent.putExtra("KCTCOMPLETE", false);
            intent.putExtra("TAPCOMPLETE", false);
            intent.putExtra("TCMCOMPLETE", false);
            intent.putExtra("TEMCOMPLETE", false);
            intent.putExtra("TPMCOMPLETE", false);
            intent.putExtra("SJTCOMPLETE", false);

        }else{

            intent.putExtras(bundle);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openSituationalJudgmentTestPage() {

        //build intent
        Intent intent = new Intent(this, SituationalJudgmentTestActivity.class);

        if(getIntent().getExtras() == null){

            intent.putExtra("BSSCOMPLETE", false);
            intent.putExtra("KCTCOMPLETE", false);
            intent.putExtra("TAPCOMPLETE", false);
            intent.putExtra("TCMCOMPLETE", false);
            intent.putExtra("TEMCOMPLETE", false);
            intent.putExtra("TPMCOMPLETE", false);
            intent.putExtra("SJTCOMPLETE", false);

        }else{

            intent.putExtras(bundle);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTeamEfficacyMeasurePage() {

        //build intent
        Intent intent = new Intent(this, TeamEfficacyMeasureActivity.class);

        if(getIntent().getExtras() == null){

            intent.putExtra("BSSCOMPLETE", false);
            intent.putExtra("KCTCOMPLETE", false);
            intent.putExtra("TAPCOMPLETE", false);
            intent.putExtra("TCMCOMPLETE", false);
            intent.putExtra("TEMCOMPLETE", false);
            intent.putExtra("TPMCOMPLETE", false);
            intent.putExtra("SJTCOMPLETE", false);

        }else{

            intent.putExtras(bundle);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openTeamPerformancePage() {

        //build intent
        Intent intent = new Intent(this, TeamPerformanceActivity.class);

        if(getIntent().getExtras() == null){

            intent.putExtra("BSSCOMPLETE", false);
            intent.putExtra("KCTCOMPLETE", false);
            intent.putExtra("TAPCOMPLETE", false);
            intent.putExtra("TCMCOMPLETE", false);
            intent.putExtra("TEMCOMPLETE", false);
            intent.putExtra("TPMCOMPLETE", false);
            intent.putExtra("SJTCOMPLETE", false);

        }else{

            intent.putExtras(bundle);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openBaselineSkillsSurvery(){

        Intent intent = new Intent(this, BaselineSkillsSurveyActivity.class);

        if(getIntent().getExtras() == null){

            intent.putExtra("BSSCOMPLETE", false);
            intent.putExtra("KCTCOMPLETE", false);
            intent.putExtra("TAPCOMPLETE", false);
            intent.putExtra("TCMCOMPLETE", false);
            intent.putExtra("TEMCOMPLETE", false);
            intent.putExtra("TPMCOMPLETE", false);
            intent.putExtra("SJTCOMPLETE", false);

        }
        else{

            intent.putExtras(bundle);
        }
        startActivity(intent);

    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {

            String selection = ((TextView) view).getText().toString();

            if (selection.equalsIgnoreCase("Knowledge and Comprehension Test")) {

                //open Knowledge and Comprehension Post Test page
                openKnowledgeComprehensionPostTestPage();
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



    @Override
    public void onBackPressed() {

        //super.onBackPressed();
    }
}


