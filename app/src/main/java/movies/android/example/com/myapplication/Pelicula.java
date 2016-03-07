package movies.android.example.com.myapplication;

/**
 * Clase que representa la existencia de una Pelicula
 */
public class Pelicula {
    private String nombre;
    //private int idDrawable;

    public Pelicula(String nombre) {
        this.nombre = nombre;
        //this.idDrawable = idDrawable;
    }

    public String getNombre() {
        return this.nombre;
    }

    /*public int getIdDrawable() {
        return idDrawable;
    }*/

    public int getId() {
        return nombre.hashCode();
    }

    /*public static Pelicula[] ITEMS = {
            new Pelicula("http://i.imgur.com/DvpvklR.png"),
            new Pelicula("http://i.imgur.com/pb0O2S9.jpg"),
            new Pelicula("http://i.imgur.com/B95YgZL.jpg"),
            new Pelicula("http://i.imgur.com/0URyPLR.jpg"),
            new Pelicula("http://i.imgur.com/X9tU4Nu.jpg"),
            new Pelicula("http://i.imgur.com/Qqb8gYW.jpg"),
            new Pelicula("http://i.imgur.com/Qqb8gYW.jpg"),
            new Pelicula("http://i.imgur.com/fyBGiVl.jpg"),
            new Pelicula("http://i.imgur.com/pYgAtEI.jpg"),
            new Pelicula("http://i.imgur.com/DvpvklR.png"),
    };*/
    public static Pelicula[] ITEMS;

    public void setITEMS( Pelicula[] items ) {
        this.ITEMS = items;
    }

    /**
     * Obtiene item basado en su identificador
     *
     * @param id identificador
     * @return Pelicula
     */
    public static Pelicula getItem(int id) {
        for (Pelicula item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

}
