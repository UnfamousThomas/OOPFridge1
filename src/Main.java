import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Külmkapp külmkapp = new Külmkapp(19);
        Ese viinerid = new Ese("viinerid", Date.from(Instant.ofEpochSecond(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(3))), 10);
        külmkapp.lisaKülmkappi(viinerid);
        //külmkapp.tühjendaKülmkapp();
        külmkapp.uuendaEsemeKogust("viinerid", 20);
        System.out.println(külmkapp.võtaSuvalineEse());
    }
}