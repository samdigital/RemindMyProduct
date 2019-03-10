package it.uniud.remindmyproduct;

import java.util.ArrayList;
import java.util.List;

public class CategorieProdotti {
    List<String> lista = new ArrayList<String>();
    List<Integer> icone = new ArrayList<Integer>();

    public CategorieProdotti() {
        lista.add("Nessuna Categoria");
        icone.add(R.drawable.icon_pallino);

        lista.add("Bevande");
        icone.add(R.drawable.icon_cat_bevande);

        lista.add("Latticini");
        icone.add(R.drawable.icon_cat_latticini);

        lista.add("Surgelati");
        icone.add(R.drawable.icon_cat_surgelati);

        lista.add("Ortofrutta");
        icone.add(R.drawable.icon_cat_ortofrutta);

        lista.add("Panificio");
        icone.add(R.drawable.icon_cat_panificio);

        lista.add("Pasticceria");
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
