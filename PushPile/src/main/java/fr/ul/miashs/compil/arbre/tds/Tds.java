package fr.ul.miashs.compil.arbre.tds;

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

    public ArrayList<Item> getSymboles() {
        return tds;
    }

    public Item getSymbole(String nom) {
        for (Item symbole : tds) {
            if (symbole.getNom().equals(nom)) {
                return symbole;
            }
        }
        return null;
    }

    public void Rechercher(){}
    public void Ajouter(){}

}
