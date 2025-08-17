import java.util.Arrays;
import java.util.ArrayList;
public class MazeSolver {
    public static void main(String[] args) {
        System.out.println(count(3, 3));
        order("", 3, 3);
        System.out.println(list);
        System.out.println(order1("", 3, 3));
        System.out.println(orderwithd("", 3, 3));
        orderwithr("", 0, 0);
        System.out.println(answr);
        boolean[][] board = {
            {true , true, true},
            {true , false , true},
            {true , true, true}
        };
        boolean[][] boardb = {
            {true , true, true},
            {true , true , true},
            {true , true, true}
        };
        int[][] path = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
        };
        // same as int[][] path = new int[boardb.length][boardb[0].length];
        orderwithr1("", board, 0, 0);
        orderudlr("", boardb, 0, 0);
        orderudlr("", boardb, 0, 0, path, 1);
    }
    static int count( int r, int c){
        if (r == 1 || c == 1){
            return 1;
        }
        int left = count(r - 1, c);
        int right = count(r, c - 1);
        return left + right;
    }
    static ArrayList<String> list = new ArrayList<>();
    static void order( String p, int r, int c){
        if (r == 1 && c == 1){
            list.add(p);
            return;
        }
        if ( r > 1){
            order(p + 'D', r - 1, c);
        }
        if ( c > 1){
            order(p + 'R' ,r, c - 1);
        }
    }
    static ArrayList<String> order1(String p, int r, int c){
        if (r == 1 && c == 1){
            ArrayList<String> ans = new ArrayList<>();
            ans.add(p);
            return ans;
        }
        ArrayList<String> ans = new ArrayList<>();
        if ( r > 1){
            ans.addAll(order1(p + 'D', r - 1, c));
        }
        if ( c > 1){
            ans.addAll(order1(p + 'R' ,r, c - 1));
        }
        return ans;
    }
    static ArrayList<String> orderwithd(String p, int r, int c){
        if (r == 1 && c == 1){
            ArrayList<String> ans = new ArrayList<>();
            ans.add(p);
            return ans;
        }
        ArrayList<String> ans = new ArrayList<>();
        if ( r > 1 && c > 1){
            ans.addAll(orderwithd(p + "DI" , r - 1, c - 1));
        }
        if ( r > 1){
            ans.addAll(orderwithd(p + 'D', r - 1, c));
        }
        if ( c > 1){
            ans.addAll(orderwithd(p + 'R' ,r, c - 1));
        }
       
        return ans;
    }
    static ArrayList<String> answr = new ArrayList<>();
    static void orderwithr( String p, int r, int c){
        if (r == 2 && c == 2){
            answr.add(p);
            return;
        }
        if (r == 1 && c == 1) {
            return;
        }
        if ( r < 2){
            orderwithr(p + 'D', r + 1, c);
        }
        if ( c < 2){
            orderwithr(p + 'R' ,r, c + 1);
        }
    }
    static void orderwithr1( String p, boolean[][] m, int r, int c){
        if (r == m.length - 1 && c == m[0].length - 1){
            System.out.println(p);
            return;
        }
        if (!m[r][c]) {
            return;
        }
        if ( r < m.length - 1){
            orderwithr1(p + 'D',m , r + 1, c);
        }
        if ( c < m[0].length - 1){
            orderwithr1(p + 'R', m ,r, c + 1);
        }
    }
    // backtracking
    static void orderudlr( String p, boolean[][] m, int r, int c){
        if (r == m.length - 1 && c == m[0].length - 1){
            System.out.println(p);
            return;
        }
        if (!m[r][c]) {
            return;
        }
        // i am at the cell
        m[r][c] = false;
        if ( r < m.length - 1){
            orderudlr(p + 'D',m , r + 1, c);
        }
        if ( c < m[0].length - 1){
            orderudlr(p + 'R', m ,r, c + 1);
        }
        if ( r > 0){
            orderudlr(p + 'U',m , r - 1, c);
        }
        if ( c > 0){
            orderudlr(p + 'L', m ,r, c - 1);
        }
        // before removing the function changes should also reverse
        m[r][c] = true;
        
    }
    static void orderudlr( String p, boolean[][] m, int r, int c, int[][] path, int steps){
        if (r == m.length - 1 && c == m[0].length - 1){
            path[r][c] = steps;
            for(int[] arr : path){
                System.out.println(Arrays.toString(arr));
            }
            System.out.println(p);
            System.out.println();
            return;
        }
        if (!m[r][c]) {
            return;
        }
        // i am at the cell
        m[r][c] = false;
        path[r][c] = steps;
        if ( r < m.length - 1){
            orderudlr(p + 'D',m , r + 1, c, path, steps + 1);
        }
        if ( c < m[0].length - 1){
            orderudlr(p + 'R', m ,r, c + 1, path, steps + 1);
        }
        if ( r > 0){
            orderudlr(p + 'U',m , r - 1, c, path, steps + 1);
        }
        if ( c > 0){
            orderudlr(p + 'L', m ,r, c - 1, path, steps + 1);
        }
        // before removing the function changes should also reverse
        m[r][c] = true;
        path[r][c] = 0;
        
    }
    

}
