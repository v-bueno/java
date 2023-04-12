import java.util.Objects;

public class Personne {
    private String nom;
    private String prenom;
    private String ville;
    private String pays;

    public Personne(String nom, String prenom, String ville, String pays) {
        this.nom = nom;
        this.prenom = prenom;
        this.ville = ville;
        this.pays = pays;
    }
    public void affiche(){
        System.out.println(getNom()+","+getPrenom()+","+getVille()+","+getPays());
    }

    public boolean estClone(Personne personne) {
        if (this == personne) return true;
        return Objects.equals(getNom(), personne.getNom()) && Objects.equals(getPrenom(), personne.getPrenom()) && Objects.equals(getVille(), personne.getVille()) && Objects.equals(getPays(), personne.getPays());
    }

    public String toString(){
        return (this.nom+" "+this.prenom+" "+this.ville+" "+this.pays);
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getVille() {
        return ville;
    }

    public String getPays() {
        return pays;
    }
}
