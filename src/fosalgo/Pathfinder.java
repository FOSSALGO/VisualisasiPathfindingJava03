package fosalgo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Pathfinder {

    private static int[][] clone(int[][] arrSatu) {
        int[][] arrDua = null;
        if (arrSatu != null) {
            arrDua = new int[arrSatu.length][];
            for (int i = 0; i < arrSatu.length; i++) {
                arrDua[i] = new int[arrSatu[i].length];
                for (int j = 0; j < arrSatu[i].length; j++) {
                    arrDua[i][j] = arrSatu[i][j];
                }
            }
        }
        return arrDua;
    }

    public static ArrayList<Titik[]> depthFirstSearch(int[][] labyrinth, Titik start, Titik finish) {
        ArrayList<Titik[]> graph = new ArrayList<>();
        int[][] solution = clone(labyrinth);
        int numRows = solution.length;
        int numCols = solution[0].length;
        //----------------------------------------------------------------------
        Stack<Titik> jejak = new Stack<>();
        // Tentukan Titik Start
        solution[finish.x][finish.y] = 0;
        int step = 1;
        solution[start.x][start.y] = step;
        jejak.add(start);

        // Menentukan Arah
        int arah = 0; // 0 = Utara, 1 = Timur, 2 = Selatan, 3 = Barat

        // Memulai Pencariadn DFS
        while (!jejak.isEmpty()) {
            Titik center = jejak.peek();
            int x = center.x;
            int y = center.y;

            //Cek Titik Finish
            if (finish.compare(center)) {
                //save graph
                for (int i = 1; i < jejak.size(); i++) {
                    Titik origin = jejak.get(i - 1);
                    Titik destination = jejak.get(i);
                    Titik[] edge = {origin, destination};
                    graph.add(edge);
                }
                System.out.println("Finish");
                break;
            } else {//cari titik lain
                // Identifikasi Titik Depan Kanan & Kiri
                Titik depan = null;
                Titik kanan = null;
                Titik kiri = null;
                Titik belakang = null;

                if (arah == 0) {
                    depan = new Titik(x - 1, y);
                    kanan = new Titik(x, y + 1);
                    kiri = new Titik(x, y - 1);
                    belakang = new Titik(x + 1, y);
                } else if (arah == 1) {
                    depan = new Titik(x, y + 1);
                    kanan = new Titik(x + 1, y);
                    kiri = new Titik(x - 1, y);
                    belakang = new Titik(x, y - 1);
                } else if (arah == 2) {
                    depan = new Titik(x + 1, y);
                    kanan = new Titik(x, y - 1);
                    kiri = new Titik(x, y + 1);
                    belakang = new Titik(x - 1, y);
                } else if (arah == 3) {
                    depan = new Titik(x, y - 1);
                    kanan = new Titik(x - 1, y);
                    kiri = new Titik(x + 1, y);
                    belakang = new Titik(x, y + 1);
                }

                //cek titik finish di sekitar
                if (finish.compare(depan)) {
                    step++;
                    solution[depan.x][depan.y] = step;
                    jejak.add(depan);
                } else if (finish.compare(kanan)) {
                    step++;
                    solution[kanan.x][kanan.y] = step;
                    jejak.add(kanan);
                } else if (finish.compare(kiri)) {
                    step++;
                    solution[kiri.x][kiri.y] = step;
                    jejak.add(kiri);
                } else if (finish.compare(belakang)) {
                    step++;
                    solution[belakang.x][belakang.y] = step;
                    jejak.add(belakang);
                }else {
                    //DO DFS
                    if (depan != null && depan.x >= 0 && depan.x < numRows && depan.y >= 0 && depan.y < numCols && solution[depan.x][depan.y] == 0) {
                        step++;
                        solution[depan.x][depan.y] = step;
                        jejak.add(depan);
                    } else if (kanan != null && kanan.x >= 0 && kanan.x < numRows && kanan.y >= 0 && kanan.y < numCols && solution[kanan.x][kanan.y] == 0) {
                        step++;
                        solution[kanan.x][kanan.y] = step;
                        jejak.add(kanan);
                        arah = (arah + 1) % 4;
                    } else if (kiri != null && kiri.x >= 0 && kiri.x < numRows && kiri.y >= 0 && kiri.y < numCols && solution[kiri.x][kiri.y] == 0) {
                        step++;
                        solution[kiri.x][kiri.y] = step;
                        jejak.add(kiri);
                        arah = (arah + 3) % 4;
                    } else {
                        step--;
                        solution[x][y] = -2;
                        jejak.pop();
                    }
                }

            }

        }//end of while pencarian

        return graph;
    }

    public static ArrayList<Titik[]> breadthFirstSearch(int[][] labyrinth, Titik start, Titik finish) {
        ArrayList<Titik[]> graph = new ArrayList<>();
        int[][] solution = clone(labyrinth);
        int numRows = solution.length;
        int numCols = solution[0].length;
        //----------------------------------------------------------------------
        LinkedList<Titik> listTitik = new LinkedList<>();
        listTitik.add(start);

        solution[finish.x][finish.y] = 0;
        int step = 1;
        solution[start.x][start.y] = step;

        boolean selesai = false;

        pencarian:
        while (!selesai && !listTitik.isEmpty()) {
            Titik center = listTitik.pollFirst();
            int x = center.x;
            int y = center.y;

            step = solution[x][y];
            step++;//increment step;

            //NORT--------------------------------------------------------------
            int i = x - 1;
            int j = y;
            if (i >= 0 && i < numRows && j >= 0 && j < numCols && solution[i][j] == 0) {
                solution[i][j] = step;
                Titik titikBaru = new Titik(i, j);
                listTitik.add(titikBaru);
                Titik[] edge = {center, titikBaru};
                graph.add(edge);
                if (finish.compare(titikBaru)) {
                    selesai = true;
                    break pencarian;
                }
            }
            //EAST--------------------------------------------------------------
            i = x;
            j = y + 1;
            if (i >= 0 && i < numRows && j >= 0 && j < numCols && solution[i][j] == 0) {
                solution[i][j] = step;
                Titik titikBaru = new Titik(i, j);
                listTitik.add(titikBaru);
                Titik[] edge = {center, titikBaru};
                graph.add(edge);
                if (finish.compare(titikBaru)) {
                    selesai = true;
                    break pencarian;
                }
            }

            //SOUTH-------------------------------------------------------------
            i = x + 1;
            j = y;
            if (i >= 0 && i < numRows && j >= 0 && j < numCols && solution[i][j] == 0) {
                solution[i][j] = step;
                Titik titikBaru = new Titik(i, j);
                listTitik.add(titikBaru);
                Titik[] edge = {center, titikBaru};
                graph.add(edge);
                if (finish.compare(titikBaru)) {
                    selesai = true;
                    break pencarian;
                }
            }

            //WEST--------------------------------------------------------------
            i = x;
            j = y - 1;
            if (i >= 0 && i < numRows && j >= 0 && j < numCols && solution[i][j] == 0) {
                solution[i][j] = step;
                Titik titikBaru = new Titik(i, j);
                listTitik.add(titikBaru);
                Titik[] edge = {center, titikBaru};
                graph.add(edge);
                if (finish.compare(titikBaru)) {
                    selesai = true;
                    break pencarian;
                }
            }

        }//End of Proses Pencarian BFS        

        return graph;
    }

}
