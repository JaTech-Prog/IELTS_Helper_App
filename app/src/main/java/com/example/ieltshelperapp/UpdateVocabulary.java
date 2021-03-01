package com.example.ieltshelperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateVocabulary extends AppCompatActivity {

    private  DataAccessMyVocabulary dbHelper;

    Button btnUpdate, btnBack;
    EditText edtWord, edtTranslate, edtDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vocabulary);

        InitializeComponents();
    }

    private void InitializeComponents(){
        dbHelper = new DataAccessMyVocabulary(UpdateVocabulary.this);
        btnUpdate = (Button) findViewById(R.id.btn_UpdateVocab_Update);
        btnBack = (Button) findViewById(R.id.btn_UpdateVocab_Back);
        edtWord = (EditText) findViewById(R.id.edt_UpdateVocab_Word);
        edtDescription = (EditText) findViewById(R.id.edt_UpdateVocab_Description);
        edtTranslate = (EditText) findViewById(R.id.edt_UpdateVocab_Translation);

        Intent intent = getIntent();
        fillFields(intent);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateVocabulary.this, MyVocabulary.class);
                UpdateVocabulary.this.startActivity(intent);
                UpdateVocabulary.this.finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateWord(intent);
            }
        });
    }

    private void updateWord(Intent intent) {
        Vocabulary oldWord = new Vocabulary();
        Vocabulary newWord = new Vocabulary();

        oldWord.setWordENG(intent.getStringExtra("word"));
        oldWord.setWordTR(intent.getStringExtra("translate"));
        oldWord.setWordDESC(intent.getStringExtra("description"));

        newWord.setWordENG(edtWord.getText().toString());
        newWord.setWordTR(edtTranslate.getText().toString());
        newWord.setWordDESC(edtDescription.getText().toString());

        dbHelper.connectToDb();
        Integer count = dbHelper.UpdateVocabulary(oldWord, newWord);
        dbHelper.closeDb();
        makeToast("Word Updated Successfully!");

    }

    private void fillFields(Intent i) {
        edtWord.setText(i.getStringExtra("word"));
        edtWord.setHint(i.getStringExtra("word"));
        edtTranslate.setText(i.getStringExtra("translate"));
        edtTranslate.setHint(i.getStringExtra("translate"));
        edtDescription.setText(i.getStringExtra("description"));
        edtDescription.setHint(i.getStringExtra("description"));
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}