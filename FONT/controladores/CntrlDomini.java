package controladores;

import dades.Supermercat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import structures.Pair;

/**
 * La clase CntrlDades es un controlador singleton que administra la información de los productos.
 * Proporciona métodos para gestionar y consultar información relacionada con productos.
 */

public class CntrlDomini {
    /**
     * Instancia singleton de la clase CntrlDades.
     */
    private static final CntrlDomini cntrlDomini = new CntrlDomini();
    /**
     * Mapa de supermercados, con el nombre del supermercado como clave y el objeto Supermercat como valor.
     */
    private static HashMap<String,Supermercat> supermercats;
    /**
     * Supermercado actualmente seleccionado.
     */
    private static Supermercat actual;
    /**
     * Instancia de CntrlPersistencia.
     */
    private final CntrlPersistencia cntrlPersistencia = CntrlPersistencia.getInstance();
    /**
     * Instancia de CntrlAlgoritme.
     */
    private final CntrlAlgoritme cntrlAlgoritme = CntrlAlgoritme.getInstance();

    /**
     * Devuelve la instancia singleton de la clase CntrlDades.
     * @return la instancia singleton de CntrlDades
     */
    public static CntrlDomini getInstance() {
        return cntrlDomini;
    }

    /**
     * Constructor privado para la clase CntrlDomini.
     * Este constructor evita la instanciación de la clase desde el exterior,
     * asegurando que la clase solo se pueda usar en un contexto estático.
     */
    private CntrlDomini() {
      supermercats = new HashMap<>();
    }

    /**
     * Devuelve los nombres de los supermercados con sus iconos
     * @return los nombres de los supermercados con sus iconos
     */
    public ArrayList<Pair<String,String>> getNomIIconaSupermercats() {
        ArrayList<Pair<String,String>> sups = new ArrayList<>(); // ArrayList de <nom,icona>
        if (supermercats.isEmpty()) return sups;
        for (Supermercat s : supermercats.values()) {
            sups.add(new Pair<>(s.getNom(), s.getIcona()));            
        }
        return sups;
    }

    /**
     * Carga los supermercados desde el archivo de persistencia
     */
    public void loadSupermercats() {

      try {
          ArrayList<ArrayList<String>> loadedSupermercats = cntrlPersistencia.loadAllSupermercats();
          for (ArrayList<String> linies : loadedSupermercats) {
            interpretInfoLines(linies);
          }
      } catch (Exception e) {
        //de moment passo cap amunt
        throw new RuntimeException(e);
      }
    }

    /**
     * Establece el algoritmo actual a partir de su nombre
     * @param algorithmName El nombre del algoritmo
     */
    private void setCurrentAlgorism(String algorithmName) {
      if (algorithmName.toLowerCase().equals("bruteforce2")){
        cntrlAlgoritme.BruteForce2();
      }
      if (algorithmName.toLowerCase().equals("bruteforce")){
        cntrlAlgoritme.BruteForceGenerator();
      }
      if (algorithmName.toLowerCase().equals("greedy")){
        cntrlAlgoritme.GreedyGenerator();
      }
      if (algorithmName.toLowerCase().equals("tspapproximation")){
        cntrlAlgoritme.TSPApproximatorGenerator();
      }
    }

    /**
     * Calcula el coste de una disposición de productos en un supermercado
     * @param disp La disposición de los productos
     * @param relations Las relaciones entre los productos
     * @return el coste de la disposición
     */
    public int computeCost(ArrayList<Integer> disp, ArrayList<ArrayList<Integer>> relations) {
      return cntrlAlgoritme.computeCost(disp, relations);
    }

    /**
     * Calcula el coste de una disposición de productos en un supermercado
     * @param disp La disposición de los productos
     * @return el coste de la disposición
     */
    public int getCostThisDisp(ArrayList<Pair<String,String>> disp) {
      if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
      ArrayList<Integer> dispIdxs = actual.getDispFromNameIconList(disp);
      return computeCost(dispIdxs, actual.getProductRelations());
    }

    /**
     * Devuelve el icono de la solución actual generada por el algoritmo.
     * @param algorithmName El nombre del algoritmo
     * @return el icono de la solución actual
     */
    public ArrayList<Pair<String,String>> getNameIconSolution(String algorithmName) {
      setCurrentAlgorism(algorithmName);
      if(actual.getNumProductes() == 0) throw new RuntimeException("Supermercat sense productes");
      ArrayList<Integer> sol = cntrlAlgoritme.getSolution();
      return actual.getProductNamesIconsIdxs(sol);
    }

    /**
     * Devuelve el coste de la solución actual generada por el algoritmo.
     * @return el coste de la solución actual
     */
    public int getCostSolution() {
      return cntrlAlgoritme.getSolutionCost();
    }
    
    /**
     * Devuelve la solución actual generada por el algoritmo.
     * @param nameIconList La lista de nombres e iconos de los productos
     * @param cost El coste de la solución
     */
    public void saveCurrentDisposition(ArrayList<Pair<String,String>> nameIconList, int cost) {
      if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
      ArrayList<Integer> disposition = actual.getDispFromNameIconList(nameIconList);
      actual.setDisposition(disposition);
      actual.setCost(cost);
    }

    /**
     * Devuelve la disposición actual de la prestatgeria
     * @param linies La lista de nombres e iconos de los productos
     */
    private void interpretInfoLines(ArrayList<String> linies) {
      //nom icona i n
      String nom = linies.get(0);
      String iconPath = linies.get(1);
      Integer n = Integer.valueOf(linies.get(2));

      //productes
      // Patró per extreure elements entre cometes o valors separats per espais
      Pattern pattern = Pattern.compile("\"([^\"]*)\"|(\\S+)");
      ArrayList<ArrayList<String>> infoProductes = new ArrayList<>();
      for(int i = 3; i < 3+n; ++i) {
        Matcher matcher = pattern.matcher(linies.get(i));
        // Llista per emmagatzemar els resultats
        ArrayList<String> elements = new ArrayList<>();
  
        while (matcher.find()) {
          if (matcher.group(1) != null) {
              // Afegir elements entre cometes
              elements.add(matcher.group(1));
          } else if (matcher.group(2) != null) {
              // Afegir elements sense cometes
              elements.add(matcher.group(2));
          }
        }
        infoProductes.add(elements);
      }

      //relacions
      ArrayList<ArrayList<Integer>> relationMatrix = new ArrayList<>();
      for (int i = n+3; i < n+n+3; ++i) {
        String row = linies.get(i);
        String[] relations = row.split(" ");
        ArrayList<Integer> rowRel = new ArrayList<>();
        for (String rel : relations) {
          rowRel.add(Integer.valueOf(rel));
        } 
        relationMatrix.add(rowRel);
      }

      
      //relacions ordenades
      ArrayList<ArrayList<Pair<Integer,Integer>>> relationMatrixSorted = new ArrayList<>();
      if (n == 1) {
        relationMatrixSorted.add(new ArrayList<>());
      }
      else {
        for (int i = 2*n+3; i < 3*n+3; ++i) {
          String row = linies.get(i);
          String[] relations = row.split(",");
          ArrayList<Pair<Integer,Integer>> rowRel = new ArrayList<>();
          for (String rel : relations) {
            String[] pair = rel.split(" ");
            Pair<Integer,Integer> relation = new Pair<> (Integer.valueOf(pair[0]), Integer.valueOf(pair[1]));
            rowRel.add(relation);
          }
          relationMatrixSorted.add(rowRel);
        }
      }


      //prestatgeria
      ArrayList<Integer> disposition = new ArrayList<>();
      if (!linies.get(3*n+3).equals("")){
        String[] dispProd = linies.get(3*n+3).split(" ");
        for(String prodIdx: dispProd) {
          disposition.add(Integer.valueOf(prodIdx));
        }
      }


      int costPrestatgeria = -1;
      if (!linies.get(3*n+4).equals("")){
        costPrestatgeria = Integer.valueOf(linies.get(3*n+4));
      }


      //ara ja tenim tota la info
      if (supermercats.containsKey(nom)) {
        actual = supermercats.get(nom);
        //esborrar tota la info prèvia
        actual.removeInfoProductes();
      }
      else{
        actual = supermercats.put(nom, new Supermercat(nom));
        actual = supermercats.get(nom); //per assegurar
      }
      actual.setCost(costPrestatgeria);
      actual.setIconPath(iconPath);
      actual.setInfoProductes(infoProductes);
      actual.setMatrixes(relationMatrix, relationMatrixSorted);
      if (!disposition.isEmpty()) actual.setDisposition(disposition);
    }

    /**
     * Carga un supermercado desde un archivo de persistencia
     * @param path La ruta del archivo de persistencia
     */
    public void loadSupermercat(String path) {
      try {
        ArrayList<String> linies = cntrlPersistencia.loadSupermercat(path);
        interpretInfoLines(linies);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    /**
     * Devuelve los nombres y los iconos de los productos en la prestatgeria
     * @return una lista con los nombres y los iconos de los productos en la prestatgeria
     */
    public ArrayList<Pair<String,String>> getProductNamesIconsDisposition() {
      if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
      return actual.getProductNamesIconsDisposition();
    }

    /**
     * Extrae la información de un supermercado
     * @return la información del supermercado
     */
    private ArrayList<String> extractInfoSupermercat() {
      //carreguem tota la info
      String nom = actual.getNom();
      String iconPath = actual.getIcona();
      ArrayList<ArrayList<String>> infoProducts = actual.getInfoProductes();
      ArrayList<ArrayList<Integer>> productRelations = actual.getProductRelations();
      ArrayList<ArrayList<Pair<Integer,Integer>>> productRelationsSorted = actual.getProductRelationsSorted();
      ArrayList<Integer> prestatgeria = actual.getDisposition();
      int costPrestatgeria = actual.getCost();

      //introduim la info
      ArrayList<String> saveObject = new ArrayList<>();
      
      //nom, icona, n
      saveObject.add(nom);
      saveObject.add(iconPath);
      saveObject.add(String.valueOf(infoProducts.size()));
      //llista productes
      for (ArrayList<String> productInfo : infoProducts) {
        String str = productInfo.get(0);
        for (int i = 1; i < productInfo.size(); ++i) {
          str += " " + productInfo.get(i);
        }
        saveObject.add(str);
      }
      //relacions
      for (ArrayList<Integer> row : productRelations) {
        String relationString = String.valueOf(row.get(0));
        for (int i = 1; i < row.size(); ++i) {
          relationString += ' ' + String.valueOf(row.get(i));
        }
        saveObject.add(relationString);
      }
      //relacions ordenades (parells separats per caracter '|')
      for (ArrayList<Pair<Integer,Integer>> row : productRelationsSorted) {
        if (row.size() == 0) {
          saveObject.add("-1");
          break;
        }
        Pair<Integer,Integer> relation = row.get(0);
        String relationString = String.valueOf(relation.getFirst()) + ' ' + String.valueOf(relation.getSecond());
        for (int i = 1; i < row.size(); ++i) {
          relation = row.get(i);
          relationString += ',' + String.valueOf(relation.getFirst()) + ' ' + String.valueOf(relation.getSecond());
        }
        saveObject.add(relationString);
      }
      //prestatgeria
      String prestatgeriaString = "";
      for (int i = 0; i < prestatgeria.size(); ++i) {
        if (i > 0) prestatgeriaString += ' ';
        prestatgeriaString += String.valueOf(prestatgeria.get(i));
      }
      saveObject.add(prestatgeriaString);
      saveObject.add(String.valueOf(costPrestatgeria));
      return saveObject;
    }

    /**
     * Guarda el supermercado actual en el archivo de persistencia
     */
    public void saveSupermercat() {
      if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
      ArrayList<String> saveObject = extractInfoSupermercat(); 
      try {
        cntrlPersistencia.saveSupermercat(saveObject);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    }

    /**
     * Consulta la relación entre dos productos
     * @param producte1 El nombre del producto1
     * @param producte2 El nombre del producto2
     * @return La relación entre los dos productos
     * @throws IllegalStateException si no hay supermercado seleccionado
     */
    public int consultarRelacioProducte(String producte1, String producte2) {
      if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
      return actual.consultarRelacioProducte(producte1, producte2);
    }

    /**
     * Si no hay un supermercado con el nombre indicado, lo crea.
     * Si ya existe, lo selecciona.
     * @param nomSuper El nombre del supermercado
     * se guarda el supermercado seleccionado en dades
     */
    public void createSupermercat(String nomSuper) {
      if (supermercats.containsKey(nomSuper)) throw new IllegalArgumentException("Ya existe un supermercado con ese nombre");
      supermercats.put(nomSuper, new Supermercat(nomSuper));
      actual = supermercats.get(nomSuper);
    }
    /**
     * Carga las matrices de distancias en los algoritmos.
     * @throws IllegalStateException si no hay supermercado seleccionado.
     */
    public void chargeMatrixesIntoAlgorithms() {
      if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
      if (actual.getNumProductes() == 0) return;
      cntrlAlgoritme.BruteForceGenerator();
      cntrlAlgoritme.setProductRelations(actual.getProductRelations());
      cntrlAlgoritme.BruteForce2();
      cntrlAlgoritme.setProductRelations(actual.getProductRelations());
      cntrlAlgoritme.GreedyGenerator();
      cntrlAlgoritme.setProductRelations(actual.getProductRelations());
      cntrlAlgoritme.setProductRelationsSorted(actual.getProductRelationsSorted());
      cntrlAlgoritme.TSPApproximatorGenerator();
      cntrlAlgoritme.setProductRelations(actual.getProductRelations());
    }

    /**
     * Consulta la descripción, nombre y precio de un producto
     * @return La descripción del producto
     */
    public int getCostDisposition() {
      if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
      return actual.getCost();
    }

    /**
     * Establece el supermercado actual a partir de su nombre
     *
     * @param nomSuper el nombre del supermercado
     * @throws IllegalArgumentException si no existe un supermercado con el nombre indicado
     */
    public void setSupermercat(String nomSuper) {
        if (!supermercats.containsKey(nomSuper)) throw new IllegalArgumentException("No existeix un supermercat amb aquest nom");
        actual = supermercats.get(nomSuper);
        //carreguem totes les matrius de distàncies als algorismes
        chargeMatrixesIntoAlgorithms();
    }
    
    /**
     * Añade un producto disponible en el supermercado
     * @param nom El nombre del producto
     * @param descripcio La descripción del producto
     * @param preu El precio del producto
     * @param icon El icono del producto
     * @param relacions Las relaciones del producto
     * @throws IllegalStateException si no hay supermercado seleccionado
     */
    public void altaProducte(String nom, String descripcio, float preu, String icon, ArrayList<Integer> relacions) {
        if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
        actual.altaProducte(nom, descripcio, preu, icon, relacions);
    }

    /**
     * Elimina un supermercado de la lista de supermercados
     * @param nom del supermercado
     */
    public void eliminarSupermercat(String nom) {
        if (supermercats.containsKey(nom)) {
          supermercats.remove(nom);
          try {
            cntrlPersistencia.deleteSupermercat(nom);
          } catch (IOException i) {
          }
        }
    }

    /**
     * Devuelve información sobre los productos
     * @throws IllegalStateException si no hay supermercado seleccionado
     * @return una lista con los nombres de los productos disponibles en el supermercado
     */
    public ArrayList<String> getNomProductes() {
      if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
      return actual.getNomProductes();
    }

    /**
     * Devuelve información sobre los productos
     * @throws IllegalStateException si no hay supermercado seleccionado
     * @return una lista con la información de los productos disponibles en el supermercado
     */
    public ArrayList<ArrayList<String>> getInfoProductes() {
        if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
        return actual.getInfoProductes();
    }

    /**
     * Devuelve información sobre las relaciones entre los productos
     * @throws IllegalStateException si no hay supermercado seleccionado
     * @return una lista con las relaciones entre los productos disponibles en el supermercado
     */
    public ArrayList<ArrayList<Integer>> getProductRelations() {
        if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
        return actual.getProductRelations();
    }

    /**
     * Devuelve las relaciones con el resto de productos, dado el nombre de un producto
     * @param nomProd El nombre del producto
     * @throws IllegalStateException si no hay supermercado seleccionado
     * @return una lista con las relaciones del producto con el resto de productos disponibles en el supermercado
     */
    public ArrayList<Integer> getRelacionsProducte(String nomProd) {
        if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
        return actual.getRelacionsProducte(nomProd);
    }

    /**
     * Devuelve el nombre de un producto a partir de su índice
     * @param idx El índice del producto
     * @throws IllegalStateException si no hay supermercado seleccionado
     * @return El nombre del producto
     */
    public String getNomProducteEnSupermercat(int idx) {
        if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
        return actual.getNomProducte(idx);
    }
    
    /**
     * Devuelve información sobre las relaciones entre los productos ordenadas
     * @throws IllegalStateException si no hay supermercado seleccionado
     * @return una lista con las relaciones (ordenadas) entre los productos disponibles en el supermercado
     */
    public ArrayList<ArrayList<Pair<Integer, Integer>>> getProductRelationsSorted() {
        if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
        return actual.getProductRelationsSorted();
    }

    /** 
     * Guarda todos los supermercados en el archivo de persistencia
     */
    public void saveAllSupermercat() {
      try {
          cntrlPersistencia.deleteAllSupermercat();
      } catch (Exception e) {
      }
      for (Supermercat sup : supermercats.values()) {
        actual = sup;
        try {
          cntrlPersistencia.saveSupermercat(extractInfoSupermercat());
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    }

    /**
     * Dispone los productos en la prestatgeria, segun el orden de la lista
     * 
     * @param disposicio una lista con el orden de los productos
     * @throws IllegalStateException si no hay supermercado seleccionado
     */
    public void setDisposition(int idx, ArrayList<Integer> disposicio) {
        if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
        actual.setDisposition(disposicio);
    }

    /**
     * Consulta el nombre de un producto a partir de su índice
     * @param idx El índice del producto
     * @throws IllegalStateException si no hay supermercado seleccionado
     * @return El nombre del producto
     */
    public String consultarNomProducteAmbIdx(int idx) {
        if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
        return actual.consultarNomProducteAmbIdx(idx);
    }
    
    /**
     * Devuelve la disposición actual de la prestatgeria
     * @param idx El índice de la prestatgeria
     * @throws IllegalStateException si no hay supermercado seleccionado
     * @return una lista con la disposición de los productos en la prestatgeria
     */
    public ArrayList<Integer> getDisposition(int idx) {
        if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
        return actual.getDisposition();
    }

    /**
     * Cambia el nombre de un supermercado por uno nuevo.
     * 
     * @param nomAntic El nombre del supermercado a cambiar.
     * @param nomNou El nuevo nombre del supermercado.
     * @throws IllegalArgumentException si ya existe un supermercado con el nombre nuevo.
     */
    public void swapInfoSupermercats(String nomAntic, String nomNou) {
        if (supermercats.containsKey(nomNou)) throw new IllegalArgumentException("Ya existe un supermercado con ese nombre");
        Supermercat antic = supermercats.get(nomAntic);
        supermercats.remove(nomAntic);
        antic.cambiarNom(nomNou);
        supermercats.put(nomNou, antic);
    }

    /**
     * Establece el icono de un supermercado
     *
     * @param iconPath la ruta del icono del supermercado
     */
    public void setSupermercatIcon(String iconPath) {
      actual.setIconPath(iconPath);
    }

    /**
     * Edita o añade información de un producto.
     * 
     * @param nomAntic El nombre del producto a editar. Es "" si se añade un producto.
     * @param nomNou El nuevo nombre del producto.
     * @param icon El icono del producto.
     * @param preu El precio del producto.
     * @param descripcio La descripción del producto.
     * @param relacions Las relaciones del producto con el resto de productos.
     */
    public void addEditInfoProducte(String nomAntic, String nomNou, String icon, float preu, String descripcio, ArrayList<Integer> relacions) {
      if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
      actual.addEditInfoProducte(nomAntic, nomNou, icon, preu, descripcio, relacions);
      chargeMatrixesIntoAlgorithms();
    }

    /**
     * Da de baja un producto de la lista de productos
     * @param nomProducte El nombre del producto a dar de baja
     * @throws IllegalStateException si no hay supermercado seleccionado
     */
    public void baixaProducte(String nomProducte) {
        if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
        actual.baixaProducte(nomProducte);
        chargeMatrixesIntoAlgorithms();
    }

    /**
     * Modifica la relación entre dos productos
     * @param producte1 El nombre del producto1
     * @param producte2 El nombre del producto2
     * @param relacio La relación entre los dos productos
     * @throws IllegalStateException si no hay supermercado seleccionado
     */
    public void modificarRelacioProducte(String producte1, String producte2, int relacio) {
        if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
        actual.modificarRelacioProducte(producte1, producte2, relacio);
    }

    /**
     * Modifica las relaciones de un producto
     * @param producte El nombre del producto
     * @param relacions Las nuevas relaciones del producto (sin añadir las del producto consigo mismo)
     * @throws IllegalStateException si no hay supermercado seleccionado
     */
    public void modificarRelacionsProducte(String producte, ArrayList<Integer> relacions) {
      if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
      actual.modificarRelacionsProducte(producte, relacions);
    }

    /**
     * Devuelve el número de productos disponibles en el supermercado
     * @throws IllegalStateException si no hay supermercado seleccionado
     * @return el número de productos disponibles
     */
    public int getNumProductes() {
        if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
        return actual.getNumProductes();
    }

    /**
     * Consulta la descripción, nombre y precio de un producto
     * @param producte El nombre del producto
     * @throws IllegalStateException si no hay supermercado seleccionado
     * @return La descripción del producto
     */
    public ArrayList<String> consultarProducte(String producte) {
        if (actual == null) throw new IllegalStateException("No hay supermercado seleccionado");
        return actual.consultarProducte(producte);
    }
}