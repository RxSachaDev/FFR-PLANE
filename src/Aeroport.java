public class Aeroport {
    private String code;
    private String lieu;

    private int lat_degre;
    private int lat_min;
    private int lat_sec;
    private char lat_orientation;

    private int long_degre;
    private int long_min;
    private int long_sec;
    private char long_orientation;

    private double[] Coordonnees; //tab[0] = x et tab[1] = y
    
    //Instanciation
    Aeroport(String code, String lieu, int lat_degre, int lat_min, int lat_sec, char lat_orientation, 
            int long_degre, int long_min, int long_sec, char long_orientation){
        this.code = code;
        this.lieu = lieu;

        this.lat_degre = lat_degre;
        this.lat_min = lat_min;
        this.lat_sec = lat_sec;
        this.lat_orientation = lat_orientation;

        this.long_degre = long_degre;
        this.long_min = long_min;
        this.long_sec = long_sec;
        this.long_orientation = long_orientation;

        Coordonnees = calCooCartesiennes();
    }

    Aeroport(String code,double[] Coordonnees){
        this.code = code;
        this.Coordonnees = Coordonnees;
    }

    //méthodes
    public double[] calCooCartesiennes() { 
        int R = 6371; //Rayon de la Terre (km) 
        int coef_lat = 1;
        int coef_long = 1;
        double[] tab = new double[2];
        if(lat_orientation == 'O' || lat_orientation == 'S')
            coef_lat = -1;
        if(long_orientation == 'O' || long_orientation == 'S')
            coef_long = -1;
        double latitude = Math.toRadians(coef_lat*(lat_degre+lat_min/60.0+lat_sec/3600.0));
        double longitude = Math.toRadians(coef_long*(long_degre+long_min/60.0+long_sec/3600.0));
        tab[0] = R*Math.cos(latitude)*Math.sin(longitude);
        tab[1] = R*Math.cos(latitude)*Math.cos(longitude);
        return tab;
    }

    public String toString() {
        return ("| "+code+" | "+lat_degre+" | "+lat_min+" | "+lat_sec+" | "+lat_orientation+" | "+long_degre+" | "+long_min+" | "+long_sec+" | "+long_orientation+" |");
    }

    //accesseurs
    public String getCode() {
        return code;
    }

    public String getLieu() {
        return lieu;
    }

    public double[] getCoordonnees() {
        return Coordonnees;
    }

    //mutateurs
    public void setCode(String code) {
        this.code = code;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setCoordonnees(double[] coordonnees) {
        Coordonnees = coordonnees;
    }
}