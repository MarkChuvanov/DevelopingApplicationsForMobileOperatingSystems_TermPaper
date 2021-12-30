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

import ru.mivlgu.pin.volleyballreferee.App;
import ru.mivlgu.pin.volleyballreferee.R;
import ru.mivlgu.pin.volleyballreferee.database.AppDatabase;
import ru.mivlgu.pin.volleyballreferee.database.dao.TeamDao;
import ru.mivlgu.pin.volleyballreferee.database.entities.Team;
import ru.mivlgu.pin.volleyballreferee.databinding.AddingTeamBinding;

public class AddingTeamFragment extends Fragment {

    private AddingTeamBinding binding;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        binding = AddingTeamBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.addTeamBtn.setOnClickListener(this :: onClick);

        return root;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }

    private void onClick (View view) {
        if (binding.teamNameEt.getText().toString().equals("")){
            Toast.makeText(getContext(), "Ошибка при добавлении команды", Toast.LENGTH_SHORT).show();
            return;
        }
        if (binding.teamCityEt.getText().toString().equals("")){
            Toast.makeText(getContext(), "Ошибка при добавлении команды", Toast.LENGTH_SHORT).show();
            return;
        }
        if (binding.teamHomeArenaEt.getText().toString().equals("")){
            Toast.makeText(getContext(), "Ошибка при добавлении команды", Toast.LENGTH_SHORT).show();
            return;
        }

        Team team = new Team();
        team.name = binding.teamNameEt.getText().toString();
        team.city = binding.teamCityEt.getText().toString();
        team.homeArena = binding.teamHomeArenaEt.getText().toString();

        AppDatabase db = App.getInstance().getDatabase();
        TeamDao teamDao = db.teamDao();
        teamDao.insert(team);

        NavController host = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        host.navigate(R.id.navigation_teams);
    }
}