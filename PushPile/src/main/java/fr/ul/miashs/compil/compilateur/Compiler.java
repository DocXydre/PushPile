package fr.ul.miashs.compil.compilateur;

import java.io.*;
import fr.ul.miashs.compil.lex.Lexer;
import fr.ul.miashs.compil.parser.Parser;
import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.tds.*;
import fr.ul.miashs.compil.traduction.Generateur;
import java_cup.runtime.Symbol;

/**
 * Compilateur simple pour PushPile
 * Usage: compiler <fichier.prog> [options]
 */
public class Compiler {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            afficherAide();
            return;
        }

        if (args[0].equals("--help") || args[0].equals("-h")) {
            afficherAide();
            return;
        }

        String inputFile = args[0];
        
        // Générer le nom du fichier de sortie à partir du fichier d'entrée
        String baseName = new File(inputFile).getName();
        String outputFile = baseName.replaceAll("\\.[^.]+$", ".asm");
        
        // Parser les options
        boolean afficherTds = false;
        boolean afficherArbre = false;
        boolean afficherArbreGui = false;
        
        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("--tds")) {
                afficherTds = true;
            } else if (args[i].equals("--arbre")) {
                afficherArbre = true;
            } else if (args[i].equals("--arbrebsim")) {
                afficherArbreGui = true;
            } else if (args[i].equals("--all")) {
                afficherTds = true;
                afficherArbre = true;
                afficherArbreGui = true;
            }
        }

        try {
            // Vérifier que le fichier existe
            File f = new File(inputFile);
            if (!f.exists()) {
                System.err.println("Erreur : fichier '" + inputFile + "' introuvable");
                System.exit(1);
            }

            // Compilation
            FileReader fr = new FileReader(inputFile);
            Lexer scanner = new Lexer(fr);
            Parser parser = new Parser(scanner);
            Symbol result = parser.parse();
            fr.close();

            Object res = (result != null) ? result.value : null;
            Tds tds = parser.getTds();

            if (res instanceof Prog prog) {
                // Générer code assembleur
                Generateur generateur = new Generateur(tds);
                String asmCode = generateur.generer_programme(prog);
                
                // Sauvegarder
                try (FileWriter writer = new FileWriter(outputFile)) {
                    writer.write(asmCode);
                }

                // Afficher l'arbre si demandé
                if (afficherArbre) {
                    System.out.println("\n ARBRE ABSTRAIT");
                    System.out.println(TxtAfficheur.formatter(prog));
                }
                
                // Afficher l'arbre sur GUI si demandé
                if (afficherArbreGui) {
                    GuiAfficheur.afficher(prog);
                }

                // Afficher la TDS si demandé
                if (afficherTds) {
                    System.out.println("\n TABLE DES SYMBOLES");
                    System.out.println(tds);
                }

                System.out.println("✓ Code assembleur -> " + outputFile);

            } else {
                System.err.println("Erreur de parsing");
                System.exit(1);
            }

        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            System.exit(1);
        }
    }

    private static void afficherAide() {
        System.out.println("PushPile - Compilateur");
        System.out.println();
        System.out.println("Usage: compiler <fichier.prog> [options]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  --help              Afficher cette aide");
        System.out.println("  --tds               Afficher la table des symboles");
        System.out.println("  --arbre             Afficher l'arbre de syntaxe");
        System.out.println("  --arbrebsim         Afficher l'arbre dans une fenêtre graphique");
        System.out.println("  --all               Afficher TDS, arbre et affichage arbre");
        System.out.println();
        System.out.println("Exemples:");
        System.out.println("  compiler source.prog");
        System.out.println("  compiler source.prog --tds");
        System.out.println("  compiler source.prog --abr --tds");
        System.out.println("  compiler exemples/Test6.txt --arbre bsim");
    }
}
