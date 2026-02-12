package fr.ul.miashs.compil.arbre.tds;


public class Item {
    String nom, type, scope;
    Categorie categorie;
    int valeur, nbParametres, nbVariables, rang;

    public Item (String nom, String type, Categorie categorie, int valeur, int nbParametres, int nbVariables, int rang, String scope){
        this.nom = nom;
        
        this.type = type;
        this.categorie = categorie;
        this.valeur = valeur;
        this.nbParametres = nbParametres;
        this.nbVariables = nbVariables;
        this.rang = rang;
        this.scope = scope;
    }
    //

    public Item(String nom, String type, Categorie categorie, int valeur) {
        this(nom, type, categorie, valeur, -1, -1, -1, null);
    }

    public Item(String nom, String type, Categorie categorie) {
        this(nom, type, categorie, 0, -1, -1, -1, null);
    }

   public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public String getCategorie() {
        return categorie.getCategorie();
    }

    public int getValeur() {
        return valeur;
    }

    public int getNbParametres() {
        return nbParametres;
    }

    public void setNbParametres(int nbParametres) {
        this.nbParametres = nbParametres;
    }

    public int getNbVariables() {
        return nbVariables;
    }

    public void setNbVariables(int nbVariables) {
        this.nbVariables = nbVariables;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "Symbole{" +
                "nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", categorie=" + categorie +
                ", valeur=" + valeur +
                ", nbParametres=" + nbParametres +
                ", nbVariables=" + nbVariables +
                ", rang=" + rang +
                ", scope='" + scope + '\'' +
                '}';
    }
}
