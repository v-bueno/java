import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 */
public class Compte implements Serializable {
    private String identifiant;
    private String motdepasse;
    private ArrayList<Partie> historique;
    private String type;
    private Boolean suspendu;

    /**
     *
     * @param identifiant
     */
    public Compte(String identifiant) {
        this.identifiant = identifiant;
        this.historique=new ArrayList<>();
    }

    /**
     *
     * @param identifiant
     * @param motdepasse
     */
    Compte(String identifiant, String motdepasse) {
        this.identifiant = identifiant;
        this.motdepasse = motdepasse;
        this.historique=new ArrayList<>();
    }

    /**
     *
     * @param identifiant
     * @param motdepasse
     * @param type
     */
    Compte(String identifiant, String motdepasse, String type) {
        this.identifiant = identifiant;
        this.motdepasse = motdepasse;
        this.type = type;
        this.historique=new ArrayList<>();
    }

    /**
     *
     * @param identifiant
     * @param motdepasse
     * @param historique
     * @param type
     * @param suspendu
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
     * @param identifiant
     * @param motdepasse
     * @param type
     * @param suspendu
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
     * @param identifiant
     */
    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    /**
     *
     * @param motdepasse
     */
    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    /**
     *
     * @param historique
     */
    public void setHistorique(ArrayList<Partie> historique) {
        this.historique = historique;
    }


    /**
     *
     * @param suspendu
     */
    public void setSuspendu(Boolean suspendu) {
        this.suspendu = suspendu;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public String getIdentifiant() {
        return identifiant;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return
     */
    public String getMotdepasse() {
        return motdepasse;
    }

    /**
     *
     * @return
     */
    public ArrayList<Partie> getHistorique() {
        return historique;
    }

    /**
     *
     * @return
     */
    public Boolean getSuspendu() {
        return suspendu;
    }

    /**
     *
     * @return
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
     * @return
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
     * @return
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
     * @param partie
     */
    public void ajoutePartie(Partie partie){
        this.historique.add(partie);
    }
}

