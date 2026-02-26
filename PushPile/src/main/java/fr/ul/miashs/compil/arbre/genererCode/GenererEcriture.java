package fr.ul.miashs.compil.arbre.genererCode;

import java.util.ArrayList;

import fr.ul.miashs.compil.arbre.tds.*;
import fr.ul.miashs.compil.arbre.traduction.*;
import fr.ul.miashs.compil.arbre.*;

public class GenererEcriture {
    
    public static void main(String[] args) {
        Tds tds = new Tds(new ArrayList<>());
        Generateur generateur = new Generateur(tds);

        Prog prog = new Prog();
        Fonction principal = new Fonction("main");
        Affectation aff = new Affectation();
        Idf res = new Idf("res");
        Plus plus = new Plus();
        Multiplication mul = new Multiplication();
        Lire lire = new Lire();
        Const c2 = new Const(2);
        Division div = new Division();
        Const c3 = new Const(3);
        Moins moins = new Moins();
        Const c5 = new Const(5);
        Ecrire ecrire = new Ecrire();

        prog.ajouterUnFils(principal);
        principal.ajouterUnFils(aff);
        aff.setFilsGauche(res);
        aff.setFilsDroit(plus);
        plus.setFilsGauche(mul);
        mul.setFilsGauche(lire);
        mul.setFilsDroit(c2);
        plus.setFilsDroit(div);
        div.setFilsGauche(moins);
        moins.setFilsGauche(lire);
        moins.setFilsDroit(c5);
        div.setFilsDroit(c3);
        principal.ajouterUnFils(ecrire);
        ecrire.ajouterUnFils(res);

        tds.getItems().add(new Item("main", "void", Categorie.FONCTION));
        tds.getItems().add(new Item("res", "int", Categorie.GLOBAL));
        System.out.println(generateur.generer_programme(prog));

        System.out.println("\n Affichage de l'arbre :");
        TxtAfficheur.afficher(prog);
        GuiAfficheur.afficher(prog);
        
    }
    
}
