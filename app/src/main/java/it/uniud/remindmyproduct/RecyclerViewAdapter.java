package it.uniud.remindmyproduct;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> nomi = new ArrayList<>();
    private ArrayList<String> descrizioni = new ArrayList<>();
    private ArrayList<String> pezzi = new ArrayList<>();
    private ArrayList<String> scadenze = new ArrayList<>();
    private ArrayList<Integer> icone = new ArrayList<>();
    private ArrayList<Integer> id_prodotti = new ArrayList<>();
    private Context context;


    public RecyclerViewAdapter(Context context, ArrayList<String> nomi, ArrayList<String> descrizioni, ArrayList<String> pezzi, ArrayList<String> scadenze, ArrayList<Integer> icone, ArrayList<Integer> id_prodotti) {
        this.nomi = nomi;
        this.descrizioni = descrizioni;
        this.pezzi = pezzi;
        this.scadenze = scadenze;
        this.icone = icone;
        this.id_prodotti = id_prodotti;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.righe_dispensa, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final int id_prodotto;
        CategorieProdotti categorie = new CategorieProdotti();
        Log.d(TAG, "onBing holder: called");
        viewHolder.nome_prodotto.setText(nomi.get(position));
        viewHolder.descizione.setText(descrizioni.get(position));
        viewHolder.pezzi.setText(context.getString(R.string.quantity_short_qta) + pezzi.get(position));
        viewHolder.data_scadenza.setText(scadenze.get(position));
        viewHolder.icona.setImageResource(categorie.getIconCategoryAtIndex(icone.get(position)));
        id_prodotto = (id_prodotti.get(position));

        viewHolder.layout_righe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked on: " + nomi.get(position) + id_prodotto);
                Toast.makeText(context, nomi.get(position), Toast.LENGTH_SHORT).show();
                Intent intentProdotto = new Intent(context.getApplicationContext(), ViewItemActivity.class);
                intentProdotto.putExtra("id_prodotto", id_prodotti.get(position));
                context.startActivity(intentProdotto);
            }
        });

    }

    @Override
    public int getItemCount() {
        return nomi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome_prodotto;
        TextView pezzi;
        TextView descizione;
        TextView data_scadenza;
        ImageView icona;
        ConstraintLayout layout_righe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome_prodotto = itemView.findViewById(R.id.nome_prodotto);
            pezzi = itemView.findViewById(R.id.pezzi);
            descizione = itemView.findViewById(R.id.descrizione);
            data_scadenza = itemView.findViewById(R.id.data_scadenza);
            icona = itemView.findViewById(R.id.icona_categoria);
            layout_righe = itemView.findViewById(R.id.layout_righe_dispensa);
        }
    }
}
