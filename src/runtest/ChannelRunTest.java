package runtest;

import io.impl.XLSX;
import model.TestCase;
import testsets.ChannelTestSet;
import utilities.ZPCheat;

import java.util.List;

public class ChannelRunTest {
    private ChannelTestSet channelTestSet;

    public ChannelRunTest() {
        channelTestSet = new ChannelTestSet(new XLSX());
        channelTestSet.loadTestSet();
    }

    public void runTest() throws Exception {
        List<TestCase> testCases = channelTestSet.getTestSet();

        for (int i=0; i<testCases.size(); i++) {
            ZPCheat zpCheat = new ZPCheat();
            zpCheat.cheatData(Integer.parseInt(testCases.get(i).getCheatID()));
            for (int j=0; j<testCases.get(i).getSteps().length; j++) {
                testCases.get(i).getSteps()[j].doStep();
            }
        }

//        testCases.get(0).getSteps()[2].doStep();
//        testCases.get(0).getSteps()[3].doStep();
//        System.out.print(Client.getInstance(1).getUserInfo());

//        ZPCheat zpCheat = new ZPCheat();
//        for (TestCase testCase: testCases) {
//            ZPCheat.cheatData(testCase.getCheatID());
//            Step[] steps = testCase.getSteps();
//            for (Step step: steps) {
//                Thread.sleep(1000);
//                System.out.println(step.toString());
//                step.doStep();
//            }
//        }
    }

    public static void main(String[] args) throws Exception {
        ChannelRunTest channelRunTest = new ChannelRunTest();
        channelRunTest.runTest();
    }
}
