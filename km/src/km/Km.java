/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package km;

import java.io.*;
import java.util.*;

/**
 *
 * @author kelement
 */
public class Km {

    // üzemanyag ár
    static final double UZEMANYAGAR = 43.74;

    //előírt KM
    static final int ELOIRTKM = 6643;

    // előírt munkanapok napok száma (a legkisebb a 15!!!)
    static final int MUNKANAPOK = 16;

    // a kész lista, amiben összeáll az útvonalnyilvántartás
    // az UtiCel.java-ban van az objektum leírása
    static ArrayList<UtiCel> uticelok = new ArrayList<>();

    // lista, amiben a települések és távolságaik vannak
    // a TavolsagAdatok.java-ban van az objektum leírása
    static ArrayList<TavolsagAdatok> tavolsagok = new ArrayList<>();

    static void test() throws FileNotFoundException, IOException {
        File file = new File("tavolsag.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String sor;
            String tmp[];
            while ((sor = reader.readLine()) != null) {
                tmp = sor.split(";");
                tavolsagok.add(new TavolsagAdatok(tmp[0], Integer.parseInt(tmp[1])));
            }
        }
    }

    // a távolság adatokat feltöltjük a <tavolsagok> listába
    static void tavolsagListaFeltolt(String filename) {
        try {
            RandomAccessFile f = new RandomAccessFile(filename, "r");
            String sor = f.readLine();
            String tmp[];
            while (sor != null) {
                tmp = sor.split(";");
                tavolsagok.add(new TavolsagAdatok(tmp[0], Integer.parseInt(tmp[1])));
                sor = f.readLine();
            }
        } catch (IOException e) {
            System.out.println("Hiba: " + e.getMessage());
        }
    }

    // a <tavolsagok> lista kiírása ellenőrzésül
    static void tavolsagListaKiir() {
        int N = tavolsagok.size();
        for (int i = 0; i < N; i++) {
            System.out.println(String.format("%20s %4d ", tavolsagok.get(i).getVaros(),
                    tavolsagok.get(i).getTavolsag()));
        }
    }

    // véletlenszerűen generál egy város és km adatot a <tavolsagok> listából,
    // és egy két elemű tömbben adja vissza: 
    // [0] város, [1] km távolság
    static String[] randomTavolsagAdat(ArrayList<TavolsagAdatok> tavolsagok) {
        int N = tavolsagok.size();
        int rnd = (int) (Math.random() * N);
        String[] tavAdat = new String[2];
        for (int i = 0; i < 1; i++) {
            tavAdat[0] = tavolsagok.get(rnd).getVaros();
            tavAdat[1] = Integer.toString(tavolsagok.get(rnd).getTavolsag());
        }
        return tavAdat;
    }

    // <uticelok> listából kiírja a lényeges adatokat a képernyőre
    static void getUticelAdatok(UtiCel cel) {
        System.out.println(String.format("%15s\t%5d km\t%.2f Ft/km\t%.2f Ft",
                cel.getVaros(),
                cel.getTavolsag(),
                cel.getUzemanyagAr(),
                cel.getUtiKoltseg()
        ));
    }

    // elválasztó vonal kiírása a képernyőre a 
    // getUticelListaAdatok metódushoz
    static void elvalasztoVonal(int hossz) {
        for (int i = 0; i < hossz; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    // tábázatos formában kiírja a képernyőre az <uticelok> lista adatait
    static void getUticelListaAdatok(ArrayList<UtiCel> uticelok) {
        elvalasztoVonal(76);
        // Város -> km -> ft/km -> utiköltség
        // formában kilistázza az adatokat
        for (int i = 0; i < uticelok.size(); i++) {
            System.out.println(String.format("%1s.\t %20s\t%5d km\t%.2f Ft/km\t%d Ft",
                    i + 1,
                    uticelok.get(i).getVaros(),
                    uticelok.get(i).getTavolsag(),
                    uticelok.get(i).getUzemanyagAr(),
                    Math.round(Km.uticelok.get(i).getUtiKoltseg())
            ));
            elvalasztoVonal(76);
        }

        // a lista végére láblécet rajzol
        // darabszám, össz km, össz Ft adatokkal
        System.out.println(String.format("%17s db\t %s km\t\t\t %s Ft",
                getUticelListaDb(uticelok),
                getUticelListaOsszKm(uticelok),
                getUticelListaOsszFt(uticelok)
        ));
        System.out.println(String.format("\t%21s km a különbség", ELOIRTKM - getUticelListaOsszKm(uticelok)));
    }

    // visszaadja az <uticelok> listában szereplő adatok
    // km összegét (megtett össz km)
    static int getUticelListaOsszKm(ArrayList<UtiCel> uticelok) {
        int N = uticelok.size();
        int osszKm = 0;
        for (int i = 0; i < N; i++) {
            osszKm += uticelok.get(i).getTavolsag();
        }
        return osszKm;
    }

    // visszaadja az <uticelok> listában szereplő adatok
    // Ft összegét (utiköltség)
    static int getUticelListaOsszFt(ArrayList<UtiCel> uticelok) {
        int N = uticelok.size();
        int osszFt = 0;
        for (int i = 0; i < N; i++) {
            osszFt += uticelok.get(i).getUtiKoltseg();
        }
        return osszFt;
    }

    // visszaadja az <uticelok> listában szereplő adatok
    // darabszámát (utazások száma)
    static int getUticelListaDb(ArrayList<UtiCel> uticelok) {
        int N = uticelok.size();
        return N;
    }

    // true a visszatérési értéke, ha az <uticelok> listában megtalálhatóak
    // a VarosKm tombben megadott adatok
    // (ilyen tömbben adja vissza az adatokat
    // a randomTavolsagAdat() is
    static boolean getUticelListaVarosKmVane(ArrayList<UtiCel> uticelok, String[] VarosKm) {
        int i = 0;
        int N = uticelok.size() - 1;
        while ((i <= N) && !uticelok.get(i).getVaros().equals(VarosKm[0])) {
            i++;
        }
        if (i <= N) {
            return true;
        } else {
            return false;
        }
    }

    static void general() {
        // fő metódus!

        // addig ismétli ez a while blokk az <uticelok> lista feltöltését,
        // amíg 10 km nem lesz a különbség az előírt és a generált között
        while (!(ELOIRTKM - getUticelListaOsszKm(uticelok) <= 10)) {
            uticelok.clear(); //lenullázzuk az <uticelok> listát, tutira
            int listaKM = 0;  // az <uticelok> lista km összege

            // addig, amíg el nem érjük az előírt mennyiséget
            while (listaKM < ELOIRTKM && uticelok.size() < MUNKANAPOK) {
                String tmp[] = randomTavolsagAdat(tavolsagok); //tömbbe generál a randomakármi

                // ha van már ilyen város az <uticelok> listában, akkor generálunk újra
                while (getUticelListaVarosKmVane(uticelok, tmp)) {
                    tmp = randomTavolsagAdat(tavolsagok);
                }
                // ha a generált km nagyobb lenne, mint a fennmaradó km, akkor többet generálnánk
                // így inkább kilépünk az utolsó while ciklusból
                if (listaKM + Integer.parseInt(tmp[1]) < ELOIRTKM) {
                    uticelok.add(new UtiCel(tmp, UZEMANYAGAR, 0));
                } else {
                    break;
                }
                listaKM = getUticelListaOsszKm(uticelok); // ciklusváltozó
            }
        }
        // a fennmaradó < 10km -t hozzáadjuk a lista pl. negyedik eleméhez
        uticelok.get(4).setKorrekcio(ELOIRTKM - getUticelListaOsszKm(uticelok));
    }
    
    public static void general2() {
        double atlagKm = ELOIRTKM / MUNKANAPOK;
        System.out.println("Átlag km: " + atlagKm);
        String tmp[] = randomTavolsagAdat(tavolsagok);
        
        while (Integer.parseInt(tmp[1]) < atlagKm) {
            while (getUticelListaVarosKmVane(uticelok, tmp)) {
                    tmp = randomTavolsagAdat(tavolsagok);
                }
            tmp = randomTavolsagAdat(tavolsagok);
        }
        
        for (String tmp1 : tmp) {
            System.out.println(tmp1);
        }
    }

    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        //tavolsagListaFeltolt("tavolsag.txt");
        test();
        general2();
        getUticelListaAdatok(uticelok);
    }

}
