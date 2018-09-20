package luanvan.luanvantotnghiep.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Model.Answer;
import luanvan.luanvantotnghiep.Model.AnswerByQuestion;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;

public class FillInTheBlankActivity extends AppCompatActivity {

    private TextView mTvQuestion;
    private List<PositionCode> positionCodeList = new ArrayList<>();

    private List<Question> mQuestionList = new ArrayList<>();
    private List<Answer> mAnswerList = new ArrayList<>();
    private List<AnswerByQuestion> mAnswerByQuestionList = new ArrayList<>();

    private static final Character START_CODE = '&';
    private static final Character END_CODE = '|';
    private static final Character START_SHOW = '{';
    private static final Character END_SHOW = '}';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_the_blank);

        setupToolbar();

        init();

        addDataDK();

        addDataAnswerDK();

        addDataAnswerByQuestionDK();
    }

    private void init() {
        mTvQuestion = findViewById(R.id.tv_question);
        SpannableString spannableString = handleClickQuestion("300 là số có &| chữ số, chia &| cho 2 và là số &|.");

        mTvQuestion.setText(spannableString);
        mTvQuestion.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private SpannableString handleClickQuestion(String question) {
        ClickableSpan span;

        question = question.replace(START_SHOW, START_CODE);
        question = question.replace(END_SHOW, END_CODE);

        positionCodeList.clear();
        for (int i = 0; i < question.length() - 1; i++) {
            if (question.substring(i, i + 1).equals("&")) {
                for (int j = i + 1; j < question.length() - 1; j++) {
                    if (question.substring(j, j + 1).equals("|")) {
                        positionCodeList.add(new PositionCode(i, j + 1));
                        break;
                    }
                }
            }
        }

        question = question.replace(START_CODE, START_SHOW);
        question = question.replace(END_CODE, END_SHOW);

        SpannableString ss = new SpannableString(question);

        for (int i = 0; i < positionCodeList.size(); i++) {
            final PositionCode positionCode = positionCodeList.get(i);
            final String finalQuestion = question;
            span = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    showDialog(finalQuestion, positionCode);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }

            };
            ss.setSpan(span, positionCode.start, positionCode.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return ss;
    }

    public void check(View view) {
        String text = mTvQuestion.getText().toString();

        String dapAn = "{3},{het},{chan}";
        String strDapAn[] = dapAn.split(",");

        String temp = "";
        int index = 0;

        for (int i = 0; i < positionCodeList.size(); i++) {
            PositionCode positionCode = positionCodeList.get(i);
            temp += text.substring(index, positionCode.start);
            if (text.substring(positionCode.start, positionCode.end).toLowerCase().equals(strDapAn[i].toLowerCase())) {
                temp += "<font color='green'>" + text.substring(positionCode.start, positionCode.end).toLowerCase() + "</font>";
            } else {
                temp += "<font color='red'>" + text.substring(positionCode.start, positionCode.end).toLowerCase() + "</font>";
            }
            index = positionCode.end;
        }
        if (index < text.length()) {
            temp += text.substring(index, text.length());
        }
        mTvQuestion.setText(Html.fromHtml(temp));
    }

    private void showDialog(final String text, final PositionCode posi) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        alert.setTitle("Enter Your Answer");
        alert.setView(edittext);

        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String youEditTextValue = edittext.getText().toString();
                youEditTextValue = standardizeString(youEditTextValue);
                String temp = text.substring(0, posi.start);
                String result = temp + "&" + youEditTextValue + "|";
                temp = text.substring(posi.end, text.length());
                result += temp;

                SpannableString ss = handleClickQuestion(result);
                mTvQuestion.setText(ss);
                mTvQuestion.setMovementMethod(LinkMovementMethod.getInstance());
            }
        });

        alert.show();
    }

    private String standardizeString(String str) {
        str = str.trim();
        str = str.replaceAll("\\s+", " ");
        return str;
    }

    private void setupToolbar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class PositionCode {
        int start;
        int end;

        PositionCode(int s, int e) {
            start = s;
            end = e;
        }
    }

    private void addDataDK() {
        mQuestionList = new ArrayList<>();
        Question question;

        question = new Question(1, "&| là khoa học nghiên cứu các chất, sự biến đổi và ứng dụng.");
        mQuestionList.add(question);

        question = new Question(2, "Hóa học có vai trò &| trong cuộc sống chúng ta.");
        mQuestionList.add(question);

        question = new Question(3, "Chất có khắp mọi nơi, ở đâu có vật thể ở đó có &|.");
        mQuestionList.add(question);

        question = new Question(4, "Nước &| gồm nhiều chất trộn lẫn là một &|.");
        mQuestionList.add(question);

        question = new Question(5, "&| là chất tinh khiết.");
        mQuestionList.add(question);

        question = new Question(6, "dựa vào sự &| về tính chất &| có thể tách một chất ra khỏi &|.");
        mQuestionList.add(question);

        question = new Question(7, "&| là &| vô cùng nhỏ và &| về điện.");
        mQuestionList.add(question);

        question = new Question(8, "Nguyên tử là hạt nhân mang &| và vỏ tạo bởi một hay nhiều &| mang &|.");
        mQuestionList.add(question);

        question = new Question(9, "Hạt nhân tạo bởi &| và nơtron.");
        mQuestionList.add(question);

        question = new Question(10, "Trong mỗi nguyên, số proton bằng số electron");
        mQuestionList.add(question);
    }

    private void addDataAnswerDK() {

        mAnswerList = new ArrayList<>();
        Answer answer;

        answer = new Answer(1, "{hóa học}");
        mAnswerList.add(answer);

        answer = new Answer(2, "{rất quan trọng}");
        mAnswerList.add(answer);

        answer = new Answer(3, "{chất}");
        mAnswerList.add(answer);

        answer = new Answer(4, "{tự nhiên},{hỗn hợp}");
        mAnswerList.add(answer);

        answer = new Answer(5, "{nước cất}");
        mAnswerList.add(answer);

        answer = new Answer(6, "{khác nhau},{vật lý},{hỗn hợp}");
        mAnswerList.add(answer);

        answer = new Answer(7, "{nguyên tử},{hạt},{trung hòa}");
        mAnswerList.add(answer);

        answer = new Answer(8, "{điện tích dương},{electron},{điện tích âm}");
        mAnswerList.add(answer);

        answer = new Answer(9, "{proton}");
        mAnswerList.add(answer);

        answer = new Answer(10, "{electron}");
        mAnswerList.add(answer);
    }

    private void addDataAnswerByQuestionDK() {
        mAnswerByQuestionList = new ArrayList<>();
        AnswerByQuestion answerByQuestion;

        answerByQuestion = new AnswerByQuestion(1, 1, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(2, 2, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(3, 3, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(4, 4, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(5, 5, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(6, 6, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(7, 7, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(8, 8, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(9, 9, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(10, 10, true);
        mAnswerByQuestionList.add(answerByQuestion);
    }

}
