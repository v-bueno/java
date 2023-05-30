import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Compte implements Serializable {
    private String identifiant;
    private String motdepasse;
    private ArrayList<Partie> historique;
    private String type;
    private Boolean suspendu;

    /**
     *
     * @param identifiant Identifiant du compte.
     */
    public Compte(String identifiant) {
        this.identifiant = identifiant;
        this.historique=new ArrayList<>();
    }

    /**
     *
     * @param identifiant Identifiant du compte.
     * @param motdepasse Mot de Passe du compte.
     */
    Compte(String identifiant, String motdepasse) {
        this.identifiant = identifiant;
        this.motdepasse = motdepasse;
        this.historique=new ArrayList<>();
    }

    /**
     *
     * @param identifiant Identifiant du compte.
     * @param motdepasse Mot de Passe du compte.
     * @param historique Historique des Parties.
     * @param type Type de compte admin ou joueur.
     * @param suspendu Indique si le compte est suspendu ou non.
     */
    public Compte(String identifiant, String motdepasse, ArrayList<Partie> historique, String type, Boolean suspendu) {
        this.identifiant = identifiant;
        this.motdepasse = motdepasse;
        this.historique = historique;
        this.type = type;
        this.suspendu = suspendu;
        this.historique=new ArrayList<>();
    }


    /**
     *
     * @param identifiant Identifiant du compte.
     * @param motdepasse Mot de Passe du compte.
     * @param type Type de compte admin ou joueur.
     * @param suspendu Indique si le compte est suspendu.
     */
    public Compte(String identifiant, String motdepasse, String type, Boolean suspendu) {
        this.identifiant = identifiant;
        this.motdepasse = motdepasse;
        this.type = type;
        this.suspendu = suspendu;
        this.historique=new ArrayList<>();
    }

    /**
     *
     * @param identifiant Nouvel Identidiant du compte.
     */
    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    /**
     *
     * @param motdepasse Nouveau Mot de Passe du compte.
     */
    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    /**
     *
     * @param historique Nouvel Historique du compte.
     */
    public void setHistorique(ArrayList<Partie> historique) {
        this.historique = historique;
    }


    /**
     *
     * @param suspendu Indique si le compte devient suspendu.
     */
    public void setSuspendu(Boolean suspendu) {
        this.suspendu = suspendu;
    }

    /**
     *
     * @param type Nouveau Type de compte.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return Renvoie l'identifiant du compte.
     */
    public String getIdentifiant() {
        return identifiant;
    }

    /**
     *
     * @return Renvoie le type du compte.
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return Renvoie le mot de passe du compte.
     */
    public String getMotdepasse() {
        return motdepasse;
    }

    /**
     *
     * @return Renvoie l'historique du compte.
     */
    public ArrayList<Partie> getHistorique() {
        return historique;
    }

    /**
     *
     * @return Indique si le compte est suspendu.
     */
    public Boolean getSuspendu() {
        return suspendu;
    }

    /**
     *
     * @return Renvoie l'objet convertit en String.
     */
    @Override
    public String toString() {
        return "Compte{" +
                "identifiant='" + identifiant + '\'' +
                ", motdepasse='" + motdepasse + '\'' +
                ", historique=" + historique +
                ", type='" + type + '\'' +
                ", suspendu=" + suspendu +
                '}';
    }


    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void serialiser() throws IOException, ClassNotFoundException {
        File file= new File("Comptes.data");
        HashMap<String,Compte> hashMap;
        if(file.exists()){
            FileInputStream fis=new FileInputStream(file);
            ObjectInputStream ois= new ObjectInputStream(fis);
            hashMap=(HashMap<String,Compte>) ois.readObject();
            hashMap.put(this.getIdentifiant(),this);
        }else{
             hashMap=new HashMap<>();
             hashMap.put(this.getIdentifiant(),this);
        }
        FileOutputStream fos=new FileOutputStream(file);
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(hashMap);
        oos.flush();
        oos.close();
        fos.close();
    }

    /**
     *
     * @return Renvoie le compte dans Compte.data qui a le même identifiant et mot de passe que celui-ci.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Compte verifieIdentifiantMotdepasse() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("Comptes.data");
        ObjectInputStream ois = new ObjectInputStream(fis);
        HashMap<String,Compte> hashMap= (HashMap<String,Compte>) ois.readObject();
        Compte compte=hashMap.get(this.getIdentifiant());
        if (compte!=null) {
            if (compte.getMotdepasse().equals(this.getMotdepasse())) {
                return compte;
            }
        }
        ois.close();
        return null;
    }

    /**
     *
     * @return Indique si il y a un Compte dans Compte.data qui possède déjà cet identifiant.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Boolean verifieIdentifiant() throws IOException,ClassNotFoundException {
        FileInputStream fis = new FileInputStream("Comptes.data");
        ObjectInputStream ois = new ObjectInputStream(fis);
        HashMap<String,Compte> hashMap= (HashMap<String,Compte>) ois.readObject();
        if(hashMap.get(this.getIdentifiant())==null){
            return false;
        }
        return true;
    }


    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void supprimer() throws IOException, ClassNotFoundException {
        FileInputStream fis= new FileInputStream("Comptes.data");
        ObjectInputStream ois=new ObjectInputStream(fis);
        HashMap<String,Compte> hashMap= (HashMap<String,Compte>) ois.readObject();
        hashMap.remove(this.getIdentifiant());
        FileOutputStream fos= new FileOutputStream("Comptes.data");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(hashMap);
    }

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void suspendre() throws IOException, ClassNotFoundException {
        FileInputStream fis= new FileInputStream("Comptes.data");
        ObjectInputStream ois=new ObjectInputStream(fis);
        HashMap<String,Compte> hashMap= (HashMap<String,Compte>) ois.readObject();
        Compte compte = hashMap.get(this.getIdentifiant());
        compte.setSuspendu(!compte.getSuspendu());
        FileOutputStream fos= new FileOutputStream("Comptes.data");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(hashMap);
    }

    /**
     *
     * @param partie Partie à ajouter dans l'historique.
     */
    public void ajoutePartie(Partie partie){
        this.historique.add(partie);
    }
}

