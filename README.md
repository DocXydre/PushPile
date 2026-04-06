# PushPile

Compilateur Java pour le langage PushPile développé à l'Université de Lorraine (MIASHS - MIAGE).

## L'Équipe

- **Chef de projet** : MATHIS Thomas
- **Développeurs** : MIRGUET Ethan, TAMRANI Houda, MELHAOUI Malak

## Démarrage Rapide

### Prérequis
- Java 17+
- Maven 3.8+

### Build
```bash
mvn clean package
```

## Utilisation

### macOS / Linux
```bash
./bindist/bin/compiler exemples/Test6.txt [options]
```

### Windows
```bash
.\bindist\bin\compiler.bat exemples/Test6.txt [options]
```

### Options
 `--help`  Affiche l'aide 
 `--tds`  Table des symboles 
 `--arbre`  Arbre de syntaxe 
 `--arbrebsim`  Arbre graphique (fenêtre) 
 `--all`  TDS + arbre + graphique 

### Exemples
```bash
# Compilation simple
compiler exemples/Test6.txt

# Avec tous les affichages
compiler exemples/Test6.txt --all

# Arbre graphique seulement
compiler exemples/Test6.txt --arbrebsim
```

## Structure

- **src/main/cup/** : Grammaire (parser.cup)
- **src/main/jflex/** : Lexer (scanner.jflex)
- **src/main/java/fr/ul/miashs/compil/** :
  - `compilateur/` : Point d'entrée (Compiler.java)
  - `arbre/` : AST
  - `tds/` : Table des symboles
  - `traduction/` : Générateur assembleur Bsim

## Tests

9 fichiers de test : `exemples/Test1.txt` à `exemples/Test9.txt` (du basique à la récursivité)

## Sortie

- Fichier `.asm` au même nom que l'entrée
- Affichages optionnels selon les options