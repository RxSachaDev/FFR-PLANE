import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Coloration {
    private int kmax;
    private int nbSommet;
    private ArrayList<Integer> sommet = new ArrayList<>();
    private String fichier;

    Coloration() {
        fichier = null;
        kmax = 0;
        nbSommet = 0;
        sommet = new ArrayList<Integer>();
    }

    Coloration(String fichier, int kmax, int nbSommet, ArrayList<Integer> sommet){
        this.fichier = fichier;
        this.kmax = kmax;
        this.nbSommet = nbSommet;
        this.sommet = sommet;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public String getFichier() {
        return fichier;
    }

    public void lireFichier() throws IOException {
        int cpt =0;
        FileInputStream fileInputStream = new FileInputStream(this.fichier);
        Scanner scanner = new Scanner(fileInputStream);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                String[] elements = line.split("\\s+");

                for (int j = 0; j < elements.length; j++) {
                    if (cpt == 0){
                        kmax = Integer.parseInt(elements[j]);
                    }
                    else if (cpt == 1) {
                        nbSommet = Integer.parseInt(elements[j]);
                    }
                    else{
                        sommet.add(Integer.parseInt(elements[j]));
                    }
                }
                cpt++;
            }
        }

        // Fermer les flux
        scanner.close();
        fileInputStream.close();
        /*for (int k = 0; k < sommet.size(); k++) {
            System.out.println(sommet.get(k));
        } Affichage */
    }

    public ArrayList<Integer> sommetDistinct(){
        ArrayList<Integer> newTab = new ArrayList<>();
        for (int i = 0; i< sommet.size(); i++){
            if (!newTab.contains(sommet.get(i))){
                newTab.add(sommet.get(i));
            }
        }
        Collections.sort(newTab);
        return newTab;
    }

    public ArrayList<Integer> compterDegré(int x) {
        int cpt = 0;
        ArrayList<Integer> degré = new ArrayList<>();
        degré.add(x);
        for (int i = 0; i<sommet.size(); i++){
            if (sommet.get(i) == x)
                cpt++;
        }
        degré.add(cpt);
        return degré;
    }

    public ArrayList<Integer> precedent(int x){
        int i = 0;
        ArrayList<Integer> precedent = new ArrayList<>();
        while (i< sommet.size()){
            if (sommet.get(i) == x && i%2 == 1){
                precedent.add(sommet.get(i-1));
            }
            i++;
        }
        return precedent;
    }

    public ArrayList<ArrayList<Integer>> WP() {
        ArrayList<ArrayList<Integer>> degréTrier = new ArrayList<>();

        for (int i = 0; i < sommetDistinct().size(); i++) {
            ArrayList<Integer> degré = compterDegré(sommetDistinct().get(i));

            if (degréTrier.isEmpty()) {
                degréTrier.add(degré);
            } else {
                boolean inserted = false;

                for (int y = 0; y < degréTrier.size(); y++) {
                    if (degréTrier.get(y).get(1) < degré.get(1)) {
                        degréTrier.add(y, degré);
                        inserted = true;
                        break;
                    }
                }

                if (!inserted) {
                    degréTrier.add(degré);
                }
            }
        }
        return degréTrier;
    }
}
