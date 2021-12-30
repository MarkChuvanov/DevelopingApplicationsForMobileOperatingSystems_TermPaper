package ru.mivlgu.pin.volleyballreferee.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mivlgu.pin.volleyballreferee.database.entities.GameEvent;
import ru.mivlgu.pin.volleyballreferee.databinding.ItemGameEventBinding;

public class GameEventAdapter extends RecyclerView.Adapter<GameEventAdapter.ViewHolder> {

    private List<GameEvent> gameEvents;

    public void setGameEvents (List<GameEvent> gameEvents) {
        this.gameEvents = gameEvents;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemGameEventBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
        holder.bind(gameEvents.get(position));
    }

    @Override
    public int getItemCount () {
        return gameEvents.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        ItemGameEventBinding binding;

        public ViewHolder (@NonNull ItemGameEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind (GameEvent gameEvent) {
            binding.nameTv.setText(gameEvent.name);
        }
    }
}