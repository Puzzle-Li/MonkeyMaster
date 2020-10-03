package com.cebsit.monkeymaster.tasks.t003.display;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.cebsit.monkeymaster.R;

public class RewardFrag_t003 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_main_tasks_shared_reward, container, false);
        view.findViewById(R.id.container_reward).setBackgroundColor(getResources().getColor(ViewModel_t003.rewardColor));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Navigation.findNavController(view).navigate(R.id.action_t003_rewardFrag_to_intervalFrag);
            }
        }, ViewModel_t003.rewardDuration + ViewModel_t003.intervalPreReward + ViewModel_t003.intervalPostReward);
    }
}
