package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Probleme probleme = new Probleme("./src/p.txt"); // instancier le Problème avec un fichier
        BranchBound bb = new BranchBound(probleme); // initialiser l'algorithme
        Scanner scanner = new Scanner(System.in);
        int reponse;
        System.out.println("Veuiller indiquer la méthode d'exeution de l'algorithme :  ");
        System.out.println("1 - Trouver tous les séquences qui donne le cout optimal (L'algorithme prend beaucoup de temps).  ");
        System.out.println("2 - Trouver la première séquence qui donne le cout optimal (L'algorithme s'execute rapidement ). \n ");
        System.out.print("--- Votre choix  :  ");
        reponse = scanner.nextInt();
        bb.run(reponse == 1); // executer l'algorithme
        for (Noeud noeud : bb.solution_optimal) {
            System.out.println("\n-------------------------Solution optimale trouvée par B&B----------------------------------");
            System.out.println("La meilleure solution " + noeud.solution_partielle);
            // Le cout c'est la date de fin de la dernière tache sur la dernière machine
            System.out.println("Le cout " + noeud.matrice_in_out.get(probleme.nombre_machine - 1).get(probleme.nombre_taches - 1)[1]);
            System.out.println("--------------------------------------------------------------------------------------------");
        }
        Main.checkSolution(bb.solution_optimal, new ArrayList<>(Arrays.asList(6, 10, 17, 7, 18, 15, 12, 3, 1, 9, 11, 8, 19, 4, 13, 16, 14, 5, 20, 2)));
    }

    public static void checkSolution(ArrayList<Noeud> solutions, ArrayList<Integer> sequence) {
        //Cette fonction vérifie si une sequence donnée est trouvée par notre algorithme ou pas
        for (Noeud noeud : solutions) {
            if (noeud.solution_partielle.equals(sequence)) {
                System.out.println("Found");
                break;
            }
        }
    }
}
