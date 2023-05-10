public class NomVille extends Exception{
    public NomVille(String nom){
        System.out.println("Le nom de la ville contient moins de 3 ou plus de 15 caractères: "+nom);
    }
}
