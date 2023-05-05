import java.security.PublicKey;
import java.util.Objects;

import static java.lang.Math.pow;

public class Triangle2D {

    public Point2D A;

    public Point2D B;

    public Point2D C;

    public Triangle2D(Point2D a, Point2D b, Point2D c) {
        if(a.equals(b)||a.equals(c)||c.equals(a)){
            System.err.println("2 points se superposent.");
        }
        else {
            A = a;
            B = b;
            C = c;
        }
    }

    public boolean estIsocele(){
        Segment2D AB = new Segment2D(A,B);
        Segment2D BC = new Segment2D(B,C);
        Segment2D AC = new Segment2D(A,C);

        double n_AB = AB.norme();
        double n_BC = BC.norme();
        double n_AC = AC.norme();

        return (n_AB==n_BC||n_BC==n_AC||n_AC==n_AB);
    }

    public boolean estEquilateral(){
        Segment2D AB = new Segment2D(A,B);
        Segment2D BC = new Segment2D(B,C);
        Segment2D AC = new Segment2D(A,C);

        double n_AB = AB.norme();
        double n_BC = BC.norme();
        double n_AC = AC.norme();

        return (n_AB==n_BC && n_BC==n_AC);
    }

    public boolean estRectangle(){
        Segment2D AB = new Segment2D(A,B);
        Segment2D BC = new Segment2D(B,C);
        Segment2D AC = new Segment2D(A,C);

        double n_AB2 = pow(AB.norme(),2);
        double n_BC2 = pow(BC.norme(),2);
        double n_AC2 = pow(AC.norme(),2);

        return ((n_AB2==n_BC2+n_AC2)||
                (n_AC2==n_AB2+n_BC2)||
                (n_BC2==n_AB2+n_AC2));
    }
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

    public Point2D getC() {
        return C;
    }

    public void setC(Point2D c) {
        C = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Triangle2D that)) return false;
        return Objects.equals(getA(), that.getA()) && Objects.equals(getB(), that.getB()) && Objects.equals(getC(), that.getC());
    }

    @Override
    public String toString() {
        return "Triangle2D{" +
                "A=" + A +
                ", B=" + B +
                ", C=" + C +
                '}';
    }


}
