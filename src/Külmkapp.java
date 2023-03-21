import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Külmkapp {
    /**
     * List asjadest mis on külmikus
     */
    private List<Ese> asjadKülmikus;
    /**
     * Millal viimati külmkapi sisu muudeti
     */
    private Date viimatiMuudetud;
    /**
     * Kui suur on külmkapp (max)
     */
    private final int külmkapiSuurus;
    private int hetkelAsjuKülmikus;

    /**
     * Konstrueerib külmmkapi.
     * @param külmkapiSuurus Kui suur on külmkapp
     */
    public Külmkapp(int külmkapiSuurus) {
        this.viimatiMuudetud = Date.from(Instant.now());
        this.asjadKülmikus = new ArrayList<>();
        this.külmkapiSuurus = külmkapiSuurus;
        this.hetkelAsjuKülmikus = 0;
    }

    /**
     * Tühjendab külmkapi.
     */
    public void tühjendaKülmkapp() {
        asjadKülmikus.clear();
        this.hetkelAsjuKülmikus = 0;
        setViimatiMuudetudNow();
    }

    /**
     * Lisab eseme külmkappi
     * @param ese Ese mis lisada
     */
    public void lisaKülmkappi(Ese ese) {
        if(külmkapiSuurus < asjadKülmikus.size() + ese.getKogus()) {
            System.out.println("Ei mahtunud külmikusse. Söö ära.");
            return;
        }

        this.hetkelAsjuKülmikus = this.hetkelAsjuKülmikus + ese.getKogus();
        asjadKülmikus.add(ese);
        setViimatiMuudetudNow();
    }

    /**
     * Eemdlab eseme külmkapist kasutades indeksit
     * @param indeks Listi indeks
     */
    private void eemaldaKülmkappist(int indeks) {
        Ese ese = asjadKülmikus.get(indeks);
        eemaldaKülmkappist(ese);

    }

    /**
     * Paneb viimatimuudetud aja praeguse peale.
     */
    private void setViimatiMuudetudNow() {
        this.viimatiMuudetud = Date.from(Instant.now());
    }

    /**
     * Eemaldab eseme külmkapist
     * @param ese Ese mis eemaldada
     */
    private void eemaldaKülmkappist(Ese ese) {
        asjadKülmikus.remove(ese);
        this.hetkelAsjuKülmikus = this.hetkelAsjuKülmikus - ese.getKogus();
        setViimatiMuudetudNow();
    }

    /**
     * Uuendab eseme kogust ja külmkappi hulka.
     * @param esemeNimetus Mille kogust muuta
     * @param uusKogus Eseme uus kogus
     */
    public void uuendaEsemeKogust(String esemeNimetus, int uusKogus) {
        for (Ese eseKülmikus : asjadKülmikus) {
            if(esemeNimetus.equals(eseKülmikus.getEsemeNimetus())) {
                int ilmaKoguseta = this.hetkelAsjuKülmikus - eseKülmikus.getKogus();
                int uueKogusega = ilmaKoguseta + uusKogus;
                if(külmkapiSuurus < uueKogusega) {
                    System.out.println("Uus kogus ei mahtunud külmikusse. Söö ära. (Ei muutnud andmeid)");
                    return;
                }
                this.hetkelAsjuKülmikus = this.hetkelAsjuKülmikus - eseKülmikus.getKogus() + uusKogus;
                eseKülmikus.setKogus(uusKogus);
                if(uusKogus == 0) kustutaEse(eseKülmikus);
                setViimatiMuudetudNow();
                return;
            }
        }

        System.out.println("Sellist eset ei ole külmkapis");
    }

    /**
     * Kustutab eseme külmkappi listist
     * @param ese Mis kustutada
     */
    public void kustutaEse(Ese ese) {
        asjadKülmikus.remove(ese);
        setViimatiMuudetudNow();
    }

    /**
     * Tagastab suvalise eseme ning eemaldab selle.
     * @return Suvaline ese esemtest külmkapist.
     */
    public Ese võtaSuvalineEse() {
        if(asjadKülmikus.isEmpty()) {
            System.out.println("Külmkapp on tühi!");
            return null;
        }
        Random random = new Random();

        int suurus = random.nextInt(0, asjadKülmikus.size());
        Ese ese = asjadKülmikus.get(suurus);
        int esemeHulk = ese.getKogus();
        this.hetkelAsjuKülmikus = this.hetkelAsjuKülmikus - esemeHulk;
        setViimatiMuudetudNow();
        kustutaEse(ese);
        return ese;
    }
}
