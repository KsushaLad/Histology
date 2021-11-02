package com.ksusha.gistotlogy.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ksusha.gistotlogy.R;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataHolder> {
    private Context context;
    private RecViewOnClickListener recViewOnClickListener;
    private List<ListItem> listItemArray;
    private SharedPreferences preferences;
    private boolean isFavourite;

    public DataAdapter(Context context, RecViewOnClickListener recViewOnClickListener, List<ListItem> listItemArray) {
        this.context = context;
        this.recViewOnClickListener = recViewOnClickListener;
        this.listItemArray = listItemArray;
        preferences = context.getSharedPreferences("CATEGORY", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.DataHolder holder, int position) {
        holder.setData(listItemArray.get(position));
    }

    @Override
    public int getItemCount() {
        return listItemArray.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private boolean isFavouriteChecked = false;
        private TextView tvText;
        private ImageButton imageButtonFavourite;

        public DataHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tvText);
            imageButtonFavourite = itemView.findViewById(R.id.imageButton);
            imageButtonFavourite.setOnClickListener(this);
        }

        public void setData(ListItem listItem) {
            tvText.setText(listItem.getText());
            if (!isFavourite) {
                setFavourite(listItem, getAdapterPosition());
            } else {
                setFavouriteAll();
            }
        }

        @Override
        public void onClick(View view) {
            isFavouriteChecked = !isFavouriteChecked;
            if (isFavouriteChecked) {
                imageButtonFavourite.setImageResource(android.R.drawable.btn_star_big_on);
            } else {
                imageButtonFavourite.setImageResource(android.R.drawable.btn_star_big_off);
            }
            if (!isFavourite) {
                recViewOnClickListener.onItemClicked(getAdapterPosition());
            } else {
                deleteItemInFavourites();
            }
        }

        private String replaceCharAtPosition(int position, char ziroOrOne, String placeChangedZiroOrOne) {
            char[] charArray = placeChangedZiroOrOne.toCharArray();
            charArray[position] = ziroOrOne;
            return new String(charArray);
        }

        private void saveString(String stringToSave) {
            ListItem item = listItemArray.get(getAdapterPosition());
            SharedPreferences.Editor editorSave = preferences.edit();
            editorSave.putString(item.getCategory(), stringToSave);
            editorSave.apply();
            Log.d("MY", "My: " + preferences.getString(item.getCategory(), "none"));
        }

        private void deleteItemInFavourites() {
            ListItem item = listItemArray.get(getAdapterPosition());
            String dataToChange = preferences.getString(item.getCategory(), "none");
            if (dataToChange == null) return;
            String replaceData = replaceCharAtPosition(item.getPosition(), '0', dataToChange);
            saveString(replaceData);
            listItemArray.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), listItemArray.size());
        }

        private void setFavouriteAll() {
            imageButtonFavourite.setImageResource(android.R.drawable.btn_star_big_on);
        }

        private void setFavourite(ListItem item, int position) {
            String favourite_data = preferences.getString(item.getCategory(), "none");
            if (favourite_data != null) {
                char[] charArray = favourite_data.toCharArray();
                switch (charArray[position]) {
                    case '0':
                        imageButtonFavourite.setImageResource(android.R.drawable.btn_star_big_off);
                        isFavouriteChecked = false;
                        break;
                    case '1':
                        imageButtonFavourite.setImageResource(android.R.drawable.btn_star_big_on);
                        isFavouriteChecked = true;
                        break;
                }
            }
        }
    }

    public void updateArray(List<ListItem> listArray, boolean isFavourite) {
        this.isFavourite = isFavourite;
        listItemArray.clear();
        listItemArray.addAll(listArray);
        notifyDataSetChanged();
    }
}
