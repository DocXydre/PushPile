package fr.ul.miashs.compil.arbre.genererCode;

import java.util.ArrayList;

import fr.ul.miashs.compil.arbre.Fonction;
import fr.ul.miashs.compil.arbre.GuiAfficheur;
import fr.ul.miashs.compil.arbre.Prog;
import fr.ul.miashs.compil.arbre.TxtAfficheur;
import fr.ul.miashs.compil.arbre.tds.*;
import fr.ul.miashs.compil.arbre.traduction.Generateur;

public class GenererVarGlobale {
    public static void main(String[] args) {
        fr.ul.miashs.compil.arbre.tds.Tds tds = new Tds(new ArrayList<>());
        Generateur generateur = new Generateur(tds);
        Prog prog = new Prog();
        Fonction fonction = new Fonction("main");
        prog.ajouterUnFils(fonction);

        tds.getItems().add(new Item("main","void", Categorie.FONCTION));
        tds.getItems().add(new Item("i","int" ,Categorie.GLOBAL, 10));
        tds.getItems().add(new Item("j","int" ,Categorie.GLOBAL, 20));
        tds.getItems().add(new Item("k","int", Categorie.GLOBAL));
        tds.getItems().add(new Item("l","int", Categorie.GLOBAL));
        System.out.println(generateur.generer_programme(prog));

        //afficher
        TxtAfficheur.afficher(prog);
        GuiAfficheur.afficher(prog);

    }
    
}
