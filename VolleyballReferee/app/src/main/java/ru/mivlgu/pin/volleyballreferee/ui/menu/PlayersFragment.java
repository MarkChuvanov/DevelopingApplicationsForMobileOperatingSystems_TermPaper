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
import ru.mivlgu.pin.volleyballreferee.adapters.PlayerAdapter;
import ru.mivlgu.pin.volleyballreferee.database.AppDatabase;
import ru.mivlgu.pin.volleyballreferee.database.dao.PlayerDao;
import ru.mivlgu.pin.volleyballreferee.database.entities.Player;
import ru.mivlgu.pin.volleyballreferee.databinding.FragmentPlayersBinding;

public class PlayersFragment extends Fragment {

    private FragmentPlayersBinding binding;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlayersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.playersRv.getContext(),
                LinearLayout.VERTICAL);
        binding.playersRv.addItemDecoration(dividerItemDecoration);

        PlayerAdapter adapter = new PlayerAdapter();
        adapter.setListener(new PlayerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick (Player player) {
                Bundle bundle = new Bundle();
                bundle.putInt("playerId", player.id);

                NavController host = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                host.navigate(R.id.editing_player, bundle);
            }
        });

        AppDatabase db = App.getInstance().getDatabase();
        PlayerDao playerDao = db.playerDao();
        List<Player> players = playerDao.getAll();
        adapter.setPlayers(players);

        binding.playersRv.setAdapter(adapter);

        binding.addPlayersBtn.setOnClickListener(this::onClick);

        return root;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }

    public void onClick (View view) {
        NavController host = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        host.navigate(R.id.adding_player);
    }
}