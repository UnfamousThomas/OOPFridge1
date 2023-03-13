import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Külmkapp {
    private List<Ese> asjadKülmikus;
    private Date viimatiMuudetud;
    private final int külmkappiSuurus;
    private int hetkelAsjuKülmikus;

    public Külmkapp(Date viimatiSisestatud, int külmkappiSuurus) {
        this.viimatiMuudetud = viimatiSisestatud;
        this.asjadKülmikus = new ArrayList<>();
        this.külmkappiSuurus = külmkappiSuurus;
        this.hetkelAsjuKülmikus = 0;
    }


    public void tühjendaKülmkapp() {
        asjadKülmikus.clear();
    }

    public void lisaKülmkappi(Ese ese) {
        // 10 + 0+10
        // 10 <= 10
        if(külmkappiSuurus < asjadKülmikus.size() + ese.getKogus()) {
            System.out.println("Ei mahtunud külmikusse. Söö ära.");
            return;
        }

        this.hetkelAsjuKülmikus = this.hetkelAsjuKülmikus + ese.getKogus();
        asjadKülmikus.add(ese);
    }

    private void eemaldaKülmkappist(int indeks) {
        asjadKülmikus.remove(indeks);
        setViimatiMuudetudNow();

    }

    private void setViimatiMuudetudNow() {
        this.viimatiMuudetud = Date.from(Instant.now());
    }

    private void eemaldaKülmkappist(Ese ese) {
        asjadKülmikus.remove(ese);
        setViimatiMuudetudNow();
    }

    public void uuendaEsemeKogust(String esemeNimetus, int uusKogus) {
        for (Ese eseKülmikus : asjadKülmikus) {
            if(esemeNimetus.equals(eseKülmikus.getEsemeNimetus())) {
                int ilmaKoguseta = this.hetkelAsjuKülmikus - eseKülmikus.getKogus();
                int uueKogusega = ilmaKoguseta + uusKogus;
                if(külmkappiSuurus < uueKogusega) {
                    System.out.println("Uus kogus ei mahtunud külmikusse. Söö ära. (Ei muutnud andmeid)");
                    return;
                }
                this.hetkelAsjuKülmikus = this.hetkelAsjuKülmikus - eseKülmikus.getKogus();
                eseKülmikus.setKogus(uusKogus);
                if(uusKogus == 0) kustutaEse(eseKülmikus);
                setViimatiMuudetudNow();
                return;
            }
        }

        System.out.println("Sellist eset ei ole külmkapis");
    }

    public void kustutaEse(Ese ese) {
        asjadKülmikus.remove(ese);
        setViimatiMuudetudNow();
    }

    public Ese võtaSuvalineEse() {
        Random random = new Random();
        if(asjadKülmikus.isEmpty()) {
            System.out.println("Külmkapp on tühi!");
            return null;
        }

        int suurus = random.nextInt(0, asjadKülmikus.size());
        //todo eemalda ka
        Ese ese = asjadKülmikus.get(suurus);
        int esemeHulk = ese.getKogus();
        this.hetkelAsjuKülmikus = this.hetkelAsjuKülmikus - esemeHulk;
        setViimatiMuudetudNow();
        kustutaEse(ese);
        return ese;
    }
}
