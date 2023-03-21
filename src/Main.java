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
        Scanner scanner = new Scanner(System.in);
        String failiNimi = scanner.nextLine();
        try {
            külmkapp = loeKülmkapp(failiNimi);

            teeMidagi(külmkapp, failiNimi);
        } catch (ParseException parseException) {
            parseException.printStackTrace();
            System.exit(1);
        }



    }

    private static void teeMidagi(Külmkapp külmkapp, String failiNimi) {
        System.out.println("Mida soovid teha? (1 - lisa külmkappi ese, 2 - võta suvaline ese külmkapist, 3 - eemalda ese külmkapist, 4- salvesta külmkapp ja lõpeta töö");
        Scanner scanner = new Scanner(System.in);
        int midagi = scanner.nextInt();

        if(midagi == 4) {
            try {
                külmkapp.salvestaKülmkapp(failiNimi);

                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(midagi == 2) {
            Ese ese = külmkapp.võtaSuvalineEse();
            if(ese != null) {
                prindiEse(ese);
            }
        }

        if(midagi == 3) {
            System.out.println("Mis on eseme nimi?");
            String nimi = scanner.nextLine();
            Ese ese = külmkapp.leiaEseNimetusega(nimi);
            külmkapp.kustutaEse(ese);
        }

        if(midagi == 1) {
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Mis on uue eseme nimi?");
            String nimi = scanner1.nextLine();
            System.out.println(nimi);
            System.out.println("Mitu ühikut soovid lisada?");
            int kogus = Integer.parseInt(scanner1.nextLine());
            System.out.println("Kuupäev pahaks (MM/dd/yyyy)");
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date date = sdf.parse(scanner1.nextLine());
                Ese ese = new Ese(nimi, date, kogus);
                külmkapp.lisaKülmkappi(ese);
            } catch (ParseException e) {
                System.out.println("Midagi läks kuupäevaga valesti! Proovi uuesti.");
                teeMidagi(külmkapp, failiNimi);
            }

        }

        teeMidagi(külmkapp, failiNimi);
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
            int suurus = scanner.nextInt();
            loodudKülmik = new Külmkapp(suurus);
        }

        return loodudKülmik;
    }
}