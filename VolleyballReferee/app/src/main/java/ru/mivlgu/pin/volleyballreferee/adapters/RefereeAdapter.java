package ru.mivlgu.pin.volleyballreferee.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.mivlgu.pin.volleyballreferee.database.entities.Referee;
import ru.mivlgu.pin.volleyballreferee.databinding.ItemRefereeBinding;

public class RefereeAdapter extends RecyclerView.Adapter<RefereeAdapter.ViewHolder> {

    private List<Referee> referees;
    private OnItemClickListener listener;

    public void setReferees (List<Referee> referees) {
        this.referees = referees;
        notifyDataSetChanged();
    }

    public void setListener (OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemRefereeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
        holder.bind(referees.get(position), listener);
    }

    @Override
    public int getItemCount () {
        return referees.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        ItemRefereeBinding binding;

        public ViewHolder (@NonNull ItemRefereeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind (Referee referee, OnItemClickListener listener) {
            binding.fullNameTv.setText(referee.fullName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(referee);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick (Referee referee);
    }
}