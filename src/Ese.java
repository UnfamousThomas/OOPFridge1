import java.util.Date;

public class Ese {

    private String esemeNimetus;
    private Date lähebHalvaks;
    private int kogus;

    public Ese(String esemeNimetus, Date lähebHalvaks, int kogus) {
        this.esemeNimetus = esemeNimetus;
        this.lähebHalvaks = lähebHalvaks;
        this.kogus = kogus;
    }

    public String getEsemeNimetus() {
        return esemeNimetus;
    }

    public void setEsemeNimetus(String esemeNimetus) {
        this.esemeNimetus = esemeNimetus;
    }

    public Date getLähebHalvaks() {
        return lähebHalvaks;
    }

    public int getKogus() {
        return kogus;
    }

    public void setLähebHalvaks(Date lähebHalvaks) {
        this.lähebHalvaks = lähebHalvaks;
    }

    public void setKogus(int kogus) {
        this.kogus = kogus;
    }

    public boolean kasOnHalvaksLäinud() {
        if(lähebHalvaks.getTime() < System.currentTimeMillis()) {
            return true;
        };

        return false;
    }

    @Override
    public String toString() {
        return esemeNimetus + " - " + kogus;
    }

    @Override
    public boolean equals(Object obj) {
        Ese eseObj = (Ese) obj;
        return this.toString().equals(eseObj.toString());
    }
}
