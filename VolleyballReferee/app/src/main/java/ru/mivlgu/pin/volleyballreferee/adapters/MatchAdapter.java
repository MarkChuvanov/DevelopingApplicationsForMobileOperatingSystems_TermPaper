package ru.mivlgu.pin.volleyballreferee.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mivlgu.pin.volleyballreferee.App;
import ru.mivlgu.pin.volleyballreferee.database.AppDatabase;
import ru.mivlgu.pin.volleyballreferee.database.dao.TeamDao;
import ru.mivlgu.pin.volleyballreferee.database.entities.Match;
import ru.mivlgu.pin.volleyballreferee.databinding.ItemMatchBinding;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {

    private List<Match> matches;
    private OnItemClickListener listener;

    public void setMatches (List<Match> matches) {
        this.matches = matches;
        notifyDataSetChanged();
    }

    public void setListener (OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMatchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder (@NonNull MatchAdapter.ViewHolder holder, int position) {
        holder.bind(matches.get(position), listener);
    }

    @Override
    public int getItemCount () {
        return matches.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        ItemMatchBinding binding;

        public ViewHolder (@NonNull ItemMatchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind (Match match, OnItemClickListener listener) {

            AppDatabase db = App.getInstance().getDatabase();
            TeamDao teamDao = db.teamDao();
            binding.teamsTv.setText(teamDao.getById(match.firstTeamId).name + " - " + teamDao.getById(match.secondTeamId).name);
            binding.matchDateTv.setText(match.date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(match);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick (Match match);
    }
}