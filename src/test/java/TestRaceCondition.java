import org.junit.jupiter.api.Test;

public class TestRaceCondition {
    @Test
    void RaceConditionOnIntVar() throws InterruptedException {
        String[] args=null;
        RaceConditionOnIntVar.main(args);
        assert(RaceConditionOnIntVar.x == (2*RaceConditionOnIntVar.NUM_INCREMENTS));
    }
    @Test
    void RaceConditionOnLongVar() throws InterruptedException {
        String[] args=null;
        RaceConditionOnLongVar.main(args);
        assert(RaceConditionOnLongVar.x == (2*RaceConditionOnLongVar.NUM_INCREMENTS));
    }
}
