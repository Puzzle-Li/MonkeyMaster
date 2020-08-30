package com.cebsit.monkeymaster.main.newmonkey;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.cebsit.monkeymaster.R;
import com.cebsit.monkeymaster.backend.UtilsSystem;
import com.cebsit.monkeymaster.database.Monkey;
import com.cebsit.monkeymaster.main.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NewMonkeyFrag extends PreferenceFragmentCompat {

    private MainViewModel mainViewModel;
    private SharedPreferences sp;
    private List<String> monkeyNameList;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.new_monkey, rootKey);

        sp = PreferenceManager.getDefaultSharedPreferences(getContext());

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        monkeyNameList = mainViewModel.getAllMonkeysNameLive().getValue() == null ? new ArrayList<String>() : mainViewModel.getAllMonkeysNameLive().getValue();

        //TODO 增加功能：根据传过来的值自动调整设置界面

        final FloatingActionButton fab_save = getActivity().findViewById(R.id.fab_save);
        fab_save.setVisibility(View.VISIBLE);
        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Monkey monkey = new Monkey();
                monkey.setMonkeyName(sp.getString("new_monkey_name", ""));
                monkey.setGender(sp.getString("new_monkey_gender", "Male"));
                monkey.setWeight(Double.parseDouble(sp.getString("new_monkey_weight", "0")));
                Monkey.Birthmonth birthmonth = new Monkey.Birthmonth();
                birthmonth.setYear(Integer.parseInt(sp.getString("new_monkey_birthmonth_year", "0")));
                birthmonth.setMonth(Integer.parseInt(sp.getString("new_monkey_birthmonth_month", "0")));
                monkey.setBirthmonth(birthmonth);

                int presentYear = UtilsSystem.getYear(new Date());
                if (monkey.getMonkeyName().equals("")) {
                    Snackbar.make(view, getResources().getString(R.string.snackbar_msg_new_monkey_name_empty), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if (monkeyNameList.contains(monkey.getMonkeyName())) {
                    Snackbar.make(view, getResources().getString(R.string.snackbar_msg_new_monkey_name_exist), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if (monkey.getWeight() <= 0) {
                    Snackbar.make(view, getResources().getString(R.string.snackbar_msg_new_monkey_weight), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if (monkey.getBirthmonth().getYear() < 1970 || monkey.getBirthmonth().getYear() > presentYear) {
                    Snackbar.make(view, getResources().getString(R.string.snackbar_msg_new_monkey_birthmonth_year), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if (monkey.getBirthmonth().getMonth() < 1 || monkey.getBirthmonth().getMonth() > 12) {
                    Snackbar.make(view, getResources().getString(R.string.snackbar_msg_new_monkey_birthmonth_month), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    mainViewModel.insertMonkeys(monkey);
                    Snackbar.make(view, getResources().getString(R.string.snackbar_msg_success_save), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    fab_save.setVisibility(View.INVISIBLE);
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_newMonkeyNav_to_monkeyHouseNav);
                }
            }
        });

    }
}