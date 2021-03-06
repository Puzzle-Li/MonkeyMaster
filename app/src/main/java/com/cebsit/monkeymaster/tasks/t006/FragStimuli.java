package com.cebsit.monkeymaster.tasks.t006;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.cebsit.monkeymaster.R;
import com.cebsit.monkeymaster.tasks.TimeFormat;
import com.cebsit.monkeymaster.tasks.t006.TrialDatabase.Trial.Performance.Stimulus;

import java.util.HashMap;
import java.util.Random;

public class FragStimuli extends Fragment {

    TrialViewModel trialViewModel;
    private ConstraintLayout cell;
    private ImageButton[] stimuli = new ImageButton[3];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_main_tasks_shared_matrix, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        trialViewModel = new ViewModelProvider(this).get(TrialViewModel.class);
        int[][] permutations = new int[][]{
                {1, 2, 3},
                {1, 3, 2},
                {2, 1, 3},
                {2, 3, 1},
                {3, 1, 2},
                {3, 2, 1}
        };
        Random random = new Random();
        int colorPermutationIndex = random.nextInt(6);
        int shapePermutationIndex = random.nextInt(6);
        int columnPermutationIndex = random.nextInt(6);
        int[] colorPermutation = permutations[colorPermutationIndex];
        int[] shapePermutation = permutations[shapePermutationIndex];
        int[] columnPermutation = permutations[columnPermutationIndex];

        HashMap<Integer, String> colorMap = new HashMap<>();
        colorMap.put(1, "blue");
        colorMap.put(2, "green");
        colorMap.put(3, "yellow");

        HashMap<Integer, String> shapeMap = new HashMap<>();
        shapeMap.put(1, "triangle");
        shapeMap.put(2, "circle");
        shapeMap.put(3, "star");

        trialViewModel.getTrial().getPerformance().setStimulus1(new Stimulus(1, columnPermutation[0], colorMap.get(colorPermutation[0]), shapeMap.get(shapePermutation[0])));
        trialViewModel.getTrial().getPerformance().setStimulus2(new Stimulus(2, columnPermutation[1], colorMap.get(colorPermutation[1]), shapeMap.get(shapePermutation[1])));
        trialViewModel.getTrial().getPerformance().setStimulus3(new Stimulus(3, columnPermutation[2], colorMap.get(colorPermutation[2]), shapeMap.get(shapePermutation[2])));

        if (trialViewModel.getCCC() == 10) {
            trialViewModel.changeShift();
        }
        trialViewModel.getTrial().getPerformance().setShift(trialViewModel.getShift());

        for (int i = 0; i < stimuli.length; i++) {
            boolean correct = false;
            stimuli[i] = new ImageButton(getContext());
            stimuli[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            setStimulus(i, shapePermutation[i], colorPermutation[i]);
            setColumn(i, columnPermutation[i]);
            cell.addView(stimuli[i]);

            switch (trialViewModel.getShift()) {
                case 0:
                    if (colorPermutation[i] == 1) {
                        correct = true;
                    }
                    break;
                case 1:
                    if (shapePermutation[i] == 1) {
                        correct = true;
                    }
                    break;
                case 2:
                    if (colorPermutation[i] == 2) {
                        correct = true;
                    }
                    break;
                case 3:
                    if (shapePermutation[i] == 2) {
                        correct = true;
                    }
                    break;
            }

            if (correct) {
                trialViewModel.getTrial().getPerformance().setTrueStimulusNum(i + 1);
            }

            final boolean finalCorrect = correct;
            final int finalI = i;
            stimuli[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    trialViewModel.getTrial().getPerformance().setTappedTime(new TimeFormat(System.currentTimeMillis()));
                    trialViewModel.getTrial().getPerformance().setTappedStimulusNum(finalI +1);
                    if (finalCorrect) {
                        trialViewModel.getTrial().setCorrect(true);
                        trialViewModel.addCCC();
                        trialViewModel.getTrial().getPerformance().setConsecutiveCorrectCount(trialViewModel.getCCC());
                        Navigation.findNavController(view).navigate(R.id.action_t006_stimuliFrag_to_rewardFrag);
                    } else {
                        trialViewModel.getTrial().setCorrect(false);
                        trialViewModel.resetCCC();
                        trialViewModel.getTrial().getPerformance().setConsecutiveCorrectCount(trialViewModel.getCCC());
                        Navigation.findNavController(view).navigate(R.id.action_t006_stimuliFrag_to_errorFrag);
                    }
                }
            });
        }
    }

    private void setStimulus(int stimuliIndex, int shapeIndex, int colorIndex) {
        int ImageRId = R.drawable.ic_stimulus_error_300;

        switch (shapeIndex) {
            case 1:
                switch (colorIndex) {
                    case 1:
                        ImageRId = R.drawable.stimulus_t006_triangle_blue;
                        break;
                    case 2:
                        ImageRId = R.drawable.stimulus_t006_triangle_green;
                        break;
                    case 3:
                        ImageRId = R.drawable.stimulus_t006_triangle_yellow;
                        break;
                }
                break;
            case 2:
                switch (colorIndex) {
                    case 1:
                        ImageRId = R.drawable.stimulus_t006_circle_blue;
                        break;
                    case 2:
                        ImageRId = R.drawable.stimulus_t006_circle_green;
                        break;
                    case 3:
                        ImageRId = R.drawable.stimulus_t006_circle_yellow;
                        break;
                }
                break;

            case 3:
                switch (colorIndex) {
                    case 1:
                        ImageRId = R.drawable.stimulus_t006_star_blue;
                        break;
                    case 2:
                        ImageRId = R.drawable.stimulus_t006_star_green;
                        break;
                    case 3:
                        ImageRId = R.drawable.stimulus_t006_star_yellow;
                        break;
                }
                break;
        }
        stimuli[stimuliIndex].setImageResource(ImageRId);
    }

    private void setColumn(int stimuliIndex, int columnIndex) {
        switch (stimuliIndex) {
            case 0:
                switch (columnIndex) {
                    case 1:
                        cell = getActivity().findViewById(R.id.cell1);
                        break;
                    case 2:
                        cell = getActivity().findViewById(R.id.cell2);
                        break;
                    case 3:
                        cell = getActivity().findViewById(R.id.cell3);
                        break;
                }
                break;
            case 1:
                switch (columnIndex) {
                    case 1:
                        cell = getActivity().findViewById(R.id.cell4);
                        break;
                    case 2:
                        cell = getActivity().findViewById(R.id.cell5);
                        break;
                    case 3:
                        cell = getActivity().findViewById(R.id.cell6);
                        break;
                }
                break;
            case 2:
                switch (columnIndex) {
                    case 1:
                        cell = getActivity().findViewById(R.id.cell7);
                        break;
                    case 2:
                        cell = getActivity().findViewById(R.id.cell8);
                        break;
                    case 3:
                        cell = getActivity().findViewById(R.id.cell9);
                        break;
                }
                break;
        }
    }
}
