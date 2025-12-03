package year2024.day14;

import com.google.common.math.IntMath;

import utils.Vector2;

public class Robot {

    private final Vector2 mapSize;
    private Vector2 position;
    private Vector2 velocity;

    public Robot(Vector2 position, Vector2 direction, Vector2 mapSize) {
        this.position = position;
        this.velocity = direction;
        this.mapSize = mapSize;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void move() {
        position = position.add(velocity);
        position = new Vector2(IntMath.mod(position.x(), mapSize.x()), IntMath.mod(position.y(), mapSize.y()));
    }

    @Override
    public String toString(){
        return position.toString();
    }
}
