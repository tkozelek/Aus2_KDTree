package kozelek.test;

import kozelek.data.GPS;
import kozelek.data.objekt.Entity;
import kozelek.tree.KDTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TreeWrapperTest {

    private KDTree<TestEntity> treeWrapper;
    private double x1,x2;
    private double y1,y2;
    private int pocet;
    private Random rand;
    private ArrayList<TestEntity> testEntities;

    @BeforeEach
    public void init() {
        this.rand = new Random();
        this.x1 = 0;
        this.x2 = 200;
        this.y1 = 0;
        this.y2 = 200;
        this.pocet = 5000;

        this.treeWrapper = new KDTree<>();
        this.testEntities = new ArrayList<>();
    }

    @BeforeEach
    void setUp() {
        treeWrapper = new KDTree<>();
        testEntities = new ArrayList<>();
        this.rand = new Random();
    }

    private TestEntity createRandomObject() {
        GPS gps = new GPS(this.randomX(), this.randomY());

        return new TestEntity(gps);
    }

    public double randomX() {
        return this.rand.nextDouble(this.x1, this.x2);
    } // (this.x1 + (this.x2 - this.x1) * this.rand.nextDouble());

    public double randomY() {
        return this.rand.nextDouble(this.y1, this.y2);
    } // return (this.y1 + (this.y2 - this.y1) * this.rand.nextDouble());

    @Test
    void testInsertAndFind() {
        for (int i = 0; i < 1000; i++) {
            TestEntity test = this.createRandomObject();
            treeWrapper.insert(test);
            this.testEntities.add(test);
        }

        for (int j = 0; j < pocet; j++) {
            TestEntity test = this.testEntities.get(rand.nextInt(this.testEntities.size()));

            TestEntity found = treeWrapper.find(test);

            assertNotNull(found);
            assertEquals(test, found);
        }

        TestEntity notFound = treeWrapper.find(new TestEntity(new GPS(x2 + 5, x1 + 5)));
        assertNull(notFound);
    }

    @Test
    void testInsertAndFindRandomly() {
        ArrayList<TestEntity> foundList = new ArrayList<>();
        ArrayList<TestEntity> foundListbr = new ArrayList<>();
        for (int i = 0; i < pocet*2; i++) {
            if (rand.nextBoolean()) {
                TestEntity test = this.createRandomObject();
                treeWrapper.insert(test);
                testEntities.add(test);

                TestEntity testfind = treeWrapper.find(test);

                if (testfind == null)
                    fail();

                foundList.add(testfind);
            } else {
                if (!testEntities.isEmpty()) {
                    TestEntity test = this.testEntities.get(rand.nextInt(this.testEntities.size()));

                    TestEntity found = treeWrapper.find(test);

                    assertNotNull(found);
                    assertEquals(test, found);
                } else
                    i--;
            }
            int pointer = 0;
            for (int j = 0; j < testEntities.size(); j++) {
                assertEquals(testEntities.get(pointer), foundListbr.get(pointer));
                assertEquals(testEntities.get(pointer), foundList.get(pointer));
                ++pointer;
            }
        }
    }

    static class TestEntity extends Entity {
        public TestEntity(GPS gps) {
            super(gps);
        }

        @Override
        public int compare(Entity other, int level) {
            return 0;
        }
    }
}
