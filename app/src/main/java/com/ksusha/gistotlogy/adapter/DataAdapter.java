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
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //рисовка каждого Item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.DataHolder holder, int position) { //загрузка данных для загрузки внутрь каждого Item
        holder.setData(listItemArray.get(position));
    }

    @Override
    public int getItemCount() { //количество элементов для загрузки в адаптер - RecyclerView
        return listItemArray.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private boolean isFavouriteChecked = false;
        private TextView textViewItem;
        private ImageButton imageButtonFavourite;

        public DataHolder(@NonNull View itemView) {
            super(itemView);
            textViewItem = itemView.findViewById(R.id.tvText);
            imageButtonFavourite = itemView.findViewById(R.id.imageButton);
            imageButtonFavourite.setOnClickListener(this);
        }

        public void setData(ListItem listItem) { //заполнение данными Item
            textViewItem.setText(listItem.getText());
            if (!isFavourite) { //если НЕ во вкладке "Избранное"
                setFavourite(listItem, getAdapterPosition());
            } else {
                setFavouriteAll();
            }
        }

        @Override
        public void onClick(View view) { //нажатие на кнопку "Поместить в избранное"
            isFavouriteChecked = !isFavouriteChecked; //если нажмем на кнопку будет true и наоборот
            if (isFavouriteChecked) { //если будет true
                imageButtonFavourite.setImageResource(android.R.drawable.btn_star_big_on);
            } else {
                imageButtonFavourite.setImageResource(android.R.drawable.btn_star_big_off);
            }
            if (!isFavourite) {
                recViewOnClickListener.onItemClicked(getAdapterPosition());
            } else {
                deleteItemInFavourites(); //если в Избранных, то удалить Item, если кнопка нажата
            }
        }

        private String replaceCharAtPosition(int position, char ziroOrOne, String placeChangedZiroOrOne) { //перезапись созданного String
            char[] charArray = placeChangedZiroOrOne.toCharArray(); //String по отдельным char
            charArray[position] = ziroOrOne; //замена символов
            return new String(charArray);
        }

        private void saveString(String stringToSave) { //сохранение в память строки
            ListItem item = listItemArray.get(getAdapterPosition());
            SharedPreferences.Editor editorSave = preferences.edit();
            editorSave.putString(item.getCategory(), stringToSave);
            editorSave.apply();
            Log.d("MY", "My: " + preferences.getString(item.getCategory(), "none"));
        }

        private void deleteItemInFavourites() { //удаление с "Избранного" Item
            ListItem item = listItemArray.get(getAdapterPosition());
            String dataToChange = preferences.getString(item.getCategory(), "none");
            if (dataToChange == null) return;
            String replaceData = replaceCharAtPosition(item.getPosition(), '0', dataToChange);
            saveString(replaceData);
            listItemArray.remove(getAdapterPosition()); //удаление по нажатой позиции
            notifyItemRemoved(getAdapterPosition()); //сообщение адаптеру с какой позиции удален Item
            notifyItemRangeChanged(getAdapterPosition(), listItemArray.size()); //подстраивание под новую длину адаптера
        }

        private void setFavouriteAll() { //все элементы отмеченны как избранное
            imageButtonFavourite.setImageResource(android.R.drawable.btn_star_big_on);
        }

        private void setFavourite(ListItem item, int position) { //получение из памяти String для категории
            String favourite_data = preferences.getString(item.getCategory(), "none");
            if (favourite_data != null) {
                char[] charArray = favourite_data.toCharArray(); //разбивание String на отдельные char
                switch (charArray[position]) {
                    case '0':
                        imageButtonFavourite.setImageResource(android.R.drawable.btn_star_big_off); //не отмечено как избранное
                        isFavouriteChecked = false;
                        break;
                    case '1':
                        imageButtonFavourite.setImageResource(android.R.drawable.btn_star_big_on); //отмечено как избранное
                        isFavouriteChecked = true;
                        break;
                }
            }
        }
    }

    public void updateArray(List<ListItem> listArray, boolean isFavourite) { //обновление списка
        this.isFavourite = isFavourite;
        listItemArray.clear(); //очистка
        listItemArray.addAll(listArray); //добавление нового списка
        notifyDataSetChanged(); //показ адаптеру об изменении данных
    }
}
