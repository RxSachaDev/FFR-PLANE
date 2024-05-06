public class ErreurFormatLigne extends NumberFormatException {
    private int ligne;
    private String fichier;

    ErreurFormatLigne (int ligneFichier, String nomFichier){
        ligne = ligneFichier;
        fichier = nomFichier;
    }

    @Override
    public String toString() {
        return ("Non conformité de la ligne "+ligne+" du fichier '"+fichier+"' !");
    }
}
