public class NbHab extends Exception {
    public NbHab(int population){
        System.out.println("Une ville ne peut pas avoir un nombre d'habitants négatif : "+population);
    }
}
