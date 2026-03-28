package fr.ul.miashs.compil.arbre.exemples;

import java.io.*;
import java.nio.file.*;
import java.util.*;
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
        String exemplesDir = "exemples";
        File dir = new File(exemplesDir);

        if (!dir.exists()) {
            System.out.println("Le dossier " + exemplesDir + " n'existe pas.");
            return;
        }

        File[] fichiers = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (fichiers == null || fichiers.length == 0) {
            System.out.println("Aucun fichier .txt trouvé dans " + exemplesDir);
            return;
        }

        Arrays.sort(fichiers);

        System.out.println("\n" + "=".repeat(80));
        System.out.println("EXEMPLES DE TEST - Langage Cuisinier");
        System.out.println("=".repeat(80));

        for (File fichier : fichiers) {
            traiterFichier(fichier);
        }
    }

    private static void traiterFichier(File fichier) throws IOException, Exception {
        String contenu = new String(Files.readAllBytes(fichier.toPath()));
        System.out.println("\n" + "-".repeat(80));
        System.out.println("📄 " + fichier.getName());
        System.out.println("-".repeat(80));
        System.out.println("CODE SOURCE:");
        System.out.println(contenu);
        
        try {
            // Parser le fichier avec CUP/JFlex
            StringReader sr = new StringReader(contenu);
            Lexer scanner = new Lexer(sr);
            Parser parser = new Parser(scanner);
            Symbol result = parser.parse();  // Lance le parsing
            
            // Récupérer le résultat et la TDS du parser
            Object res = (result != null) ? result.value : null;
            Tds tds = parser.getTds();
            
            if (res instanceof Prog prog) {
                System.out.println("\n--- ARBRE SYNTAXIQUE ---");
                afficherArbre(prog, 0);
                
                System.out.println("\n--- CODE GÉNÉRÉ ---");
                
                // Utiliser la TDS créée par le parser
                Generateur generateur = new Generateur(tds);
                System.out.println(generateur.generer_programme(prog));
                
                System.out.println("\nTable des symboles:");
                System.out.println(tds);
            } else {
                System.out.println("ERREUR: Résultat du parsing invalide");
            }
            
        } catch (Exception e) {
            System.out.println("ERREUR PARSING: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void afficherArbre(Noeud noeud, int profondeur) {
        if (noeud == null) return;
        
        String indent = "  ".repeat(profondeur);
        System.out.println(indent + noeud.getLabel());
        
        if (!noeud.estFeuille()) {
            for (Noeud fils : noeud.getFils()) {
                if (fils != null) {
                    afficherArbre(fils, profondeur + 1);
                }
            }
        }
    }
}
