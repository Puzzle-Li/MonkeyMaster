package com.cebsit.monkeymaster.tasks.t006.display;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.cebsit.monkeymaster.R;
import com.cebsit.monkeymaster.tasks.t006.trial.Trial_t006.Stimulus;

import java.util.HashMap;
import java.util.Random;

public class StimuliFrag_t006 extends Fragment {

    ViewModel_t006 viewModel_t006;

    private int[][] permutations;
    int shift = 0;
    private ConstraintLayout cell;
    //    ConstraintLayout[] cells = new ConstraintLayout[9];
    private ImageButton[] stimuli = new ImageButton[3];
//    ImageButton stimulus1;
//    ImageButton stimulus2;
//    ImageButton stimulus3;
//    int shift = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_main_tasks_shared_matrix, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel_t006 = new ViewModelProvider(this).get(ViewModel_t006.class);
        permutations = new int[][]{
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

        HashMap<Integer,String> colorMap = new HashMap<>();
        colorMap.put(1, "blue");
        colorMap.put(2, "green");
        colorMap.put(3, "yellow");

        HashMap<Integer,String> shapeMap = new HashMap<>();
        shapeMap.put(1, "triangle");
        shapeMap.put(2, "circle");
        shapeMap.put(3, "star");

        viewModel_t006.getTrial_t006().setStimulus1(1, columnPermutation[0], colorMap.get(colorPermutation[0]), shapeMap.get(shapePermutation[0]));
        viewModel_t006.getTrial_t006().setStimulus2(2, columnPermutation[1], colorMap.get(colorPermutation[1]), shapeMap.get(shapePermutation[1]));
        viewModel_t006.getTrial_t006().setStimulus3(3, columnPermutation[2], colorMap.get(colorPermutation[2]), shapeMap.get(shapePermutation[2]));


        boolean correct = false;
        shift = 0;

        viewModel_t006.getConsecutiveCorrectCount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer consecutiveCorrectCount) {
                if (consecutiveCorrectCount == 10) {
                    shift += 1;
                    if (shift == 4) {
                        shift = 0;
                    }
                }
            }
        });

        for (int i = 0; i < stimuli.length; i++) {

            stimuli[i] = new ImageButton(getContext());
            stimuli[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            setStimulus(i, shapePermutation[i], colorPermutation[i]);
            setColumn(i, columnPermutation[i]);
            cell.addView(stimuli[i]);

            switch (shift) {
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
            }

            final boolean finalCorrect = correct;
            stimuli[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalCorrect) {
                        Navigation.findNavController(view).navigate(R.id.action_t006_stimuliFrag_to_rewardFrag);
                    } else {
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