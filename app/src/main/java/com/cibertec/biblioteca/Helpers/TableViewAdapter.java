package com.cibertec.biblioteca.Helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cibertec.biblioteca.R;

import java.util.List;

public class TableViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<BookRow> userInfo;

    public TableViewAdapter(List<BookRow> userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_list_item, parent, false);

        return new RowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {
            rowViewHolder.txtCodigo.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtTitulo.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtTipo.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtEstado.setBackgroundResource(R.drawable.table_header_cell_bg);
        }
        else {
            BookRow fila = userInfo.get(rowPos-1);

            rowViewHolder.txtCodigo.setText(fila.getCodigo());
            rowViewHolder.txtTitulo.setText(fila.getTitulo());
            rowViewHolder.txtTipo.setText(fila.getTipo());
            rowViewHolder.txtEstado.setText(fila.getEstado());
        }
    }

    @Override
    public int getItemCount() {
        return userInfo.size()+1;
    }

    public static class RowViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtCodigo, txtEstado, txtTitulo, txtTipo;

        public RowViewHolder(View itemView) {
            super(itemView);

            txtCodigo = itemView.findViewById(R.id.txtCodigo);
            txtEstado = itemView.findViewById(R.id.txtEstado);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtTipo = itemView.findViewById(R.id.txtTipo);
        }
    }
}
