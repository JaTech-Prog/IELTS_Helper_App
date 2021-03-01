package com.example.ieltshelperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button myVocabulary, vocabularyQuiz, onlineVocabulary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
    }

    private void initializeComponents()
    {
        myVocabulary = (Button) findViewById(R.id.myVocabulary);
        vocabularyQuiz = (Button) findViewById(R.id.vocabularyQuiz);
        onlineVocabulary = (Button) findViewById(R.id.btn_main_onlineVocabulary);

        myVocabulary.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyVocabulary.class);
            MainActivity.this.startActivity(intent);
            MainActivity.this.finish();
        });

        vocabularyQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VocabularyQuiz.class);
            MainActivity.this.startActivity(intent);
            MainActivity.this.finish();
        });

        onlineVocabulary.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OnlineVocabulary.class);
            MainActivity.this.startActivity(intent);
            MainActivity.this.finish();
        });
    }
}