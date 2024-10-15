package kozelek.test;

import kozelek.data.GPS;
import kozelek.data.objekt.Entity;
import kozelek.tree.TreeWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TreeWrapperTest {

    private TreeWrapper<TestEntity> treeWrapper;
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

        this.treeWrapper = new TreeWrapper<>();
        this.testEntities = new ArrayList<>();
    }

    @BeforeEach
    void setUp() {
        treeWrapper = new TreeWrapper<>();
        testEntities = new ArrayList<>();
        this.rand = new Random();
    }

    private TestEntity createRandomObject() {
        GPS tl = new GPS(this.randomX(), this.randomY());
        GPS br = new GPS(this.randomX(), this.randomY());

        return new TestEntity(tl, br);
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
            boolean isLeft = rand.nextBoolean();
            GPS gps = isLeft ? test.getTopLeft() : test.getBottomRight();
            TestEntity found = treeWrapper.find(gps, isLeft);

            assertNotNull(found);
            assertEquals(test, found);
        }

        TestEntity notFound = treeWrapper.find(new GPS(x2 + 5, y2 + 5), true);
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

                TestEntity testfind = treeWrapper.find(test.getTopLeft(), true);
                TestEntity testfindbr = treeWrapper.find(test.getBottomRight(), false);

                if (testfind == null || testfindbr == null)
                    fail();

                foundList.add(testfind);
                foundListbr.add(testfindbr);

            } else {
                if (!testEntities.isEmpty()) {
                    TestEntity test = this.testEntities.get(rand.nextInt(this.testEntities.size()));
                    boolean isLeft = rand.nextBoolean();
                    GPS gps = isLeft ? test.getTopLeft() : test.getBottomRight();
                    TestEntity found = treeWrapper.find(gps, isLeft);

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
        public TestEntity(GPS topLeft, GPS bottomRight) {
            super(topLeft, bottomRight);
        }
    }
}
