package fr.ul.miashs.compil.arbre.genererCode;

import java.util.ArrayList;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.arbre.tds.*;
import fr.ul.miashs.compil.arbre.traduction.Generateur;

public class GenererEcriturebis {

    public static void main(String[] args) {

        Tds tds = new Tds(new ArrayList<>());
        Generateur generateur = new Generateur(tds);

        // Création des noeuds
        Prog prog = new Prog();
        Fonction main = new Fonction("main");
        Ecrire ecrire = new Ecrire();

        Plus plus = new Plus();

        Multiplication mul = new Multiplication();
        Idf a = new Idf("a");
        Const c2 = new Const(2);

        Division div = new Division();
        Moins moins = new Moins();
        Idf b = new Idf("b");
        Const c5 = new Const(5);
        Const c3 = new Const(3);

        // Construction de l'arbre
        prog.ajouterUnFils(main);
        main.ajouterUnFils(ecrire);
        ecrire.ajouterUnFils(plus);

        plus.setFilsGauche(mul);
        plus.setFilsDroit(div);

        mul.setFilsGauche(a);
        mul.setFilsDroit(c2);

        div.setFilsGauche(moins);
        div.setFilsDroit(c3);

        moins.setFilsGauche(b);
        moins.setFilsDroit(c5);

        // TDS
        tds.getItems().add(new Item("main", "void", Categorie.FONCTION));
        tds.getItems().add(new Item("a", "int", Categorie.GLOBAL));
        tds.getItems().add(new Item("b", "int", Categorie.GLOBAL));

        System.out.println(generateur.generer_programme(prog));

        System.out.println("\n Affichage de l'arbre :");
          TxtAfficheur.afficher(prog);
         GuiAfficheur.afficher(prog);
    }
}
