public class Ville {
    private String nom;
    private int population;
    private Pays pays;
    private Ville[] villesJumelees;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) throws NomVille {
        if (nom.length()>2 && nom.length()<16) {
            this.nom = nom;
        }
        else{
            throw new NomVille(nom);
        }
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) throws NbHab {
        if (population > 0){
            this.population = population;
        }
        else {
            throw new NbHab(population);
        }
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public Ville[] getVillesJumelees() {
        return villesJumelees;
    }

    public void setVillesJumelees(Ville[] villesJumelees) {
        this.villesJumelees = villesJumelees;
    }

    public Ville(String nom, int population, Pays pays, Ville[] villesJumelees) {
        this.nom = nom;
        this.population = population;
        this.pays = pays;
        this.villesJumelees = villesJumelees;
    }

    public Ville(String nom, int population, Pays pays) {
        this.nom = nom;
        this.population = population;
        this.pays = pays;
        this.villesJumelees = null;
    }
}
