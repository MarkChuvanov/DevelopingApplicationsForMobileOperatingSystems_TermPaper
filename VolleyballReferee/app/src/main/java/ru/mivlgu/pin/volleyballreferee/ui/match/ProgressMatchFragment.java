package ru.mivlgu.pin.volleyballreferee.ui.match;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.mivlgu.pin.volleyballreferee.App;
import ru.mivlgu.pin.volleyballreferee.MatchManager;
import ru.mivlgu.pin.volleyballreferee.R;
import ru.mivlgu.pin.volleyballreferee.database.AppDatabase;
import ru.mivlgu.pin.volleyballreferee.database.dao.GameEventDao;
import ru.mivlgu.pin.volleyballreferee.database.dao.MatchDao;
import ru.mivlgu.pin.volleyballreferee.database.dao.MatchProgressDao;
import ru.mivlgu.pin.volleyballreferee.database.dao.TeamDao;
import ru.mivlgu.pin.volleyballreferee.database.entities.GameEvent;
import ru.mivlgu.pin.volleyballreferee.database.entities.Match;
import ru.mivlgu.pin.volleyballreferee.database.entities.MatchProgress;
import ru.mivlgu.pin.volleyballreferee.database.entities.Team;
import ru.mivlgu.pin.volleyballreferee.databinding.ProgressMatchBinding;

public class ProgressMatchFragment extends Fragment {

    private ProgressMatchBinding binding;
    private AppDatabase db;
    private Match match;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        binding = ProgressMatchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = App.getInstance().getDatabase();

        match = new Match();

        Bundle bundle = getArguments();
        match.firstTeamId = bundle.getInt("firstTeamId");
        match.secondTeamId = bundle.getInt("secondTeamId");
        match.location = bundle.getString("location");
        match.refereeId = bundle.getInt("refereeId");
        match.date = bundle.getString("date");
        match.startTime = bundle.getString("startTime");
        match.result = "";

        setMatch(match);

        GameEventDao gameEventDao = db.gameEventDao();
        List<GameEvent> gameEvents = gameEventDao.getAll();
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < gameEvents.size(); i++) {
            if (gameEvents.get(i).id != 1 & gameEvents.get(i).id != 2) {
                names.add(gameEvents.get(i).name);
            }
        }
        String[] items = names.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
        binding.gameEventsSpinner.setAdapter(adapter);

        binding.firstTeamBtn.setOnClickListener(this :: onFirstTeam);
        binding.secondTeamBtn.setOnClickListener(this :: onSecondTeam);
        binding.addGameEventBtn.setOnClickListener(this :: onAdd);

        matchEvents = new ArrayList<>();

        setMatchResult();
        setResult();

        return root;
    }

    private int firstTeamPoints;
    private int secondTeamPoints;
    private ArrayList<Integer> matchEvents;

    private void onAdd (View view) {
        matchEvents.add((int) binding.gameEventsSpinner.getSelectedItemId() + 3);
    }

    public void onSecondTeam (View view) {
        secondTeamPoints++;
        matchEvents.add(2);
        checkMatch();
    }

    public void onFirstTeam (View view) {
        firstTeamPoints++;
        matchEvents.add(1);
        checkMatch();
    }

    private void saveMatch () {
        if (MatchManager.getMatchWinnerId(firstTeamSets, secondTeamSets) == 1) {
            match.winningTeamId = match.firstTeamId;
        } else {
            match.winningTeamId = match.secondTeamId;
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        match.endTime = timeFormat.format(date);
        StringBuilder builder = new StringBuilder(match.result);
        builder.deleteCharAt(match.result.length() - 2);
        match.result = builder.toString();
        MatchDao matchDao = db.matchDao();
        matchDao.insert(match);

        Match lastMatch = matchDao.getLastMatch();

        MatchProgressDao matchProgressDao = db.matchProgressDao();
        for (Integer gameEventId : matchEvents) {
            MatchProgress matchProgress = new MatchProgress();
            matchProgress.matchId = lastMatch.id;
            matchProgress.gameEventId = gameEventId;
            matchProgressDao.insert(matchProgress);
        }
    }

    private int sets;

    private void checkMatch () {
        if (MatchManager.isSetFinished(sets + 1, firstTeamPoints, secondTeamPoints)) {
            sets++;
            if (MatchManager.getSetWinnerId(firstTeamPoints, secondTeamPoints) == 1) {
                firstTeamSets++;
            } else {
                secondTeamSets++;
            }
            match.result += String.format("%d:%d, ", firstTeamPoints, secondTeamPoints);
            setMatchResult();
            if (MatchManager.isMatchFinished(firstTeamSets, secondTeamSets)) {
                match.result = String.format("%d:%d (%s)", firstTeamSets, secondTeamSets, match.result);
                saveMatch();

                MatchDao matchDao = db.matchDao();
                Match lastMatch = matchDao.getLastMatch();

                Bundle bundle = new Bundle();
                bundle.putInt("matchId", lastMatch.id);
                NavController host = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                host.popBackStack(R.id.progress_match, true);
                host.navigate(R.id.details_match, bundle);
                return;
            }
            secondTeamPoints = 0;
            firstTeamPoints = 0;
            setResult();
        } else {
            setResult();
        }
    }

    private void setResult () {
        binding.resultTv.setText(String.format("%d:%d", firstTeamPoints, secondTeamPoints));
    }

    private int firstTeamSets;
    private int secondTeamSets;

    private void setMatchResult () {
        binding.matchResultTv.setText(String.format("%d:%d", firstTeamSets, secondTeamSets));
    }

    private void setMatch (Match match) {

        TeamDao teamDao = db.teamDao();
        Team firstTeam = teamDao.getById(match.firstTeamId);
        binding.firstTeamTv.setText(firstTeam.name);
        Team secondTeam = teamDao.getById(match.secondTeamId);
        binding.secondTeamTv.setText(secondTeam.name);
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }
}