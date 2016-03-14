package movies.android.example.com.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Clase que representa la existencia de una Pelicula
 */
public class Pelicula implements Parcelable {
    public String nombre; //En realidad se refiere al póster
    public String titulo;
    public String anio;
    public String duracion;
    public String calificacion;
    public String descripcion;
    //public Pelicula[] ITEMS;

    public Pelicula() {}

    /*public Pelicula(String nombre) {
        this.nombre = nombre;
        //this.idDrawable = idDrawable;
    }*/

    public Pelicula(Parcel in) {
        nombre = in.readString();
        titulo = in.readString();
        anio = in.readString();
        duracion = in.readString();
        calificacion = in.readString();
        descripcion = in.readString();
        //readFromParcel(in);
    }

    public static final Creator<Pelicula> CREATOR = new Creator<Pelicula>() {
        @Override
        public Pelicula createFromParcel(Parcel in) {
            return new Pelicula(in);
        }

        @Override
        public Pelicula[] newArray(int size) {
            return new Pelicula[size];
        }
    };

    public int getId() {
        return nombre.hashCode();
    }

    public String getNombre() {
        return nombre;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAnio() {
        return anio;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(titulo);
        dest.writeString(anio);
        dest.writeString(duracion);
        dest.writeString(calificacion);
        dest.writeString(descripcion);
    }

    /* http://androcode.es/2012/12/trabajando-con-parcelables/ */
    private void readFromParcel(Parcel in) {
        nombre = in.readString();
        titulo = in.readString();
        anio = in.readString();
        duracion = in.readString();
        calificacion = in.readString();
        descripcion = in.readString();
        //in.readTypedArray(ITEMS, CREATOR);
    }
}
