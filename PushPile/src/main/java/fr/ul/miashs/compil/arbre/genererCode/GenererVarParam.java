package fr.ul.miashs.compil.arbre.genererCode;

import java.util.ArrayList;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.arbre.tds.*;
import fr.ul.miashs.compil.arbre.traduction.Generateur;

public class GenererVarParam {

    public static void main(String[] args) {

        Tds tds = new Tds(new ArrayList<>());
        Generateur generateur = new Generateur(tds);

        Prog prog = new Prog();

        // Fonction f
        Fonction f = new Fonction("f");
        Affectation aff = new Affectation();
        Idf res = new Idf("res");
        Plus plus = new Plus();
        Multiplication mul = new Multiplication();
        Idf a = new Idf("a");
        Const c2 = new Const(2);
        Division div = new Division();
        Moins moins = new Moins();
        Idf b = new Idf("b");
        Const c5 = new Const(5);
        Const c3 = new Const(3);
        Retour retour = new Retour("f");

        prog.ajouterUnFils(f);

        f.ajouterUnFils(aff);
        aff.setFilsGauche(res);
        aff.setFilsDroit(plus);

        plus.setFilsGauche(mul);
        mul.setFilsGauche(a);
        mul.setFilsDroit(c2);

        plus.setFilsDroit(div);
        div.setFilsGauche(moins);
        moins.setFilsGauche(b);
        moins.setFilsDroit(c5);
        div.setFilsDroit(c3);

        f.ajouterUnFils(retour);
        retour.setLeFils(res);

        // Fonction main
        Fonction main = new Fonction("main");
        Ecrire ecrire = new Ecrire();
        Appel appel = new Appel("f");
        Idf a2 = new Idf("a");
        Idf c = new Idf("c");

        prog.ajouterUnFils(main);
        main.ajouterUnFils(ecrire);
        ecrire.ajouterUnFils(appel);
        appel.ajouterUnFils(a2);
        appel.ajouterUnFils(c);

        // TDS
        tds.getItems().add(new Item("main", "void", Categorie.FONCTION));
        tds.getItems().add(new Item("f", "int", Categorie.FONCTION, 0, 2, 1, 0, null));

        tds.getItems().add(new Item("a", "int", Categorie.GLOBAL, 100));
        tds.getItems().add(new Item("c", "int", Categorie.GLOBAL, 170));

        tds.getItems().add(new Item("a", "int", Categorie.PARAMETRE, 0, 0, 0, 0, "f"));
        tds.getItems().add(new Item("b", "int", Categorie.PARAMETRE, 0, 0, 0, 1, "f"));
        tds.getItems().add(new Item("res", "int", Categorie.LOCAL, 0, 0, 0, 0, "f"));

        System.out.println(generateur.generer_programme(prog));
          TxtAfficheur.afficher(prog);
         GuiAfficheur.afficher(prog);
    }
}
