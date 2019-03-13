package it.uniud.remindmyproduct;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        CategorieProdotti categorie = new CategorieProdotti(context);
        viewHolder.nome_prodotto.setText(nomi.get(position));
        viewHolder.descizione.setText(descrizioni.get(position));
        viewHolder.pezzi.setText(context.getString(R.string.quantity_short_qta) + " " + pezzi.get(position));
        viewHolder.icona.setImageResource(categorie.getIconCategoryAtIndex(icone.get(position)));
        id_prodotto = id_prodotti.get(position);

        // blocco per mettere il colore di sfondo alla lista
        String scadenza = scadenze.get(position);
        viewHolder.data_scadenza.setText(scadenza);
        Date data_oggi=new Date();
        Date data_scad=new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            data_scad = dateFormat.parse(scadenza);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(data_scad.getTime() < data_oggi.getTime()) {
            viewHolder.background.setColorFilter(context.getResources().getColor(R.color.colorAccent));
        } else {
            if( data_scad.getTime() < data_oggi.getTime()+7*24*60*60*1000 ) {
                viewHolder.background.setColorFilter(context.getResources().getColor(R.color.colorLightYellow));
            } else {
                viewHolder.background.setColorFilter(context.getResources().getColor(R.color.colorLightGreen));
            }
        }

        viewHolder.layout_righe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProdotto = new Intent(context.getApplicationContext(), ViewItemActivity.class);
                intentProdotto.putExtra("id_prodotto", id_prodotto);
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
        ImageView background;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome_prodotto = itemView.findViewById(R.id.nome_prodotto);
            pezzi = itemView.findViewById(R.id.pezzi);
            descizione = itemView.findViewById(R.id.descrizione);
            data_scadenza = itemView.findViewById(R.id.data_scadenza);
            icona = itemView.findViewById(R.id.icona_categoria);
            layout_righe = itemView.findViewById(R.id.layout_righe_dispensa);
            background = itemView.findViewById(R.id.sfondo);
        }
    }
}
