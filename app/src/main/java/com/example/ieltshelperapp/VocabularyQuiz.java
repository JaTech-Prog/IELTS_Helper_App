package com.example.ieltshelperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class VocabularyQuiz extends AppCompatActivity {

    // Butonların şekilleri için ListActivity yerine AppCompatActivity al ve alttaki bloğu oluştur. Alttaki kod List elementini tanımasını sağlar.
    protected ListView getListView() { return findViewById( android.R.id.list ); }
    Button btnBack, btnAddQuiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_quiz);

        initializeComponents();
    }

    private void initializeComponents() {
        btnBack = (Button) findViewById(R.id.btnBackVocabQuiz);
        btnAddQuiz = (Button) findViewById(R.id.btnAddVocabQuiz);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(VocabularyQuiz.this, MainActivity.class);
            VocabularyQuiz.this.startActivity(intent);
            VocabularyQuiz.this.finish();
        });

        btnAddQuiz.setOnClickListener(v -> {
            // Quiz Ekleme Ekranına yönlendir
        });
    }
}