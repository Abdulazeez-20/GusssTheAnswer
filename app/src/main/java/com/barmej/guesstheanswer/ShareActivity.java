package com.barmej.guesstheanswer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.EditText;

public class ShareActivity extends AppCompatActivity {

    public EditText mEditTextTitle;
    private String mQuestionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        mEditTextTitle = findViewById(R.id.edit_text_share_title);
        mQuestionText = getIntent().getStringExtra("Question text share");

        SharedPreferences sharedPreferences = getSharedPreferences("app pref",MODE_PRIVATE);
        String questionTitle = sharedPreferences.getString("share title","");
        mEditTextTitle.setText(questionTitle);
    }

    public void onShareQuestionClick(View view){
        String questionTitle = mEditTextTitle.getText().toString();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT,questionTitle + "\n" + mQuestionText);
        shareIntent.setType("text/plain");
        startActivity(shareIntent);

        SharedPreferences sharedPreferences = getSharedPreferences("app pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("share title", questionTitle);
        editor.apply();
     }
}