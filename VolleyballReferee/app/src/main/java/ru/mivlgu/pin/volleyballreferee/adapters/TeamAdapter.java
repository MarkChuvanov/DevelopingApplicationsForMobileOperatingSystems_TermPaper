package ru.mivlgu.pin.volleyballreferee.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.mivlgu.pin.volleyballreferee.database.entities.Referee;
import ru.mivlgu.pin.volleyballreferee.database.entities.Team;
import ru.mivlgu.pin.volleyballreferee.databinding.ItemTeamBinding;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    private List<Team> teams;
    private OnItemClickListener listener;

    public void setTeams (List<Team> teams) {
        this.teams = teams;
        notifyDataSetChanged();
    }

    public void setListener (OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemTeamBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
        holder.bind(teams.get(position), listener);
    }

    @Override
    public int getItemCount () {
        return teams.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        ItemTeamBinding binding;

        public ViewHolder (@NonNull ItemTeamBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind (Team team, OnItemClickListener listener) {
            binding.nameTv.setText(team.name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(team);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick (Team team);
    }
}