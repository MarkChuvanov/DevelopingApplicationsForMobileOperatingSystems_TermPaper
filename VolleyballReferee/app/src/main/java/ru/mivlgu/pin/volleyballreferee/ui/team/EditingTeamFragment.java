package ru.mivlgu.pin.volleyballreferee.ui.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

import ru.mivlgu.pin.volleyballreferee.App;
import ru.mivlgu.pin.volleyballreferee.R;
import ru.mivlgu.pin.volleyballreferee.database.AppDatabase;
import ru.mivlgu.pin.volleyballreferee.database.dao.MatchDao;
import ru.mivlgu.pin.volleyballreferee.database.dao.TeamDao;
import ru.mivlgu.pin.volleyballreferee.database.entities.Match;
import ru.mivlgu.pin.volleyballreferee.database.entities.Team;
import ru.mivlgu.pin.volleyballreferee.databinding.EditingTeamBinding;

public class EditingTeamFragment extends Fragment {

    private EditingTeamBinding binding;
    private AppDatabase db;
    private Team team;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        binding = EditingTeamBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.editTeamBtn.setOnClickListener(this :: onClick);

        db = App.getInstance().getDatabase();

        TeamDao teamDao = db.teamDao();
        team = teamDao.getById(getArguments().getInt("teamId"));

        setTeam(team);

        return root;
    }

    private void setTeam (Team team) {
        binding.teamNameEt.setText(team.name);
        binding.teamCityEt.setText(team.city);
        binding.teamHomeArenaEt.setText(team.homeArena);

        int numberOfGames = 0;
        int numberOfWinningGames = 0;
        MatchDao matchDao = db.matchDao();
        List<Match> matches = matchDao.getAll();
        for (Match match : matches) {
            if (match.firstTeamId == team.id || match.secondTeamId == team.id) {
                numberOfGames++;
            }
            if (match.winningTeamId == team.id) {
                numberOfWinningGames++;
            }
        }
        binding.numberOfGamesTv.setText(String.valueOf(numberOfGames));
        binding.numberOfWinningGamesTv.setText(String.valueOf(numberOfWinningGames));
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }

    private void onClick (View view) {
        if (binding.teamNameEt.getText().toString().equals("")){
            Toast.makeText(getContext(), "Ошибка при редактировании команды", Toast.LENGTH_SHORT).show();
            return;
        }
        if (binding.teamCityEt.getText().toString().equals("")){
            Toast.makeText(getContext(), "Ошибка при редактировании команды", Toast.LENGTH_SHORT).show();
            return;
        }
        if (binding.teamHomeArenaEt.getText().toString().equals("")){
            Toast.makeText(getContext(), "Ошибка при редактировании команды", Toast.LENGTH_SHORT).show();
            return;
        }

        team.name = binding.teamNameEt.getText().toString();
        team.city = binding.teamCityEt.getText().toString();
        team.homeArena = binding.teamHomeArenaEt.getText().toString();

        TeamDao teamDao = db.teamDao();
        teamDao.update(team);

        NavController host = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        host.navigate(R.id.navigation_teams);
    }
}