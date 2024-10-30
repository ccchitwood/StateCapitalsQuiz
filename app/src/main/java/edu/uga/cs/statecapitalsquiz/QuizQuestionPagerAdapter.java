package edu.uga.cs.statecapitalsquiz;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class QuizQuestionPagerAdapter extends FragmentStateAdapter {

    public QuizQuestionPagerAdapter(FragmentManager fm, Lifecycle lc) {
        super(fm, lc);
    }

    @Override
    public Fragment createFragment(int position) {
        return QuizQuestionFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return QuizQuestionFragment.getNumberOfVersions();
    }
}
