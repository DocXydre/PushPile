package fr.ul.miashs.compil.arbre.genererCode;

import java.util.ArrayList;

import fr.ul.miashs.compil.arbre.tds.*;
import fr.ul.miashs.compil.arbre.traduction.*;
import fr.ul.miashs.compil.arbre.*;


public class GenererExpression {
    public static void main(String[] args) {
        Tds tds = new Tds(new ArrayList<>());
        Generateur generateur = new Generateur(tds);

        Prog prog = new Prog();
        Fonction principal = new Fonction("main");
        Affectation aff = new Affectation();
        Idf x = new Idf("x");
        Plus plus = new Plus();
        Multiplication mul = new Multiplication();
        Idf a = new Idf("a");
        Const c2 = new Const(2);
        Division div = new Division();
        Const c3 = new Const(3);
        Moins moins = new Moins();
        Idf b = new Idf("b");
        Const c5 = new Const(5);

        prog.ajouterUnFils(principal);
        principal.ajouterUnFils(aff);
        aff.setFilsGauche(x);
        aff.setFilsDroit(plus);
        plus.setFilsGauche(mul);
        mul.setFilsGauche(a);
        mul.setFilsDroit(c2);
        plus.setFilsDroit(div);
        div.setFilsGauche(moins);
        moins.setFilsGauche(b);
        moins.setFilsDroit(c5);
        div.setFilsDroit(c3);


        tds.getItems().add(new Item("main", "void", Categorie.FONCTION));
        tds.getItems().add(new Item("x", "int", Categorie.GLOBAL, 0));
        tds.getItems().add(new Item("a", "int", Categorie.GLOBAL, 100));
        tds.getItems().add(new Item("b", "int", Categorie.GLOBAL, 170));
        System.out.println(generateur.generer_programme(prog));

        TxtAfficheur.afficher(prog);
        GuiAfficheur.afficher(prog);


    }
    
}
