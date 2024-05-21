
import java.util.*;

import flights.Flight;

import java.io.*;

public class ListeVols {
    private List<Flight> ListeVols;
    
    //Instanciation
    ListeVols(){
        ListeVols = new ArrayList<Flight>();
    }

    //méthodes
    public boolean ajVol(Flight vol){
        boolean bool = false;
        if(vol!=null && !ListeVols.contains(vol)){
            bool = ListeVols.add(vol);
        }
        return bool;
    }

    public void afficher(){
        for(int i = 0 ; i<ListeVols.size() ; i++){
            System.out.println(ListeVols.get(i));
        }
    }

    public boolean remplirListe(String cheminFichier, ListeAeroports listeAeroports) throws FileNotFoundException { 
        try { 
            File fichier = new File(cheminFichier);
            Scanner scanner = new Scanner(fichier);
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String[] Valeurs = ligne.split(";");
                this.ajVol(new Flight(Valeurs[0], listeAeroports.getAeroport(Valeurs[1]),listeAeroports.getAeroport(Valeurs[2]),
                        Integer.parseInt(Valeurs[3]),Integer.parseInt(Valeurs[4]),Integer.parseInt(Valeurs[5])));
            }
            scanner.close();
        } catch (FileNotFoundException error) {
            throw new FileNotFoundException();
        }

        /* Pour la ListeAeroports :
         * hashmap => clé = code    ;   valeur = Aeroport (Object)
         * 
         * Pour remplir la ListeVols :
         * on parcours la ligne classiquement,
         * pour les vols, on a leur code dans les 2 premieres positions 
         * (soit on prend juste les code, soit on parcours ListeAeroports pour y associer les aeroports correspondants)
         */
        return true;
    }

    public void viderListe(){
        ListeVols.clear();
    }
    
    public List<Flight> getListeVols() {
        return ListeVols;
    }
}
