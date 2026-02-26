package fr.ul.miashs.compil.arbre.traduction;

import java.util.Objects;
import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.arbre.tds.*;

public class Generateur {

    private final Tds tds;
    private int labelCount = 0;

    public Generateur(Tds tds) {
        this.tds = tds;
    }

    private String getNextLabel(String base) {
        return base + "_" + (labelCount++);
    }

    // --- PROGRAMME ET DATA ---
    public String generer_programme(Prog prog) {
        StringBuilder code = new StringBuilder();
        code.append(".include beta.uasm\n");
        code.append(".include intio.uasm\n");
        code.append(".options tty\n\n");

        code.append("CMOVE(pile, SP)\n");
        code.append("CALL(main)\n");
        code.append("HALT()\n\n");

        code.append(genererData());

        for (Noeud fonction : prog.getFils()) {
            code.append(genererFonction((Fonction) fonction));
        }

        code.append("\npile: LONG(0)\n");
        return code.toString();
    }

    private String genererData() {
        StringBuilder data = new StringBuilder();
        for (Item sym : this.tds.getItems()) {
            if (sym.getCategorie().equals(Categorie.GLOBAL.getCategorie())) {
                data.append(sym.getNom()).append(": LONG(").append(sym.getValeur()).append(")\n");
            }
        }
        data.append("\n");
        return data.toString();
    }

    // --- FONCTIONS ---
    public String genererFonction(Fonction f) {
        StringBuilder code = new StringBuilder();
        String nomF = f.getValeur().toString();
        Item funcItem = this.tds.getItem(nomF);

        int nbLocaux = Math.max(0, funcItem.getNbVariables());

        code.append(nomF).append(":\n");
        code.append("\tPUSH(LP)\n")
            .append("\tPUSH(BP)\n")
            .append("\tMOVE(SP, BP)\n");

        if (nbLocaux > 0) {
            code.append("\tALLOCATE(").append(nbLocaux).append(")\n");
        }

        for (Noeud fils : f.getFils()) {
            code.append(genererInstruction(fils));
        }

        code.append("ret_").append(nomF).append(":\n");
        if (nbLocaux > 0) {
            code.append("\tDEALLOCATE(").append(nbLocaux).append(")\n");
        }
        code.append("\tPOP(BP)\n")
            .append("\tPOP(LP)\n")
            .append("\tRTN()\n\n");

        return code.toString();
    }

    // --- EXPRESSIONS ---
    public String genererExpression(Noeud expr) {
        if (expr == null) return "";
        StringBuilder code = new StringBuilder();

        if (expr instanceof Idf idf) {
            Item item = this.tds.getItem(idf.getValeur().toString());
            if (item.getCategorie().equals(Categorie.LOCAL.getCategorie())) {
                int offset = (item.getRang() + 1) * -4;
                code.append("\tGETFRAME(R0, ").append(offset).append(")\n");
            } else if (item.getCategorie().equals(Categorie.PARAMETRE.getCategorie())) {
                // Cadre Beta : BP+0=ancienBP, BP+4=LP, BP+8=adresseRetourCALL
                // parametre rang 0 (dernier poussé) est à BP+12
                // formule : (nbParam + 2 - rang) * 4
                String scope = item.getScope();
                int nbParam = this.tds.getItem(scope).getNbParametres();
                int offset = (nbParam + 2 - item.getRang()) * 4;
                code.append("\tGETFRAME(R0, ").append(offset).append(")\n");
            } else {
                code.append("\tLD(").append(item.getNom()).append(", R0)\n");
            }
            code.append("\tPUSH(R0)\n");

        } else if (expr instanceof Const c) {
            code.append("\tCMOVE(").append(c.getValeur()).append(", R0)\n");
            code.append("\tPUSH(R0)\n");

        } else if (expr instanceof Lire) {
            code.append("\tRDINT()\n\tPUSH(R0)\n");

        } else if (expr instanceof Appel a) {
            code.append(genererAppel(a));

        } else if (expr instanceof Plus || expr instanceof Moins || expr instanceof Multiplication || expr instanceof Division) {
            code.append(genererExpression(expr.getFils().get(0)));
            code.append(genererExpression(expr.getFils().get(1)));
            code.append("\tPOP(R2)\n\tPOP(R1)\n");

            if (expr instanceof Plus) code.append("\tADD(R1, R2, R0)\n");
            else if (expr instanceof Moins) code.append("\tSUB(R1, R2, R0)\n");
            else if (expr instanceof Multiplication) code.append("\tMUL(R1, R2, R0)\n");
            else if (expr instanceof Division) code.append("\tDIV(R1, R2, R0)\n");

            code.append("\tPUSH(R0)\n");

        } else if (expr instanceof Superieur || expr instanceof Inferieur || expr instanceof Egal || expr instanceof InferieurEgal || expr instanceof SuperieurEgal) {
            code.append(genererExpression(expr.getFils().get(0)));
            code.append(genererExpression(expr.getFils().get(1)));
            code.append("\tPOP(R2)\n\tPOP(R1)\n");

            if (expr instanceof Superieur) code.append("\tCMPLT(R2, R1, R0)\n");
            else if (expr instanceof Inferieur) code.append("\tCMPLT(R1, R2, R0)\n");
            else if (expr instanceof Egal) code.append("\tCMPEQ(R1, R2, R0)\n");
            else if (expr instanceof InferieurEgal) code.append("\tCMPLE(R1, R2, R0)\n");
            else if (expr instanceof SuperieurEgal) code.append("\tCMPLE(R2, R1, R0)\n");

            code.append("\tPUSH(R0)\n");
        }
        return code.toString();
    }

    // --- INSTRUCTIONS ---
    public String genererInstruction(Noeud i) {
        if (i instanceof Affectation a) return genererAffectation(a);
        if (i instanceof Ecrire e) return genererEcriture(e);
        if (i instanceof Si s) return genererConditionnel(s);
        if (i instanceof TantQue t) return genererIteration(t);
        if (i instanceof Retour r) return genererRetour(r);
        if (i instanceof Appel a) return genererAppel(a);
        if (i instanceof Bloc b) {
            StringBuilder sb = new StringBuilder();
            for (Noeud f : b.getFils()) sb.append(genererInstruction(f));
            return sb.toString();
        }
        return "";
    }

    public String genererAffectation(Affectation a) {
        StringBuilder code = new StringBuilder();
        code.append(genererExpression(a.getFilsDroit()));
        code.append("\tPOP(R0)\n");

        Idf gauche = (Idf) a.getFilsGauche();
        Item item = this.tds.getItem(gauche.getValeur().toString());

        if (item.getCategorie().equals(Categorie.LOCAL.getCategorie())) {
            int offset = (item.getRang() + 1) * -4;
            code.append("\tPUTFRAME(R0, ").append(offset).append(")\n");
        } else {
            code.append("\tST(R0, ").append(item.getNom()).append(")\n");
        }
        return code.toString();
    }

    public String genererAppel(Appel a) {
        StringBuilder code = new StringBuilder();
        Item func = this.tds.getItem(a.getValeur().toString());

        if (!func.getType().equals("void")) code.append("\tALLOCATE(1)\n");

        for (Noeud arg : a.getFils()) code.append(genererExpression(arg));

        code.append("\tCALL(").append(a.getValeur()).append(")\n");
        code.append("\tDEALLOCATE(").append(func.getNbParametres()).append(")\n");

        return code.toString();
    }

    public String genererRetour(Retour r) {
        StringBuilder code = new StringBuilder();
        code.append(genererExpression(r.getLeFils()));
        code.append("\tPOP(R0)\n");

        Item func = this.tds.getItem(r.getValeur().toString());
        // slot retour = BP + (nbParam+3)*4
        int nbParam = Math.max(0, func.getNbParametres());
        int offsetRes = (nbParam + 3) * 4;
        code.append("\tPUTFRAME(R0, ").append(offsetRes).append(")\n");
        code.append("\tBR(ret_").append(r.getValeur()).append(")\n");
        return code.toString();
    }

    public String genererConditionnel(Si s) {
        String labelElse = getNextLabel("else");
        String labelEnd = getNextLabel("endif");
        StringBuilder code = new StringBuilder();

        code.append(genererExpression(s.getCondition()));
        code.append("\tPOP(R0)\n");
        code.append("\tBF(R0, ").append(labelElse).append(")\n");

        code.append(genererInstruction(s.getBlocAlors()));
        code.append("\tBR(").append(labelEnd).append(")\n");

        code.append(labelElse).append(":\n");
        if (s.getBlocSinon() != null) code.append(genererInstruction(s.getBlocSinon()));

        code.append(labelEnd).append(":\n");
        return code.toString();
    }

    public String genererIteration(TantQue t) {
        String labelLoop = getNextLabel("loop");
        String labelEnd = getNextLabel("endloop");
        StringBuilder code = new StringBuilder();

        code.append(labelLoop).append(":\n");
        code.append(genererExpression(t.getCondition()));
        code.append("\tPOP(R0)\n");
        code.append("\tBF(R0, ").append(labelEnd).append(")\n");

        code.append(genererInstruction(t.getBloc()));
        code.append("\tBR(").append(labelLoop).append(")\n");

        code.append(labelEnd).append(":\n");
        return code.toString();
    }

    public String genererEcriture(Ecrire e) {
        return genererExpression(e.getLeFils()) + "\tPOP(R0)\n\tWRINT()\n";
    }
}