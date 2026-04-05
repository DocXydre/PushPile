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

### Build

```bash
# Compiler et packager le projet
mvn clean package
```

### Utilisation

```bash
# Afficher l'aide
./bindist/bin/compiler --help

# Compiler un fichier (affiche arbre + TDS, génère output.asm)
./bindist/bin/compiler exemples/Test6.txt
```

**Sortie :**
- Affichage de l'arbre de syntaxe dans le terminal
- Affichage de la table des symboles (TDS) dans le terminal
- Génération du fichier code assembleur BSim : `output.asm`

### Commandes Disponibles

| Commande | Description |
|----------|-------------|
| `compiler <fichier.prog>` | Compile un fichier (sortie: `output.asm`) |
| `compiler --help` | Affiche l'aide |
| `compiler -h` | Affiche l'aide (version courte) |

**Exemple :**
```bash
./bindist/bin/compiler exemples/Test6.txt
```

## 📂 Structure des Fichiers

Le projet suit une architecture basée sur les outils JFlex et CUP :

- **src/main/cup/** : Grammaire syntaxique (parser.cup)
- **src/main/jflex/** : Analyseur lexical (scanner.jflex)
- **src/main/java/fr/ul/miashs/compil/** :
  - **main/** : Point d'entrée - **Compiler.java**
  - **arbre/** : Gestion de l'Arbre Abstrait (AST)
  - **tds/** : Gestion de la Table des Symboles (TDS)
  - **traduction/** : Générateur de code assembleur BSim
  - **lecteurTest/** : Classe de test (ancienne interface)

## 🧪 Exemples d'Utilisation

Le projet inclut 9 fichiers de test (`exemples/Test1.txt` à `exemples/Test9.txt`) couvrant différents niveaux de complexité, du programme minimal à la récursivité.

```bash
# Compiler Test1 (programme vide)
./bindist/bin/compiler exemples/Test1.txt

# Compiler Test6 (conditions)
./bindist/bin/compiler exemples/Test6.txt

# Compiler Test9 (récursivité - niveau maximal)
./bindist/bin/compiler exemples/Test9.txt
```

Le programme génère automatiquement :
- L'arbre syntaxique (affichage textuel)
- La table des symboles
- Le code assembleur BSim dans `output.asm`