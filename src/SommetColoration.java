public class SommetColoration {
    private Vol vol;
    private int degre;
    private int couleur;
    
    //Instanciation
    SommetColoration(Vol vol){
        this.vol = vol;
        this.degre = 0;
        this.couleur = 0;
    }

    //accesseurs
    public Vol getVol() {
        return vol;
    }

    public int getDegre() {
        return degre;
    }

    public int getCouleur() {
        return couleur;
    }

    //mutateurs
    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public void setDegre(int degre) {
        this.degre = degre;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }
}
