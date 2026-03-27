package fr.ul.miashs.compil.lex;

import java_cup.runtime.Symbol;
import fr.ul.miashs.compil.parser.Sym; // Remplacez "Sym" par le nom généré par votre plugin CUP

%%

%class Lexer
%public
%unicode
%line
%column
%cup

%{
    // Fonction utilitaire pour générer les tokens
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

/* Expressions régulières de base */
Espace       = [ \t\f\r\n]+
Chiffre      = [0-9]
Entier       = {Chiffre}+
Lettre       = [a-zA-Z_éèêàù]
Identifiant  = {Lettre} ({Lettre} | {Chiffre} | "-")*

/* Commentaires (consigne étape 2) */
CommentaireLigne = "//" .*
CommentaireBloc  = "/*" ~"*/"
Commentaire      = {CommentaireLigne} | {CommentaireBloc}

%%

<YYINITIAL> {

    /* Espaces et Commentaires (ignorés) */
    {Espace}                  { /* ignorer */ }
    {Commentaire}             { /* ignorer */ }

    /* Mots-clés : Portée et Déclarations */
    "aliment"                 { return symbol(Sym.ALIMENT); }
    "épice"                   { return symbol(Sym.EPICE); }
    "param"                   { return symbol(Sym.PARAM); }
    "ingrédients"             { return symbol(Sym.INGREDIENTS); }
    "repas"                   { return symbol(Sym.REPAS); }
    "recette"                 { return symbol(Sym.RECETTE); }

    /* Mots-clés : Types */
    "quantité"                { return symbol(Sym.QUANTITE); }
    "cuillère"                { return symbol(Sym.CUILLERE); }
    "petite-cuillère"         { return symbol(Sym.PETITE_CUILLERE); }
    "vide"                    { return symbol(Sym.VIDE); }

    /* Mots-clés : Instructions */
    "mélanger"                { return symbol(Sym.MELANGER); }
    "servir"                  { return symbol(Sym.SERVIR); }
    "déguster"                { return symbol(Sym.DEGUSTER); }
    "cuit"                    { return symbol(Sym.CUIT); }
    "crue"                    { return symbol(Sym.CRUE); }
    "mijoter"                 { return symbol(Sym.MIJOTER); }
    "étape"                   { return symbol(Sym.ETAPE); }
    "prêt"                    { return symbol(Sym.PRET); }
    "dresser"                 { return symbol(Sym.DRESSER); }

    /* Opérateurs arithmétiques */
    "ajouter"                 { return symbol(Sym.AJOUTER); }
    "réduire"                 { return symbol(Sym.REDUIRE); }
    "monter"                  { return symbol(Sym.MONTER); }
    "fouetter"                { return symbol(Sym.FOUETTER); }
    "couper"                  { return symbol(Sym.COUPER); }

    /* Opérateurs booléens et de comparaison */
    "meilleur"                { return symbol(Sym.MEILLEUR); }
    "un-peu-meilleur"         { return symbol(Sym.UN_PEU_MEILLEUR); }
    "pas-bon"                 { return symbol(Sym.PAS_BON); }
    "un-peu-bon"              { return symbol(Sym.UN_PEU_BON); }
    "parfait"                 { return symbol(Sym.PARFAIT); }
    "étonnant"                { return symbol(Sym.ETONNANT); }
    "mangeable"               { return symbol(Sym.MANGEABLE); }
    "immangeable"             { return symbol(Sym.IMMANGEABLE); }

    /* Ponctuations : INVERSÉES (selon votre règle) */
    ")"                       { return symbol(Sym.PAR_O); }  /* Parenthese ouvrante logique */
    "("                       { return symbol(Sym.PAR_F); }  /* Parenthese fermante logique */
    "}"                       { return symbol(Sym.ACCO_O); } /* Accolade ouvrante logique */
    "{"                       { return symbol(Sym.ACCO_F); } /* Accolade fermante logique */

    /* Autres ponctuations */
    ";"                       { return symbol(Sym.PTVIRG); }
    ","                       { return symbol(Sym.VIRG); }
    "."                       { return symbol(Sym.POINT); }

    /* Valeurs dynamiques : Identifiants et Nombres */
    {Entier}                  { return symbol(Sym.ENTIER, Integer.parseInt(yytext())); }
    
    /* On place l'identifiant en dernier pour ne pas masquer les mots-clés */
    {Identifiant}             { return symbol(Sym.IDENT, yytext()); }

    /* Gestion des erreurs (caractère non reconnu) */
    [^]                       { System.err.println("Erreur lexicale à la ligne " + yyline + " : plat non reconnu -> " + yytext()); }
}