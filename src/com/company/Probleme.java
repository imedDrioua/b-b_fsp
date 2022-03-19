package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Probleme {
    int nombre_machine;
    int nombre_taches;
    int[][] matrice_temps;

    public Probleme() {  //Constructeur pour tester manuellement (ICI les lignes sont les taches et les colonnes sont les machines)
        this.nombre_machine = 3;
        this.nombre_taches = 4;
        matrice_temps = new int[4][3];
        matrice_temps[0][0] = 77;
        matrice_temps[0][1] = 11;
        matrice_temps[0][2] = 82;
        matrice_temps[1][0] = 34;
        matrice_temps[1][1] = 92;
        matrice_temps[1][2] = 8;
        matrice_temps[2][0] = 88;
        matrice_temps[2][1] = 36;
        matrice_temps[2][2] = 30;
        matrice_temps[3][0] = 1;
        matrice_temps[3][1] = 98;
        matrice_temps[3][2] = 9;
    }

    public Probleme(String fileName) {
        this.lire_fichier(fileName);
    }

    private void lire_fichier(String fileName) { // Lire la matrice à partir d'un fichier texte
        FileReader fr;
        BufferedReader br;
        String line;
        String[] tokens;
        int m = 0, n = 0, tache = 0;
        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            line = br.readLine();
            boolean param = true;
            while (line != null) {
                line = line.trim();
                if (!line.matches(";;;*.*")) { // si la line n'est pas un commentaire
                    tokens = line.split("\\s+");
                    if (param) {
                        n = Integer.parseInt(tokens[0]);
                        line = br.readLine();
                        tokens = line.split("\\s+");
                        m = Integer.parseInt(tokens[0]);
                        matrice_temps = new int[n][m];
                        this.nombre_taches = n;
                        this.nombre_machine = m;
                        param = false;
                    } else {
                        for (int i = 0; i < m; i++) {
                            matrice_temps[tache][i] = Integer.parseInt(tokens[i]);
                        }
                        tache++;
                    }
                }
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Could not find file");
            e.printStackTrace();

        } catch (IOException e) {
            System.err.println("IO exception was thrown");
            e.printStackTrace();
        }
        matrice_temps = transposeMatrix(matrice_temps);
        this.nombre_taches = m;
        this.nombre_machine = n;

    }

    public int[][] transposeMatrix(int[][] m) {
        int[][] temp = new int[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }
}