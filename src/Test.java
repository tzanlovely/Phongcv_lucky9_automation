import client.IClient;
import client.LDClient;
import func.Function;
import utilities.MethodExtractor;
import utilities.ZPCheat;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class Test {
    public static void main(String[] args) throws Exception {
//        ZPCheat zpCheat = new ZPCheat();
//        ZPCheat.cheatData(61);
//        Thread.sleep(30000);
//        ZPCheat.cheatData(63); label_Title
//        Function.backToLobby(LDClient.getInstance(1));
        LDClient ldClient = LDClient.getInstance(1);
        System.out.println( ldClient.isExist(Arrays.asList("string:Your total gold must be less than\n1,000,000 gold.\nPlease join the higher channel!")));

    }

    private static class Test1{
        public static void func1(int a) {
            System.out.println(a);
        }
    }
}
