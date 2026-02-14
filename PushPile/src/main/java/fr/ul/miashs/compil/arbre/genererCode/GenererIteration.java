package fr.ul.miashs.compil.arbre.genererCode;

import java.util.ArrayList;

import fr.ul.miashs.compil.arbre.tds.*;
import fr.ul.miashs.compil.arbre.traduction.*;
import fr.ul.miashs.compil.arbre.*;

public class GenererIteration {
    public static void main(String[] args) {
        Tds tds = new Tds(new ArrayList<>());
        Generateur generateur = new Generateur(tds);

        // Construction de l'arbre selon l'image
        Prog prog = new Prog();
        Fonction principal = new Fonction("main");
        
        // Affectation : x = 0
        Affectation aff1 = new Affectation();
        Idf x1 = new Idf("x");
        Const c0 = new Const(0);
        aff1.setFilsGauche(x1);
        aff1.setFilsDroit(c0);
        
        // TantQue (iteration #8)
        TantQue boucle = new TantQue();
        
        // Condition : x < 8 (inferieur)
        Inferieur condition = new Inferieur();
        Idf x2 = new Idf("x");
        Const c8 = new Const(8);
        condition.setFilsGauche(x2);
        condition.setFilsDroit(c8);
        boucle.setCondition(condition);
        
        // Bloc de la boucle
        Bloc blocBoucle = new Bloc();
        
        // Ecrire (affichage de x)
        Ecrire ecrire = new Ecrire();
        Idf x3 = new Idf("x");
        ecrire.ajouterUnFils(x3);
        
        // Affectation : x = x + 1
        Affectation aff2 = new Affectation();
        Idf x4 = new Idf("x");
        Plus plus = new Plus();
        Idf x5 = new Idf("x");
        Const c1 = new Const(1);
        plus.setFilsGauche(x5);
        plus.setFilsDroit(c1);
        aff2.setFilsGauche(x4);
        aff2.setFilsDroit(plus);
        
        // Ajout des instructions au bloc de la boucle
        blocBoucle.ajouterUnFils(ecrire);
        blocBoucle.ajouterUnFils(aff2);
        boucle.setBloc(blocBoucle);
        
        // Ajout à la fonction main
        principal.ajouterUnFils(aff1);
        principal.ajouterUnFils(boucle);
        prog.ajouterUnFils(principal);

        // Construction de la TDS
        tds.getItems().add(new Item("main", "void", Categorie.FONCTION));
        tds.getItems().add(new Item("x", "int", Categorie.GLOBAL, 0));
        
        // Génération du code
        System.out.println(generateur.generer_programme(prog));

        // Affichage de l'arbre
        TxtAfficheur.afficher(prog);
        GuiAfficheur.afficher(prog);
    }
}
