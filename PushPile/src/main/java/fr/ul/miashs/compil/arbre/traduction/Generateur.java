/**
 * Exercice d'introduction à la génération de code Beta
 * @author Azim Roussanaly
 * Created at 25 févr. 2026
 */
package fr.ul.miashs.compil.arbre.traduction;
import java.util.Objects;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.arbre.tds.*;
/**
 * Générateur de code pour un arbre d'affectation
 */
public class Generateur {

    private final Tds tds;
    public Generateur(Tds tds) {
        this.tds = tds;
    }
    /**
     * Générer le code pour une affectation
     * @param aff : noeud d'affectation
     * @return code généré
     */
    public String genererAffectation(Affectation a) {
        StringBuilder code = new StringBuilder();
        code.append(genererExpression(a.getFilsDroit()));

        Noeud gauche = a.getFilsGauche();

        if (gauche instanceof Idf gaucheIdf) {
            if (this.tds.getItem(gaucheIdf.getValeur().toString()).getCategorie().equals(Categorie.LOCAL.getCategorie())) {
                int offset = (this.tds.getItem(gaucheIdf.getValeur().toString()).getRang() + 1) * -4;
                code.append("  PUTFRAME(R0, ").append(offset).append(")\n");
            } else {
                String nomVariable = gaucheIdf.getValeur().toString();
                code.append("  ST(R0").append(", ").append(nomVariable).append(")\n");
            }
        } else if (gauche instanceof Const constante) {
            int constanteValeur = constante.getValeur();
            code.append("  CMOVE(").append(constanteValeur).append(", R0").append(")\n");
            code.append("  ST(R0").append(",").append(constanteValeur).append(")\n");
        }

        return code.toString();
    }
    /**
     * Générer le code pour une expression
     * @param expr : noeud d'expression
     * @return code généré
     */
    public String genererExpression(Noeud expr) {
        StringBuffer code = new StringBuffer();
        if (expr instanceof Idf) {
            Idf idf = (Idf) expr;
            Item item= this.tds.getItem(idf.getValeur().toString());
            if (item.getCategorie().equals(Categorie.LOCAL.getCategorie())) {
                int offset = (item.getRang() + 1) * -4;
                code.append("  GETFRAME(R0, ").append(offset).append(")\n");
                code.append("  PUSH(R0)\n");
            } else if (item.getCategorie().equals(Categorie.PARAMETRE.getCategorie())) {
                String scope = item.getScope();
                int offset = (this.tds.getItem(scope).getNbParametres() + 1) - item.getRang() * -4;
                code.append("  GETFRAME(R0, ").append(offset).append(")\n");
                code.append("  PUSH(R0)\n");
            } else {
                code.append("  LD(").append(item.getNom()).append(", R0)\n");
                code.append("  PUSH(R0)\n");
            }

        } else if (expr instanceof Const) {
            Const constante = (Const) expr;
            code.append("  CMOVE(").append(constante.getValeur()).append(", R0)\n");
            code.append("  PUSH(R0)\n");

        } else if (expr instanceof Lire) {
            return genererLecture((Lire) expr);

        } else if (expr instanceof Appel) {
            return genererAppel((Appel) expr);

        } else if (expr instanceof Superieur) {
            code.append(genererExpression(expr.getFils().get(0)))
                    .append("  POP(R1)\n")
                    .append(genererExpression(expr.getFils().get(1)))
                    .append("  POP(R2)\n")
                    .append("  CMPLE(R2, R1, R0)\n");

        } else if (expr instanceof Inferieur) {
            code.append(genererExpression(expr.getFils().get(0)))
                    .append("  POP(R1)\n")
                    .append(genererExpression(expr.getFils().get(1)))
                    .append("  POP(R2)\n")
                    .append("  CMPLT(R1, R2, R0)\n");

        } else if (expr instanceof Egal) {
            code.append(genererExpression(expr.getFils().get(0)))
                    .append("  POP(R1)\n")
                    .append(genererExpression(expr.getFils().get(1)))
                    .append("  POP(R2)\n")
                    .append("  CMPEQ(R2, R1, R0)\n");

        } else if (expr instanceof InferieurEgal) {
            code.append(genererExpression(expr.getFils().get(0)))
                    .append("  POP(R1)\n")
                    .append(genererExpression(expr.getFils().get(1)))
                    .append("  POP(R2)\n")
                    .append("  CMPLE(R1, R2, R0)\n");

        } else if (expr instanceof SuperieurEgal) {
            code.append(genererExpression(expr.getFils().get(0)))
                    .append("  POP(R1)\n")
                    .append(genererExpression(expr.getFils().get(1)))
                    .append("  POP(R2)\n")
                    .append("  CMPLT(R2, R1, R0)\n");

        } else if (expr != null) {
            Noeud gauche = expr.getFils().get(0);
            Noeud droit = expr.getFils().get(1);

            if (!(droit instanceof Idf || droit instanceof Const)) {
                code.append(genererExpression(droit));
                if (!(gauche instanceof Idf || gauche instanceof Const)) {
                    code.append(genererExpression(gauche))
                            .append("  POP(R1)\n")
                            .append("  POP(R2)\n");
                } else {
                    code.append("  POP(R2)\n")
                            .append(genererExpression(gauche))
                            .append("  POP(R1)\n");
                }
            } else {
                code.append(genererExpression(gauche))
                        .append("  POP(R1)\n")
                        .append(genererExpression(droit))
                        .append("  POP(R2)\n");
            }

            if (expr instanceof Plus) {
                code.append("  ADD(R1,R2,R0)\n");
            } else if (expr instanceof Moins) {
                code.append("  SUB(R1,R2,R0)\n");
            } else if (expr instanceof Multiplication) {
                code.append("  MUL(R1,R2,R0)\n");
            } else if (expr instanceof Division) {
                code.append("  DIV(R1,R2,R0)\n");
            }
            code.append("  PUSH(R0)\n");
        }

        return code.toString();
    }

    //a revoir 
    private String genererData() {
        StringBuilder data = new StringBuilder();
        // data.append("BR(").append(this.tds.getItems().get(0).getNom()).append(")\n");
        for (Item sym : this.tds.getItems()) {
            if (sym.getCategorie().equals("global") && Objects.equals(sym.getType(), "int")) {
                data.append(sym.getNom()).append(": LONG(").append(sym.getValeur()).append(")\n");
            }
        }
        data.append("\n");
        return data.toString();
    }
    public String generer_programme(Prog porg) {
        StringBuffer code = new StringBuffer();
        code.append(".include beta.uasm\n");
        code.append(".include intio.uasm\n");
        code.append("CMOVE(pile, SP)\n");
        code.append("CALL(main)\n");
        code.append("HALT()\n");
        code.append(genererData());

        for (Noeud fonction : porg.getFils()) {
            code.append(genererFonction((Fonction) fonction));
        }        
        code.append("pile:");
        return code.toString();
    }

    public String genererFonction(Fonction f) {
        StringBuffer code = new StringBuffer();
        code.append(f.getValeur()).append(":\n");
        code.append("PUSH(LP)\n")
            .append("PUSH(BP)\n")
            .append("MOVE(SP, BP)\n")
            //.append("ALLOCATE(").append(this.tds.getItem(f.getValeur().toString()).getNbVariables()).append(")\n");
            .append("ALLOCATE(0)\n");
        for (Noeud fils : f.getFils()) {
            code.append(genererInstruction(fils));
        }
        code.append("ret_").append(f.getValeur()).append(":\n");
            // code.append("  DEALLOCATE(").append(this.tds.getItem(f.getValeur().toString()).getNbVariables()).append(")\n")
            code.append("DEALLOCATE(0)\n")
                    .append("  POP(BP)\n")
                    .append("  POP(LP)\n")
                    .append("  RTN()\n");

        
        return code.toString();
    }

    private StringBuilder append(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'append'");
    }
    public String genererEcriture(Ecrire e) {
        StringBuffer code = new StringBuffer();
        code.append(genererExpression(e.getLeFils()));
        code.append("POP(R0)\n");
        code.append("WRINT()\n");
        return code.toString();
    }

    public String genererLecture(Lire l) {
        return "  RDINT()\n" +
                "  PUSH(R0)\n";
    }

    public String genererAppel(Appel a) {
        StringBuffer code = new StringBuffer();
        if(this.tds.getItem(a.getValeur().toString()).getCategorie().equals("int")){ {
            code.append("ALLOCATE(1)\n");
        }
        for(Noeud fils : a.getFils()){
            code.append(genererExpression(fils));
        }
        code.append("CALL(").append(a.getValeur().toString()).append(")\n")
          .append("DEALLOCATE(").append(this.tds.getItem(a.getValeur().toString()).getNbParametres()).append(")\n");
        }
        return code.toString();
    }
    
    public String genererRetour(Retour r) {
        StringBuffer code = new StringBuffer();
        code.append(genererExpression(r.getLeFils()));
        code.append("POP(R0)\n");
        if (!r.getFils().isEmpty()) {
            code.append(genererExpression(r.getFils().get(0)));
            int offsetResultat = (2 + this.tds.getItem(r.getValeur().toString()).getNbVariables()) * -4;
            code.append("  POP(R0)\n")
                    .append("  PUTFRAME(R0, ").append(offsetResultat).append(")\n");
        }
        code.append("  BR(ret_").append(r.getValeur().toString()).append(")\n");
        return code.toString();
    }

    public String genererConditionnel(Si s) {
        StringBuilder code = new StringBuilder();
        code.append(genererExpression(s.getFils().get(0)))
                .append("  BT(R0, THEN_LABEL)\n");
        code.append("  BF(R0, ELSE_LABEL)\n");
        code.append("THEN_LABEL:\n")
                .append(genererInstruction(s.getFils().get(1)))
                .append("  BR(END_IF_LABEL)\n");
        code.append("ELSE_LABEL:\n")
                .append(genererInstruction(s.getFils().get(2)));
        code.append("END_IF_LABEL:\n");
        return code.toString();
    }


    public String genererIteration(TantQue tantQue) {
        StringBuilder code = new StringBuilder();
        code.append("LOOP_:").append("\n");
        tantQue.getFils().forEach(fils -> {
            if (fils instanceof Bloc) {
                code.append(genererInstruction(fils));
            } else {
                code.append(genererExpression(fils));
                code.append("  POP(R0)\n")
                        .append("  BF(R0, END_LOOP_)\n");
            }
        });
        code.append("BR(LOOP_)\n")
                .append("END_LOOP_:\n");
        return code.toString();
    }

    public String genererInstruction(Noeud instruction) {
        if (instruction instanceof Affectation a) {
            return genererAffectation(a);
        } else if (instruction instanceof Si s) {
            return genererConditionnel(s);
        } else if (instruction instanceof TantQue tantQue) {
            return genererIteration(tantQue);
        } else if (instruction instanceof Appel a) {
            return genererAppel(a);
        } else if (instruction instanceof Retour r) {
            return genererRetour(r);
        } else if (instruction instanceof Ecrire e) {
            return genererEcriture(e);
        } else if (instruction instanceof Bloc b) {
            StringBuilder code = new StringBuilder();
            for (Noeud fils : b.getFils()) {
                code.append(genererInstruction(fils));
            }
            return code.toString();
        }
        return "";
    }
}




    
// }

// package fr.ul.miashs.compil.arbre.traduction;

// import fr.ul.miashs.compil.arbre.*;
// import fr.ul.miashs.compil.arbre.tds.*;
// import fr.ul.miashs.compil.arbre.traduction.*;

// import java.util.Objects;


// public class Generateur {

//     private final Tds tds;

//     private String genererHeader() {
//         return ".include beta.uasm\n.include intio.uasm\n";
//     }

//     private String genererFooter() {
//         return "HALT()\n";
//     }

//     public Generateur(Tds tds) {
//         this.tds = tds;
//     }

//     private String genererData() {
//         StringBuilder data = new StringBuilder();
//         data.append("  BR(").append(this.tds.getItems().get(0).getNom()).append(")\n");
//         for (Item sym : this.tds.getItems()) {
//             if (sym.getCategorie().equals(Categorie.GLOBAL.getCategorie()) && Objects.equals(sym.getType(), "int")) {
//                 data.append(sym.getNom()).append(": LONG(").append(sym.getValeur()).append(")\n");
//             }
//         }
//         data.append("\n");
//         return data.toString();
//     }

//     public String genererProgramme(Prog prog) {
//         StringBuilder code = new StringBuilder(genererHeader());
//         code.append(genererData());

//         for (Noeud fonction : prog.getFils()) {
//             code.append(genererFonction((Fonction) fonction));
//         }
//         code.append(genererFooter());
//         return code.toString();
//     }

//     public String genererFonction(Fonction fonction) {
//         StringBuilder code = new StringBuilder();
//         code.append(fonction.getValeur()).append(":\n");
//         if (!Objects.equals(this.tds.getItem(fonction.getValeur().toString()).getType(), "void")) {
//             code.append("  PUSH(LP)\n")
//                     .append("  PUSH(BP)\n")
//                     .append("  MOVE(SP, BP)\n");
//         }
//         int nbVariables = this.tds.getItem(fonction.getValeur().toString()).getNbVariables();
//         nbVariables = nbVariables == -1 ? 0 : nbVariables;
//         code.append("  ALLOCATE(").append(nbVariables).append(")\n");
//         for (Noeud instruction : fonction.getFils()) {
//             code.append(genererInstruction(instruction));
//         }

//         if (!Objects.equals(this.tds.getItem(fonction.getValeur().toString()).getType(), "void")) {
//             code.append("ret_").append(fonction.getValeur()).append(":\n");
//             code.append("  DEALLOCATE(").append(nbVariables).append(")\n")
//                     .append("  POP(BP)\n")
//                     .append("  POP(LP)\n")
//                     .append("  RTN()\n");
//         }

//         return code.toString();
//     }

//     public String genererAffectation(Affectation a) {
//         StringBuilder code = new StringBuilder();
//         code.append(genererExpression(a.getFilsDroit()));

//         Noeud gauche = a.getFilsGauche();

//         if (gauche instanceof Idf gaucheIdf) {
//             Item item = this.tds.getItem(gaucheIdf.getValeur().toString());
//             if (item.getCategorie().equals(Categorie.LOCAL.getCategorie())) {
//                 int offset = (item.getRang() + 1) * -4;
//                 code.append("  PUTFRAME(R0, ").append(offset).append(")\n");
//             } else {
//                 String nomVariable = gaucheIdf.getValeur().toString();
//                 code.append("  ST(R0").append(", ").append(nomVariable).append(")\n");
//             }
//         } else if (gauche instanceof Const constante) {
//             int constanteValeur = constante.getValeur();
//             code.append("  CMOVE(").append(constanteValeur).append(", R0").append(")\n");
//             code.append("  ST(R0").append(",").append(constanteValeur).append(")\n");
//         }

//         return code.toString();
//     }


//     public String genererExpression(Noeud n) {
//         StringBuilder code = new StringBuilder();

//         if (n instanceof Idf) {
//             Idf idf = (Idf) n;
//             Item symb = this.tds.getItem(idf.getValeur().toString());
//             if (symb.getCategorie().equals(Categorie.LOCAL.getCategorie())) {
//                 int offset = (symb.getRang() + 1) * -4;
//                 code.append("  GETFRAME(R0, ").append(offset).append(")\n");
//                 code.append("  PUSH(R0)\n");
//             } else if (symb.getCategorie().equals(Categorie.PARAMETRE.getCategorie())) {
//                 String scope = symb.getScope();
//                 int offset = (this.tds.getItem(scope).getNbParametres() + 1) - symb.getRang() * -4;
//                 code.append("  GETFRAME(R0, ").append(offset).append(")\n");
//                 code.append("  PUSH(R0)\n");
//             } else {
//                 code.append("  LD(").append(symb.getNom()).append(", R0)\n");
//                 code.append("  PUSH(R0)\n");
//             }

//         } else if (n instanceof Const) {
//             Const constante = (Const) n;
//             code.append("  CMOVE(").append(constante.getValeur()).append(", R0)\n");
//             code.append("  PUSH(R0)\n");

//         } else if (n instanceof Lire) {
//             return genererLecture((Lire) n);

//         } else if (n instanceof Appel) {
//             return genererAppel((Appel) n);

//         } else if (n instanceof Superieur) {
//             code.append(genererExpression(n.getFils().get(0)))
//                     .append("  POP(R1)\n")
//                     .append(genererExpression(n.getFils().get(1)))
//                     .append("  POP(R2)\n")
//                     .append("  CMPLE(R2, R1, R0)\n");

//         } else if (n instanceof Inferieur) {
//             code.append(genererExpression(n.getFils().get(0)))
//                     .append("  POP(R1)\n")
//                     .append(genererExpression(n.getFils().get(1)))
//                     .append("  POP(R2)\n")
//                     .append("  CMPLT(R1, R2, R0)\n");

//         } else if (n instanceof Egal) {
//             code.append(genererExpression(n.getFils().get(0)))
//                     .append("  POP(R1)\n")
//                     .append(genererExpression(n.getFils().get(1)))
//                     .append("  POP(R2)\n")
//                     .append("  CMPEQ(R2, R1, R0)\n");

//         } else if (n instanceof InferieurEgal) {
//             code.append(genererExpression(n.getFils().get(0)))
//                     .append("  POP(R1)\n")
//                     .append(genererExpression(n.getFils().get(1)))
//                     .append("  POP(R2)\n")
//                     .append("  CMPLE(R1, R2, R0)\n");

//         } else if (n instanceof SuperieurEgal) {
//             code.append(genererExpression(n.getFils().get(0)))
//                     .append("  POP(R1)\n")
//                     .append(genererExpression(n.getFils().get(1)))
//                     .append("  POP(R2)\n")
//                     .append("  CMPLT(R2, R1, R0)\n");

//         } else if (n != null) {
//             Noeud gauche = n.getFils().get(0);
//             Noeud droit = n.getFils().get(1);

//             if (!(droit instanceof Idf || droit instanceof Const)) {
//                 code.append(genererExpression(droit));
//                 if (!(gauche instanceof Idf || gauche instanceof Const)) {
//                     code.append(genererExpression(gauche))
//                             .append("  POP(R1)\n")
//                             .append("  POP(R2)\n");
//                 } else {
//                     code.append("  POP(R2)\n")
//                             .append(genererExpression(gauche))
//                             .append("  POP(R1)\n");
//                 }
//             } else {
//                 code.append(genererExpression(gauche))
//                         .append("  POP(R1)\n")
//                         .append(genererExpression(droit))
//                         .append("  POP(R2)\n");
//             }

//             if (n instanceof Plus) {
//                 code.append("  ADD(R1,R2,R0)\n");
//             } else if (n instanceof Moins) {
//                 code.append("  SUB(R1,R2,R0)\n");
//             } else if (n instanceof Multiplication) {
//                 code.append("  MUL(R1,R2,R0)\n");
//             } else if (n instanceof Division) {
//                 code.append("  DIV(R1,R2,R0)\n");
//             }
//             code.append("  PUSH(R0)\n");
//         }

//         return code.toString();
//     }


//     public String genererEcriture(Ecrire e) {
//         return genererExpression(e.getFils().get(0)) +
//                 "  POP(R0)\n" +
//                 "  WRINT()\n";
//     }

//     public String genererLecture(Lire l) {
//         return "  RDINT()\n" +
//                 "  PUSH(R0)\n";
//     }

//     public String genererRetour(Retour r) {
//         StringBuilder code = new StringBuilder();
//         if (!r.getFils().isEmpty()) {
//             code.append(genererExpression(r.getFils().get(0)));
//             int offsetResultat = (2 + this.tds.getItem(r.getValeur().toString()).getNbVariables()) * -4;
//             code.append("  POP(R0)\n")
//                     .append("  PUTFRAME(R0, ").append(offsetResultat).append(")\n");
//         }
//         code.append("  BR(ret_").append(r.getValeur().toString()).append(")\n");
//         return code.toString();
//     }

//     public String genererAppel(Appel a) {
//         StringBuilder code = new StringBuilder();
//         if (this.tds.getItem(a.getValeur().toString()).getType().equals("int")) {
//             code.append("ALLOCATE(1)\n");
//         }
//         for (Noeud fils : a.getFils()) {
//             code.append(genererExpression(fils));
//         }
//         code.append("  CALL(").append(a.getValeur().toString()).append(")\n")
//                 .append("  DEALLOCATE(").append(this.tds.getItem(a.getValeur().toString()).getNbParametres()).append(")\n");
//         return code.toString();
//     }

//     public String genererConditionnel(Si s) {
//         StringBuilder code = new StringBuilder();
//         code.append(genererExpression(s.getFils().get(0)))
//                 .append("  BT(R0, THEN_LABEL)\n");
//         code.append("  BF(R0, ELSE_LABEL)\n");
//         code.append("THEN_LABEL:\n")
//                 .append(genererInstruction(s.getFils().get(1)))
//                 .append("  BR(END_IF_LABEL)\n");
//         code.append("ELSE_LABEL:\n")
//                 .append(genererInstruction(s.getFils().get(2)));
//         code.append("END_IF_LABEL:\n");
//         return code.toString();
//     }

//     public String genererIteration(TantQue tantQue) {
//         StringBuilder code = new StringBuilder();
//         code.append("LOOP :").append("\n");
//         tantQue.getFils().forEach(fils -> {
//             if (fils instanceof Bloc) {
//                 code.append(genererInstruction(fils));
//             } else {
//                 code.append(genererExpression(fils));
//                 code.append("  POP(R0)\n")
//                         .append("  BF(R0, END_LOOP)\n");
//             }
//         });
//         code.append("BR(LOOP)\n")
//                 .append("END_LOOP:\n");
//         return code.toString();
//     }

//     public String genererInstruction(Noeud instruction) {
//         if (instruction instanceof Affectation a) {
//             return genererAffectation(a);
//         } else if (instruction instanceof Si s) {
//             return genererConditionnel(s);
//         } else if (instruction instanceof TantQue tantQue) {
//             return genererIteration(tantQue);
//         } else if (instruction instanceof Appel a) {
//             return genererAppel(a);
//         } else if (instruction instanceof Retour r) {
//             return genererRetour(r);
//         } else if (instruction instanceof Ecrire e) {
//             return genererEcriture(e);
//         } else if (instruction instanceof Bloc b) {
//             StringBuilder code = new StringBuilder();
//             for (Noeud fils : b.getFils()) {
//                 code.append(genererInstruction(fils));
//             }
//             return code.toString();
//         }
//         return "";
//     }
// }
