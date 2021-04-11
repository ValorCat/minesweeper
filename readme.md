# Minesweeper
Available as a .exe installer (for Windows users) and as an executable JAR. If you use the Windows installer, it will also add a shortcut on your desktop.

The JAR version requires JDK 16 and [JavaFX SDK 16](https://gluonhq.com/products/javafx/). Since JavaFX is no longer part of the standard library (as of Java 11), the JAR must be run like this:
```shell
$ java --module-path path/to/javafx/lib --add-modules ALL-MODULE-PATH -jar Minesweeper.jar
```
(Make sure to replace `path/to/javafx/lib` with the actual path of the `lib` directory of your JavaFX SDK.)

---

All images are the properties of their respective owners.