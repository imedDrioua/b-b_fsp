package com.company;
import java.util.ArrayList;

public class BranchBound {
    Probleme probleme;
    ArrayList<Noeud> noeuds_actifs;
    Noeud solution_optimal;
    int lower_bound = (int) 1e6;
    int best = (int) 1e6;
    boolean check = false;
    Noeud courant = null;

    public BranchBound(Probleme probleme) {
        this.probleme = probleme;
        noeuds_actifs = new ArrayList<Noeud>();
        solution_optimal = null;
    }

    public void run() {
        for (int i = 0; i < probleme.nombre_taches; i++) {
            ArrayList<Integer> sol_part = new ArrayList<Integer>();
            sol_part.add(i);
            Noeud noeud = new Noeud(sol_part, this.probleme);
            noeuds_actifs.add(noeud);
            if (courant == null) {
                courant = noeud;
            } else if (noeud.upper_bound < courant.upper_bound) {
                courant = noeud;
            }
        }
        boolean cont = true;
        while (noeuds_actifs.size() != 0 && cont) {
            if (!check) this.lower_bound = this.get_lower_bound();
            Noeud noeud_a_deviser = null;
            int j = 0;
            while (j < noeuds_actifs.size()) {
                if ((!check && noeuds_actifs.get(j).upper_bound <= lower_bound) || (check && noeuds_actifs.get(j).upper_bound < this.best)) {
                    if (!check) noeud_a_deviser = courant;
                    else {
                        lower_bound = noeuds_actifs.get(j).upper_bound;
                        noeud_a_deviser = noeuds_actifs.get(j);
                    }
                } else if (check) {
                    noeuds_actifs.remove(j);
                    j--;
                }
                j++;
            }
            if (noeud_a_deviser != null) this.branch(noeud_a_deviser);
            else cont = false;
        }
    }

    private void branch(Noeud noeud) {
        ArrayList<Integer> taches_rest = noeud.reste_solution;
        noeuds_actifs.remove(noeud);
        this.lower_bound = this.get_lower_bound();
        if (taches_rest.size() == 1) {
            int rest = noeud.reste_solution.get(0);
            ArrayList<Integer> part_sol = new ArrayList<>(noeud.solution_partielle);
            part_sol.add(rest);
            Noeud fils = new Noeud(part_sol, this.probleme);
            if (fils.upper_bound < this.best) {
                solution_optimal = fils;
                this.check = true;
                this.best = fils.upper_bound;
                System.out.println("-----------------------------------------------------------");
                System.out.println("Une solution réalisable a été trouvée avec un makespane de : " + fils.upper_bound);
                System.out.println("La séquence correspondante : " + fils.solution_partielle);
                System.out.println("Recherche d'une solution optimale ... ");
                System.out.println("-----------------------------------------------------------");
            }
        } else {
            courant = null;
            for (int j : taches_rest) {
                ArrayList<Integer> part_sol = new ArrayList<>(noeud.solution_partielle);
                part_sol.add(j);
                Noeud fils = new Noeud(part_sol, this.probleme);
                if (courant == null) {
                    courant = fils;
                } else {
                    if (fils.upper_bound <= courant.upper_bound) {
                        courant = fils;
                    }
                }
                if (check) {

                    if (fils.upper_bound <= this.best) {
                        noeuds_actifs.add(fils);

                    }
                } else {
                    noeuds_actifs.add(fils);
                }
            }
        }
    }

    private int get_lower_bound() {
        int lower = (int) 1e6;
        for (Noeud node : noeuds_actifs) {
            if (node.upper_bound < lower) {
                lower = node.upper_bound;
            }
        }
        return lower;
    }

}
