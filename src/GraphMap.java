/*
import java.util.*;

public class GraphMap<E,T> {
    class SommetPrinc {
        private int id;
        private T valeur;
        private int couleur = -1;
        
        public SommetPrinc(int id , T valeur){
            this.id = id;
            this.valeur = valeur;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public T getValeur() {
            return valeur;
        }

        public void setValeur(T valeur) {
            this.valeur = valeur;
        }

        public int getCouleur() {
            return couleur;
        }

        public void setCouleur(int couleur) {
            this.couleur = couleur;
        }

        public String toString() {
            return ("Id : "+id+" | Valeur : "+valeur.toString()+" | Couleur : "+couleur);
        }
    }

    class SommetAdj {
        private int id;
        private E valeur;

        public SommetAdj(int id, E valeur){
            this.id = id;
            this.valeur = valeur;
        }

        public int getId() {
            return id;
        }

        public E getValeur() {
            return valeur;
        }

        public String toString() {
            return ("IdAdj : "+id+" | ValeurAdj : "+valeur.toString());
        }
    }

    private Map<SommetPrinc,List<SommetAdj>> map ;
    private int id;
    private int nbCouleurs;

    public GraphMap(int k){
        this.id = 0;
        this.nbCouleurs = k;
        map = new HashMap<>();
    }

    private SommetPrinc findKeyVal(T val){
        SommetPrinc sp = null;
        Set<SommetPrinc> s = map.keySet();
        Object[] tabS = s.toArray();
        int i = 0;
        while(i<tabS.length && ((SommetPrinc)tabS[i]).getValeur()!=val){i++;}
        if(i<tabS.length) sp = (SommetPrinc)tabS[i];
        return sp;
    }

    private SommetPrinc findKeyId(int id){
        SommetPrinc sp = null;
        Set<SommetPrinc> s = map.keySet();
        Object[] tabS = s.toArray();
        int i = 0;
        while(i<tabS.length && ((SommetPrinc)tabS[i]).getId()!=id){i++;}
        if(i<tabS.length) sp = (SommetPrinc)tabS[i];
        return sp;
    }

    public SommetPrinc addNode(T val){
        SommetPrinc spr = null;
        if(this.findKeyVal(val)==null){
            spr = new SommetPrinc(this.id++,val);
            map.put(spr, new LinkedList<>());
        }
        return spr;
    }

    public void addEdge(T val1, T val2, E valArete){
        SommetPrinc spr1 = this.findKeyVal(val1);
        SommetPrinc spr2 = this.findKeyVal(val2);
        if(spr1==null)
            spr1 = addNode(val1);
        if(spr2==null)
            spr2 = addNode(val2);
        if(!hasEdge(val1,val2)){
            map.get(spr1).add(new SommetAdj(spr2.getId(), valArete));
            map.get(spr2).add(new SommetAdj(spr1.getId(), valArete));
        } else {
            System.out.println("There is an edge between "+val1.toString()+" and "+val2.toString()+" ");
        }

    }

    public boolean hasEdge (int id1, int id2){
        boolean b = false;
        List<SommetAdj> Isa = map.get(this.findKeyId(id1));
        int i = 0;
        while (i<Isa.size() && Isa.get(i).getId()!=id2){
            i++;
        }
        if(i<Isa.size())
            b = true;
        return b;
    }

    public boolean hasEdge (T val1, T val2){
        boolean b = false;
        List<SommetAdj> Isa = map.get(this.findKeyVal(val1));
        SommetPrinc sp2 = this.findKeyVal(val2);
        int i = 0;
        while (i<Isa.size() && Isa.get(i).getValeur()!=val2){
            i++;
        }
        if(i<Isa.size())
            b = true;
        return b;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for(SommetPrinc sp : map.keySet()){
            buffer.append(sp.toString()).append('\n').append("Voisins :").append('\n');
            for (SommetAdj sa : map.get(sp)) {
                 buffer.append(sa.toString()).append('\n');         
            }
            buffer.append("\n\n");  
        }
        return buffer.toString();
    }

    //Color tous les noeuds (en débutant par la couleur 0) et affiche le résultat
    public boolean greedyColoring(){
        boolean b = true;
        boolean[] coul_attrib = new boolean[this.nbCouleurs];
        for(int i = 0 ; i<this.nbCouleurs ; i++){
            coul_attrib[i] = false;
        }

        Iterator<Map.Entry<SommetPrinc,List<SommetAdj>>> iterator = map.entrySet().iterator();

        while(iterator.hasNext() && b){
            Map.Entry<SommetPrinc, List<SommetAdj>> cour = iterator.next();
            //Indiquer que les couleurs des sommets adj sont attribuées
            System.out.println(cour.getKey());
            List<SommetAdj> Isa = cour.getValue();
            for (SommetAdj sa : Isa){
                if (findKeyId(sa.getId()).getCouleur()!=1)
                    coul_attrib[findKeyId(sa.getId()).getCouleur()] = true;
            } 
            //Trouver la premiere couleur disponible
            int i = 0;
            while (i<this.nbCouleurs && coul_attrib[i] == true){i++;}
            if(i<this.nbCouleurs)
                cour.getKey().setCouleur(i);
            else
                b = false;
            if(b){
            //Relâcher la contrainte relative à la couleur des noeuds adjacents
                for (SommetAdj sa : Isa) {
                    if(findKeyId(sa.getId()).getCouleur()!=-1)
                        coul_attrib[findKeyId(sa.getId()).getCouleur()] = false;
                }
            }
        }
        return  b;
    }
}
*/