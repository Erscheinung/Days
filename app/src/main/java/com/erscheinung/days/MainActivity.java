package com.erscheinung.days;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.time.LocalDate;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.text.*;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private double progressStatus = 0;
    private Handler handler = new Handler();
    private int currentProgress = 0;
    private String pattern = "dd MM yyyy";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button DateButton = findViewById(R.id.button);
        final TextView DaysPassed = findViewById(R.id.textView2);
        progressBar = findViewById(R.id.progressBar);

        String dateInString =new SimpleDateFormat(pattern).format(new Date());
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        final String firstInput = "13 02 1999"; //Add your birth date here
        final String secondInput = dateInString;
        final LocalDate firstDate = LocalDate.parse(firstInput, formatter);
        final LocalDate secondDate = LocalDate.parse(secondInput, formatter);
        final long days = ChronoUnit.DAYS.between(firstDate, secondDate);
        final String daysText = " " + days;
        progressStatus = days*100/30000;


        DateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaysPassed.setText(daysText);

                new Thread(new Runnable(){
                    public void run(){
                        while(currentProgress < progressStatus){
                            currentProgress+=1;
                            handler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(currentProgress);
                                }
                            });
                            try {
                                Thread.sleep(100);
                            } catch(InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                progressBar.setMax(100);
                progressBar.setProgress((int)progressStatus);
                    }
                }).start();
            }
        });
    }

}
