package com.barmej.guesstheanswer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.zip.Inflater;

public class QuestionActivity extends AppCompatActivity {

    private String[] Questions;
    private static final boolean[] Answers = {
            false,
            true,
            true,
            false,
            true,
            false,
            false,
            false,
            false,
            true,
            true,
            false,
            true,
            true
    };

    private String[] Details;

    private TextView mTextQuestion;

    private String mCurrentQuestion, mCurrentDetail;
    private boolean mCurrentAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        String appLang = sharedPreferences.getString("app_lang", "");
        if(!appLang.equals(""))
            LocaleHelper.setLocale(this, appLang);

        Questions = getResources().getStringArray(R.array.questions);
        Details = getResources().getStringArray(R.array.details);
        mTextQuestion = findViewById(R.id.text_view_question);
        showNewQuestion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuChangeLanguage) {
            showLanguageDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showLanguageDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.change_language_text)
                .setItems(R.array.languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String language = "ar";
                        switch (which){
                            case 0:
                                language = "ar";
                                break;

                            case 1:
                                language = "en";
                                break;
                        }
                        saveLanguage(language);

                        LocaleHelper.setLocale(QuestionActivity.this, language);
                        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                }).create();
        alertDialog.show();
    }

    private void saveLanguage(String lang){
        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("app_lang",lang);
        editor.apply();
    }

    private void showNewQuestion() {
        Random random = new Random();
        int randomQuestionIndex = random.nextInt(Questions.length);
        mCurrentQuestion = Questions[randomQuestionIndex];
        mCurrentAnswer = Answers[randomQuestionIndex];
        mCurrentDetail = Details[randomQuestionIndex];

        mTextQuestion.setText(mCurrentQuestion);
    }

    public void onChangeQuestionClick(View view) {
        showNewQuestion();
    }

    public void onTrueClick(View view) {
        if (mCurrentAnswer == true) {
            showNewQuestion();
        } else {
            Toast.makeText(this, "اجابة خاطئه", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(QuestionActivity.this, ANSWERActivity.class);
            intent.putExtra("Question_answer", mCurrentDetail);
            startActivity(intent);
        }
    }

    public void onFalseClick(View view) {
        if (mCurrentAnswer == false) {
            showNewQuestion();
        } else {
            Toast.makeText(this, "اجابة خاطئه", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(QuestionActivity.this, ANSWERActivity.class);
            intent.putExtra("Question_answer", mCurrentDetail);
            startActivity(intent);
        }
    }

    public void onShareQuestionClick(View view) {
        Intent intent = new Intent(QuestionActivity.this, ShareActivity.class);
        intent.putExtra("Question text share", mCurrentQuestion);
        startActivity(intent);

    }
}
