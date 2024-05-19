import java.io.*;
import java.util.List;

public class Test {
    public static final String FICHIER_AEROPORTS = "./data/aeroports.txt";
    public static final String FICHIER_VOLS = "./data/vol-test4.csv";
    private static int k_max = 2;
    public static void main(String[] args) {
        ListeAeroports listeAero = new ListeAeroports();
        ListeVols listeVols = new ListeVols();
        try {
            listeAero.remplirListe(FICHIER_AEROPORTS);
            
            
            double[] tab1 = {(-2),(-2)};
            Aeroport A1 = new Aeroport("A1", tab1);

            double[] tab2 = {(3),(3)};
            Aeroport A2 = new Aeroport("A2", tab2);

            double[] tab3 = {(1),(1)};
            Aeroport A3 = new Aeroport("A3", tab3);

            double[] tab4 = {(3),(-1)};
            Aeroport A4 = new Aeroport("A3", tab4);

            Vol V3 = new Vol("V3",A1,A2,7,0,50);
            Vol V4 = new Vol("V4",A3,A4,7,45,10);

            System.out.println(Collision.hasCollision(V3,V4));

        } catch (FileNotFoundException erreur) {
            System.err.println("    > ERREUR : Impossible de traiter ce fichier !");
        } catch (ErreurFormatLigne erreur) {
            System.err.println("    > ERREUR : "+erreur);
        }
/*
        List<Vol> listeVolsA = listeVols.getListeVols();
        for(int ii = 1 ; ii<10 ; ii++){
            String temp = "./data/vol-test"+ii+".csv";
            try {
                listeVols.remplirListe(temp, listeAero);
            } catch (Exception e) {
                // TODO: handle exception
            }
            int count = 0;
            listeVolsA = listeVols.getListeVols();
            for(int i = 0 ; i<listeVolsA.size()-1 ; i++) {
                for(int j = i+1 ; j<listeVolsA.size() ; j++){
                    if(Collision.hasCollision(listeVolsA.get(i), listeVolsA.get(j))) count++;
                }
            }
            System.out.println(count);
            listeVols.viderListe();
        }
    */
    }
    
}
