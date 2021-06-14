
import utilities.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
//        Method method = MethodExtraction.getPublicMethodByName(A.class, "A");
//        String[] a = new String[]{"abcd"};
//        method.invoke(new A(), new Object[]{a});
        Client client2 = Client.getInstance(2);
        List<String> list = new ArrayList<>();
        list.add("name:btn_BetX3");
        client2.click(list);
    }

    private static class A {
        public void A(String... abc) {
            System.out.println(Arrays.asList(abc).size());
        }
//
//        private void B() {
//
//        }
    }
//
//    private static class B extends A {
//        public void B() {
//            System.out.println("b");
//        }
//    }

}
