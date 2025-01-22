package controladores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import structures.Paths;


/**
 * La clase CntrlPersistencia se encarga de gestionar la persistencia de datos
 * relacionados con supermercados. Proporciona métodos para guardar y cargar
 * información de supermercados en archivos, así como para gestionar datos
 * generales.
 */
/**
 * Controlador encarregat de gestionar la persistència de dades dels supermercats.
 */
public class CntrlPersistencia {
    /**
     * Instancia única de la clase CntrlPersistencia.
     */
    private static final CntrlPersistencia cntrlPersistencia = new CntrlPersistencia();

    /**
     * Obtiene la instancia única de la clase CntrlPersistencia.
     */
    private CntrlPersistencia() {
    }

    /**
     * Retorna la instància única del controlador de persistència.
     *
     * @return instància de CntrlPersistencia.
     */
    public static CntrlPersistencia getInstance() {
        return cntrlPersistencia;
    }

    /**
     * Desa un supermercat en el fitxer de dades.
     *
     * @param linies Llista de línies a desar. La primera línia conté el nom del supermercat.
     * @throws IOException Si ocorre un error durant l'escriptura del fitxer.
     * @throws IllegalArgumentException Si la llista de línies és buida o nul·la.
     */
    public void saveSupermercat(ArrayList<String> linies) throws IOException {
        if (linies == null || linies.isEmpty()) {
            throw new IllegalArgumentException("La llista de línies no pot estar buida.");
        }

        try (FileWriter fw = new FileWriter(Paths.SUPERMERCATS + linies.get(0) + ".txt")) {
            for (String linia : linies) {
                fw.write(linia + "\n");
            }
        }
    }

    /**
     * Carrega les dades d'un supermercat des d'un fitxer.
     *
     * @param path path del fitxer.
     * @return Llista de línies amb les dades del supermercat.
     * @throws IOException Si ocorre un error durant la lectura del fitxer.
     * @throws FileNotFoundException Si el fitxer del supermercat no existeix.
     */
    public ArrayList<String> loadSupermercat(String path) throws IOException {
        ArrayList<String> linies = new ArrayList<>();
        File file = new File(path);

        //llegir les linies        
        try (FileReader fr = new FileReader(file); Scanner scan = new Scanner(fr)) {
          while (scan.hasNextLine()) {
            linies.add(scan.nextLine());
          }
        }
        
        //guardar el fitxer a la carpeta de data, en cas que no ho estigui hehe
        if (!path.equals(Paths.SUPERMERCATS + linies.get(0) + ".txt")){
          File destFile = new File(Paths.SUPERMERCATS + linies.get(0) + ".txt");
          try (FileReader fr = new FileReader(file);
            FileWriter fw = new FileWriter(destFile)) {
              int c;
              while ((c = fr.read()) != -1) {
               fw.write(c);
              }
          }
        }

        return linies;
    }

    /**
     * Carrega les dades de tots els supermercats de la carpeta de persistència.
     *
     * @return Llista de llistes amb les dades de cada supermercat.
     * @throws IOException Si ocorre un error durant la lectura dels fitxers.
     */
    public ArrayList<ArrayList<String>> loadAllSupermercats() throws IOException {
        ArrayList<ArrayList<String>> supermercats = new ArrayList<>();
        File folder = new File(Paths.SUPERMERCATS);

        if (!folder.exists() || !folder.isDirectory()) {
            throw new IOException("La carpeta de supermercats no existeix o no és vàlida.");
        }

        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    supermercats.add(loadSupermercat(Paths.SUPERMERCATS + file.getName()));
                }
            }
        }
        return supermercats;
    }
    
    /**
     * Elimina un supermercat de la persistència.
     *
     * @param nomSupermercat Nom del supermercat a eliminar (sense extensió).
     * @throws IOException Si ocorre un error durant l'eliminació del fitxer.
     * @throws FileNotFoundException Si el fitxer del supermercat no existeix.
     */
    public void deleteSupermercat(String nomSupermercat) throws IOException {
        File file = new File(Paths.SUPERMERCATS + nomSupermercat + ".txt");
        if (!file.exists()) {
            throw new FileNotFoundException("No s'ha trobat el supermercat: " + nomSupermercat);
        }

        if (!file.delete()) {
            throw new IOException("No es va poder eliminar el fitxer: " + file.getAbsolutePath());
        }
    }

    /**
     * Elimina todos los supermercados de la persistencia.
     * @throws IOException Si ocurre un error durante la eliminación de los archivos.
     */
    public void deleteAllSupermercat() throws IOException { 
      File folder = new File(Paths.SUPERMERCATS);

      if (!folder.exists() || !folder.isDirectory()) {
          throw new IOException("La carpeta de supermercats no existeix o no és vàlida.");
      }
      File[] listOfFiles = folder.listFiles();
      if (listOfFiles != null) {
        for (File file : listOfFiles) {
          if (file.isFile()) {
              String fileName = file.getName();
              String nomSupermercat = fileName.substring(0, fileName.lastIndexOf('.'));
              deleteSupermercat(nomSupermercat);
          }
        }
      }
    }


}