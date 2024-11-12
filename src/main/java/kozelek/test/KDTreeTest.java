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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class KDTreeTest {

    private KDTree<TestEntity> tree;
    private double x1,x2;
    private double y1,y2;
    private int pocet, removepocet;
    private Random rand;
    private ArrayList<TestEntity> testEntities;
    private final double duplicate = 0.2d;

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
        double randDuplicate = this.rand.nextDouble();
        if (!this.testEntities.isEmpty() && randDuplicate < this.duplicate) {
            gps = this.getRandomObject().getGPS();
        }

        return new TestEntity(gps);
    }

    public TestEntity getRandomObject() {
        return this.testEntities.get(this.rand.nextInt(this.testEntities.size()));
    }

    public double randomX() {
        return this.rand.nextDouble(this.x1, this.x2);
    } // (this.x1 + (this.x2 - this.x1) * this.rand.nextDouble());

    public double randomY() {
        return this.rand.nextDouble(this.y1, this.y2);
    } // return (this.y1 + (this.y2 - this.y1) * this.rand.nextDouble());

    @Test
    void testInsertAndFind() {
        for (int i = 0; i < pocet; i++) {
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
        for (int i = 0; i < pocet*2; i++) {
            if (rand.nextBoolean()) { // insert
                TestEntity test = this.createRandomObject();
                tree.insert(test);
                testEntities.add(test);
            } else { // try find
                if (!testEntities.isEmpty()) {
                    TestEntity test = this.testEntities.get(rand.nextInt(this.testEntities.size()));

                    ArrayList<TestEntity> found = tree.find(test);

                    assertNotNull(found);
                    assertTrue(found.contains(test));
                } else
                    i--;
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
            TestEntity original = this.testEntities.remove(rand.nextInt(this.testEntities.size()));
            TestEntity edited = new TestEntity(new GPS(this.randomX(), this.randomY()));

            tree.edit(original, edited);

            ArrayList<TestEntity> found = tree.find(edited);
            TestEntity tryFindOld = tree.findOneNode(original) != null ? tree.findOneNode(original).getBoundObject() : null;

            // Assertions to verify correct behavior
            assertNotNull(found, "Je v strome");
            assertTrue(found.contains(edited));
            assertNull(tryFindOld, "Stara sa nenasla");
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
            assertEquals(test, deleted, "Test == deleted");
        }

        // remaining
        for (TestEntity remaining : this.testEntities) {
            ArrayList<TestEntity> found = tree.find(remaining);

            assertNotNull(found);
            assertTrue(found.contains(remaining));
        }
    }

    @Test
    void testRandomOperations() {
        int init = 1000;
        int numberOfRandomOperations = 3;
        for (int i = 0; i < init; i++) {
            TestEntity test = this.createRandomObject();
            tree.insert(test);
            this.testEntities.add(test);
        }

        for (int j = 0; j < pocet; j++) {
            int operation = rand.nextInt(numberOfRandomOperations);

            switch (operation) {
                case 0: // insert
                    TestEntity newEntity = this.createRandomObject();
                    tree.insert(newEntity);
                    this.testEntities.add(newEntity);
                    continue;
                case 1:
                    if (!testEntities.isEmpty()) {
                        TestEntity toDelete = this.testEntities.get(rand.nextInt(this.testEntities.size()));
                        tree.delete(toDelete);
                        this.testEntities.remove(toDelete);
                        assertNull(tree.find(toDelete));
                    }
                    continue;

                case 2:
                    if (!testEntities.isEmpty()) {
                        TestEntity toEdit = this.testEntities.remove(rand.nextInt(this.testEntities.size()));
                        TestEntity edited = this.createRandomObject();
                        tree.edit(toEdit, edited);
                        this.testEntities.add(edited);
                        assertNotNull(tree.find(edited));
                    }
                    continue;
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
