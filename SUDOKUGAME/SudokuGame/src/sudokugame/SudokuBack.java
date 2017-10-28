package sudokugame;

import java.io.*;
import java.util.*;
import java.util.BitSet;

public class SudokuBack {

    private static final String FileInputName = "Sudoku1.txt";
    private static final String FileOutputName = "SudokuOUT.txt";
    
    private static final char V = '*';
    private static final int N = 3;
    private static final int DIM = N * N;
    private BitSet sl[];
    private BitSet sc[];
    private BitSet[][] sb;
    private int[][] v;
    
    SudokuBack (){
        this.sl=new BitSet[DIM];
        this.sc=new BitSet[DIM];
        for (int i=0; i< DIM ; i++){
            this.sl[i]= new BitSet();
            this.sc[i]= new BitSet();
        }
        this.sb= new BitSet[N][N];
        for (int i=0; i< N; i++)
            for(int j=0;j<N;j++)
                sb[i][j]= new BitSet();
        this.v= new int[DIM][DIM];
    }
    
    boolean set(int l,int c, int value){
        if(!sl[l].get(value) && !sc[c].get(value) && !sb[l/N][c/N].get(value)){
            sl[l].set(value);
            sc[c].set(value);
            sb[l/N][c/N].set(value);
            v[l][c] = value;
            return true;
        }
        else
            return false;
    }
    
    void unset(int l,int c, int value){
        sl[l].clear(value);
        sc[c].clear(value);
        sb[l/N][c/N].clear(value);
        v[l][c] = 0;
    }
    
    void run(Scanner scanner,PrintStream out){
        int l = 0,c = 0;
        while (scanner.hasNext() && l<DIM) {
            String s = scanner.next();
            for (int i=0;i<s.length(); i++){
                char ch = s.charAt(i);
                if (Character.isDigit(ch) || ch == V){
                    if(ch != V){
                        if(!set(l,c,Character.digit(ch, 10)))
                        return;
                    }
                    if (DIM== ++c){
                    l++;
                    c=0;
                    }
                }
            }
        }
        back(0, -1 ,out);
    }
    
    private void WriteSolution(PrintStream out){
        for (int l=0;l < DIM; l++)
        {
            out.print(v[l][0]);
            for (int c = 1 ; c < DIM; c++)
            {
                out.print(' ');
                out.print(v[l][c]);
            }
            out.println();
        }
        out.println();
    }
    
    private void back(int l,int c,PrintStream out) {
        do{
            c++;
            if(c==DIM){
                c=0;
                l++;
                if(l == DIM) {
                    WriteSolution(out);
                    return;
                }
            }
        }
        while (v[l][c] != 0);
        for (int value =1 ; value <= DIM; ++value){
            if (set(l,c,value)){
                back(l,c,out);
                unset(l,c,value);
            }
        }
    }
    
     
    public static void main(String[] args) throws IOException {
       Scanner scanner = null;
       PrintStream out = null;
       try {
           out = new PrintStream(new File(FileOutputName));
           scanner = new Scanner(new File(FileInputName));
           new SudokuBack().run(scanner,out);
       }finally {
           if (scanner != null){
               scanner.close();
           }
           if (out != null){
               out.close();
           }
       }
          
    }
    
}
