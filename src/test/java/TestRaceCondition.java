import org.junit.jupiter.api.Test;

public class TestRaceCondition {
    @Test
    void TestRaceConditionOnIntVar() throws InterruptedException {
        String[] args=null;
        RaceConditionOnIntVar.main(args);
        assert(RaceConditionOnIntVar.x == (2*RaceConditionOnIntVar.NUM_INCREMENTS));
    }
    @Test
    void TestRaceConditionOnLongVar() throws InterruptedException {
        String[] args=null;
        RaceConditionOnLongVar.main(args);
        assert(RaceConditionOnLongVar.x == (2*RaceConditionOnLongVar.NUM_INCREMENTS));
    }
    @Test
    void TestSynchronizedOnIntVar() throws InterruptedException {
        String[] args=null;
        SynchronizedOnIntVar.main(args);
        assert(SynchronizedOnIntVar.x == (2*SynchronizedOnIntVar.NUM_INCREMENTS));
    }

}
