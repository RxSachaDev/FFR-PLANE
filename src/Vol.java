public class Vol {
    private static int i = 0; //Faire un compteur pour attribuer un numéro de vol à chaque vol
    private int numeroVol;
    
    private String nom;
    private Aeroport aero_depart;
    private Aeroport aero_arrivee;
    private int h_depart;
    private int min_depart;
    private int duree;

    private double distanceVol; //Calculée une fois au lieu de la calculer à chaque comparaison entre 2 vols
    
    //Instanciation
    Vol(String nom, Aeroport aero_depart, Aeroport aero_arrivee, int h_depart, int min_depart, int duree){
        this.nom = nom;
        this.aero_depart = aero_depart;
        this.aero_arrivee = aero_arrivee;
        this.h_depart = h_depart;
        this.min_depart = min_depart;
        this.duree = duree;
        distanceVol = ToolBox.distance(aero_depart.getCoordonnees(), aero_arrivee.getCoordonnees());
        i++;
        numeroVol = i;
    }

    //méthodes
    public String toString() {
        return ("| "+numeroVol+" | "+nom+" | "+aero_depart.getCode()+" | "+aero_arrivee.getCode()+" | "+h_depart+" | "+min_depart+" | "+duree+" |");
    }

    //accesseurs
    public String getNom() {
        return nom;
    }

    public Aeroport getAero_depart() {
        return aero_depart;
    }

    public Aeroport getAero_arrivee() {
        return aero_arrivee;
    }

    public int getH_depart() {
        return h_depart;
    }

    public int getMin_depart() {
        return min_depart;
    }

    public int getDuree() {
        return duree;
    }

    public double getDistanceVol() {
        return distanceVol;
    }

    //mutateurs
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAero_depart(Aeroport aero_depart) {
        this.aero_depart = aero_depart;
    }

    public void setAero_arrivee(Aeroport aero_arrivee) {
        this.aero_arrivee = aero_arrivee;
    }

    public void setH_depart(int h_depart) {
        this.h_depart = h_depart;
    }

    public void setMin_depart(int min_depart) {
        this.min_depart = min_depart;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setDistanceVol(double distanceVol) {
        this.distanceVol = distanceVol;
    }
}
