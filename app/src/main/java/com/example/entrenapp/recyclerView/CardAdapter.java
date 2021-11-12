package com.example.entrenapp.recyclerView;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapter<T extends Cardable> extends RecyclerView.Adapter<CardAdapter.ViewHolder> {


    private List<T> dataset;
    private Integer layoutID;
    Context context;
    Resources r;
    String packageName;
    private ViewHolder.OnNoteListener onNoteListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View view;
        private OnNoteListener onNoteListener;

        public ViewHolder(View view,OnNoteListener onNoteListener) {
            super(view);
            this.view = view;
            this.onNoteListener=onNoteListener;
            view.setOnClickListener(this);
        }

        public void bindTextViewWithData(int subtitleBodyContentId, String entry) {
            TextView textView = (TextView) view.findViewById(subtitleBodyContentId);
            textView.setText(entry);
        }

        @Override
        public void onClick(View v) {
            if(this.onNoteListener != null)
                this.onNoteListener.onNoteClick(getAdapterPosition());
        }

        public interface OnNoteListener{
            void onNoteClick(int position);
        }

    }

    public CardAdapter(List<T> dataset , Integer layoutID, Context context){
        this.dataset=dataset;
        this.layoutID = layoutID;
        this.context = context;
        this.r = context.getResources();
        this.packageName = context.getPackageName();
    }


    public CardAdapter(List<T> dataset, Integer layoutID, Context context, ViewHolder.OnNoteListener onNoteListener) { //, Consumer<T> binder) {
        this(dataset,layoutID,context);
        this.onNoteListener = onNoteListener;
    }




    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(layoutID, viewGroup, false);
        return new ViewHolder(view,this.onNoteListener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        int xmlTagID = 0;
        for (Cardable.CardCaption c: dataset.get(position).getCaptions()) {
                // Cambio el título de cada caption
                xmlTagID = r.getIdentifier(c.getXmlTagID() + "_header", "id", packageName); // Reemplaza al R.id.view_name
                if ( xmlTagID != 0)
                    try {
                        viewHolder.bindTextViewWithData(xmlTagID, c.getCaptionTitle());
                    } catch (Exception e) {};
                // Cambio el cuerpo de cada caption
                // "subtitle"
                xmlTagID = r.getIdentifier(c.getXmlTagID(), "id", packageName); // Reemplaza al R.id.view_name
                 if ( xmlTagID != 0)
                     try {
                         viewHolder.bindTextViewWithData(xmlTagID, c.getContent());
                     } catch (Exception e){};

        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}