import java.util.Objects;

public class Segment2D {

    private Point2D A;

    private Point2D B;

    public Point2D getA() {
        return A;
    }

    public void setA(Point2D a) {
        A = a;
    }

    public Point2D getB() {
        return B;
    }

    public void setB(Point2D b) {
        B = b;
    }

    public Segment2D(Point2D a, Point2D b) {
        if (!(a.equals(b))) {
            A = a;
            B = b;
        }
        else{
            System.err.println("2 points identiques aux extrémités du segment.");
        }
    }

    @Override
    public String toString() {
        return "Segment2D{" +
                "A=" + A +
                ", B=" + B +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Segment2D segment2D)) return false;
        return ((Objects.equals(getA(), segment2D.getA()) && Objects.equals(getB(), segment2D.getB()))
                ||(Objects.equals(getB(), segment2D.getA()) && Objects.equals(getA(), segment2D.getB())));
    }

}
