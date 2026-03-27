package fr.ul.miashs.compil.arbre.exemples;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Lecteur et afficheur des fichiers d'exemples de test
 * Affiche le contenu des fichiers Test1.txt à Test9.txt
 */
public class LectureExemples {

    public static void main(String[] args) throws IOException {
        String exemplesDir = "PushPile/exemples";
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
            afficherFichier(fichier);
        }

        afficherInstructions();
    }

    private static void afficherFichier(File fichier) throws IOException {
        String contenu = new String(Files.readAllBytes(fichier.toPath()));
        System.out.println("\n" + "-".repeat(80));
        System.out.println("📄 " + fichier.getName());
        System.out.println("-".repeat(80));
        System.out.println(contenu);
    }

    private static void afficherInstructions() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("GUIDE D'UTILISATION");
        System.out.println("=".repeat(80));
        System.out.println("""
Ces fichiers de test conti ennent le code source en langage cuisinier.

CORRESPONDANCE AVEC LES GÉNÉRATEURS:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

 Test1.txt  → GenererProgramme.java
   • Programme minimal avec main vide
   • Démontre la fonction principale

 Test2.txt  → GenererVarGlobale.java
   • Variables globales avec et sans initialisation
   • Syntaxe: aliment ingrédients quantité <nom> [mélanger <valeur>] ;

 Test3.txt  → GenererExpression.java
   • Expressions arithmétiques avec priorités
   • x = (a * 2) + (b - 5) / 3

 Test4.txt  → GenererEcriture.java
   • Lecture (déguster) et écriture (servir)
   • Combinaison de lire() et ecrire()

 Test5.txt  → GenererEcriturebis.java
   • Écriture directe d'une expression
   • servir ) <expression> ( ;

 Test6.txt  → GenererCondition.java
   • Conditionnelle if/else
   • cuit ) condition ( { ... } crue { ... }

 Test7.txt  → GenererIteration.java
   • Boucle while
   • mijoter ) condition ( { ... }

 Test8.txt  → GenererVarParam.java
   • Fonctions avec paramètres et variables locales
   • param quantité <nom> et épice ingrédients quantité <nom>

 Test9.txt  → GenererRecursivite.java
   • Appels récursifs
   • Calcul factorial ou similaire

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

COMMENT EXÉCUTER UN TEST:

1. Allez dans: src/main/java/fr/ul/miashs/compil/arbre/genererCode/
2. Sélectionnez une classe (ex: GenererProgramme.java)
3. Cliquez droit → Run 'GenererProgramme.main()'
4. Consultez la sortie console pour:
   - Code assembleur généré
   - Affichage de l'arbre
   - Table des symboles

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

COMMENT CONSTRUIRE UN ARBRE MANUELLEMENT:

  Tds tds = new Tds(new ArrayList<>());
  Generateur gen = new Generateur(tds);
  
  Prog prog = new Prog();
  Fonction main = new Fonction("main");
  prog.ajouterUnFils(main);
  
  // Ajouter des instructions
  Affectation aff = new Affectation();
  aff.setFilsGauche(new Idf("variable"));
  aff.setFilsDroit(new Const(42));
  main.ajouterUnFils(aff);
  
  // Générer le code
  System.out.println(gen.generer_programme(prog));

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        """);
    }
}
