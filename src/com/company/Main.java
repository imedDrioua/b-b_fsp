package com.company;

public class Main {

    public static void main(String[] args) {
        Probleme probleme = new Probleme("./src/p.txt"); // instancier le Problème avec un fichier
        BranchBound bb = new BranchBound(probleme); // initialiser l'algorithme
        bb.run(); // executer l'algorithme
        System.out.println("-------------------------Solution optimale trouvée par B&B----------------------------------");
        System.out.println("La meilleure solution " + bb.solution_optimal.solution_partielle);
        // Le cout c'est la date de fin de la dernière tache sur la dernière machine
        System.out.println("Le cout " + bb.solution_optimal.matrice_in_out.get(probleme.nombre_machine - 1).get(probleme.nombre_taches - 1)[1]);
        System.out.println("--------------------------------------------------------------------------------------------");
    }
}
