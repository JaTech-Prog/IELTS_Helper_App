package com.example.ieltshelperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class OnlineVocabulary extends AppCompatActivity {

    Button btnBack, btnTranslate;
    EditText edtWord;
    TextView txtTranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_vocabulary);

        initializeComponents();

    }

    private void initializeComponents(){
        btnBack = (Button) findViewById(R.id.btn_onlineVocab_back);
        btnTranslate = (Button) findViewById(R.id.btn_onlineVocab_translate);
        edtWord = (EditText) findViewById(R.id.edt_onlineVocab_word);
        txtTranslate = (TextView) findViewById(R.id.txt_onlineVocab_Translate);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(OnlineVocabulary.this, MainActivity.class);
            OnlineVocabulary.this.startActivity(intent);
            OnlineVocabulary.this.finish();
        });

        btnTranslate.setOnClickListener(v -> {
            String text = edtWord.getText().toString();

            try {
                txtTranslate.setText(translate("en", "tr", text));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static String translate(String langFrom, String langTo, String text) throws IOException {


        String urlStr = "https://script.google.com/macros/s/AKfycbzgSH6mPCwvdk61EVpCjI-5ssHRC1Ysl_12ACD8VD6nM5yboU629ZWE/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}