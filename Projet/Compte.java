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
    private int nombreReponses;
    private int nombreQuestions;
    private int nombreParties;

    /**
     *
     * @param identifiant Identifiant du compte.
     */
    public Compte(String identifiant) {
        this.identifiant = identifiant;
        this.historique=new ArrayList<>();
        this.nombreReponses=0;
        this.nombreQuestions=0;
        this.nombreParties=0;
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
        this.nombreReponses=0;
        this.nombreQuestions=0;
        this.nombreParties=0;
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
        this.nombreReponses=0;
        this.nombreQuestions=0;
        this.nombreParties=0;
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

    public void setNombreReponses(int nombreReponses) {
        this.nombreReponses = nombreReponses;
    }

    public void setNombreQuestions(int nombreQuestions) {
        this.nombreQuestions = nombreQuestions;
    }

    public void setNombreParties(int nombreParties) {
        this.nombreParties = nombreParties;
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

    public int getNombreReponses() {
        return nombreReponses;
    }

    public int getNombreQuestions() {
        return nombreQuestions;
    }

    public int getNombreParties() {
        return nombreParties;
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

    public void incrementeNombreQuestion(){
        this.nombreQuestions=nombreQuestions+1;
    }
    public void incrementeNombreReponses(){
        this.nombreReponses=nombreReponses+1;
    }
    public void incrementeNombreParties(){
        this.nombreParties=nombreParties+1;
    }

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void serialiser() throws IOException, ClassNotFoundException {
        File file= new File("Comptes.data");
        HashMap<String,Compte> hashMap; //Initialise une hashmap de Compte, la clé correspondra à l'identifiant du compte
        //si le fichier existe on récupère la hashmap sérialisé et on y met le compte
        if(file.exists()){
            FileInputStream fis=new FileInputStream(file);
            ObjectInputStream ois= new ObjectInputStream(fis);
            hashMap=(HashMap<String,Compte>) ois.readObject();
            hashMap.put(this.getIdentifiant(),this);
        }else{ //Sinon on instancie une nouvelle hashmap et on y met le compte
             hashMap=new HashMap<>();
             hashMap.put(this.getIdentifiant(),this);
        }
        //On sérialise la nouvelle hashmap dans le fichier
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
     * @return Indique si il y a un Compte dans Comptes.data qui possède déjà cet identifiant.
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
        HashMap<String,Compte> hashMap= (HashMap<String,Compte>) ois.readObject(); //récupère la hashmap sérialisée
        hashMap.remove(this.getIdentifiant()); //retire le compte correspondant à l'identifiant
        FileOutputStream fos= new FileOutputStream("Comptes.data");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(hashMap); //sérialise la nouvelle hashmap
    }

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void suspendre() throws IOException, ClassNotFoundException {
        FileInputStream fis= new FileInputStream("Comptes.data");
        ObjectInputStream ois=new ObjectInputStream(fis);
        HashMap<String,Compte> hashMap= (HashMap<String,Compte>) ois.readObject();//Récupère la hashmap sérialisée
        Compte compte = hashMap.get(this.getIdentifiant());//Récupère le compte avec le bon identifiant
        compte.setSuspendu(!compte.getSuspendu());//Inverse la valeur du booléen suspendu
        FileOutputStream fos= new FileOutputStream("Comptes.data");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(hashMap);//Sérialise la hasmap avec le compte modifié
    }

    /**
     *
     * @param partie Partie à ajouter dans l'historique.
     */
    public void ajoutePartie(Partie partie){
        this.historique.add(partie);
    }
}

