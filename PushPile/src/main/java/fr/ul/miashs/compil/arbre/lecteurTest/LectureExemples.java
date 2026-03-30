package fr.ul.miashs.compil.arbre.lecteurTest;

import java.io.*;
import fr.ul.miashs.compil.lex.Lexer;
import fr.ul.miashs.compil.parser.Parser;
import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.arbre.tds.*;
import fr.ul.miashs.compil.arbre.traduction.Generateur;
import java_cup.runtime.Symbol;

/**
 * Lecteur, parseur et générateur de code pour les fichiers d'exemples
 * Lit les fichiers Test1.txt à Test9.txt, les parse avec CUP/JFlex, 
 * puis génère l'arbre et le code assembleur
 */
public class LectureExemples {

    public static void main(String[] args) throws IOException, Exception {
        try {
            // === CHOISIR UN TEST (décommenter la ligne désirée) ===
            // FileReader fr = new FileReader("exemples/Test1.txt");
            // FileReader fr = new FileReader("exemples/Test2.txt");
            // FileReader fr = new FileReader("exemples/Test3.txt");
            // FileReader fr = new FileReader("exemples/Test4.txt");
            // FileReader fr = new FileReader("exemples/Test5.txt");
            FileReader fr = new FileReader("exemples/Test6.txt");
            // FileReader fr = new FileReader("exemples/Test7.txt");
            // FileReader fr = new FileReader("exemples/Test8.txt");
            // FileReader fr = new FileReader("exemples/Test9.txt");
            //FileReader fr = new FileReader("exemples/Test1.txt");

            Lexer scanner = new Lexer(fr);
            Parser parser = new Parser(scanner);
            Symbol result = parser.parse();

            Object res = (result != null) ? result.value : null;
            Tds tds = parser.getTds();

            if (res instanceof Prog prog) {
                TxtAfficheur.afficher(prog);
                GuiAfficheur.afficher(prog);

                Generateur generateur = new Generateur(tds);
                System.out.println(generateur.generer_programme(prog));

                System.out.println(tds);
            } else {
                System.out.println("ERREUR: Résultat du parsing invalide");
            }

        } catch (Exception e) {
            System.out.println("Erreur de parsing : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
