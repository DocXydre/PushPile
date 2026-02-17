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
// package fr.ul.miashs.compil.arbre.genererCode;

// import java.util.ArrayList;
// import fr.ul.miashs.compil.arbre.tds.*;
// import fr.ul.miashs.compil.arbre.traduction.*;
// import fr.ul.miashs.compil.arbre.*;

// public class GenererEcriture {
//     public static void main(String[] args) {
//         // 1. Initialisation de la TDS (Table des Symboles)
//         Tds tds = new Tds(new ArrayList<>());
//         // On déclare explicitement "res" en tant que global pour correspondre à l'ID utilisé dans l'arbre
//         tds.getItems().add(new Item("main", "void", Categorie.FONCTION));
//         tds.getItems().add(new Item("res", "int", Categorie.GLOBAL));

//         Generateur generateur = new Generateur(tds);

//         // 2. Création de la racine du programme
//         Prog prog = new Prog();
//         Fonction principal = new Fonction("main");
//         prog.ajouterUnFils(principal);

//         // --- Construction de l'Affectation : res = (Lire * 2) + ((Lire - 5) / 3) ---
//         Affectation aff = new Affectation();
//         aff.setFilsGauche(new Idf("res")); // Variable cible

//         Plus racineExpression = new Plus();

//         // Branche Gauche : (Lire * 2)
//         Multiplication mul = new Multiplication();
//         mul.setFilsGauche(new Lire()); // Instance de lecture unique
//         mul.setFilsDroit(new Const(2));

//         // Branche Droite : ((Lire - 5) / 3)
//         Division div = new Division();
//         Moins soustraction = new Moins();
//         soustraction.setFilsGauche(new Lire()); // Seconde instance de lecture unique
//         soustraction.setFilsDroit(new Const(5));
        
//         div.setFilsGauche(soustraction);
//         div.setFilsDroit(new Const(3));

//         // Assemblage final de l'expression d'affectation
//         racineExpression.setFilsGauche(mul);
//         racineExpression.setFilsDroit(div);
//         aff.setFilsDroit(racineExpression);

//         // Ajout de l'affectation à la fonction main
//         principal.ajouterUnFils(aff);

//         // --- Construction de l'Ecriture : ecrire(res) ---
//         Ecrire ecrire = new Ecrire();
//         ecrire.ajouterUnFils(new Idf("res")); // On lit la valeur de "res" pour l'afficher
//         principal.ajouterUnFils(ecrire);

//         // 3. Génération et affichage du code Beta
//         System.out.println(generateur.generer_programme(prog));

//         // Affichages visuels pour vérification
//         TxtAfficheur.afficher(prog);
//         // GuiAfficheur.afficher(prog); // Optionnel selon ton environnement
//     }
// }