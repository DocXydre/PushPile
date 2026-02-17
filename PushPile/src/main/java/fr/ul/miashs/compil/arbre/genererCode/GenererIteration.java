package fr.ul.miashs.compil.arbre.genererCode;

import java.util.ArrayList;

import fr.ul.miashs.compil.arbre.tds.*;
import fr.ul.miashs.compil.arbre.traduction.*;
import fr.ul.miashs.compil.arbre.*;

public class GenererIteration {
    public static void main(String[] args) {
        Tds tds = new Tds(new ArrayList<>());
        Generateur generateur = new Generateur(tds);

      
        Prog prog = new Prog();
        Fonction principal = new Fonction("main");
        Affectation aff1 = new Affectation();
        Idf x1 = new Idf("x");
        Const c0 = new Const(0);
        aff1.setFilsGauche(x1);
        aff1.setFilsDroit(c0);
        
        TantQue boucle = new TantQue();
        
        Inferieur condition = new Inferieur();
        Idf x2 = new Idf("x");
        Const c8 = new Const(8);
        condition.setFilsGauche(x2);
        condition.setFilsDroit(c8);
        boucle.setCondition(condition);
        
        Bloc blocBoucle = new Bloc();
        
        Ecrire ecrire = new Ecrire();
        Idf x3 = new Idf("x");
        ecrire.ajouterUnFils(x3);
        
        Affectation aff2 = new Affectation();
        Idf x4 = new Idf("x");
        Plus plus = new Plus();
        Idf x5 = new Idf("x");
        Const c1 = new Const(1);
        plus.setFilsGauche(x5);
        plus.setFilsDroit(c1);
        aff2.setFilsGauche(x4);
        aff2.setFilsDroit(plus);
        
        blocBoucle.ajouterUnFils(ecrire);
        blocBoucle.ajouterUnFils(aff2);
        boucle.setBloc(blocBoucle);
        principal.ajouterUnFils(aff1);
        principal.ajouterUnFils(boucle);
        prog.ajouterUnFils(principal);

        tds.getItems().add(new Item("main", "void", Categorie.FONCTION));
        tds.getItems().add(new Item("x", "int", Categorie.GLOBAL, 0));
        
        System.out.println(generateur.generer_programme(prog));

        // Affichage de l'arbre
        System.out.println("\n Affichage de l'arbre :");
        TxtAfficheur.afficher(prog);
        GuiAfficheur.afficher(prog);
    }
}
