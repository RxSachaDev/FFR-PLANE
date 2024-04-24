import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class test {
    private static Scanner ent = new Scanner(System.in);
    private static int kmax;
    private static int nbSommet;
    private static ArrayList<Integer> sommet = new ArrayList<>();
    private static ArrayList<Integer> sommetUnique = new ArrayList<>();
    private static Coloration fichier = new Coloration(null, kmax, nbSommet, sommet);

    public static void main(String[] args) {
       /*System.out.println("Entrer le nom du fichier : ");
        String name = ent.nextLine();*/
        fichier.setFichier("graph-test0.txt");
        try {
            fichier.lireFichier();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sommetUnique = fichier.sommetDistinct();
        for (int i = 0; i<sommetUnique.size(); i++){
            System.out.println(sommetUnique.get(i) + " : " + fichier.compterDegré(sommetUnique.get(i)));
        }
        
        System.out.println(fichier.WP());
    }
}
