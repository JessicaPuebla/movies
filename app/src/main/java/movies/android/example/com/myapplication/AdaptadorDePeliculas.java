package movies.android.example.com.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * {@link BaseAdapter} para poblar coches en un grid view
 */

public class AdaptadorDePeliculas extends BaseAdapter {
    private Context context;
    private ArrayList<Pelicula> ITEMS;

    public AdaptadorDePeliculas(Context context) {
        this.context = context;
        ITEMS = new ArrayList<Pelicula>();
        //ITEMS.add(new Pelicula("http://i.imgur.com/DvpvklR.png")); //Imágenes falsas
    }

    @Override
    public int getCount() {
        //return this.ITEMS.length;
        return this.ITEMS.size();
    }

    @Override
    public Pelicula getItem(int position) {
        //return this.ITEMS[position];
        return this.ITEMS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item, viewGroup, false);
        }

        ImageView imagenPeli = (ImageView) view .findViewById(R.id.imagen_pelicula);
        //TextView nombreCoche = (TextView) view.findViewById(R.id.nombre_coche);

        final Pelicula item = getItem(position);

        Picasso.with(context)
                .load(item.getNombre())
                .into(imagenPeli);

        return view;
    }

    /* Limpiar el arreglo */
    public void clear() {
        ITEMS.clear();
    }

    /* Agregar elementos al arreglo */
    public void agregar(Pelicula pelicula) {
        ITEMS.add(pelicula);
    }
    /*public void add( String pelicula ) {
        this.ITEMS.add(new Pelicula(pelicula));
    }*/

}