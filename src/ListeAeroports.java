import java.io.*;
import java.util.*;

public class ListeAeroports {
    /*Necessaire car pour calculer la distance drectement depuis la classe vol
     * On a besoin des aeroports donc transformer ListeAeroports en hashMap
     */
    /* Peut etre organisé en hashMap
     * clé = Code
     * Valeur = Aeroport
     */ 
    private Map<String,Aeroport> mapAeroports;

    //Instanciation
    ListeAeroports(){
        mapAeroports = new HashMap<>();
    }

    //méthodes
    public boolean ajAeroport(Aeroport aeroport){
        boolean bool = false;
        if (mapAeroports.get(aeroport.getCode()) == null) {
            mapAeroports.put(aeroport.getCode(), aeroport);
            bool = true;
        }
        return bool;
    }

    public boolean remplirListe(String cheminFichier) throws FileNotFoundException { 
        int i = 1;
        try { 
            FileReader fichier = new FileReader(cheminFichier);
            Scanner scanner = new Scanner(fichier);
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String[] Valeurs = ligne.split(";");
                this.ajAeroport(new Aeroport(Valeurs[0], Valeurs[1], 
                  Integer.parseInt(Valeurs[2]), Integer.parseInt(Valeurs[3]), Integer.parseInt(Valeurs[4]), Valeurs[5].charAt(0), 
                  Integer.parseInt(Valeurs[6]), Integer.parseInt(Valeurs[7]), Integer.parseInt(Valeurs[8]), Valeurs[9].charAt(0)));
                i++;
            }
            scanner.close();
        } catch (FileNotFoundException erreur) {
            throw new FileNotFoundException();
        } catch (NumberFormatException erreur) {
            throw new ErreurFormatLigne(i,cheminFichier);
        } catch (ArrayIndexOutOfBoundsException erreur){
            throw new ErreurFormatLigne(i,cheminFichier);
        }
        return true;
    }

    public Aeroport getAeroport(String key){
        return mapAeroports.get(key);
    }

    public void afficher(){
        int compt = 0;
        for (String key : mapAeroports.keySet()) {
            System.out.println(mapAeroports.get(key));
            compt++;
        }
        System.out.println("###### "+compt+" ######");
    }
}
