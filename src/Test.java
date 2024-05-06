import java.io.*;

public class Test {
    public static final String FICHIER_AEROPORTS = "./data/aeroports.txt";
    public static final String FICHIER_VOLS = "./data/vol-test9.csv";
    private static int k_max = 2;
    public static void main(String[] args) {
        ListeAeroports listeAero = new ListeAeroports();
        ListeVols listeVols = new ListeVols();
        try {
            listeAero.remplirListe(FICHIER_AEROPORTS);
            listeVols.remplirListe(FICHIER_VOLS, listeAero);
            
            Aeroport A1 = listeAero.getAeroport("MRS");
            Aeroport A2 = listeAero.getAeroport("BES");
            Aeroport A3 = listeAero.getAeroport("LYS");
            Aeroport A4 = listeAero.getAeroport("BOD");

            Vol V1 = new Vol("AF000090",A1,A2,7,33,81);
            Vol V2 = new Vol("AF000132",A3,A4,7,34,47);

            Collision.enCollision(V1, V2);
            listeVols.afficher();

        } catch (FileNotFoundException erreur) {
            System.err.println("    > ERREUR : Impossible de traiter ce fichier !");
        } catch (ErreurFormatLigne erreur) {
            System.err.println("    > ERREUR : "+erreur);
        }
    }
}
