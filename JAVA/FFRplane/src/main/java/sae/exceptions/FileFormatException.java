package sae.exceptions;

/**
 * La classe FileFormatError représente une exception personnalisée lancée lorsqu'une ligne dans un fichier ne respecte pas le format attendu.
 * 
 * @author mathe
 */
public class FileFormatException extends NumberFormatException {
    private int line;
    private String fileName;

    /**
     * Construit un nouveau FileFormatError avec le numéro de ligne et le nom du fichier spécifiés.
     *
     * @param lineInFile le numéro de ligne où l'erreur s'est produite
     * @param fileName le nom du fichier où l'erreur s'est produite
     */
    public FileFormatException(int lineInFile, String filePath) {
        this.line = lineInFile;
        String[] temp = filePath.split("\\\\");
        fileName = temp[temp.length-1];
    }


    /**
     * Retourne une représentation en chaîne de caractères de l'exception FileFormatError.
     *
     * @return une représentation en chaîne de caractères de l'exception FileFormatError
     */
    @Override
    public String getMessage() {
        return String.format("Non-conformité à la ligne %d du fichier %s !", line, fileName);
    }
}
