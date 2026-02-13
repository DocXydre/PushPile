package fr.ul.miashs.compil.arbre.genererCode;

import java.util.ArrayList;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.arbre.tds.*;
import fr.ul.miashs.compil.arbre.traduction.Generateur;

public class GenererCondition {

    public static void main(String[] args) {

        Tds tds = new Tds(new ArrayList<>());
        Generateur generateur = new Generateur(tds);

        Prog prog = new Prog();
        Fonction main = new Fonction("main");

        Si si = new Si();
        Superieur sup = new Superieur();
        Idf a = new Idf("a");
        Idf b = new Idf("b");

        Bloc blocThen = new Bloc();
        Affectation aff1 = new Affectation();
        Idf x1 = new Idf("x");
        Const c1000 = new Const(1000);

        Bloc blocElse = new Bloc();
        Affectation aff2 = new Affectation();
        Idf x2 = new Idf("x");
        Const c2000 = new Const(2000);

        prog.ajouterUnFils(main);
        main.ajouterUnFils(si);

        sup.setFilsGauche(a);
        sup.setFilsDroit(b);
        si.setCondition(sup);

        aff1.setFilsGauche(x1);
        aff1.setFilsDroit(c1000);
        blocThen.ajouterUnFils(aff1);
        si.setBlocAlors(blocThen);

        aff2.setFilsGauche(x2);
        aff2.setFilsDroit(c2000);
        blocElse.ajouterUnFils(aff2);
        si.setBlocSinon(blocElse);

        tds.getItems().add(new Item("main", "void", Categorie.FONCTION));
        tds.getItems().add(new Item("a", "int", Categorie.GLOBAL));
        tds.getItems().add(new Item("b", "int", Categorie.GLOBAL));
        tds.getItems().add(new Item("x", "int", Categorie.GLOBAL));

        System.out.println(generateur.generer_programme(prog));
    }
}
