import java.io.*;
import java.util.List;

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
            
            // Aeroport A1 = listeAero.getAeroport("MRS");
            // Aeroport A2 = listeAero.getAeroport("BES");
            // Aeroport A3 = listeAero.getAeroport("LYS");
            // Aeroport A4 = listeAero.getAeroport("BOD");
            // Vol V1 = new Vol("AF000090",A1,A2,7,33,81);
            // Vol V2 = new Vol("AF000132",A3,A4,7,34,47);

            double[] tab1 = {(-1),(-3)};
            Aeroport A1 = new Aeroport("A1", tab1);

            double[] tab2 = {(2),(0)};
            Aeroport A2 = new Aeroport("A2", tab2);

            double[] tab3 = {(4),(2)};
            Aeroport A3 = new Aeroport("A3", tab3);

            double[] tab4 = {(1),(-1)};
            Aeroport A4 = new Aeroport("A3", tab4);

            // Vol V3 = new Vol("V3",A10,A20,7,33,81);
            // Vol V4 = new Vol("V4",A10,A30,7,34,50);

            // Vol V3 = new Vol("V3",A10,A20,6,30,45);
            // Vol V4 = new Vol("V4",A20,A10,7,30,10);

            Vol V3 = new Vol("V3",A1,A2,7,30,25);
            Vol V4 = new Vol("V4",A4,A3,8,0,10);

            // Collision.isInCollision(V1, V2);
            // System.out.println(Collision.isInCollision(V3, V4));

            // listeVols.afficher();

        } catch (FileNotFoundException erreur) {
            System.err.println("    > ERREUR : Impossible de traiter ce fichier !");
        } catch (ErreurFormatLigne erreur) {
            System.err.println("    > ERREUR : "+erreur);
        }

        int count = 0;
        List<Vol> listeVolsA = listeVols.getListeVols();
        for(int i = 0 ; i<listeVolsA.size()-1 ; i++) {
            for(int j = i+1 ; j<listeVolsA.size() ; j++){
                if(Collision.isInCollision(listeVolsA.get(i), listeVolsA.get(j))) count++;
            }
        }
        System.out.println(count);
    }
}
