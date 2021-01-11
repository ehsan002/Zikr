package com.ehsanrc.zikr_update.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ehsanrc.zikr_update.R;
import com.ehsanrc.zikr_update.model.Dua;

import java.util.ArrayList;
import java.util.List;

public class DuaListAdapter extends RecyclerView.Adapter<DuaListAdapter.DuaViewHolder> {

    private final String DUA_LIST_CLASS = "class com.ehsanrc.zikr_update.view.Dualist";
    private final String FAVORITE_CLASS = "class com.ehsanrc.zikr_update.view.Favorites";

    private final ArrayList<Dua> duaList;
    private final String className;

    public DuaListAdapter(ArrayList<Dua> duaList, String className) {
        this.duaList = duaList;
        this.className = className;
    }

    public void updateList(List<Dua> newDuaList){

        duaList.clear();
        duaList.addAll(newDuaList);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public DuaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dua, parent, false);

        return new DuaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DuaViewHolder holder, int position) {

        TextView duaTitle = holder.itemView.findViewById(R.id.textViewDuaList);
        LinearLayout linearLayout = holder.itemView.findViewById(R.id.linear_layout_list);

        duaTitle.setText(duaList.get(position).duaTitle);
        linearLayout.setOnClickListener(v -> {

            if (className.equals(DUA_LIST_CLASS)){
                DualistDirections.ActionDetails action = DualistDirections.actionDetails();
                action.setPosition(position);
                action.setDuaId(duaList.get(position).id);
                action.setFavorite(duaList.get(position).isFavorite);
                Navigation.findNavController(linearLayout).navigate(action);
            }else if(className.equals(FAVORITE_CLASS)){
                FavoritesDirections.ActionDetailsFromFavorite action = FavoritesDirections.actionDetailsFromFavorite();
                action.setDuaId(duaList.get(position).id);
                action.setFavorite(duaList.get(position).isFavorite);
                Navigation.findNavController(linearLayout).navigate(action);
            }
        });

    }

    @Override
    public int getItemCount() {
        return duaList.size();
    }

    static class DuaViewHolder extends RecyclerView.ViewHolder{

        public View itemView;

        public DuaViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

}
