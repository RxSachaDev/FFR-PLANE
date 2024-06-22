package sae.exceptions;

/**
 * La classe DataMismatchException représente une exception personnalisée lancée lorsqu'il y a une non-conformité entre les données de deux fichiers.
 * Étend NumberFormatException.
 * 
 * @author mathe
 */
public class DataMismatchException extends NumberFormatException {
    private final String fileName1;
    private final String fileName2;

    
    /**
     * Construit une nouvelle DataMismatchException avec les chemins de fichiers spécifiés.
     *
     * @param filePath1 le chemin complet du premier fichier concerné
     * @param filePath2 le chemin complet du deuxième fichier concerné
     */
    public DataMismatchException(String filePath1, String filePath2) {
        String[] temp = filePath1.split("\\\\");
        fileName1 = temp[temp.length-1];
        temp = filePath2.split("\\\\");
        fileName2 = temp[temp.length-1];
    }

    
    /**
     * Retourne une représentation en chaîne de caractères de l'exception DataMismatchException.
     *
     * @return une représentation en chaîne de caractères de l'exception DataMismatchException
     */
    @Override
    public String getMessage() {
        return String.format("Les données des fichiers %s et %s ne coïncident pas !", fileName2, fileName1);
    }
}