package movies.android.example.com.myapplication;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jessica on 07/03/2016.
 */
public class FragmentMovies extends Fragment {

    private AdaptadorDePeliculas adaptador;

    public FragmentMovies() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        View rootView = inflater.inflate(R.layout.content_main, container, false);

        adaptador = new AdaptadorDePeliculas(rootView.getContext());
        GridView gridView = (GridView) rootView.findViewById(R.id.grid);
        gridView.setAdapter(adaptador);

        return rootView; //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void updateMovies() {
        MoviesManager movieTask = new MoviesManager();
        // Obtener de los ajustes la forma de ordenar
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( getActivity() );
        String ordenarpor = prefs.getString( getString( R.string.pref_order_key ),
                getString( R.string.pref_order_default ) );
        //Log.e(FragmentMovies.class.getSimpleName(), "AJUSTE: "+ordenarpor);
        movieTask.execute( ordenarpor );
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    public class MoviesManager extends AsyncTask<String, Void, Pelicula[]> {

        private final String LOG_TAG = MoviesManager.class.getSimpleName();
        private Pelicula[] items;

        @Override
        protected Pelicula[] doInBackground(String... params) {

            // If there's no zip code, there's nothing to look up.  Verify size of params.
            if (params.length == 0) {
                //return null;
                Log.e(LOG_TAG, "No hay cinfiguración de orden.");
            }

            String peliculas = "";

            try {
                peliculas = searchIMDB((String) params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                return getMoviesDataFromJson(peliculas);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        /**
         * Searches IMDBs API for the given query
         *
         * @param ordenarPor The query to search.
         * @return A list of all hits.
         */
        public String searchIMDB(String ordenarPor) throws IOException {

            //String orden  = "popularity.desc";
            String idioma = "es";
            String pais   = "";
            String cert   = "";
            if( ordenarPor.equalsIgnoreCase("vote_average.desc") ) { //Si está ordenado por Mejor valorado
                pais = "MX";
                cert = "R";
            }

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                // Build URL
                final String BASE_URL =
                        "https://api.themoviedb.org/3/discover/movie?";
                final String SORT_PARAM   = "sort_by";
                final String CERTC_PARAM  = "certification_country";
                final String CERT_PARAM   = "certification";
                final String APIKEY_PARAM = "api_key";
                final String LANGUA_PARAM = "language";

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_PARAM, ordenarPor) //.params[0]
                        .appendQueryParameter(CERTC_PARAM, pais)
                        .appendQueryParameter(CERT_PARAM, cert)
                        .appendQueryParameter(APIKEY_PARAM, BuildConfig.MOVIE_API_KEY)
                        .appendQueryParameter(LANGUA_PARAM, idioma)
                        .build();

                URL url = new URL(builtUri.toString());
                //Log.e( LOG_TAG, builtUri.toString() );

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                return buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
        }

        private Pelicula[] getMoviesDataFromJson(String moviesJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_RESULTS = "results";
            final String OWM_POSTER  = "poster_path";

            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesJson.getJSONArray(OWM_RESULTS);

            this.items = new Pelicula[moviesArray.length()];
            String baseImagen = "http://image.tmdb.org/t/p/";
            String tamaImagen = "w185/";

            for(int i = 0; i < moviesArray.length(); i++) {

                String poster = "";

                // Get the JSON object representing the movie
                JSONObject movie = moviesArray.getJSONObject(i);

                if ( (movie.getString(OWM_POSTER) != null) && (!movie.getString(OWM_POSTER).equals("")) && (movie.getString(OWM_POSTER) != "null")  ) {
                    poster = baseImagen + tamaImagen + movie.getString(OWM_POSTER);
                    //Log.e(LOG_TAG, movie.getString(OWM_POSTER));
                    this.items[i] = new Pelicula(poster);
                }
            }

            //return peliculas;
            return this.items;
        }

        @Override
        /* Llenar el arreglo con los elementos arrojados */
        protected void onPostExecute(Pelicula[] result) {
            if (result != null) {
                adaptador.clear();
                for(Pelicula peli : result) {
                    if( peli == null ) {
                        Log.e(LOG_TAG, "Error, la instancia de esta película es nula.");
                    } else {
                        //Log.e(LOG_TAG, peli.getNombre());
                        adaptador.add(peli.getNombre());
                    }
                }
                // New data is back from the server.  Hooray!
            }
        }
    }
}
