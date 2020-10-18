package jp.co.stnet.cms.domain.service.example;

import org.junit.jupiter.api.Test;

public class Q122 {

    @Test
    void test001() {
        int k = 2;
        int[][] t1 = new int[k+1][3];
        t1[0][0] = 1;
        t1[0][1] = 2;
        t1[0][2] = 1;
        t1[1][0] = 1;
        t1[1][1] = 1;
        t1[1][2] = 1;
        t1[2][0] = 2;
        t1[2][1] = 2;
        t1[2][2] = 0;

        for (int a = 0; a <= 20; a++ ){
            program(t1, a);
        }
    }

    @Test
    void test002() {
        int k = 2;
        int[][] t1 = new int[k+1][3];
        t1[0][0] = 0;
        t1[0][1] = 1;
        t1[0][2] = 1;
        t1[1][0] = 2;
        t1[1][1] = 0;
        t1[1][2] = 0;
        t1[2][0] = 1;
        t1[2][1] = 2;
        t1[2][2] = 0;

        for (int a = 0; a <= 20; a++ ){
            program(t1, a);
        }
    }

    @Test
    void test003() {
        int k = 4;
        int[][] t1 = new int[k+1][3];
        t1[0][0] = 1;
        t1[0][1] = 4;
        t1[0][2] = 1;
        t1[1][0] = 1;
        t1[1][1] = 2;
        t1[1][2] = 1;
        t1[2][0] = 3;
        t1[2][1] = 1;
        t1[2][2] = 0;
        t1[3][0] = 2;
        t1[3][1] = 3;
        t1[3][2] = 0;
        t1[4][0] = 4;
        t1[4][1] = 4;
        t1[4][2] = 0;

        for (int a = 0; a <= 30; a++ ){
            program(t1, a);
        }
    }

    private void program(int[][] t1, int a) {

        int q = 0;
        int x = a;
        int c = 0;
        while (x != 0) {
            c = x % 2;
            q = t1[q][c];
            x = x / 2;
        }
        if (t1[q][2] == 1) {
            System.out.println("a = " + a + ", Yes");
        } else {
            System.out.println("a = " + a + ", No");
        }

    }


}



