import utilities.MethodExtractor;
import utilities.ZPCheat;

import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) throws Exception {
        ZPCheat zpCheat = new ZPCheat();
        ZPCheat.cheatData(61);
        Thread.sleep(30000);
        ZPCheat.cheatData(63);
    }

    private static class Test1{
        public static void func1(int a) {
            System.out.println(a);
        }
    }
}
