import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args)  {
        Külmkapp külmkapp;
        System.out.println("Palun sisesta fail kust lugeda külmkapp.");
        Scanner tekstiScanner = new Scanner(System.in);
        String failiNimi = tekstiScanner.nextLine();
        try {
            külmkapp = loeKülmkapp(failiNimi);

            teeMidagi(külmkapp, failiNimi, tekstiScanner);
        } catch (ParseException parseException) {
            parseException.printStackTrace();
            System.exit(1);
        }



    }

    private static void teeMidagi(Külmkapp külmkapp, String failiNimi, Scanner tekstiScanner) {
        System.out.println("Mida soovid teha?");
        System.out.println("1 - lisa külmkappi ese");
        System.out.println("2 - võta suvaline ese külmkapist");
        System.out.println("3 - eemalda ese külmkapist");
        System.out.println("4- salvesta külmkapp ja lõpeta töö");
        System.out.println("5 - näita külmkapi esemeid");
        System.out.println("6 - Eemalda kõik halvaks läinud esemed");
        int midagi = Integer.parseInt(tekstiScanner.nextLine());

        if(midagi == 6) {
            try {
                külmkapp.salvestaKülmkapp(failiNimi);

                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(midagi == 4) {
            külmkapp.eemaldaKülmkapistHalvaksLäinud();
        }

        if(midagi == 1) {
            külmkapp.näitaKülmkappi();
        }

        if(midagi == 5) {
            Ese ese = külmkapp.võtaSuvalineEse();
            if(ese != null) {
                prindiEse(ese);
            }
        }

        if(midagi == 3) {
            if(külmkapp.kasOnTühi()) {
                System.out.println("Ei saa eemaldada, külmkapp on tühi.");
                teeMidagi(külmkapp, failiNimi, tekstiScanner);
            }
            System.out.println("Mis on eseme nimi?");
            String nimi = tekstiScanner.nextLine();
            Ese ese = külmkapp.leiaEseNimetusega(nimi);
            külmkapp.kustutaEse(ese);
        }

        if(midagi == 2) {
            System.out.println("Mis on uue eseme nimi?");
            String nimi = tekstiScanner.nextLine();
            System.out.println("Mitu ühikut soovid lisada?");
            int kogus = Integer.parseInt(tekstiScanner.nextLine());
            System.out.println("Kuupäev pahaks (MM/dd/yyyy)");
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date date = sdf.parse(tekstiScanner.nextLine());
                Ese ese = new Ese(nimi, date, kogus);
                külmkapp.lisaKülmkappi(ese);
            } catch (ParseException e) {
                System.out.println("Midagi läks kuupäevaga valesti! Proovi uuesti.");
                teeMidagi(külmkapp, failiNimi, tekstiScanner);
            }

        }

        teeMidagi(külmkapp, failiNimi, tekstiScanner);
    }

    private static void prindiEse(Ese ese) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("Nimi: " + ese.getEsemeNimetus());
        System.out.println("Kogus: " + ese.getKogus());
        System.out.println("Läheb halvaks: " + sdf.format(ese.getLähebHalvaks()));
    }

    private static Külmkapp loeKülmkapp(String failiNimi) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        Külmkapp loodudKülmik = null;
        List<Ese> esemed = new ArrayList<>();
        int külmKapiSuurus = 0;
        Date külmkapiMuudetud = null;
        try {
            File file = new File(failiNimi);
            Scanner failiScanner = new Scanner(file);
            while (failiScanner.hasNextLine()) {
                String[] elemendid = failiScanner.nextLine().split(" ");
                if (elemendid[0].equals("K")) {
                    külmKapiSuurus = Integer.parseInt(elemendid[1]);
                    külmkapiMuudetud = new SimpleDateFormat("MM/dd/yyyy").parse(elemendid[2]);

                } else {
                    String esemeNimetus = elemendid[0];
                    int kogus = Integer.parseInt(elemendid[1]);
                    Date date = new SimpleDateFormat("MM/dd/yyyy").parse(elemendid[2]);
                    Ese ese = new Ese(esemeNimetus, date, kogus);
                    esemed.add(ese);
                }
            }

            loodudKülmik = new Külmkapp(külmKapiSuurus, esemed, külmkapiMuudetud);
        } catch (IOException e){
            System.out.println("Loon uue külmkapi.");
            System.out.println("Sisesta uue külmkapi suurus");
            int suurus = Integer.parseInt(scanner.nextLine());
            loodudKülmik = new Külmkapp(suurus);
        }

        return loodudKülmik;
    }
}