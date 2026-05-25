import org.cef.OS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {

    private class Orszag {
        public String orszag;
        public String jel;
        public int oLakos;
        public String fovaros;
        public int fLakos;

        public Orszag(String sor) {
            String[] s = sor.split(";");
            orszag = s[0];
            jel = s[1];
            oLakos = Integer.parseInt(s[2]);
            fovaros = s[3];
            fLakos = Integer.parseInt(s[4]);
        }
    }

    private ArrayList<Orszag> orszagok = new ArrayList<>();

    public Main() {

        // 0. feladat
        betolt("fovaros.csv");
        System.out.printf("0) Összesen %d ország adata lett beolvasva.\n", orszagok.size());

        // 1. feladat
        Orszag legnagyobb = orszagok.get(0);
        for (Orszag orszag : orszagok) if (orszag.oLakos > legnagyobb.oLakos) legnagyobb = orszag;
        System.out.printf("1) Az ország, ahol a legtöbben élnek: %s, %,d fő\n", legnagyobb.orszag, legnagyobb.oLakos);
        Orszag masodik = null; int i = 0; do { masodik = orszagok.get(i++); } while (masodik.oLakos == legnagyobb.oLakos);
        for (Orszag orszag : orszagok) if (orszag.oLakos != legnagyobb.oLakos && orszag.oLakos > masodik.oLakos) masodik = orszag;
        System.out.printf("   A második legnagyobb népesség: %s, %,d fő\n", masodik.orszag, masodik.oLakos);

        // 2. feladat
        int bp = 0; while (bp < orszagok.size() && !orszagok.get(bp).fovaros.equals("Budapest")) bp++;
        int db = 0; for (Orszag orszag : orszagok) if (orszag.fLakos < orszagok.get(bp).fLakos) db++;
        System.out.printf("2) Összesen %d fővárosban élnek kevesebben, mint Budapesten!\n", db);

        // 3. feladat
        TreeSet<String> cJelek = new TreeSet<>();
        for (Orszag orszag : orszagok) if (orszag.jel.contains("C")) cJelek.add(orszag.jel);
        System.out.printf("3) Országjel, amiben szerepel 'C' betű: %s\n", String.join(", ", cJelek));

        // 4. feladat
        int fo = 0; for (Orszag orszag : orszagok) if (orszag.oLakos < 20_000_000) fo += orszag.fLakos;
        System.out.printf("4) A 20 millió főnél kisebb országok fővárosainak össznépessége: %,d fő\n", fo);

        // 5. feladat
        TreeMap<Integer, Integer> stat = new TreeMap<>();
        for (Orszag orszag : orszagok) {
            int kat = orszag.fLakos / 5000000;
            if (!stat.containsKey(kat)) stat.put(kat, 1);
            else stat.put(kat, stat.get(kat)+1);
        }
        System.out.printf("5) Fővárosok népesség szerint csoportosítva (5 millió fő):\n");
        for (Integer kat : stat.keySet()) {
            System.out.printf("   %,10d - %,10d: %d\n", kat*5000000, (kat+1)*5000000-1, stat.get(kat));
        }

        // 6. feladat
        PrintWriter ki = null;
        try {
            ki = new PrintWriter(new File("nagyok.txt"), "utf-8");
            for (Orszag orszag : orszagok) if (orszag.oLakos > 200_000_000) ki.printf("%s (%d millió)\r\n", orszag.orszag, orszag.oLakos/1000000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ki != null) ki.close();
        }
        System.out.printf("6) Nagy népességű országok a nagyok.txt fájlban!\n");
    }

    private void betolt(String fajlnev) {
        Scanner be = null;
        try {
            be = new Scanner(new File(fajlnev), "utf-8");
            be.nextLine();
            while (be.hasNextLine()) orszagok.add(new Orszag(be.nextLine()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (be != null) be.close();
        }
    }

    public static void main(String[] args) {
	    new Main();
    }
}
