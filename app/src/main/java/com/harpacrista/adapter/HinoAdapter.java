package com.harpacrista.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.harpacrista.R;
import com.harpacrista.entity.Hino;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Diogo Silva on 03/04/2016.
 */
public class HinoAdapter extends ArrayAdapter<Hino> {

    private final int resource;
    private final ArrayList<Hino> itensOriginais;
    private final View.OnClickListener onFavoritoClickListener;

    public HinoAdapter(Context context, int resource, List<Hino> objects, View.OnClickListener onFavoritoClickListener) {
        super(context, resource, objects);
        this.resource = resource;
        this.itensOriginais = new ArrayList<Hino>(objects);
        this.onFavoritoClickListener = onFavoritoClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        HinoViewHolder viewHolder = null;
        if(convertView == null){
            convertView = parent.inflate(getContext(), resource, null);
            viewHolder = new HinoViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (HinoViewHolder) convertView.getTag();
        }
        Hino item = getItem(position);
        viewHolder.nome.setText(item.getId() + "- " + item.getNome());
        viewHolder.descricao.setText(item.getAutor());
        viewHolder.favorito.setOnClickListener(onFavoritoClickListener);
        viewHolder.favorito.setTag(item);
        if(item.isFavorito()){
            viewHolder.favorito.setImageResource(R.drawable.heart);
        }else{
            viewHolder.favorito.setImageResource(R.drawable.heart_outline);
        }
        return convertView;
    }

    public class HinoViewHolder{
        TextView nome;
        TextView descricao;
        ImageView favorito;
        public HinoViewHolder(View view){
            nome = (TextView) view.findViewById(R.id.item_hino_titulo);
            descricao = (TextView) view.findViewById(R.id.item_hino_descricao);
            favorito = (ImageView) view.findViewById(R.id.item_hino_favorito);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                List<Hino> values = (List<Hino>) results.values;
                if(values == null){
                    values = itensOriginais;
                }
                for (Hino item : values) {
                    add(item);
                }
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();

                if (constraint != null && constraint.toString().length() > 0) {
                    List<Hino> founded = new ArrayList<Hino>();
                    for(Hino item: itensOriginais){
                        if(item.toString().toLowerCase().contains(constraint)){
                            founded.add(item);
                        }
                    }
                    result.values = founded;
                    result.count = founded.size();
                }
                return result;
            }
        };
    }
}