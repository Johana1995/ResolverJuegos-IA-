/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * 
 */
package sudokuheuristica;

import java.util.LinkedList;

/**
 *
 * @author johana huiza zamora 
 */
public class SudokuHeuristica {

    /**
     * @param args the command line arguments
     */
    static int vueltas;
    public static void main(String[] args) {
       int m[][]={
           {0,0,0,0,0,0,0,0,0},
           {0,0,0,0,0,0,0,0,0},
           {0,0,0,0,0,0,0,0,0},
           
           {0,0,0,0,0,0,0,0,0},
           {0,0,0,0,0,0,0,0,0},
           {0,0,0,0,0,0,0,0,0},
           
           {0,0,0,0,0,0,0,0,0},
           {0,0,0,0,0,0,0,0,0},
           {0,0,0,0,0,0,0,0,0}
       };
       int ma[][]={
           {1,0,0,0,0,0,0,0,0},
           {0,2,0,0,0,0,0,0,0},
           {0,0,3,0,0,0,0,0,0},
           
           {0,0,0,4,0,0,0,0,0},
           {0,0,0,0,5,0,0,0,0},
           {0,0,0,0,0,6,0,0,0},
           
           {0,0,0,0,0,0,7,0,0},
           {0,0,0,0,0,0,0,8,0},
           {0,0,0,0,0,0,0,0,9}
       };
        resolverSudoku(ma);
    }

    private static void resolverSudoku(int[][] m) {
        vueltas=0;
        int h=(int)Math.sqrt(m.length);//altura
        LinkedList<Area> l= getOrdenLlenado(m,h);
        if (!sudoku(l,0,l.getFirst().fil,l.getFirst().col,h,m)) {
            System.out.println("No hay Solucion :(");
        } else {
            System.out.println("Vueltas "+vueltas);
        }
    }

    private static LinkedList<Area> getOrdenLlenado(int[][] m, int h) {
        LinkedList<Area> l=new LinkedList<>();
        int a,b;
        for (int i = 0; i < h; i++) {
            a=i*h;
            for (int j = 0; j < h; j++) {
                b=j*h;
                l.add(new Area(a, b, cantidadVaciosEnArea(a, b, h, m)));
            }
        }
        Collections.sort(l, new Comparator<Area>() {
        @Override public int compare(final Area o1, final Area o2) {
        if (o1.vacios> o2.vacios) {
         return 1;
        } else if (o1.vacios < o2.vacios ) {
        return -1;
       }  
      return 0;
        } 
       });

        return l;
    }

    private static boolean sudoku(LinkedList<Area> l, int pos, int fil, int col, int h, int[][] m) {
        if (termino(m)) {
            mostrar(m);return true;
        }
        Area c= l.get(pos);
        if (fil>= c.fil+h) {
            if (pos+1>= l.size()) {
                return false;
            } else {
                return sudoku(l, pos+1, l.get(pos+1).fil, l.get(pos).col, h, m);
            }
        }
        if (col>= c.col+h) {
            return sudoku(l, pos, fil+1, c.col, h, m);
        }
        if (m[fil][col]!=0) {
            return sudoku(l, pos, fil, col+1, h, m);
        }
        LinkedList<Integer> l1= getValores(m,fil,col,h);
        while (!l1.isEmpty()) {            
            m[fil][col]=l1.removeFirst();
            if (sudoku(l, pos, fil, col+1, h, m)) {
                return true;
            }
            vueltas++;
            m[fil][col]=0;
        }
        return false;
    }

    private static int cantidadVaciosEnArea(int a, int b, int h, int[][] m) {
        int c=0;
        for (int i = a; i < a+h; i++) {
            for (int j = b; j < b+h; j++) {
                if (m[i][j]==0) {
                    c++;
                }
            }
        }
        return c;
    }

    private static boolean termino(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (m[i][j]==0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void mostrar(int[][] m) {
        String s="";
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                s+=m[i][j]+"\t";
            }
            s+="\n";
        }
        System.out.println(s);
    }

    private static LinkedList<Integer> getValores(int[][] m, int fil, int col, int h) {
        LinkedList<Integer> l= new LinkedList<>();
        for (int k = 1; k <= m.length; k++) {
            if (!enFila(m,fil,k) && !enCol(m,col,k) && !enArea(m,fil,col,k,h)) {
                l.add(k);
            }
        }
        return l;
    }

    private static boolean enFila(int[][] m, int fil, int k) {
        for (int j = 0; j < m[fil].length; j++) {
            if (m[fil][j]==k) {
                return true;
            }
        }
        return false;
    }

    private static boolean enCol(int[][] m, int col, int k) {
        for (int i = 0; i < m.length; i++) {
            if (m[i][col]==k) {
                return true;
            }
        }
        return false;
    }

    private static boolean enArea(int[][] m, int fil, int col, int k, int h) {
        int i1= fil-fil%h;
        int j1= col-col%h;
        for (int i = i1; i < i1+h; i++) {
            for (int j = j1; j < j1+h; j++) {
                if (m[i1][j1]==k) {
                    return true;
                }
            }
        }
        return false;
    }
    
}
