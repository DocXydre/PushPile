package fr.ul.miashs.compil.arbre.tds;

public enum Categorie {
      
    
    GLOBAL("global"),
    PARAMETRE("param"),
    LOCAL("local"),
    FONCTION("fonction");

    private final String categorie;

    Categorie(String categorie) {
        this.categorie = categorie;
    }

    public String getCategorie() {
        return categorie;
    }

    public static Categorie getCategorie(String categorie) {
        for (Categorie c : Categorie.values()) {
            if (c.getCategorie().equals(categorie)) {
                return c;
            }
        }   
        return null;
    }

    @Override
    public String toString() {
        return categorie;
    }
}

