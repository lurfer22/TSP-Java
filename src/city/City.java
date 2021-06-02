package city;

public class City {
    private final int x;
    private final int y;

    public City(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // calculate the distance between the passed city and
    // this city
    // d=Math.sqrt((x2−x1)^2 + (y2−y1)^2)
    public double calculateDistance(City city) {
        return Math.sqrt(Math.pow(city.getX() - this.x, 2) + Math.pow(city.getY() - this.y, 2));
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof City)) return false;

        City t = (City)o;
        return this.x == t.getX() && this.y == t.getY();
    }

    @Override
    public String toString() {
        return "(" + this.getX() + ", " + this.getY() + ")";
    }
}
