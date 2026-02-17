package fr.ul.miashs.compil.arbre.genererCode;
import java.util.ArrayList;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.arbre.tds.*;
import fr.ul.miashs.compil.arbre.traduction.Generateur;

public class GenererProgramme {
    public static void main(String[] args) {
        // Création d'une TDS d'exemple
        Tds tds = new Tds(new ArrayList<>());
        Generateur gen = new Generateur(tds);
         
        Prog prog = new Prog();
        Fonction fonction = new Fonction("main");
        prog.ajouterUnFils(fonction);
        
        tds.getItems().add(new Item("main", "void", Categorie.FONCTION));
        

        System.out.println(gen.generer_programme(prog));
        
        //affichage de l'arbre
        System.out.println("\n Affichage de l'arbre :");
        TxtAfficheur.afficher(prog);
        GuiAfficheur.afficher(prog);
    }
    
}
