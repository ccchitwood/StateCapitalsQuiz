package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;

public class QuizQuestionFragment extends Fragment {

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
        // select question
        // gather answers
        // set textview and radio button text
    }

    public static int getNumberOfVersions() { return 6; }
    // for six questions?
}
