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
import ru.mivlgu.pin.volleyballreferee.adapters.RefereeAdapter;
import ru.mivlgu.pin.volleyballreferee.database.AppDatabase;
import ru.mivlgu.pin.volleyballreferee.database.dao.RefereeDao;
import ru.mivlgu.pin.volleyballreferee.database.entities.Referee;
import ru.mivlgu.pin.volleyballreferee.databinding.FragmentRefereesBinding;

public class RefereesFragment extends Fragment {

    private FragmentRefereesBinding binding;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRefereesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.refereesRv.getContext(),
                LinearLayout.VERTICAL);
        binding.refereesRv.addItemDecoration(dividerItemDecoration);

        RefereeAdapter adapter = new RefereeAdapter();
        adapter.setListener(new RefereeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick (Referee referee) {
                Bundle bundle = new Bundle();
                bundle.putInt("refereeId", referee.id);

                NavController host = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                host.navigate(R.id.editing_referee, bundle);
            }
        });

        AppDatabase db = App.getInstance().getDatabase();
        RefereeDao refereeDao = db.refereeDao();
        List<Referee> referees = refereeDao.getAll();
        adapter.setReferees(referees);

        binding.refereesRv.setAdapter(adapter);

        binding.addRefereesBtn.setOnClickListener(this::onClick);

        return root;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }

    public void onClick (View view) {
        NavController host = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        host.navigate(R.id.adding_referee);
    }
}