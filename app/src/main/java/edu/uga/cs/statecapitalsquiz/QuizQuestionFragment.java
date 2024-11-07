package edu.uga.cs.statecapitalsquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.xml.transform.Result;

public class QuizQuestionFragment extends Fragment {

    private TextView stateQuestion;
    private RadioButton answerOne;
    private RadioButton answerTwo;
    private RadioButton answerThree;
    private static Set<Integer> usedQuestions = new HashSet<>();
    private static int correctAnswerCount = 0;
    private HistoryData historyData;

    public QuizQuestionFragment() {
    // empty constructor
    }

    public static QuizQuestionFragment newInstance(int questionID) {
        QuizQuestionFragment fragment = new QuizQuestionFragment();
        Bundle args = new Bundle();
        args.putInt("questionID", questionID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quiz_question, container, false );
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);
        // open the database
        QuestionData questionData = new QuestionData(getActivity());
        questionData.open();
        List<Question> questionList = questionData.retrieveAllQuestions();
        Log.w("Size of question list", questionList.size() + "");

        // select question
        Random random = new Random();
        int randomInt = random.nextInt(questionList.size());
        while (usedQuestions.contains(randomInt)) {
            randomInt = random.nextInt(questionList.size());
        }
        usedQuestions.add(randomInt);
        Question chosenQuestion = questionList.get(randomInt);
        String correctAnswer = chosenQuestion.getAnswer1();
        // set textview and radio button text
        stateQuestion = getView().findViewById( R.id.textView3 );
        stateQuestion.setText("What is the capital of " + chosenQuestion.getState() + "?");

        List<String> answers = new ArrayList<>();
        answers.add(chosenQuestion.getAnswer1());
        answers.add(chosenQuestion.getAnswer2());
        answers.add(chosenQuestion.getAnswer3());

        Collections.shuffle(answers);


        answerOne = getView().findViewById(R.id.radioButton);
        answerTwo = getView().findViewById(R.id.radioButton2);
        answerThree = getView().findViewById(R.id.radioButton3);

        answerOne.setText(answers.get(0));
        answerTwo.setText(answers.get(1));
        answerThree.setText(answers.get(2));

        answerOne.setOnClickListener(v -> validateAnswer(answerOne.getText().toString(), correctAnswer));
        answerTwo.setOnClickListener(v -> validateAnswer(answerTwo.getText().toString(), correctAnswer));
        answerThree.setOnClickListener(v -> validateAnswer(answerThree.getText().toString(), correctAnswer));
    }

    private void validateAnswer(String selectedAnswer, String correctAnswer) {
        if (selectedAnswer.equals(correctAnswer)) {
            correctAnswerCount++;
            Toast.makeText(getActivity(), "You are correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Incorrect. The correct answer is " + correctAnswer +
                    ".", Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(() -> {
            ViewPager2 viewPager = getActivity().findViewById(R.id.viewpager);

            if (viewPager != null) {
                int nextPosition = viewPager.getCurrentItem() + 1;
                if (nextPosition == 6) {
                    // open results database for saving
                    historyData = new HistoryData(getActivity());
                    historyData.open();

                    new ResultSaver(historyData, correctAnswerCount).execute();
                }
                viewPager.setCurrentItem(nextPosition, true);  // true for smooth scrolling
            }
        }, 1500); // Delay in milliseconds (1.5 seconds)
    }

    public static int getNumberOfVersions() { return 6; }

    public class ResultSaver extends AsyncTask<History, History> {

        private HistoryData historyData;
        private int score;

        public ResultSaver(HistoryData historyData, int score) {
            this.historyData = historyData;
            this.score = score;
        }

        protected History doInBackground(History... histories) {
            History history = new History();
            int currentDate = (int) (System.currentTimeMillis() / 1000);
            history.setDate(currentDate);
            history.setScore(score);
            historyData.storeResults(history);

            return new History();
        }

        protected void onPostExecute (History history) {
            Intent intent = new Intent(getActivity(), EndScreen.class);
            intent.putExtra("correctAnswerCount", correctAnswerCount);
            correctAnswerCount = 0;
            usedQuestions.clear();
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (historyData != null) {
            historyData.close();
        }
    }
}
