package kozelek;

import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;

public class Main {
    public static void main(String[] args) {
        Nehnutelnost neh = new Nehnutelnost(1, "ahoj", new GPS(-1,2), new GPS(2,2));
        System.out.println(neh.getTopLeft().getX());
    }
}