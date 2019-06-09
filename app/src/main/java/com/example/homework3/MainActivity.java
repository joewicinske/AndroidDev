package com.example.homework3;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.homework3.dummy.DummyContent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.Locale;

/**
 * RecyclerView Branch 
 */
public class MainActivity extends AppCompatActivity implements GlucoseDataFragment.OnListFragmentInteractionListener {

    private EditText fastingInput, breakfastInput, lunchInput, dinnerInput, notesInput;
    private TextView resultsView, dateLabel;
    private Button clearButton, historyButton, submitButton;
    private CheckBox normalCheckBox;
    private List<GlucoseData> dataList = DummyContent.ITEMS;
    private GlucoseDataFragment myFrag = new GlucoseDataFragment();
    private String[] notes = new String[] {"", "", "", ""};
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
    }

    private void initializeViews() {
        resultsView = (TextView) findViewById(R.id.resultLabel);
        fastingInput = (EditText) findViewById(R.id.fastingInput);
        View.OnFocusChangeListener  focusChangeListener =new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                switch (v.getId()){
                    case R.id.fastingInput:
                        if (!hasFocus && !fastingInput.getText().toString().isEmpty()){
                            // user just entered or modified a value
                            int fastingNum = Integer.parseInt(fastingInput.getText().toString());
                            notes[0] = evaluateResult("Fasting:", fastingNum);
                            rewriteNotes();
                        }else if (!hasFocus && fastingInput.getText().toString().isEmpty()) {
                    notes[0] = "";
                         }

                        break;
                    case R.id.lunchInput:
                        if (!hasFocus && !lunchInput.getText().toString().isEmpty()){
                            int lunchNum = Integer.parseInt(lunchInput.getText().toString());
                            notes[1] = evaluateResult("Lunch:", lunchNum);
                            rewriteNotes();
                        }else if (!hasFocus && lunchInput.getText().toString().isEmpty()) {
                    notes[1] = "";
                        }
                        break;
                    case R.id.dinnerInput:
                        if (!hasFocus && !dinnerInput.getText().toString().isEmpty()){
                            int dinnerNum = Integer.parseInt(dinnerInput.getText().toString());
                            notes[2] = evaluateResult("Dinner:", dinnerNum);
                            rewriteNotes();

                        }else if (!hasFocus && dinnerInput.getText().toString().isEmpty()) {
                            notes[2] = "";
                        }
                        break;
                    case R.id.breakfastInput:
                        if (!hasFocus && !breakfastInput.getText().toString().isEmpty()){
                            int breakfastNum = Integer.parseInt(breakfastInput.getText().toString());
                            notes[3] = evaluateResult("Breakfast:", breakfastNum);
                            rewriteNotes();

                        }else if (!hasFocus && breakfastInput.getText().toString().isEmpty()){
                            notes[3] = "";
                        }
                        break;
                    default:
                        Log.e(this.getClass().getSimpleName(), "Error in Actions of Buttons");
                }
            }
        };

        breakfastInput = (EditText) findViewById(R.id.breakfastInput);
        lunchInput = (EditText) findViewById(R.id.lunchInput);
        dinnerInput = (EditText) findViewById(R.id.dinnerInput);
        notesInput = (EditText) findViewById(R.id.notesInput);

        lunchInput.setOnFocusChangeListener(focusChangeListener);
        dinnerInput.setOnFocusChangeListener(focusChangeListener);
        breakfastInput.setOnFocusChangeListener(focusChangeListener);
        fastingInput.setOnFocusChangeListener(focusChangeListener);

        clearButton = (Button) findViewById(R.id.clearButton);
        historyButton = (Button) findViewById(R.id.historyButton);
        normalCheckBox = (CheckBox) findViewById(R.id.normalCheckBoxItem);
        dateLabel = (TextView) findViewById(R.id.dateLabel);
       // dateLabel.setText(getDateLabelTxt(new Date()));
        dateLabel.setText("Select a Date:");
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar cal =   Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                cal.set(Calendar.MONTH, month);
                dateLabel.setText(getDateLabelTxt(cal.getTime()));
            }
        };

        dateLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Black, dateSetListener, year, month ,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        submitButton = (Button) findViewById(R.id.submitButton);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.clearButton:
                        clearInputs();
                        break;
                    case R.id.historyButton:
                        getHistory();
                        break;
                    case R.id.submitButton:
                        submitData();
                        break;
                    default:
                        Log.e(this.getClass().getSimpleName(), "Error in Actions of Buttons");
                }
            }
        };
        clearButton.setOnClickListener(listener);
        historyButton.setOnClickListener(listener);
        submitButton.setOnClickListener(listener);

    }

    private String evaluateResult(String s, int glucoseLevel) {
        String finalResult;
        if (glucoseLevel < 70){
            return String.format("[%s] HYPOGLYCEMIC", s);
        }else if(glucoseLevel >= 70 && glucoseLevel < 100){
            return String.format("[%s] NORMAL", s);
        }else{
            return String.format("[%s] ABNORMAL", s);
        }
    }

    private void rewriteNotes() {
        String newNote ="";
        for (String  note: notes){
            newNote += note;
        }
        resultsView.setText(newNote);
        String fastingText = fastingInput.getText().toString();
        String breakfastText = breakfastInput.getText().toString();
        String lunchText = lunchInput.getText().toString();
        String dinnerText = dinnerInput.getText().toString();
        String notesText = notesInput.getText().toString();
        String entryDateText = dateLabel.getText().toString();
        int fastingNum = Integer.parseInt(fastingText.isEmpty() ? "0": fastingText);
        int breakfastNum = Integer.parseInt(breakfastText.isEmpty() ? "0": breakfastText);
        int lunchNum = Integer.parseInt(lunchText.isEmpty() ? "0": lunchText);
        int dinnerNum = Integer.parseInt(dinnerText.isEmpty() ? "0": dinnerText);
        GlucoseData glucoseData  = new GlucoseData(fastingNum, breakfastNum, lunchNum, dinnerNum);
        normalCheckBox.setChecked(glucoseData.isNormal());
    }

    private void submitData() {
        String fastingText = fastingInput.getText().toString();
        String breakfastText = breakfastInput.getText().toString();
        String lunchText = lunchInput.getText().toString();
        String dinnerText = dinnerInput.getText().toString();
        String notesText = notesInput.getText().toString();
        String entryDateText = dateLabel.getText().toString();
        int fastingNum = Integer.parseInt(fastingText);
        int breakfastNum = Integer.parseInt(breakfastText);
        int lunchNum = Integer.parseInt(lunchText);
        int dinnerNum = Integer.parseInt(dinnerText);

        GlucoseData glucoseData = new GlucoseData();
        glucoseData.setId(dataList.size());
        glucoseData.setBreakfast(breakfastNum);
        glucoseData.setLunch(lunchNum);
        glucoseData.setDinner(dinnerNum);
        glucoseData.setFasting(fastingNum);
        glucoseData.setEntryDate(entryDateText);
       // dataList.add(glucoseData);
        onListFragmentInteraction(glucoseData);
        clearInputs();
    }

    public static String getDateLabelTxt(Date myDate) {
        String pattern = "MMM dd, yyyy";
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(pattern, new Locale("en", "US"));
        String date = simpleDateFormat.format(myDate);
        return date;
    }

    private void clearInputs() {
        resultsView.setText("");
        fastingInput.setText("");
        breakfastInput.setText("");
        lunchInput.setText("");
        dinnerInput.setText("");
        notesInput.setText("");
        normalCheckBox.setText("Normal");
        dateLabel.setText("Select a Date");
    }

    private void getHistory(){
        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        fragTransaction.replace(R.id.sample_content_fragment, myFrag);
        fragTransaction.commit();
    }
    /**
     * Accepts the edit text input and allows us to proceed to the next activity or fragment
     */
    private void processInput(){

    }

    @Override
    public void onListFragmentInteraction(GlucoseData item) {
        //dataList.add(item);
        if (item == null){
            System.err.println("Item is nlull");
        }else if (myFrag.getList() == null){
            System.err.println("List is nlull");
        }else{
            myFrag.updateList(item);
            getHistory();
        }

    }
}
