package fr.ul.miashs.compil.tds;

import java.util.ArrayList;

public class Tds {
private final ArrayList<Item> tds;

    public Tds(ArrayList<Item> tds) {
        this.tds = tds;
        for (int i = 0; i < tds.size(); i++) {
            if (tds.get(i).getNom().equals("main")) {
                tds.add(0, tds.remove(i));
                break;
            }
        } //
    }

    public ArrayList<Item> getItems() {
        return tds;
    }

    public Item getItem(String nom) {
        for (Item symbole : tds) {
            if (symbole.getNom().equals(nom)) {
                return symbole;
            }
        }
        return null;
    }

    public Item getItem(String nom, String functionScope) {
        // Chercher d'abord dans le scope courant (paramètres et variables locales)
        if (functionScope != null) {
            for (Item symbole : tds) {
                if (symbole.getNom().equals(nom) && functionScope.equals(symbole.getScope())) {
                    return symbole;
                }
            }
        }
        // Fallback sur les variables globales
        for (Item symbole : tds) {
            if (symbole.getNom().equals(nom) && symbole.getScope() == null) {
                return symbole;
            }
        }
        return null;
    }

    public void Rechercher(){}
    public void Ajouter(){}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Table des symboles:\n");
        for (Item item : tds) {
            sb.append(item.toString()).append("\n");
        }
        return sb.toString();
    }

}
