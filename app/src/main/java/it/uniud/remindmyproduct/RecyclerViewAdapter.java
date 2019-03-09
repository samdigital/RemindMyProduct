package it.uniud.remindmyproduct;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> titoli = new ArrayList<>();
    private Context context;


    public RecyclerViewAdapter(ArrayList<String> titoli, Context context) {
        this.titoli = titoli;
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
        Log.d(TAG, "onBing holder: called");
        viewHolder.titolo.setText(titoli.get(position));
        viewHolder.layout_righe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked on: " + titoli.get(position));
                Toast.makeText(context, titoli.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return titoli.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titolo;
        ConstraintLayout layout_righe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titolo = itemView.findViewById(R.id.titolo);
            layout_righe = itemView.findViewById(R.id.layout_righe_dispensa);
        }
    }
}
