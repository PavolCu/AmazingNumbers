
class Circle implements Measurable {
    private double radius;


    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * Math.pow(radius, 2);
    }
}

// do not change the interface
interface Measurable {
    double area();
}