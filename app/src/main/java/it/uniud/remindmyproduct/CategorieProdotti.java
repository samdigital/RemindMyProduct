package it.uniud.remindmyproduct;

import java.util.ArrayList;
import java.util.List;

public class CategorieProdotti {
    List<String> lista = new ArrayList<String>();

    public CategorieProdotti() {
        lista.add("Nessuna Categoria");
        lista.add("Bevande");
        lista.add("Da Frigo");
        lista.add("Ortofrutta");
        lista.add("Panificio");
        lista.add("Pasticceria");
        lista.add("Droghe/Spezie");
    }


    public void add(String categoriaStr) {
        lista.add(categoriaStr);
    }

    public int getNumberOfItems() {
        return lista.size();
    }

    public String getItemAtIndex(int index) {
        return lista.get(index);
    }

    public List<String> getListaCategorie() {
        return lista;
    }


}
