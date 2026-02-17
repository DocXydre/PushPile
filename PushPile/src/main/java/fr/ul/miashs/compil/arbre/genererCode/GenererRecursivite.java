package fr.ul.miashs.compil.arbre.genererCode;

import java.util.ArrayList;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.arbre.tds.*;
import fr.ul.miashs.compil.arbre.traduction.Generateur;

public class GenererRecursivite {
    public static void main(String[] args) {
        Tds tds = new Tds(new ArrayList<>());
        Generateur generateur = new Generateur(tds);

        Prog prog = new Prog();
        Fonction f = new Fonction("f");
        Si condition = new Si();
        InferieurEgal inf = new InferieurEgal();
        Idf a = new Idf("a");
        Const c0 = new Const(0);
        Bloc alors = new Bloc();
        Retour retour = new Retour("f");
        Bloc sinon = new Bloc();
        Retour retour2 = new Retour("f");
        Plus plus = new Plus();
        Appel appel = new Appel("f");
        Moins moins = new Moins();
        Const c1 = new Const(1);
        Fonction main = new Fonction("main");
        Ecrire ecrire = new Ecrire();
        Appel appel2 = new Appel("f");
        Const c6 = new Const(6);

        prog.ajouterUnFils(f);
        f.ajouterUnFils(condition);
        condition.setCondition(inf);
        inf.setFilsGauche(a);
        inf.setFilsDroit(c0);
        condition.setBlocAlors(alors);
        alors.ajouterUnFils(retour);
        retour.setLeFils(c0);
        condition.setBlocSinon(sinon);
        sinon.ajouterUnFils(retour2);
        retour2.setLeFils(plus);
        plus.setFilsGauche(a);
        plus.setFilsDroit(appel);
        appel.ajouterUnFils(moins);
        moins.setFilsGauche(a);
        moins.setFilsDroit(c1);
        prog.ajouterUnFils(main);
        main.ajouterUnFils(ecrire);
        ecrire.ajouterUnFils(appel2);
        appel2.ajouterUnFils(c6);

        tds.getItems().add(new Item("main", "void", Categorie.FONCTION));
        tds.getItems().add(new Item("f", "int", Categorie.FONCTION, 0, 1, 0, 0, null));
        tds.getItems().add(new Item("a", "int", Categorie.PARAMETRE, 0, 0, 0, 0, "f"));

        System.out.println(generateur.generer_programme(prog));

        System.out.println("\n Affichage de l'arbre :");
          TxtAfficheur.afficher(prog);
         GuiAfficheur.afficher(prog);
    }
}