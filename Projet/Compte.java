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

    public Compte(String identifiant) {
        this.identifiant = identifiant;
        this.historique=new ArrayList<>();
    }

    Compte(String identifiant, String motdepasse) {
        this.identifiant = identifiant;
        this.motdepasse = motdepasse;
        this.historique=new ArrayList<>();
    }
    Compte(String identifiant, String motdepasse, String type) {
        this.identifiant = identifiant;
        this.motdepasse = motdepasse;
        this.type = type;
        this.historique=new ArrayList<>();
    }

    public Compte(String identifiant, String motdepasse, ArrayList<Partie> historique, String type, Boolean suspendu) {
        this.identifiant = identifiant;
        this.motdepasse = motdepasse;
        this.historique = historique;
        this.type = type;
        this.suspendu = suspendu;
        this.historique=new ArrayList<>();
    }



    public Compte(String identifiant, String motdepasse, String type, Boolean suspendu) {
        this.identifiant = identifiant;
        this.motdepasse = motdepasse;
        this.type = type;
        this.suspendu = suspendu;
        this.historique=new ArrayList<>();
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public void setHistorique(ArrayList<Partie> historique) {
        this.historique = historique;
    }



    public void setSuspendu(Boolean suspendu) {
        this.suspendu = suspendu;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public String getType() {
        return type;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public ArrayList<Partie> getHistorique() {
        return historique;
    }

    public Boolean getSuspendu() {
        return suspendu;
    }

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

    public Boolean verifieIdentifiant() throws IOException,ClassNotFoundException {
        FileInputStream fis = new FileInputStream("Comptes.data");
        ObjectInputStream ois = new ObjectInputStream(fis);
        HashMap<String,Compte> hashMap= (HashMap<String,Compte>) ois.readObject();
        if(hashMap.get(this.getIdentifiant())==null){
            return false;
        }
        return true;
    }





    public void supprimer() throws IOException, ClassNotFoundException {
        FileInputStream fis= new FileInputStream("Comptes.data");
        ObjectInputStream ois=new ObjectInputStream(fis);
        HashMap<String,Compte> hashMap= (HashMap<String,Compte>) ois.readObject();
        hashMap.remove(this.getIdentifiant());
        FileOutputStream fos= new FileOutputStream("Comptes.data");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(hashMap);
    }

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

    public void ajoutePartie(Partie partie){
        this.historique.add(partie);
    }
}

