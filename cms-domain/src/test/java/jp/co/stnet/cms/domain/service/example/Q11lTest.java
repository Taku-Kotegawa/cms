package jp.co.stnet.cms.domain.service.example;


import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class Q11lTest {


    @Test
    public void test001() {

        int n = 3;

        Map<Integer, Integer> a = new HashMap<>();
        a.put(1, 1);
        a.put(2, 2);
        a.put(3, 3);

        System.out.println(calc(a, n));
        System.out.println(calcC(a, n));
    }

    @Test
    public void test002() {

        int n = 3;

        Map<Integer, Integer> a = new HashMap<>();
        a.put(1, 1);
        a.put(2, 3);
        a.put(3, 2);

        System.out.println(calc(a, n));
        System.out.println(calcC(a, n));
    }

    @Test
    public void test003() {

        int n = 3;

        Map<Integer, Integer> a = new HashMap<>();
        a.put(1, 3);
        a.put(2, 2);
        a.put(3, 1);

        System.out.println(calc(a, n));
        System.out.println(calcC(a, n));
    }

    @Test
    public void test004() {

        int n = 4;

        Map<Integer, Integer> a = new HashMap<>();
        a.put(1, 4);
        a.put(2, 3);
        a.put(3, 1);
        a.put(4, 2);

        System.out.println(calc(a, n));
        System.out.println(calcC(a, n));
    }

    @Test
    public void test005() {

        int n = 5;

        Map<Integer, Integer> a = new HashMap<>();
        a.put(1, 5);
        a.put(2, 4);
        a.put(3, 1);
        a.put(4, 2);
        a.put(5, 3);

        System.out.println(calc(a, n));
        System.out.println(calcC(a, n));
    }

    private Map<Integer, Integer> calc(Map<Integer, Integer> a, int n) {

        for (int i = n; i >= 2; i--) {
            for (int j = 1; j <= i - 1; j++) {
                if (a.get(i) < a.get(j)) {
                    a.put(j, a.get(j) - 1);
                }
            }
        }
        return a;
    }

    private Integer calcC(Map<Integer, Integer> a, int n) {
        int c = 0;

        for (int i = n; i >= 2; i--) {
            c = (c + a.get(i) - 1) * (i - 1);
        }
        return c;
    }


}