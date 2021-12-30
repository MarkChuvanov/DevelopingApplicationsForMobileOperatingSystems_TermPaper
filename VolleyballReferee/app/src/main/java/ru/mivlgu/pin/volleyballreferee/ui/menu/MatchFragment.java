package ru.mivlgu.pin.volleyballreferee.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.mivlgu.pin.volleyballreferee.App;
import ru.mivlgu.pin.volleyballreferee.R;
import ru.mivlgu.pin.volleyballreferee.database.AppDatabase;
import ru.mivlgu.pin.volleyballreferee.database.dao.RefereeDao;
import ru.mivlgu.pin.volleyballreferee.database.dao.TeamDao;
import ru.mivlgu.pin.volleyballreferee.database.entities.Referee;
import ru.mivlgu.pin.volleyballreferee.database.entities.Team;
import ru.mivlgu.pin.volleyballreferee.databinding.FragmentMatchBinding;

public class MatchFragment extends Fragment {

    private FragmentMatchBinding binding;
    AppDatabase db;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMatchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = App.getInstance().getDatabase();
        TeamDao teamDao = db.teamDao();
        List<Team> teams = teamDao.getAll();
        ArrayList<String> names = new ArrayList<>();
        for (Team team : teams) {
            names.add(team.name);
        }
        String[] items = names.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
        binding.firstTeamSpinner.setAdapter(adapter);
        binding.secondTeamSpinner.setAdapter(adapter);

        binding.firstTeamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected (AdapterView<?> parent,
                                        View itemSelected, int selectedItemPosition, long selectedId) {
                binding.locationEt.setText(teams.get(selectedItemPosition).homeArena);
            }

            public void onNothingSelected (AdapterView<?> parent) {
            }
        });

        RefereeDao refereeDao = db.refereeDao();
        List<Referee> referees = refereeDao.getAll();
        names = new ArrayList<>();
        for (Referee referee : referees) {
            names.add(referee.fullName);
        }
        items = names.toArray(new String[0]);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
        binding.refereeSpinner.setAdapter(adapter);

        binding.startMatchBtn.setOnClickListener(this :: onClick);

        return root;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }

    public void onClick (View view) {
        TeamDao teamDao = db.teamDao();
        List<Team> teams = teamDao.getAll();
        if (teams.size() == 0) {
            Toast.makeText(getContext(), "Ошибка при создании матча!", Toast.LENGTH_SHORT).show();
            return;
        }

        RefereeDao refereeDao = db.refereeDao();
        List<Referee> referees = refereeDao.getAll();
        if (referees.size() == 0) {
            Toast.makeText(getContext(), "Ошибка при создании матча!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (binding.locationEt.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Ошибка при создании матча!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (binding.firstTeamSpinner.getSelectedItemId() + 1 == binding.secondTeamSpinner.getSelectedItemId() + 1) {
            Toast.makeText(getContext(), "Ошибка при создании матча!", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();

        Bundle bundle = new Bundle();
        bundle.putInt("firstTeamId", (int) binding.firstTeamSpinner.getSelectedItemId() + 1);
        bundle.putInt("secondTeamId", (int) binding.secondTeamSpinner.getSelectedItemId() + 1);
        bundle.putString("location", binding.locationEt.getText().toString());
        bundle.putInt("refereeId", (int) binding.refereeSpinner.getSelectedItemId() + 1);
        bundle.putString("date", dateFormat.format(date));
        bundle.putString("startTime", timeFormat.format(date));
        NavController host = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        host.navigate(R.id.progress_match, bundle);
    }
}