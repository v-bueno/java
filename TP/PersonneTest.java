public class PersonneTest {
    public static void main(String args[]) {
        Personne mwa = new Personne("BUENO", "Vincent", "Lannion", "France");
        Personne peut_etre_mwa = new Personne("BUENO", "Vincent", "Lannion", "France");
        Personne personne = new Personne("Homme","Lambda","Kelkpart","TOUPIPOU");
        System.out.println(personne);
        System.out.println("mwa est égal à peut_etre_mwa ? : "+mwa.estClone(peut_etre_mwa));
        System.out.println("peut_etre_mwa est égal à personne ? : "+peut_etre_mwa.estClone(personne));
        System.out.println("personne est égal à mwa ? : "+personne.estClone(mwa));
    }


}
