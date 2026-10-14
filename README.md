# K-d tree

Semester project for Algorithms and Data Structures 2 (FRI UNIZA, 2024). A generic k-d tree written
from scratch in Java, used to find properties and land parcels by GPS position, with a Swing GUI.

## What it does

- Generic `KDTree<T>`: insert, find all records with the same keys, edit, and delete (with
  replacement by the minimum or maximum node in a subtree), in-order and level-order traversal.
- Deleting works when several records share the same key: nodes with that key in the subtree are
  taken out and inserted again.
- Three trees side by side: properties, parcels, and both together, so a search by GPS returns
  everything at that position, and a property and a parcel on the same point reference each other.
- A random data generator and saving and loading to CSV.

## Run it

Open the project in IntelliJ IDEA and run `kozelek.Main` (the GUI uses IntelliJ GUI Designer forms).
Java 23, Maven.
