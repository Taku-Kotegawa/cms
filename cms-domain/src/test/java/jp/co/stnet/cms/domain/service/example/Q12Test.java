package jp.co.stnet.cms.domain.service.example;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class Q12Test {

    @Test
    public void test001() {

        String a = "3+2*";
        calc(a);

    }

    @Test
    public void test002() {

        String a = "3+2*5";
        calc(a);

    }

    private void calc(String str) {

        int i = 0;
        int j = 0;

        Map<Integer, Integer> a = new HashMap<>();
        Map<Integer, Integer> b = new HashMap<>();

//        for (int z = 1; z <= 5; z++) {
//            a.put(z, 0);
//            b.put(z, 0);
//        }

        char[] c = str.toCharArray();

        for (int x = 0; x < c.length; x++) {

            String cc = String.valueOf(c[x]);

            if (cc.equals("+")) {

                if (j > 0 && b.get(j - 1) == 1) {
                    j--;
                    i--;
                    a.put(i - 1, a.get(i - 1) * a.get(i));
                }

                if (j > 0 && b.get(j - 1) == 2) {
                    j--;
                    i--;
                    a.put(i - 1, a.get(i - 1) * a.get(i));
                }

            } else if (cc.equals("*")) {

                if (j > 0 && b.get(j - 1) != null && b.get(j - 1) == 1) {
                    j--;
                    i--;
                    a.put(i - 1, a.get(i - 1) * a.get(i));
                }
                b.put(j, 1);
                j++;

            } else {
                a.put(i, Integer.valueOf(cc));
                i++;
            }

            System.out.println("c = " + c[x] + ", i = " + i + ", j = " + j);
            System.out.println("a = " + a);
            System.out.println("b = " + b);
            System.out.println();

        }

        System.out.println("i = " + i + ", j = " + j);
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println();


        b.put(j, 2);
        j++;

        System.out.println("i = " + i + ",j = " + j);
        System.out.println("a = " + a);
        System.out.println("b = " + b);

    }

}
