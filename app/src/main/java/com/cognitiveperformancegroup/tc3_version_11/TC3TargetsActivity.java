package com.cognitiveperformancegroup.tc3_version_11;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class TC3TargetsActivity extends AppCompatActivity {

    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private Button menuButton;
    private int numRows;
    private int numCol;
    final Context context = this;
    private String scenario;
    TextView scenarioTextView;
    private int sheetNum;
    private int row;
    private String[] eventNumbers;
    private String domain;
    private String group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tc3_targets);

        group = getIntent().getExtras().getString("group");

        domain = getIntent().getExtras().getString("domain");

        sheetNum = getIntent().getExtras().getInt("sheetNum");
        row = getIntent().getExtras().getInt("rowNum");

        //Toast.makeText(getApplicationContext(), "sheetnum: " + sheetNum, Toast.LENGTH_LONG).show();


        try {
            InputStream is = getAssets().open("Tests.xls");

            //create Workbook object
            Workbook workbook = Workbook.getWorkbook(is);

            //create Sheet object of first sheet
            Sheet sheet = workbook.getSheet(sheetNum);



            numCol = sheet.getColumns();
            numRows = sheet.getRows();

            TextView[] temp = new TextView[numCol];
            final EditText[] editText = new EditText[numCol];
            final Button[] checkButtons = new Button[numCol];
            final Button[] editButtons = new Button[numCol];
            LinearLayout[] outerHorizontal = new LinearLayout[numCol];
            LinearLayout[] innerVertical = new LinearLayout[numCol];
            LinearLayout[] innerHorizontal = new LinearLayout[numCol];
            LinearLayout[] linearLayout = new LinearLayout[numCol];
            String[] commentText = new String[numCol];
            LinearLayout[] checkGroupLayout1 = new LinearLayout[numCol];
            LinearLayout[] checkGroupLayout2 = new LinearLayout[numCol];


            eventNumbers = new String[numRows];

            //store all of the event numbers in string array
            for (int k = 0; k < numRows; k++) {


                eventNumbers[k] = sheet.getCell(1, k).getContents();
            }


            int numTargets = Integer.parseInt(sheet.getCell(0, row).getContents());


            for (int i = 0; i < numTargets; i++) {

                //Toast.makeText(getApplicationContext(), "row: " + row, Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), "numRows: " + numRows, Toast.LENGTH_LONG).show();

                int size = 4;

                //create LayoutParams objects for the horizontal/vertical layout holding each target
                LinearLayout.LayoutParams[] layoutParams = new LinearLayout.LayoutParams[size];

                //initialize layout parameters
                for (int j = 0; j < size; j++) {

                    if (j < (size - 1)) {
                        layoutParams[j] = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    } else {
                        layoutParams[j] = new LinearLayout.LayoutParams(400, 200);
                    }
                }


                linearLayout[i] = (LinearLayout) findViewById(R.id.middle);

                layoutParams[0].setMargins(10, 0, 0, 10);
                outerHorizontal[i] = new LinearLayout(this);
                outerHorizontal[i].setLayoutParams(layoutParams[0]);
                outerHorizontal[i].setOrientation(LinearLayout.HORIZONTAL);

                layoutParams[1].setMargins(5, 0, 20, 0);
                innerVertical[i] = new LinearLayout(this);
                innerVertical[i].setLayoutParams(layoutParams[1]);
                innerVertical[i].setOrientation(LinearLayout.VERTICAL);

                layoutParams[2].setMargins(0, 30, 0, 0);
                innerHorizontal[i] = new LinearLayout(this);
                innerHorizontal[i].setLayoutParams(layoutParams[2]);
                innerHorizontal[i].setOrientation(LinearLayout.HORIZONTAL);

                checkButtons[i] = new Button(this);
                checkButtons[i].setBackground(getDrawable(R.drawable.observed_checkmark_grey));
                checkButtons[i].setLayoutParams(new LinearLayout.LayoutParams(110, 110));

                outerHorizontal[i].addView(checkButtons[i]);

                final int index = i;

                checkButtons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (checkButtons[index].getBackground().getConstantState() == getDrawable(R.drawable.observed_checkmark_grey).getConstantState()) {

                            checkButtons[index].setBackground(getDrawable(R.drawable.observed_checkmark_green));
                            writeOutputExcel();

                        } else {

                            checkButtons[index].setBackground(getDrawable(R.drawable.observed_checkmark_grey));

                        }


                    }
                });


                temp[i] = new TextView(this);
                temp[i].setText((i + 1) + ". " + sheet.getCell(i + 3, row).getContents());
                temp[i].setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Large);
                temp[i].setTextColor(Color.rgb(0, 0, 0));
                temp[i].setPadding(15, 20, 0, 0);

                innerVertical[i].addView(temp[i]);






                //create horizontal linear layout to contain check boxes
                checkGroupLayout1[i] = new LinearLayout(this);
                checkGroupLayout1[i].setOrientation(LinearLayout.HORIZONTAL);

                checkGroupLayout2[i] = new LinearLayout(this);
                checkGroupLayout2[i].setOrientation(LinearLayout.HORIZONTAL);

                int numRoles = 5;
                String role = "";

                //create array of check boxes, size 5
                CheckBox[] roleButtons = new CheckBox[numCol];


                for (int k = 0; k < numRoles; k++) {

                    roleButtons[k] = new CheckBox(this);

                    if (k == 0) {
                        role = "Casualty";
                    }
                    else if (k == 1) {
                        role = "Fire Team";
                    }
                    else if (k == 2) {
                        role = "First Responder";
                    }
                    else if (k == 3) {
                        role = "Squad Leader";
                    }
                    else if (k == 4) {
                        role = "Medic/Corpsman";
                    }


                    roleButtons[k].setText(role);
                    roleButtons[k].setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Large);
                    roleButtons[k].setTextColor(Color.rgb(0, 0, 0));
                    roleButtons[k].setPadding(15, 20, 0, 0);

                    if (k < 3) {
                        checkGroupLayout1[i].setGravity(Gravity.CENTER_HORIZONTAL);
                        checkGroupLayout1[i].addView(roleButtons[k]);
                    }
                    else {
                        checkGroupLayout2[i].setGravity(Gravity.CENTER_HORIZONTAL);
                        checkGroupLayout2[i].addView(roleButtons[k]);
                    }


                }

                innerVertical[i].addView(checkGroupLayout1[i]);
                innerVertical[i].addView(checkGroupLayout2[i]);






                //event description
                if (i == 0) {

                    //append event description title
                    TextView eventText = (TextView) findViewById(R.id.event_description);
                    eventText.append(sheet.getCell(2, row).getContents());

                    //append the scenario title to include the scenario type passed in to activity
                    scenario = getIntent().getExtras().getString("scenario");
                    scenarioTextView = (TextView) findViewById(R.id.scenario);
                    scenarioTextView.append(scenario);

                    //append event number title
                    TextView eventNumber = (TextView) findViewById(R.id.event);
                    eventNumber.append(sheet.getCell(1, row).getContents());
                }

                layoutParams[3].setMargins(40, 0, 0, 0);
                editText[i] = new EditText(this);
                editText[i].setBackgroundResource(R.drawable.rounded_corners);
                GradientDrawable drawable = (GradientDrawable) editText[i].getBackground();
                drawable.setColor(Color.WHITE);
                editText[i].setPadding(5, 5, 0, 5);
                editText[i].setLayoutParams(layoutParams[3]);
                editText[i].setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Large);
                editText[i].setTextColor(Color.rgb(0, 0, 0));
                editText[i].setCursorVisible(false);
                editText[i].setOnTouchListener(
                        new View.OnTouchListener() {
                            public boolean onTouch(View v, MotionEvent event) {
                                return true; // the listener has consumed the event
                            }
                        });

                editButtons[i] = new Button(this);
                editButtons[i].setTextColor(Color.rgb(0, 0, 0));
                editButtons[i].setBackground(getDrawable(R.drawable.blue_button));
                editButtons[i].setTextColor(Color.rgb(240, 240, 240));
                editButtons[i].setText("Add Comment");
                editButtons[i].setLayoutParams(new LinearLayout.LayoutParams(300, 100));
                editButtons[i].setTypeface(Typeface.DEFAULT_BOLD);

                //set to multiline
                editText[index].setLines(5);
                editText[index].setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                editText[index].setSingleLine(false);
                editText[index].setHorizontallyScrolling(false);
                editText[index].setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

                editButtons[index].setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (checkButtons[index].getBackground().getConstantState() == getDrawable(R.drawable.observed_checkmark_green).getConstantState()) {
                                    AlertDialog.Builder comments = new AlertDialog.Builder(context, R.style.TC3AlertDialog);
                                    final EditText commentsInput = new EditText(context);

                                    commentsInput.setWidth(600);
                                    comments.setView(commentsInput);
                                    comments.setTitle("Comments");
                                    commentsInput.setInputType(InputType.TYPE_CLASS_TEXT);
                                    commentsInput.setLines(5);
                                    commentsInput.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_CLASS_TEXT);
                                    commentsInput.setSingleLine(false);
                                    commentsInput.setHorizontallyScrolling(false);
                                    commentsInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
                                    commentsInput.setGravity(Gravity.TOP | Gravity.LEFT);
                                    commentsInput.setText(editText[index].getText());

                                    comments.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            editText[index].setText(commentsInput.getText().toString());

                                            if (editText[index].getText().toString().equals("")) {

                                                editButtons[index].setText("Add Comment");

                                            } else {
                                                editButtons[index].setText("Edit Comment");
                                            }

                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                                        }
                                    });

                                    comments.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();

                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                                        }
                                    });


                                    comments.show();


                                } else {

                                    Toast.makeText(getApplicationContext(), "You must first indicate that this target has been observed", Toast.LENGTH_SHORT).show();

                                }

                            }


                        });


                innerHorizontal[i] .setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                innerHorizontal[i].addView(editButtons[i]);
                innerHorizontal[i].addView(editText[i]);

                innerVertical[i].addView(innerHorizontal[i]);
                outerHorizontal[i].addView(innerVertical[i]);
                linearLayout[i].addView(outerHorizontal[i]);

                final Button nextButton = (Button) findViewById(R.id.nextButton);
                final int tempTargets = numTargets;

                nextButton.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {

                                                      if (row < numRows-1) {
                                                          //go to next row
                                                          row++;
                                                          openTC3(sheetNum, row);
                                                      } else {

                                                          AlertDialog.Builder instructions = new AlertDialog.Builder(TC3TargetsActivity.this, R.style.TC3AlertDialog);
                                                          final TextView instructionsText = new TextView(TC3TargetsActivity.this);

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

                                                          //-----------------------


                                                      }

                                                  }
                                              }
                );



            }


        } catch (IOException e) {

            e.printStackTrace();
        } catch (BiffException e) {

            e.printStackTrace();
        }


































        menuButton = (Button) findViewById(R.id.menu_button);

        //disable fading when opening drawer menu
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));

        //get list items from strings.xml
        drawerListViewItems = eventNumbers;

        //get ListView defined in activity_main.xml
        drawerListView = (ListView) findViewById(R.id.left_drawer);
        drawerListView.setBackgroundColor(Color.rgb(0, 0, 119));

        //set the adapter for the list view
        drawerListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_items, drawerListViewItems));

        drawerListView.setOnItemClickListener(new DrawerItemClickListener());


        final Button saveAndExit = (Button) findViewById(R.id.kct_save_and_exit);

        saveAndExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                intent.setClass(getApplicationContext(), LoginActivity.class);

                startActivity(intent);

            }
        });

        final Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                              if (row == 0) {

                                                  //go to scenario selection page
                                                  openScenarioSelectionPage(domain);
                                              }
                                              else {

                                                  //go to previous row
                                                  row--;
                                                  openTC3(sheetNum, row);
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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {

            String selection = ((TextView) view).getText().toString();

            for (int i = 0; i < numRows; i++) {


                if (selection.equalsIgnoreCase(eventNumbers[i])) {

                    openTC3(sheetNum, i);

                }

            }

            drawerLayout.closeDrawer(drawerListView);

        }
    }

    public void openTC3(int sheetNum, int rowNum) {

        //build intent
        Intent intent = new Intent(this, TC3TargetsActivity.class);

        intent.putExtra("group", group);

        intent.putExtra("domain", domain);

        intent.putExtra("scenario", scenario);

        intent.putExtra("sheetNum", sheetNum);

        intent.putExtra("rowNum", rowNum);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    public void openScenarioSelectionPage(String s) {

        //Build Intent
        Intent intent = new Intent(this, ScenarioSelectionActivity.class);

        //pass value to next activity
        intent.putExtra("domain", s);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        //should users be allowed to go back to this page?
        finish();
    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed();
    }

    public void writeOutputExcel() {

        try {

            String filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents/output.xls";

            File f = new File(filename);

            WritableWorkbook workbook;

            if(!f.exists()){

                WorkbookSettings wbSettings = new WorkbookSettings();

                wbSettings.setLocale(new Locale("en", "EN"));

                workbook = Workbook.createWorkbook(f, wbSettings);

            }else{

                Workbook copiedWorkbook = Workbook.getWorkbook(f);

                workbook = Workbook.createWorkbook(f, copiedWorkbook);



            }

            WritableSheet sheet;

            if(workbook.getSheet("ASA-B1") == null) {
                sheet = workbook.createSheet("ASA-B1", 0);
            }else{

                sheet = workbook.getSheet("ASA-B1");

            }






            try {




                //not used
                //WritableCell cell = sheet2.getWritableCell(0, 0);

                int num = 0;

                if (group.charAt(0) == 'X') {

                    num = 1;
                }
                else if (group.charAt(0) == 'A') {

                    num = 2;
                }
                else if (group.charAt(0) == 'B') {

                    num = 3;
                }
                else if (group.charAt(0) == 'C') {

                    num = 4;
                }
                else if (group.charAt(0) == 'D') {

                    num = 5;
                }
                else if (group.charAt(0) == 'E') {

                    num = 6;
                }
                else if (group.charAt(0) == 'F') {

                    num = 7;
                }
                else if (group.charAt(0) == 'G') {

                    num = 8;
                }
                else if (group.charAt(0) == 'H') {

                    num = 9;
                }

                Label label = new Label(0, num, group);
                sheet.addCell(label); //add cell!

                WritableCellFeatures wcf = new WritableCellFeatures();
                //wcf.setComment();

                // set cell features!
                label.setCellFeatures(wcf);

                workbook.write();
                workbook.close();


            }
            catch (IOException e) {

                e.printStackTrace();
            } catch (WriteException e) {

                e.printStackTrace();
            }



            //FileWriter fw = new FileWriter(filename, true);

            //fw.write("testing....");
            //fw.close();


        } catch (IOException ioe) {

            Toast.makeText(getApplicationContext(), "Unable to save response.", Toast.LENGTH_SHORT).show();
            ioe.printStackTrace();

        } catch (BiffException e) {
            e.printStackTrace();
        }

    }
}
