package com.example.ieltshelperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.List;

public class MyVocabulary extends AppCompatActivity {

    protected ListView getListView() { return findViewById( android.R.id.list ); }
    private DataAccessMyVocabulary dbHelper;
    Button btnBack, btnAdd, btnDeleteWord, btnUpdateWord;
    ListView listView;
    Dialog myDialog;

    
    Cursor cursor;
    SimpleCursorAdapter words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vocabulary);
        this.getListView().setDividerHeight(2);

        initializeComponents();

        dbHelper = new DataAccessMyVocabulary(MyVocabulary.this);
        GetVocabulary();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyVocabulary.this, VocabularyAdd.class);
                MyVocabulary.this.startActivity(intent);
                MyVocabulary.this.finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyVocabulary.this, MainActivity.class);
                MyVocabulary.this.startActivity(intent);
                MyVocabulary.this.finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                TextView txtWord = view.findViewById(R.id.WordListENG);
                TextView txtTranslate = view.findViewById(R.id.WordListTR);
                TextView txtDescription = view.findViewById(R.id.WordListDESC);

                String word = txtWord.getText().toString();
                String translate = txtTranslate.getText().toString();
                String description = txtDescription.getText().toString();
                //makeToast("clicked item " + word + ", id : " +id + ", pos : " + position);

                ShowPopup(word, translate, description);
            }
        });
    }

    private void GetVocabulary() {
        dbHelper.connectToDb();
        cursor = dbHelper.getAllVocabulary();
        startManagingCursor(cursor);
        String[] from = new String[] {
                DataAccessMyVocabulary.WORD_ENG_COL,
                DataAccessMyVocabulary.WORD_TR_COL,
                DataAccessMyVocabulary.WORD_DESC_COL,
        };

        int[] to = new int[] {
                R.id.WordListENG,
                R.id.WordListTR,
                R.id.WordListDESC
        };

        words = new SimpleCursorAdapter(this, R.layout.row_item, cursor, from, to);

        listView.setAdapter(words);
        //setListAdapter(words);
        dbHelper.closeDb();
    }

    private void initializeComponents(){
        btnBack = (Button) findViewById(R.id.btnBackMyVocab);
        btnAdd = (Button) findViewById(R.id.btnAddMyVocab);
        listView =(ListView) findViewById(android.R.id.list);
        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.my_vocabulary_popup);
        btnDeleteWord = (Button) myDialog.findViewById(R.id.btn_MyVocab_PopUp_Delete);
        btnUpdateWord = (Button) myDialog.findViewById(R.id.btn_MyVocab_PopUp_Update);
    }

    public void ShowPopup(String word, String translate, String description) {
        TextView txtClose, txtWord, txtTranslate, txtDescription;;


        txtWord = (TextView) myDialog.findViewById(R.id.txt_MyVocab_PopUp_Word);
        txtTranslate = (TextView) myDialog.findViewById(R.id.txt_MyVocab_PopUp_Translate);
        txtDescription = (TextView) myDialog.findViewById(R.id.txt_MyVocab_PopUp_Description);


        txtWord.setText(word);
        txtTranslate.setText(translate);
        txtDescription.setText(description);

        txtClose = (TextView) myDialog.findViewById(R.id.txt_MyVocab_PopUp_Close);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                myDialog.dismiss();
            }
        });

        btnUpdateWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyVocabulary.this, UpdateVocabulary.class);
                intent.putExtra("word", word);
                intent.putExtra("translate", translate);
                intent.putExtra("description", description);
                MyVocabulary.this.startActivity(intent);
                MyVocabulary.this.finish();
                myDialog.dismiss();
            }
        });

        btnDeleteWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyVocabulary.this);
                builder.setTitle("Deleting Word!");
                builder.setMessage("Do you want to delete word : " + word);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteWord(word, translate, description);
                        myDialog.dismiss();
                        GetVocabulary();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();

                alertDialog.show();

            }
        });



        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private void DeleteWord(String word, String translate, String description){
        Vocabulary wordDeleted = new Vocabulary();
        wordDeleted.setWordENG(word);
        wordDeleted.setWordTR(translate);
        wordDeleted.setWordDESC(description);

        dbHelper.connectToDb();

        Integer deleted = dbHelper.deleteWord(wordDeleted);
        makeToast("Word has been deleted successfully!");
    }

    private void makeToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}