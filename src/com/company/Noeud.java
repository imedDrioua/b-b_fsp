package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class Noeud {
      Probleme probleme;
      ArrayList<Integer> solution_partielle;
      ArrayList<Integer> reste_solution;
      ArrayList<HashMap<Integer,Integer[]>> matrice_in_out;
      int upper_bound;

      public Noeud(ArrayList<Integer> sol_part,Probleme probleme)
      {
            this.probleme = probleme;
            this.solution_partielle = sol_part;
            this.reste_solution=new ArrayList<Integer>();
            matrice_in_out = new ArrayList<HashMap<Integer,Integer[]>>();

            for(int i=0;i < probleme.nombre_taches; i++){
                  if (! sol_part.contains(i)) reste_solution.add(i);
            }

            int time_d=0;
            int time_fin=0;
            for(int j=0;j<probleme.nombre_machine;j++){
                  int indice =0;
                   matrice_in_out.add(j,new HashMap<Integer,Integer[]>());
                  if(j != 0) time_d = matrice_in_out.get(j-1).get(0)[1];
                 for (int i : sol_part){
                       if(j!= 0 && indice != 0)
                             time_d = Math.max(matrice_in_out.get(j).get(indice-1)[1],matrice_in_out.get(j-1).get(indice)[1]);
                       time_fin = time_d + probleme.matrice_temps[i][j];
                       Integer duree[]={time_d,time_fin};
                       matrice_in_out.get(j).put(indice,duree);
                       indice ++;
                       time_d = time_fin;
                 }
            }
           this.calculateUpperBound();

      }

      public void calculateUpperBound() {
            int upper_bound_c= -1;
            for (int i=0;i<probleme.nombre_machine;i++)
            {
                  int q = matrice_in_out.get(i).get(solution_partielle.size()-1)[1];
                  int temp_rest=0;
                  for(int k : reste_solution) temp_rest += probleme.matrice_temps[k][i];
                  int b= q + temp_rest + calculer_somme(i);
                  if(b>upper_bound_c) upper_bound_c = b;
            }
            this.upper_bound =upper_bound_c ;
      }
      int calculer_somme(int machine){

            if (machine == probleme.nombre_machine-1) return 0 ;
            if(this.reste_solution.size() !=0) {
                  int min = (int) 1e6;
                  for (int k : reste_solution) {
                        int somme = 0;
                        for (int j = machine + 1; j < probleme.nombre_machine; j++) {
                              somme += probleme.matrice_temps[k][j];
                        }
                        if (somme < min) {
                              min = somme;
                        }
                  }
                  return min;
            }
            return 0;
      }

      @Override
      public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Noeud noeud = (Noeud) o;
            return upper_bound == noeud.upper_bound && Objects.equals(probleme, noeud.probleme) && solution_partielle.equals(noeud.solution_partielle) && reste_solution.equals(noeud.reste_solution) && Objects.equals(matrice_in_out, noeud.matrice_in_out);
      }


      @Override
      public int hashCode() {
            return Objects.hash(probleme, solution_partielle, reste_solution, matrice_in_out, upper_bound);
      }
}
