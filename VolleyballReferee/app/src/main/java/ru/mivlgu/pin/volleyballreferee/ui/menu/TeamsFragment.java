package ru.mivlgu.pin.volleyballreferee.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import java.util.List;

import ru.mivlgu.pin.volleyballreferee.App;
import ru.mivlgu.pin.volleyballreferee.R;
import ru.mivlgu.pin.volleyballreferee.adapters.TeamAdapter;
import ru.mivlgu.pin.volleyballreferee.database.AppDatabase;
import ru.mivlgu.pin.volleyballreferee.database.dao.TeamDao;
import ru.mivlgu.pin.volleyballreferee.database.entities.Team;
import ru.mivlgu.pin.volleyballreferee.databinding.FragmentTeamsBinding;

public class TeamsFragment extends Fragment {

    private FragmentTeamsBinding binding;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTeamsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.teamsRv.getContext(),
                LinearLayout.VERTICAL);
        binding.teamsRv.addItemDecoration(dividerItemDecoration);

        TeamAdapter adapter = new TeamAdapter();
        adapter.setListener(new TeamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick (Team team) {
                Bundle bundle = new Bundle();
                bundle.putInt("teamId", team.id);

                NavController host = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                host.navigate(R.id.editing_team, bundle);
            }
        });

        AppDatabase db = App.getInstance().getDatabase();
        TeamDao teamDao = db.teamDao();
        List<Team> teams = teamDao.getAll();
        adapter.setTeams(teams);

        binding.teamsRv.setAdapter(adapter);

        binding.addTeamsBtn.setOnClickListener(this::onClick);

        return root;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }

    public void onClick (View view) {
        NavController host = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        host.navigate(R.id.adding_team);
    }
}