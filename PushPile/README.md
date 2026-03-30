# PushPile

PushPile est un compilateur développé en Java dans le cadre de la licence MIASHS option MIAGE à l'Université de Lorraine. Il permet de traduire un langage à thématique culinaire en code assembleur pour le simulateur Bsim.

## L'Équipe

- **Chef de projet** : MATHIS Thomas
- **Développeur** : MIRGUET Ethan
- **Développeur** : TAMRANI Houda
- **Développeur** : MELHAOUI Malak

## Lancement Rapide

### Prérequis

- Java 17+
- Maven 3.8+

### Compilation et Exécution

```bash
# Compiler le projet
mvn clean compile

# Lancer le compilateur (point d'entrée test)
mvn exec:java -Dexec.mainClass="fr.ul.miashs.compil.arbre.lecteurTest.LectureExemples"
```

## 📂 Structure des Fichiers

Le projet suit une architecture basée sur les outils JFlex et CUP :

- **src/main/cup/** : Grammaire syntaxique (parser.cup)
- **src/main/jflex/** : Analyseur lexical (scanner.jflex)
- **src/main/java/fr/ul/miashs/compil/** :
  - **arbre/** : Gestion de l'Arbre Abstrait (AST)
  - **tds/** : Gestion de la Table des Symboles (TDS)
  - **traduction/** : Générateur de code assembleur
  - **lecteurTest/** : Classe principale pour exécuter les tests

## 🧪 Tests et Progression

Le projet a été développé de manière agile suivant 9 points de progression (du programme minimal à la récursivité).

Pour changer de test, modifiez le chemin du fichier dans `LectureExemples.java` :

```java
// Modifiez le chiffre de 1 à 9 pour tester les différents paliers
FileReader fr = new FileReader("exemples/Test6.txt");
```

Le programme génère automatiquement :
- L'arbre syntaxique (affichage textuel et graphique)
- La table des symboles
- Le code assembleur final