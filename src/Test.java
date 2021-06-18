import client.IClient;
import client.LDClient;
import func.Function;
import utilities.MethodExtractor;
import utilities.ZPCheat;

import java.lang.reflect.Method;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws Exception {
//        ZPCheat zpCheat = new ZPCheat();
//        ZPCheat.cheatData(61);
//        Thread.sleep(30000);
//        ZPCheat.cheatData(63);
//        Function.backToLobby(LDClient.getInstance(1));
        IClient client = LDClient.getInstance(1);
        System.out.println(client.checkUser(Arrays.asList("gold:1000000")));
    }

    private static class Test1{
        public static void func1(int a) {
            System.out.println(a);
        }
    }
}
