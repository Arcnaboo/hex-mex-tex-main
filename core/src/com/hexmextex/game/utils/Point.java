package com.hexmextex.game.utils;

import com.badlogic.gdx.math.Vector2;

import java.util.Objects;

public class Point {

    public final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 vector2() {
        return new Vector2((float)x, (float) y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 &&
                Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return (13 * (int)Math.round(x) * (int)Math.round(y)) % 100001;
    }
}
