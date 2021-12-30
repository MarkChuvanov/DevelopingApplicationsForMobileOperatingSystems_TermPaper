package ru.mivlgu.pin.volleyballreferee.ui.match;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import ru.mivlgu.pin.volleyballreferee.App;
import ru.mivlgu.pin.volleyballreferee.adapters.GameEventAdapter;
import ru.mivlgu.pin.volleyballreferee.database.AppDatabase;
import ru.mivlgu.pin.volleyballreferee.database.dao.GameEventDao;
import ru.mivlgu.pin.volleyballreferee.database.dao.MatchDao;
import ru.mivlgu.pin.volleyballreferee.database.dao.MatchProgressDao;
import ru.mivlgu.pin.volleyballreferee.database.dao.RefereeDao;
import ru.mivlgu.pin.volleyballreferee.database.dao.TeamDao;
import ru.mivlgu.pin.volleyballreferee.database.entities.GameEvent;
import ru.mivlgu.pin.volleyballreferee.database.entities.Match;
import ru.mivlgu.pin.volleyballreferee.database.entities.MatchProgress;
import ru.mivlgu.pin.volleyballreferee.database.entities.Referee;
import ru.mivlgu.pin.volleyballreferee.database.entities.Team;
import ru.mivlgu.pin.volleyballreferee.databinding.DetailsMatchBinding;

public class DetailsMatchFragment extends Fragment {

    private DetailsMatchBinding binding;
    private AppDatabase db;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        binding = DetailsMatchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = App.getInstance().getDatabase();

        MatchDao matchDao = db.matchDao();
        Match match = matchDao.getById(getArguments().getInt("matchId"));

        setMatch(match);

        return root;
    }

    private void setMatch (Match match) {
        TeamDao teamDao = db.teamDao();
        Team firstTeam = teamDao.getById(match.firstTeamId);
        binding.firstTeamTv.setText(firstTeam.name);
        Team secondTeam = teamDao.getById(match.secondTeamId);
        binding.secondTeamTv.setText(secondTeam.name);
        Team winner = teamDao.getById(match.winningTeamId);
        binding.winnerTv.setText(winner.name);

        binding.locationTv.setText(match.location);
        binding.dateTv.setText(match.date);
        binding.startTimeTv.setText(match.startTime);
        binding.endTimeTv.setText(match.endTime);
        binding.resultTv.setText(match.result);

        RefereeDao refereeDao = db.refereeDao();
        Referee referee = refereeDao.getById(match.refereeId);
        binding.refereeTv.setText(referee.fullName);

        GameEventAdapter adapter = new GameEventAdapter();
        MatchProgressDao matchProgressDao = db.matchProgressDao();
        List<MatchProgress> matchProgresses = matchProgressDao.getAllByMatchId(match.id);
        GameEventDao gameEventDao = db.gameEventDao();
        List<GameEvent> gameEvents = new ArrayList<>();
        for (MatchProgress matchProgress : matchProgresses) {
            gameEvents.add(gameEventDao.getById(matchProgress.gameEventId));
        }
        adapter.setGameEvents(gameEvents);

        binding.gameEventsRv.setAdapter(adapter);
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }
}