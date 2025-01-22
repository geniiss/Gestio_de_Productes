package dades;

/**
* La clase Producte representa un producto con un nombre, una descripción y un precio.
*/
public class Producte {
    /**
     * El nombre del producto
     */
    private String nom;
    /**
     * La descripción del producto
     */
    private String descripcio;
    /**
     * El precio del producto
     */
    private float preu;
    /**
     * El path de la imagen del producto
     */
    private String imgPath;


    /**
    * Constructor de la clase Producte.
    *
    * @param nom El nombre del producto.
    * @param descripcio Una descripción del producto.
    * @param preu El precio del producto.
    * @param iconPath El path de la imagen del producto.
    */
    public Producte(String nom, String descripcio, float preu, String iconPath) {
        this.nom = nom;
        this.descripcio = descripcio;
        this.preu = preu;
        this.imgPath = iconPath;
    }



    // Getters //
   
    /**
    * Devuelve el nombre del producto.
    *
    * @return el nombre del producto como una cadena.
    */
    public String getNom() {
        return nom;
    }


    /**
    * Devuelve la descripción del producto.
    *
    * @return la descripción del producto como una cadena.
    */
    public String getDescripcio() {
        return descripcio;
    }


    /**
    * Devuelve el precio del producto.
    *
    * @return el precio del producto como un valor flotante.
    */
    public float getPreu() {
        return preu;
    }

    /**
    * Devuelve el Path de la imagen del producto.
    *
    * @return el path donde se encuentra la imagen en String.
    */
    public String getImatgePath() {
        return imgPath;
    }



    // Setters //
    /**
     * Establece el nombre del producto.
     * @param nom El nombre del producto.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Establece la descripción del producto.
     * @param descripcio
     */
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    /**
     * Establece el precio del producto.
     * @param preu El precio del producto.
     */
    public void setPreu(float preu) {
        this.preu = preu;
    }

    /**
     * Establece el path de la imagen del producto.
     * @param imgPath El path de la imagen del producto.
     */
    public void setImatgePath(String imgPath) {
        this.imgPath = imgPath;
    }
}