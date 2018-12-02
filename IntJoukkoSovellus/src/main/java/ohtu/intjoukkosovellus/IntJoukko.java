
package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int OLETUSKAPASITEETTI = 5, // aloitustalukon koko
                            OLETUSKASVATUSKOKO = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        alustaJono(OLETUSKAPASITEETTI, OLETUSKASVATUSKOKO);
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti < 0) {
            return;
        }
        alustaJono(kapasiteetti, OLETUSKASVATUSKOKO);
    }
    
    
    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasiteetin täytyy olla positiivinen");
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("Kasvatuskoon täytyy olla positiivinen");
        }
        
        alustaJono(kapasiteetti, kasvatuskoko);
    }

    public boolean lisaa(int luku) {
        if (onkoJoukossaJoLuku(luku)) {
            return false;
        }
        lisaaLukuKohtaan(luku, alkioidenLkm);
        suurennaTaulukkoaJosLiianPieni();
        return true;
    }

    public boolean onkoJoukossaJoLuku(int luku) {
        return etsiJoukostaLuku(luku) != null;
    }

    public boolean poista(int luku) {
        int kohta = etsiKohtaJostaLoytyy(luku);
        
        if (etsiKohtaJostaLoytyy(luku) == null) {
            return false;
        }
        
        siirraAlkioitaPoistetunTilalle(kohta);
        alkioidenLkm--;
        return true;
    }
        
    private void suurennaTaulukkoaJosLiianPieni() {
        if (alkioidenLkm % ljono.length == 0) {
            int[] taulukkoOld = ljono;
            kopioiTaulukko(ljono, taulukkoOld);
            ljono = new int[alkioidenLkm + kasvatuskoko];
            kopioiTaulukko(taulukkoOld, ljono);
        }
    }
    
    private void lisaaLukuKohtaan(int luku, int kohta) {
        ljono[kohta] = luku;
        alkioidenLkm++;
    }
    
    private void siirraAlkioitaPoistetunTilalle(int kohta) {
        for (int j = kohta; j < alkioidenLkm - 1; j++) {
            int apu = ljono[j];
            ljono[j] = ljono[j + 1];
            ljono[j + 1] = apu;
        }
    }
    
    private Integer etsiKohtaJostaLoytyy(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                return i;
            }
        }
        return null;
    }
    
    private Integer etsiJoukostaLuku(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                return luku;
            }
        }
        return null;
    }    

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        System.arraycopy(vanha, 0, uusi, 0, vanha.length);
    }
    
    private void alustaJono(int kapasiteetti, int kasvatuskoko) {
        ljono = new int[kapasiteetti];
        for (int i = 0; i < ljono.length; i++) {
            ljono[i] = 0;
        }
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (alkioidenLkm > 0) {
            for (int i = 0; i < alkioidenLkm - 1; i++) {
                sb.append(ljono[i]);
                sb.append(", ");
            }
            sb.append(ljono[alkioidenLkm - 1]);
        }
        sb.append("}");
        return sb.toString();
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = ljono[i];
        }
        return taulu;
    }
   

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko joukko = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            joukko.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            joukko.lisaa(bTaulu[i]);
        }
        return joukko;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko joukko = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            for (int j = 0; j < bTaulu.length; j++) {
                if (aTaulu[i] == bTaulu[j]) {
                    joukko.lisaa(bTaulu[j]);
                }
            }
        }
        return joukko;
    }
    
    public static IntJoukko erotus ( IntJoukko a, IntJoukko b) {
        IntJoukko joukko = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            joukko.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            joukko.poista(i);
        }
        return joukko;
    }   
}
