package kozelek.test;

import kozelek.data.objekt.interfaces.IObject;
import kozelek.tree.KDTree;
import kozelek.tree.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class KDTreeTestInteger {

    private KDTree<IntegerTestEntity> tree;
    private int x1,x2;
    private int y1,y2;
    private int pocet, removepocet;
    private Random rand;
    private ArrayList<IntegerTestEntity> testEntities;

    @BeforeEach
    public void init() {
        this.rand = new Random();
        this.x1 = 1;
        this.x2 = 50;
        this.y1 = 1;
        this.y2 = 50;
        this.pocet = 20000;
        this.removepocet = 5000;

        this.tree = new KDTree<>();
        this.testEntities = new ArrayList<>();
    }

    @BeforeEach
    void setUp() {
        tree = new KDTree<>();
        testEntities = new ArrayList<>();
        this.rand = new Random();
    }

    private IntegerTestEntity createRandomObject() {
        return new IntegerTestEntity(randomX(), randomY());
    }

    public IntegerTestEntity getRandomObject() {
        return this.testEntities.get(this.rand.nextInt(this.testEntities.size()));
    }

    public int randomX() {
        return this.rand.nextInt(this.x1, this.x2);
    } // (this.x1 + (this.x2 - this.x1) * this.rand.nextDouble());

    public int randomY() {
        return this.rand.nextInt(this.y1, this.y2);
    } // return (this.y1 + (this.y2 - this.y1) * this.rand.nextDouble());

    @Test
    void testInsertAndFind() {
        for (int i = 0; i < pocet; i++) {
            IntegerTestEntity test = this.createRandomObject();
            tree.insert(test);
            this.testEntities.add(test);
        }

        for (int j = 0; j < pocet; j++) {
            IntegerTestEntity test = this.testEntities.get(rand.nextInt(this.testEntities.size()));

            ArrayList<IntegerTestEntity> found = tree.find(test);

            assertNotNull(found);
            assertTrue(found.contains(test));
        }
    }

    @Test
    void testInsertAndFindRandomly() {
        for (int i = 0; i < pocet*2; i++) {
            if (rand.nextBoolean()) { // insert
                IntegerTestEntity test = this.createRandomObject();
                tree.insert(test);
                testEntities.add(test);
            } else { // try find
                if (!testEntities.isEmpty()) {
                    IntegerTestEntity test = this.testEntities.get(rand.nextInt(this.testEntities.size()));

                    ArrayList<IntegerTestEntity> found = tree.find(test);

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
            IntegerTestEntity test = this.createRandomObject();
            tree.insert(test);
            this.testEntities.add(test);
        }

        for (int j = 0; j < pocet / 2; j++) {
            IntegerTestEntity original = this.testEntities.remove(rand.nextInt(this.testEntities.size()));
            IntegerTestEntity edited = new IntegerTestEntity(this.randomX(), this.randomY());

            tree.edit(original, edited);

            ArrayList<IntegerTestEntity> found = tree.find(edited);
            IntegerTestEntity tryFindOld = tree.findOneNode(original) != null ? tree.findOneNode(original).getBoundObject() : null;

            // Assertions to verify correct behavior
            assertNotNull(found, "Je v strome");
            assertTrue(found.contains(edited));
            assertNull(tryFindOld, "Stara sa nenasla");
        }
    }

    @Test
    void testInsertDelete() {
        for (int i = 0; i < pocet; i++) {
            IntegerTestEntity test = this.createRandomObject();
            tree.insert(test);
            this.testEntities.add(test);
        }

        for (int j = 0; j < pocet / 2; j++) {
            IntegerTestEntity test = this.testEntities.remove(rand.nextInt(this.testEntities.size()));
            IntegerTestEntity deleted = tree.delete(test);

            Node<IntegerTestEntity> found = tree.findOneNode(test);

            assertNull(found);
            assertEquals(test, deleted, "Test == deleted");
        }

        // remaining
        for (IntegerTestEntity remaining : this.testEntities) {
            ArrayList<IntegerTestEntity> found = tree.find(remaining);

            assertNotNull(found);
            assertTrue(found.contains(remaining));
        }
    }

    @Test
    void testRandomOperations() {
        int init = 0;
        int numberOfRandomOperations = 4;
        for (int i = 0; i < init; i++) {
            IntegerTestEntity test = this.createRandomObject();
            tree.insert(test);
            this.testEntities.add(test);
        }

        for (int j = 0; j < pocet; j++) {
            int operation = rand.nextInt(numberOfRandomOperations);

            switch (operation) {
                case 0: // insert
                    IntegerTestEntity newEntity = this.createRandomObject();
                    tree.insert(newEntity);
                    this.testEntities.add(newEntity);
                    continue;
                case 1: // delete
                    if (!testEntities.isEmpty()) {
                        IntegerTestEntity toDelete = this.testEntities.remove(rand.nextInt(this.testEntities.size()));
                        tree.delete(toDelete);
                        ArrayList<IntegerTestEntity> found = tree.find(toDelete);
                        assertTrue(found == null || !found.contains(toDelete), "Test == deleted");
                    } else {
                        j--;
                    }
                    continue;
                case 2: // edit
                    if (!testEntities.isEmpty()) {
                        IntegerTestEntity toEdit = this.testEntities.remove(rand.nextInt(this.testEntities.size()));
                        IntegerTestEntity edited = this.createRandomObject();
                        tree.edit(toEdit, edited);
                        this.testEntities.add(edited);

                        ArrayList<IntegerTestEntity> found = this.tree.find(edited);

                        assertNotNull(found);
                        assertTrue(found.contains(edited), "Test == edited");
                    } else {
                        j--;
                    }
                    continue;
                case 3:
                    if (!testEntities.isEmpty()) {
                        IntegerTestEntity test = this.getRandomObject();
                        ArrayList<IntegerTestEntity> found = this.tree.find(test);

                        assertTrue(found.contains(test), "Test == found");
                    } else {
                        j--;
                    }
                    continue;
            }
        }

        for (IntegerTestEntity entity : this.testEntities) {
            ArrayList<IntegerTestEntity> found = tree.find(entity);
            assertNotNull(found);
            assertTrue(found.contains(entity));
        }
    }

    static class IntegerTestEntity implements IObject {
        private final int x;
        private final int y;
        private final String uuid;

        public IntegerTestEntity(int x, int y) {
            this.x = x;
            this.y = y;
            this.uuid = generateUuid();
        }

        private String generateUuid() {
            return UUID.randomUUID().toString().substring(0, 10);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public String getUuid() {
            return uuid;
        }

        @Override
        public boolean equalKey(IObject other) {
            if (!(other instanceof IntegerTestEntity test)) {
                throw new ClassCastException("Incompatible types.");
            }
            return this.x == test.x && this.y == test.y;
        }

        @Override
        public boolean equalUUID(IObject other) {
            if (!(other instanceof IntegerTestEntity otherT)) {
                throw new ClassCastException("Incompatible types.");
            }
            return this.uuid.equals(otherT.getUuid());
        }

        @Override
        public int compare(IObject other, int level) {
            if (!(other instanceof IntegerTestEntity test)) {
                throw new ClassCastException("Incompatible types.");
            }
            return switch (level % 2) {
                case 0 -> Integer.compare(this.x, test.x);
                case 1 -> Integer.compare(this.y, test.y);
                default -> 0;
            };
        }

        @Override
        public int getDimension() {
            return 2;
        }
    }
}
