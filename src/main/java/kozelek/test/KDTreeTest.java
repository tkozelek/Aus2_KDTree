package kozelek.test;

import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.data.objekt.Entity;
import kozelek.data.objekt.IComparator;
import kozelek.data.objekt.IObject;
import kozelek.tree.KDTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TreeWrapperTest {

    private KDTree<TestEntity> tree;
    private double x1,x2;
    private double y1,y2;
    private int pocet, removepocet;
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
        this.removepocet = 1500;

        this.tree = new KDTree<>();
        this.testEntities = new ArrayList<>();
    }

    @BeforeEach
    void setUp() {
        tree = new KDTree<>();
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
            tree.insert(test);
            this.testEntities.add(test);
        }

        for (int j = 0; j < pocet; j++) {
            TestEntity test = this.testEntities.get(rand.nextInt(this.testEntities.size()));

            ArrayList<TestEntity> found = tree.find(test);

            assertNotNull(found);
            assertTrue(found.contains(test));
        }
    }

    @Test
    void testInsertAndFindRandomly() {
        ArrayList<TestEntity> foundList = new ArrayList<>();
        for (int i = 0; i < pocet*2; i++) {
            if (rand.nextBoolean()) {
                TestEntity test = this.createRandomObject();
                tree.insert(test);
                testEntities.add(test);

                ArrayList<TestEntity> testfind = tree.find(test);

                if (testfind.isEmpty())
                    fail();

                foundList.addAll(testfind);
            } else {
                if (!testEntities.isEmpty()) {
                    TestEntity test = this.testEntities.get(rand.nextInt(this.testEntities.size()));

                    ArrayList<TestEntity> found = tree.find(test);

                    assertNotNull(found);
                    assertTrue(found.contains(test));
                } else
                    i--;
            }
            int pointer = 0;
            for (int j = 0; j < testEntities.size(); j++) {
                assertEquals(testEntities.get(pointer), foundList.get(pointer));
                ++pointer;
            }
        }
    }

    @Test
    void testEditEntities() {
        for (int i = 0; i < pocet; i++) {
            TestEntity test = this.createRandomObject();
            tree.insert(test);
            this.testEntities.add(test);
        }

        for (int j = 0; j < pocet / 2; j++) {
            TestEntity test = this.testEntities.get(rand.nextInt(this.testEntities.size()));
            TestEntity edit = new TestEntity(new GPS(this.randomX(), this.randomY()));

            tree.edit(test, edit);
            ArrayList<TestEntity> found = tree.find(edit);

            assertNotNull(found);
            assertTrue(found.contains(edit));
        }
    }

    @Test
    void testInsertDelete() {
        for (int i = 0; i < pocet; i++) {
            TestEntity test = this.createRandomObject();
            tree.insert(test);
            this.testEntities.add(test);
        }

        for (int j = 0; j < pocet / 2; j++) {
            TestEntity test = this.testEntities.remove(rand.nextInt(this.testEntities.size()));
            TestEntity deleted = tree.delete(test);

            ArrayList<TestEntity> found = tree.find(test);
            assertNull(found);
            assertEquals(test, deleted);
        }

        for (TestEntity remaining : this.testEntities) {
            ArrayList<TestEntity> found = tree.find(remaining);
            assertNotNull(found);
            assertTrue(found.contains(remaining));
        }
    }

    @Test
    void testRandomOperations() {
        for (int i = 0; i < 1000; i++) {
            TestEntity test = this.createRandomObject();
            tree.insert(test);
            this.testEntities.add(test);
        }

        for (int j = 0; j < pocet; j++) {
            int operation = rand.nextInt(3);
            switch (operation) {
                case 0:
                    TestEntity newEntity = this.createRandomObject();
                    tree.insert(newEntity);
                    this.testEntities.add(newEntity);
                    assertNotNull(tree.find(newEntity));
                    break;

                case 1:
                    if (!testEntities.isEmpty()) {
                        TestEntity toDelete = this.testEntities.get(rand.nextInt(this.testEntities.size()));
                        tree.delete(toDelete);
                        this.testEntities.remove(toDelete);
                        assertNull(tree.find(toDelete));
                    }
                    break;

                case 2:
                    if (!testEntities.isEmpty()) {
                        TestEntity toEdit = this.testEntities.remove(rand.nextInt(this.testEntities.size()));
                        TestEntity edited = this.createRandomObject();
                        tree.edit(toEdit, edited);
                        this.testEntities.add(edited);
                        assertNotNull(tree.find(edited));
                    }
                    break;
            }
        }

        for (TestEntity entity : this.testEntities) {
            ArrayList<TestEntity> found = tree.find(entity);
            assertNotNull(found);
            assertTrue(found.contains(entity));
        }
    }

    static class TestEntity extends Entity implements IObject {
        public TestEntity(GPS gps) {
            super(gps);
        }

        @Override
        public boolean equalKey(IObject other) {
            if (!(other instanceof TestEntity test)) {
                throw new ClassCastException("Incompatible types.");
            }
            return Math.abs(this.getGPS().getX() - test.getGPS().getX()) < Entity.EPSILON &&
                    Math.abs(this.getGPS().getY() - test.getGPS().getY()) < Entity.EPSILON;
        }

        @Override
        public boolean equalUUID(IObject other) {
            if (!(other instanceof TestEntity otherT)) {
                throw new ClassCastException("Incompatible types.");
            }
            return this.getUuid().compareTo(otherT.getUuid()) == 0;
        }

        @Override
        public int compare(IObject other, int level) {
            if (!(other instanceof TestEntity test)) {
                throw new ClassCastException("Incompatible types.");
            }
            return switch (level % 2) {
                case 0 -> Math.abs(this.getGPS().getX() - test.getGPS().getX()) < Entity.EPSILON ? 0 : Double.compare(this.getGPS().getX(), test.getGPS().getX()); // d1 > d2 = +
                case 1 -> Math.abs(this.getGPS().getY() - test.getGPS().getY()) < Entity.EPSILON ? 0 : Double.compare(this.getGPS().getY(), test.getGPS().getY()); // d1 < d2 = -
                default -> 0;
            };
        }

        @Override
        public int getDimension() {
            return 2;
        }
    }
}
