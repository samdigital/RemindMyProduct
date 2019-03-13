package it.uniud.remindmyproduct;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class CategorieProdotti {
    List<String> lista = new ArrayList<String>();
    List<Integer> icone = new ArrayList<Integer>();
    private Context context;


    public CategorieProdotti(Context context) {
        this.context=context;

        lista.add(context.getString(R.string.cat_none));
        icone.add(R.drawable.icon_pallino);

        lista.add(context.getString(R.string.cat_bevande));
        icone.add(R.drawable.icon_cat_bevande);

        lista.add(context.getString(R.string.cat_latticini));
        icone.add(R.drawable.icon_cat_latticini);

        lista.add(context.getString(R.string.cat_surgelati));
        icone.add(R.drawable.icon_cat_surgelati);

        lista.add(context.getString(R.string.cat_ortofrutta));
        icone.add(R.drawable.icon_cat_ortofrutta);

        lista.add(context.getString(R.string.cat_panificio));
        icone.add(R.drawable.icon_cat_panificio);

        lista.add(context.getString(R.string.cat_pasticceria));
        icone.add(R.drawable.icon_cat_pasticceria);
    }


    public void add(String categoriaStr, Integer categoriaIcon) {
        lista.add(categoriaStr);
        icone.add(categoriaIcon);
    }

    public int getNumberOfItems() {
        return lista.size();
    }

    public String getItemAtIndex(int index) {
        return lista.get(index);
    }

    public int getIconAtIndex(int index) {
        return icone.get(index);
    }

    public List<String> getListaCategorie() {
        return lista;
    }

    public int getIconCategoryAtIndex(int index) {
        return icone.get(index);
    }
}
