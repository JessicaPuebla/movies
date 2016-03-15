package movies.android.example.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        usarToolbar();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void usarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {

        private static final String LOG_TAG = DetailFragment.class.getSimpleName();
        private Pelicula mPelicula;

        public DetailFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.content_detalle, container, false);

            // The detail Activity called via intent.  Inspect the intent for movie data.
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra("pelicula")) {
                mPelicula = intent.getParcelableExtra("pelicula"); //Datos de la película seleccionada
                //Toast.makeText(rootView.getContext(), mPelicula.getDescripcion(), Toast.LENGTH_SHORT).show();
                /* Poner el título de la película */
                TextView titlePei = (TextView) rootView.findViewById(R.id.titulo_pelicula);
                titlePei.setText(mPelicula.getTitulo());
                /* Año */
                TextView anioPeli = (TextView) rootView.findViewById(R.id.anio_pelicula);
                anioPeli.setText(mPelicula.getAnio());
                /* Duración */
                TextView duracion = (TextView) rootView.findViewById(R.id.duracion_pelicula);
                duracion.setText(mPelicula.getDuracion()+"min");
                /*Calificación*/
                TextView calificacion = (TextView) rootView.findViewById(R.id.calificacion_pelicula);
                calificacion.setText(mPelicula.getCalificacion());
                /* Descripción */
                TextView descripcion = (TextView) rootView.findViewById(R.id.descripcion_pelicula);
                descripcion.setText(mPelicula.getDescripcion());
                /* Poner el póster */
                ImageView imagenPeli = (ImageView) rootView.findViewById(R.id.poster_pelicula);
                Picasso.with(rootView.getContext())
                        .load(mPelicula.getNombre())
                        .fit()
                        .into(imagenPeli);

            }
            //return null;
            return rootView;
        }
    }
}
