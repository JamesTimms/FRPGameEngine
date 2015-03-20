package org.engineFRP.maths;

/**
 * Based on BennyBox's GameEngine https://github.com/BennyQBD/3DGameEngine.
 * Created by TekMaTek on 05/08/2014.
 * <p>
 * Note there is a limitation to how large x, y and z can be because of calculations
 * like length( ). x * x + y * y + z * z would have to be stored as a single float
 * while calculating. The limit that the components can sum to is 1 / 3 ( root( float.max ) ).
 */

//TODO: Should consider changing this Class into a wrapper for lwjglTests's Vector3f Class.
public class Vector3f {

    public static final Vector3f ZERO = new Vector3f(0.0f, 0.0f, 0.0f);
    public static final Vector3f ONE = new Vector3f(1.0f, 1.0f, 1.0f);
    public float x;
    public float y;
    public float z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //The limit that the components can sum to is 1 / 3 ( root( float.max ) ).
    public float length() {
        double _x = x;
        double _y = y;
        double _z = z;
        return (float) Math.sqrt(_x * _x + _y * _y + _z * _z);
    }

    //The limit that the components can sum to is 1 / 3 ( root( float.max ) ).
    public float quickLength() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public float max() {
        return Math.max(x, Math.max(y, z));
    }

    public float dot(Vector3f vector) {
        return x * vector.x + y * vector.y + z * vector.z;
    }

    public Vector3f cross(Vector3f vector) {
        float x_ = y * vector.z - z * vector.y;
        float y_ = z * vector.x - x * vector.z;
        float z_ = x * vector.y - y * vector.x;

        return new Vector3f(x_, y_, z_);
    }

    public Vector3f crossLargeFloat(Vector3f vector) {
        double x_ = y * vector.z - z * vector.y;
        double y_ = z * vector.x - x * vector.z;
        double z_ = x * vector.y - y * vector.x;

        return new Vector3f((float) x_, (float) y_, (float) z_);
    }

    public Vector3f normalized() {
        float length = length();

        return new Vector3f(x / length, y / length, z / length);
    }

    public Vector3f rotate(Vector3f axis, float angle) {
        float sinAngle = (float) Math.sin(-angle);
        float cosAngle = (float) Math.cos(-angle);

        return this.cross(axis.mul(sinAngle)).add(
                (this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
    }

    public Vector3f rotate(Quaternion rotation) {
        Quaternion conjugate = rotation.conjugate();

        Quaternion w = rotation.mul(this).mul(conjugate);

        return new Vector3f(w.x, w.y, w.z);
    }

    public Vector3f lerp(Vector3f dest, float lerpFactor) {
        return dest.sub(this).mul(lerpFactor).add(this);
    }

    public Vector3f add(Vector3f vector) {
        return new Vector3f(x + vector.x, y + vector.y, z + vector.z);
    }

    public static Vector3f add(Vector3f v, Vector3f v2) {
        return new Vector3f(v.x + v2.x, v.y + v2.y, v.z + v2.z);
    }

    public Vector3f add(float vector) {
        return new Vector3f(x + vector, y + vector, z + vector);
    }

    public Vector3f sub(Vector3f vector) {
        return new Vector3f(x - vector.x, y - vector.y, z - vector.z);
    }

    public Vector3f sub(float vector) {
        return new Vector3f(x - vector, y - vector, z - vector);
    }

    public Vector3f mul(Vector3f vector) {
        return new Vector3f(x * vector.x, y * vector.y, z * vector.z);
    }

    public Vector3f mul(float vector) {
        return new Vector3f(x * vector, y * vector, z * vector);
    }

    public Vector3f div(Vector3f vector) {
        return new Vector3f(x / vector.x, y / vector.y, z / vector.z);
    }

    public Vector3f div(float vector) {
        return new Vector3f(x / vector, y / vector, z / vector);
    }

    public Vector3f abs() {
        return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public String toString() {
        return "(" + x + " " + y + " " + z + ")";
    }

    public Vector2f getXY() {
        return new Vector2f(x, y);
    }

    public Vector2f getX_YFlipped() {
        return new Vector2f(x, -y);
    }

    public Vector2f getYZ() {
        return new Vector2f(y, z);
    }

    public Vector2f getZX() {
        return new Vector2f(z, x);
    }

    public Vector2f getYX() {
        return new Vector2f(y, x);
    }

    public Vector2f getZY() {
        return new Vector2f(z, y);
    }

    public Vector2f getXZ() {
        return new Vector2f(x, z);
    }

    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3f set(Vector3f vector) {
        set(vector.x, vector.y, vector.z);
        return this;
    }

    public boolean equals(Vector3f vector) {
        return x == vector.x && y == vector.y && z == vector.z;
    }

    public Vector3f clone() {
        return new Vector3f(this.x, this.y, this.z);
    }

}
