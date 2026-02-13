package fr.ul.miashs.compil.arbre.genererCode;

import java.util.ArrayList;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.arbre.tds.*;
import fr.ul.miashs.compil.arbre.traduction.Generateur;

public class GenererIteration {

    public static void main(String[] args) {

        Tds tds = new Tds(new ArrayList<>());
        Generateur generateur = new Generateur(tds);

        Prog prog = new Prog();
        Fonction main = new Fonction("main");

        TantQue tq = new TantQue();
        Inferieur inf = new Inferieur();
        Idf i1 = new Idf("i");
        Const c5 = new Const(5);

        Bloc bloc = new Bloc();

        Ecrire ecrire = new Ecrire();
        Idf i2 = new Idf("i");

        Affectation aff = new Affectation();
        Idf i3 = new Idf("i");
        Plus plus = new Plus();
        Idf i4 = new Idf("i");
        Const c1 = new Const(1);

        prog.ajouterUnFils(main);
        main.ajouterUnFils(tq);

        tq.ajouterUnFils(inf);
        inf.setFilsGauche(i1);
        inf.setFilsDroit(c5);

        tq.ajouterUnFils(bloc);

        bloc.ajouterUnFils(ecrire);
        ecrire.ajouterUnFils(i2);

        bloc.ajouterUnFils(aff);
        aff.setFilsGauche(i3);
        aff.setFilsDroit(plus);
        plus.setFilsGauche(i4);
        plus.setFilsDroit(c1);

        tds.getItems().add(new Item("main", "void", Categorie.FONCTION));
        tds.getItems().add(new Item("i", "int", Categorie.GLOBAL));

        System.out.println(generateur.generer_programme(prog));
    }
}
