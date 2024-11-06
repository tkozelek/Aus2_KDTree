package kozelek.generator;

import kozelek.data.objekt.Entity;
import kozelek.data.objekt.IObject;

import java.util.UUID;

public class TestClass implements IObject {
    private int x,y, id;
    private final UUID uuid = UUID.randomUUID();

    public TestClass(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "id=" + id +
                " x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int compare(IObject other, int level) {
        if (!(other instanceof TestClass test)) {
            throw new ClassCastException("Incompatible types.");
        }
        return switch (level % 2) {
            case 0 -> Integer.compare(x, test.getX());
            case 1 -> Integer.compare(y, test.getY());
            default -> 0;
        };
    }

    @Override
    public boolean equalKey(IObject other) {
        if (!(other instanceof TestClass test)) {
            throw new ClassCastException("Incompatible types.");
        }
        return Math.abs(this.getX() - test.getX()) < Entity.EPSILON &&
                Math.abs(this.getY() - test.getY()) < Entity.EPSILON;
    }

    @Override
    public boolean equalUUID(IObject other) {
        if (!(other instanceof TestClass test)) {
            throw new ClassCastException("Incompatible types.");
        }
        return this.id == test.id;
    }

    @Override
    public int getDimension() {
        return 2;
    }
}
