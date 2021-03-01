package com.example.ieltshelperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VocabularyAdd extends AppCompatActivity {

    private DataAccessMyVocabulary dbHelper;

    Button btnBack, btnAdd;
    TextView txtWord, txtTranslate, txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_add);
        initializeComponents();

        dbHelper = new DataAccessMyVocabulary(VocabularyAdd.this);
        dbHelper.connectToDb();

    }

    private void initializeComponents(){
        btnBack = (Button) findViewById(R.id.btnBackAddVocab);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(VocabularyAdd.this, MyVocabulary.class);
            VocabularyAdd.this.startActivity(intent);
            VocabularyAdd.this.finish();
        });
        btnAdd = (Button) findViewById(R.id.btnAddVocab);
        btnAdd.setOnClickListener(v -> {
            AddWord();
        });
        txtWord = (TextView) findViewById(R.id.txtAddWord);
        txtTranslate = (TextView) findViewById(R.id.txtAddTranslate);
        txtDescription = (TextView) findViewById(R.id.txtAddDescription);
    }

    private void AddWord() {
        dbHelper.connectToDb();

        Vocabulary word = new Vocabulary();
        word.setWordENG(txtWord.getText().toString());
        word.setWordTR(txtTranslate.getText().toString());
        word.setWordDESC(txtDescription.getText().toString());

        Boolean result = dbHelper.AddWord(word);
        if (result) {
            makeToast("Word Added");
        } else {
            makeToast("Error occured!");
        }

        dbHelper.closeDb();
    }

    private void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}