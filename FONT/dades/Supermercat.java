package dades;

import controladores.CntrlDomini;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import structures.Pair;

  /**
   * Supermercat representa un supermercado con sus productos y relaciones entre ellos.
   */
public class Supermercat {
    private String nomSupermercat;
    private final ArrayList<Producte> productesDisponibles;
    // Las relaciones son números enteros que representan la relación entre dos productos y están entre 0 y 100.
    private ArrayList<ArrayList<Integer>> productRelations;
    private ArrayList<ArrayList<Pair<Integer,Integer>>> productRelationsSorted; // Relacions ordenadas. El pair contiene, en el primer elemento, la relación y en el segundo el índice del producto con el que está relacionado
    private ArrayList<Integer> prestatgeria;
    private int costActual;
    private final Map<String,Integer> mapNomIdx;
    private String iconPath;

    /**
     * Constructor de la clase Supermercat.
     * @param nomSupermercat Nombre del supermercado.
     */
    public Supermercat(String nomSupermercat) {
        this.nomSupermercat = nomSupermercat;
        this.prestatgeria = new ArrayList<>();
        this.costActual = -1;
        this.productesDisponibles = new ArrayList<>();
        this.productRelations = new ArrayList<>();
        this.mapNomIdx = new HashMap<>();
        this.productRelationsSorted = new ArrayList<>();
        this.iconPath = "";
    }

    /**
     * Devuelve el coste actual de la disposición de productos.
     * @return Coste actual de la disposición de productos.
     */
    public int getCost() {
      return costActual;
    }

    /**
     * Establece el coste actual de la disposición de productos.
     * @param cost Coste actual de la disposición de productos.
     */
    public void setCost(int cost) {
      costActual = cost;
    }

    /**
     * Obtiene la lista de productos disponibles a partir de una lista de pares con el nombre y el icono de los productos.
     * @param nameIconList Lista de pares con el nombre y el icono de los productos.
     * @return Lista de productos disponibles.
     */
    public ArrayList<Integer> getDispFromNameIconList(ArrayList<Pair<String,String>> nameIconList) {
      ArrayList<Integer> disp = new ArrayList<>();
      for (Pair<String,String> nameIcon: nameIconList) {
        disp.add(mapNomIdx.get(nameIcon.getFirst()));
      }
      return disp;
    }

    /**
     * Cambia el nombre del supermercado.
     * @param nom Nuevo nombre del supermercado.
     */
    public void cambiarNom(String nom) {
        nomSupermercat = nom;
    }

    /**
     * Establece la ruta del icono del supermercado.
     * @param path Ruta del icono.
     */
    public void setIconPath(String path) {
        iconPath = path;
    }

    /**
     * Elimina la información de los productos del supermercado.
     */
    public void removeInfoProductes() {
        productesDisponibles.clear();
        productRelations.clear();
        mapNomIdx.clear();
        productRelationsSorted.clear();
        prestatgeria.clear();
    }

    /**
     * Obtiene el nombre del supermercado.
     * @return Nombre del supermercado.
     */
    public String getNom() {
        return nomSupermercat;
    }

    /**
     * Obtiene la ruta del icono del supermercado.
     * @return Ruta del icono.
     */
    public String getIcona() {
        return iconPath;
    }

    /**
     * Obtiene las relaciones entre productos.
     * @return Lista de listas con las relaciones entre productos.
     */
    public ArrayList<ArrayList<Integer>> getProductRelations() {
        return new ArrayList<>(productRelations);
    }

    /**
     * Consulta el nombre de un producto dado su índice.
     * @param idx Índice del producto.
     * @return Nombre del producto.
     */
    public String consultarNomProducteAmbIdx(int idx) {
        if (idx < 0 || idx >= productesDisponibles.size()) {
            throw new IllegalArgumentException("L'índex no és correcte.");
        }
        return productesDisponibles.get(idx).getNom();
    }

    /**
     * Consulta si existe el producto con el nombre dado.
     * @param nom Nombre del producto.
     * @return True si existe el producto, False en caso contrario.
     */
    public Boolean existeixProducte(String nom) {
      return mapNomIdx.containsKey(nom);
    }

    /**
     * Consulta la relación entre dos productos.
     * @param producte1 Nombre del primer producto.
     * @param producte2 Nombre del segundo producto.
     * @return Relación entre los dos productos.
     */
    public int consultarRelacioProducte(String producte1, String producte2) {
        if (!mapNomIdx.containsKey(producte1)){
            throw new IllegalArgumentException("El producte 1 no existeix.");
        }
        if (!mapNomIdx.containsKey(producte2)) {
            throw new IllegalArgumentException("El producte 2 no existeix.");
        }
        int index1 = mapNomIdx.get(producte1);
        int index2 = mapNomIdx.get(producte2);
        return productRelations.get(index1).get(index2);
    }

    /**
     * Obtiene las relaciones de un producto con los demás productos.
     * @param nomProducte Nombre del producto.
     * @return Lista con las relaciones del producto con los demás productos.
     */
    public ArrayList<Integer> getRelacionsProducte(String nomProducte) {
        if (!mapNomIdx.containsKey(nomProducte)) {
            throw new IllegalArgumentException("El producte no existeix.");
        }
        int index = mapNomIdx.get(nomProducte);
        ArrayList<Integer> relacions = new ArrayList<>(productRelations.get(index));
        return relacions;
    }

    /**
     * Obtiene las relaciones entre productos ordenadas.
     * @return Lista de listas con las relaciones entre productos ordenadas.
     */
    public ArrayList<ArrayList<Pair<Integer,Integer>>> getProductRelationsSorted() {
        return new ArrayList<>(productRelationsSorted);
    }

    /**
     * Obtiene los nombres de todos los productos disponibles.
     * @return Lista con los nombres de los productos.
     */
    public ArrayList<String> getNomProductes() {
        ArrayList<String> nomProductes = new ArrayList<>();
        for (Producte producte : productesDisponibles) {
            nomProductes.add(producte.getNom());
        }
        return nomProductes;
    }

    /**
     * Obtiene el nombre de un producto dado su índice.
     * @param index
     * @return Nombre del producto.
     */
    public String getNomProducte(Integer index) {
        return productesDisponibles.get(index).getNom();
    }

    /**
     * Obtiene el número de productos disponibles.
     * @return Número de productos.
     */
    public int getNumProductes() {
        return productesDisponibles.size();
    }

    /**
     * Establece las matrices de relaciones entre productos.
     * @param unsorted Matriz de relaciones no ordenada.
     * @param sorted Matriz de relaciones ordenada.
     */
    public void setMatrixes(ArrayList<ArrayList<Integer>> unsorted, ArrayList<ArrayList<Pair<Integer,Integer>>> sorted) {
      productRelations = new ArrayList<>(unsorted);
      productRelationsSorted = new ArrayList<>(sorted);
    }
    
    /**
     * Modifica la relación entre dos productos.
     * @param producte1 Nombre del primer producto.
     * @param producte2 Nombre del segundo producto.
     * @param novaRelacio Nueva relación entre los productos.
     */
    public void modificarRelacioProducte(String producte1, String producte2, int novaRelacio) {
        if (!mapNomIdx.containsKey(producte1)) {
            throw new IllegalArgumentException("El producte1 no existeix.");
        }
        if (!mapNomIdx.containsKey(producte2)) {
            throw new IllegalArgumentException("El producte2 no existeix.");
        }
        int index1 = mapNomIdx.get(producte1);
        int index2 = mapNomIdx.get(producte2);
        productRelations.get(index1).set(index2, novaRelacio);
        productRelations.get(index2).set(index1, novaRelacio);
        costActual = CntrlDomini.getInstance().computeCost(prestatgeria, productRelations);
        recalcularProductRelationsSorted();
    }

    /**
     * Modifica el nombre de un producto.
     * @param nomAntic Nombre del producto a modificar.
     * @param nomNou Nuevo nombre del producto.
     */
    public void modificarNomProducte(String nomAntic, String nomNou) {
      if (!mapNomIdx.containsKey(nomAntic)) {
        throw new IllegalArgumentException("El producte no existeix.");
      }
      if (mapNomIdx.containsKey(nomNou)) {
        throw new IllegalArgumentException("Ja existeix un producte amb aquest nom.");
      }
      int index = mapNomIdx.get(nomAntic);
      mapNomIdx.remove(nomAntic);
      mapNomIdx.put(nomNou, index);
      productesDisponibles.get(index).setNom(nomNou);
    }

    /**
     * Da de alta un nuevo producto en el supermercado.
     * @param nom Nombre del producto.
     * @param descripcio Descripción del producto.
     * @param preu Precio del producto.
     * @param icon Icono del producto.
     * @param relacions Lista de relaciones del producto con otros productos.
     */
    public void altaProducte(String nom, String descripcio, float preu, String icon, ArrayList<Integer> relacions) {
      Integer numProd = getNumProductes();
      if (mapNomIdx.containsKey(nom)) {
        throw new IllegalArgumentException("El producte ja existeix.");
      }
      if (relacions.size() != productesDisponibles.size() && numProd > 1) {
        throw new IllegalArgumentException("La mida de la llista de relacions no és correcta.");
      }
      //matriu de distàncies no ordenada
      Producte producte = new Producte(nom, descripcio, preu, icon);
      mapNomIdx.put(nom, productesDisponibles.size());
      productesDisponibles.add(producte);
      productRelations.add(new ArrayList<>(relacions));
      
      for (int i = 0; i < relacions.size(); ++i) {
        productRelations.get(i).add(relacions.get(i));
      }
      
      productRelations.get(productRelations.size() - 1).add(-1);
      //matriu de distàncies ordenada
      //fila
      
      ArrayList<Pair<Integer,Integer>> productRelationsSortedAux = new ArrayList<>();
      for (int i = 0; i < relacions.size(); i++) {
        if (relacions.get(i) == -1) continue;
        productRelationsSortedAux.add(new Pair<>(relacions.get(i), i));
      }
      productRelationsSortedAux.sort((a, b) -> b.getFirst().compareTo(a.getFirst()));
      productRelationsSorted.add(productRelationsSortedAux);
      //columna
      for (int i = 0; i < relacions.size(); i++) {
        Pair<Integer, Integer> newPair = new Pair<>(relacions.get(i), productRelationsSorted.size() - 1);
        int j = 0;
            while (j < productRelationsSorted.get(i).size() && productRelationsSorted.get(i).get(j).getFirst() > newPair.getFirst()) {
              j++;
            }
            productRelationsSorted.get(i).add(j, newPair);
        }
        if (!prestatgeria.isEmpty() || productesDisponibles.size() == 1) {
            prestatgeria.add(productesDisponibles.size() - 1);
            costActual = CntrlDomini.getInstance().computeCost(prestatgeria, productRelations);
        }

    }

    /**
     * Modifica las relaciones de un producto con los demás productos.
     * @param nom Nombre del producto.
     * @param relacions Lista de relaciones del producto con otros productos (solo tiene que tener la relación con el resto de productos, no consigo mismo).
     */
    public void modificarRelacionsProducte(String nom, ArrayList<Integer> relacions) {
        if (!mapNomIdx.containsKey(nom)) {
            throw new IllegalArgumentException("El producte no existeix.");
        }
        if (relacions.contains(-1) && relacions.size() != productesDisponibles.size() || !relacions.contains(-1) && relacions.size() != productesDisponibles.size() - 1) {
            throw new IllegalArgumentException("La mida de la llista de relacions no és correcta." + "\n Tamaño de la lista: " + relacions.size() + "\n Tamaño de la lista de productos: " + productesDisponibles.size());
        }
        
        // Matriu de distàncies no ordenada //
        // Modificar fila
        int index = mapNomIdx.get(nom);
        if (!relacions.contains(-1)) relacions.add(index, -1);
        ArrayList<Integer> rel = new ArrayList<>(relacions); // Relacions a modificar amb el format correcte
        int j = 0; // Índex de "relacions"
        productRelations.set(index, rel);

        // Modificar columna
        for (int i = 0; i < rel.size(); i++) {
            productRelations.get(i).set(index, rel.get(i));
        }


        // Matriu de distàncies ordenada //
        // Modificar fila
        ArrayList<Pair<Integer,Integer>> productRelationsSortedAux = new ArrayList<>();
        for (int i = 0; i < rel.size(); i++) {
            if (rel.get(i) == -1) continue;
            productRelationsSortedAux.add(new Pair<>(rel.get(i), i));
        }
        productRelationsSortedAux.sort((a, b) -> b.getFirst().compareTo(a.getFirst()));
        productRelationsSorted.set(index, productRelationsSortedAux);
        // Modificar columna
        for (int i = 0; i < productRelationsSorted.size(); i++) {
            if (rel.get(i) == -1) continue;
            Pair<Integer, Integer> newPair = new Pair<>(rel.get(i), index);
            j = 0;
            while (j < productRelationsSorted.get(i).size() && productRelationsSorted.get(i).get(j).getFirst() > newPair.getFirst()) {
                j++;
            }
            productRelationsSorted.get(i).add(j, newPair);
        }
    }

    /**
     * Obtiene los nombres e iconos de los productos con los índices en "prods".
     * @param prods Lista de índices de productos.
     * @return Lista de pares con el nombre y el icono de los productos.
     */
    public ArrayList<Pair<String,String>> getProductNamesIconsIdxs(ArrayList<Integer> prods) {
      ArrayList<Pair<String,String>> ret = new ArrayList<>();
      for(Integer idx: prods) {
        Producte prod = productesDisponibles.get(idx);
        ret.add(new Pair<> (prod.getNom(), prod.getImatgePath()));
      }
      return ret;
    }

    private void recalcularProductRelationsSorted() {
      productRelationsSorted.clear();
      for (int i = 0; i < productRelations.size(); i++) {
          ArrayList<Pair<Integer,Integer>> productRelationsSortedAux = new ArrayList<>();
          for (int j = 0; j < productRelations.get(i).size(); j++) {
              if (productRelations.get(i).get(j) == -1) continue;
              productRelationsSortedAux.add(new Pair<>(productRelations.get(i).get(j), j));
          }
          productRelationsSortedAux.sort((a, b) -> b.getFirst().compareTo(a.getFirst()));
          productRelationsSorted.add(productRelationsSortedAux);
      }
    }

    /**
     * Obtiene los nombres e iconos de los productos disponibles.
     * @return Lista de pares con el nombre y el icono de los productos.
     */
    public ArrayList<Pair<String,String>> getProductNamesIconsDisposition() {
      return getProductNamesIconsIdxs(prestatgeria);
    }

    /**
     * Da de baja un producto del supermercado.
     * @param nomProducte Nombre del producto a dar de baja.
     */
    public void baixaProducte(String nomProducte) {
        if (!mapNomIdx.containsKey(nomProducte)) {
            throw new IllegalArgumentException("El producte no existeix.");
        }
        int index = mapNomIdx.get(nomProducte);
        productesDisponibles.remove(index);
        productRelations.remove(index);
        for (int i = 0; i < productRelations.size(); i++) {
            productRelations.get(i).remove(index);
        }
        mapNomIdx.remove(nomProducte);
        for (Map.Entry<String, Integer> entry : mapNomIdx.entrySet()) {
            if (entry.getValue() > index) {
                mapNomIdx.put(entry.getKey(), entry.getValue() - 1);
            }
        }
        recalcularProductRelationsSorted();
        if(!prestatgeria.isEmpty()){
          for (int i = 0; i < prestatgeria.size(); i++) {
            if (prestatgeria.get(i) > index) {
              prestatgeria.set(i, prestatgeria.get(i) - 1);
            }
          }
          prestatgeria.remove(Integer.valueOf(index));
          costActual = CntrlDomini.getInstance().computeCost(prestatgeria, productRelations);
        }
    }

    /**
     * Establece el icono del supermercado.
     * @param icona Ruta del icono del supermercado.
     */
    public void setIcona(String icona) {
        iconPath = icona;
    }

    /**
     * Establece la disposición de productos en una estantería.
     * @param disp Lista con la disposición de productos.
     */
    public void setDisposition(ArrayList<Integer> disp) {
        Set<Integer> expectedNumbers = new HashSet<>();
        for (int i = 0; i < productesDisponibles.size(); i++) {
            expectedNumbers.add(i);
        }

        // Crear un conjunto con los números de la solución
        Set<Integer> solutionNumbers = new HashSet<>(disp);

        // Verificar que ambos conjuntos sean iguales
        if (!expectedNumbers.equals(solutionNumbers)) {
            throw new IllegalArgumentException("La disposició no és correcta.");
        }
        prestatgeria = new ArrayList<>(disp);
    }

    /**
     * Establece la información de los productos.
     * @param infoProductes Lista de listas con la información de los productos (Nombre, descripción, precio, icono).
     */
    public void setInfoProductes(ArrayList<ArrayList<String>> infoProductes) {
      for(ArrayList<String> producte: infoProductes){
        //productes disponibles  i mapnomidx
        String nom = producte.get(0);
        String descripcio = producte.get(1);
        Float preu = Float.valueOf(producte.get(2));
        String productIconPath = producte.get(3);
        productesDisponibles.add(new Producte(nom, descripcio, preu, productIconPath));
        mapNomIdx.put(nom, productesDisponibles.size() - 1);
      }
    }

    /**
     * Añade o edita la información de un producto, dependiendo de si nomAntic es "" (se añade el producto) o no (se modifica el producto existente).
     * @param nomAntic Nombre del producto a editar. Si se añade, debe ser vacío.
     * @param nomNou Nuevo nombre del producto.
     * @param iconPath Ruta del icono del producto.
     * @param preu Precio del producto.
     * @param descripcio Descripción del producto.
     * @param relacions Lista de relaciones del producto con otros productos.
     */
    public void addEditInfoProducte(String nomAntic, String nomNou, String iconPath, float preu, String descripcio, ArrayList<Integer> relacions) {
      if (mapNomIdx.containsKey(nomNou) && !nomAntic.equals(nomNou)) {
        throw new IllegalArgumentException("Ja existeix un producte amb aquest nom.");
      }
      // Es vol afegir un nou producte
      if (nomAntic.equals("")) {       
        altaProducte(nomNou, descripcio, preu, iconPath, relacions);
        return;
      }
      
        if (!mapNomIdx.containsKey(nomAntic)) {
            throw new IllegalArgumentException("El producte no existeix.");
        }
        // Editar un producte existent
        int index = mapNomIdx.get(nomAntic);
        productesDisponibles.get(index).setNom(nomNou);
        productesDisponibles.get(index).setDescripcio(descripcio);
        productesDisponibles.get(index).setPreu(preu);
        productesDisponibles.get(index).setImatgePath(iconPath);
        // Modificar relaciones
        mapNomIdx.remove(nomAntic);
        mapNomIdx.put(nomNou, index);
        modificarRelacionsProducte(nomNou, relacions);
    }

    /**
     * Obtiene la disposición de productos en una estantería.
     * @return Lista con la disposición de productos.
     */
    public ArrayList<Integer> getDisposition() {
        return new ArrayList<>(prestatgeria);
    }

    /**
     * Consulta la información de un producto.
     * @param producte Nombre del producto.
     * @return Lista con la información del producto.
     */
    public ArrayList<String> consultarProducte(String producte) {
        if (!mapNomIdx.containsKey(producte)) {
            throw new IllegalArgumentException("El producte no existeix.");
        }

        ArrayList<String> info = new ArrayList<>();
            
        info.add("\"" + productesDisponibles.get(mapNomIdx.get(producte)).getNom() + "\"");
        info.add("\"" + productesDisponibles.get(mapNomIdx.get(producte)).getDescripcio() + "\"");
        info.add(String.valueOf(productesDisponibles.get(mapNomIdx.get(producte)).getPreu()));
        info.add("\"" + productesDisponibles.get(mapNomIdx.get(producte)).getImatgePath() + "\"");

        return info;
    }

    /**
     * Obtiene la información de todos los productos disponibles.
     * @return Lista de listas con la información de los productos.
     */
    public ArrayList<ArrayList<String>> getInfoProductes() {
        ArrayList<ArrayList<String>> infoProductes = new ArrayList<>();
        for (int i = 0; i < productesDisponibles.size(); i++) {
            ArrayList<String> info = new ArrayList<>();
            
            info.add("\"" + productesDisponibles.get(i).getNom() + "\"");
            info.add("\"" + productesDisponibles.get(i).getDescripcio() + "\"");
            info.add(String.valueOf(productesDisponibles.get(i).getPreu()));
            info.add("\"" + productesDisponibles.get(i).getImatgePath() + "\"");

            infoProductes.add(info);
        }
        return infoProductes;
    }
}
