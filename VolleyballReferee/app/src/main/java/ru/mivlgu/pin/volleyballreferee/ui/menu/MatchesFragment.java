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
import ru.mivlgu.pin.volleyballreferee.adapters.MatchAdapter;
import ru.mivlgu.pin.volleyballreferee.database.AppDatabase;
import ru.mivlgu.pin.volleyballreferee.database.dao.MatchDao;
import ru.mivlgu.pin.volleyballreferee.database.entities.Match;
import ru.mivlgu.pin.volleyballreferee.databinding.FragmentMatchesBinding;

public class MatchesFragment extends Fragment {

    private FragmentMatchesBinding binding;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMatchesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.matchesRv.getContext(),
                LinearLayout.VERTICAL);
        binding.matchesRv.addItemDecoration(dividerItemDecoration);

        MatchAdapter adapter = new MatchAdapter();
        adapter.setListener(new MatchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick (Match match) {
                Bundle bundle = new Bundle();
                bundle.putInt("matchId", match.id);

                NavController host = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                host.navigate(R.id.details_match, bundle);
            }
        });

        AppDatabase db = App.getInstance().getDatabase();
        MatchDao matchDao = db.matchDao();
        List<Match> matches = matchDao.getAll();
        adapter.setMatches(matches);

        binding.matchesRv.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }
}