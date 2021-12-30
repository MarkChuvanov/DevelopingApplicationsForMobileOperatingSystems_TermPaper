package ru.mivlgu.pin.volleyballreferee.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mivlgu.pin.volleyballreferee.database.entities.Player;
import ru.mivlgu.pin.volleyballreferee.databinding.ItemPlayerBinding;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private List<Player> players;
    private OnItemClickListener listener;

    public void setPlayers (List<Player> players) {
        this.players = players;
        notifyDataSetChanged();
    }

    public void setListener (OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemPlayerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
        holder.bind(players.get(position), listener);
    }

    @Override
    public int getItemCount () {
        return players.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        ItemPlayerBinding binding;

        public ViewHolder (@NonNull ItemPlayerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind (Player player, OnItemClickListener listener) {
            binding.fullNameTv.setText(player.fullName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(player);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick (Player player);
    }
}