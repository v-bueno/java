public class PtsProches extends Exception {
    public PtsProches(Segment2D ab, Segment2D bc, Segment2D ca){
        System.out.println("Au moins l'un des segments est mesure moins d'1cm : "+ab+bc+ca);
    }
}
