package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class QuizQuestionFragment extends Fragment {

    private TextView stateQuestion;
    private RadioButton answerOne;
    private RadioButton answerTwo;
    private RadioButton answerThree;

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
        Log.w("Size of question list", questionList.size() + ""); // It's 0

        // select question
        Random random = new Random();
        int randomInt = random.nextInt(questionList.size());
        Question chosenQuestion = questionList.get(randomInt);
        // set textview and radio button text
        stateQuestion = getView().findViewById( R.id.textView3 );
        stateQuestion.setText("What is the capital of " + chosenQuestion.getState() + "?");

        answerOne = getView().findViewById(R.id.radioButton);
        answerTwo = getView().findViewById(R.id.radioButton2);
        answerThree = getView().findViewById(R.id.radioButton3);

        // Incorporate randomization + enforce questions aren't duplicated
        answerOne.setText(chosenQuestion.getAnswer1());
        answerTwo.setText(chosenQuestion.getAnswer2());
        answerThree.setText(chosenQuestion.getAnswer3());
    }

    public static int getNumberOfVersions() { return 6; }
    // for six questions?
}
