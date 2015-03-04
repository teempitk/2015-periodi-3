package Tranforms;

import DataStructures.MyLinkedList;
import Utils.BitConversions;
import Utils.ArrayUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Burrows-Wheeler -muunnos ei ole varsinainen pakkausmenetelmä, vaan muunnos,
 * joka tehostaa muiden pakkausmenetelmien toimintaa. BWT:n ideasta saa hyvän
 * kuvan mm. Wikipedian artikkelista
 * http://en.wikipedia.org/wiki/Burrows–Wheeler_transform. Menetelmän ideana on
 * muuntaa dataa siten, että siinä esiintyisi alkuperäistä enemmän peräkkäisiä
 * saman merkin toistoja, kuitenkin niin, että muunnetun datan koko on (lähes)
 * sama kuin alkuperäisen, ja muunnos voidaan myös kumota. Muunnettuun dataan
 * täytyy tallentaa myös yksi pointteri, joka osoittaa muunnetusta tiedostosta
 * tavun, joka oli alkuperäisessä tiedostossa viimeisenä. Näin muunnetun
 * tiedoston koko = alkuperäisen tiedoston koko + 4 tavua.
 *
 * Muunnetussa tiedostossa esiintyvät tasan samat tavut kuin alkuperäisessä,
 * mutta eri järjestyksessä. Tästä syystä muunnoksesta ei ole etua pelkille
 * symbolikoodauksille (esim Huffman), vaan välissä täytyy tehdä jotain
 * muutakin. Tässä pakkausohjelmassa BWT:tä käyttää hyväkseen MTF-muunnos.
 *
 * Tämä toteutus on varsin optimoitu, eikä BWT:n ymmärtämistä helpottavaa
 * data.length x data.length -kokoista matriisia tosiasiassa luoda kokonaan.
 *
 * @author teemupitkanen1
 */
public class BurrowsWheeler {

    /**
     * Bytearray, johon syötetiedosto luetaan. Purkuvaiheessa datan ensimmäiset
     * neljä tavua ovat pointteri alkuperäisen datan viimeiseen tavuun, jotka
     * tulee muistaa poistaa.
     */
    private static byte[] data;
    /**
     * Sisältää viitteen datan jokaisen "sanan" alkuun. BWT:ssä ideana on
     * karkeasti muodostaa data.length x data.length -kokoinen matriisi.
     * Matriisin i:nnellä rivillä on kaikki datan tavut järjestyksessä data[i
     * ... data.length]+data[0 ... i-1]. Jokainen rivi on siis tässä yksi sana,
     * ja jokainen sana sisältää samat tavut. Tässä metodin toimintaa on
     * optimoitu siten, ettei matriisia oikeasti luoda, vaan kukin rivi
     * esitetään sanan alkua merkkaavana pointterina dataan. Muunnoksen alussa
     * lines-taulukko sisältää siis yksinkertaisesti luvut
     * 0,1,2,...,data.length. Muunnoksessa "matriisin" rivit järjestetään
     * aakkosjärjestykseen, jolloin lines-taulukossa on jokin permutaatio
     * alkuperäisistä luvuista.
     *
     * Varsinainen muunnettu data on "matriisin viimeinen sarake".
     */
    private static int[] lines;

    /**
     * BWT-muunnoksen tekevä kutsuttava metodi.
     *
     * @param inputFile Tiedosto, jossa olevalle datalle muunnos tehdään.
     * @param outputFile Tiedosto, johon muunnettu data kirjoitetaan.
     * @throws IOException
     */
    public static void transform(String inputFile, String outputFile) throws IOException {
        System.out.println("Burrows-Wheeler transform started.");
        long BWTStartTime = System.nanoTime();
        data = Files.readAllBytes(Paths.get(inputFile));
        byte[] transformedData = new byte[data.length];
        int lastByteInOriginalDataPointer = 0;
        lines = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            lines[i] = i;
        }
        lines = quickSortBWTLines(lines, 0);
        for (int i = 0; i < data.length; i++) {
            transformedData[i] = data[(lines[i] - 1 + data.length) % data.length];
            if (lines[i] == 0) {
                lastByteInOriginalDataPointer = i;
            }
        }
        FileOutputStream outStream = new FileOutputStream(new File(outputFile));
        outStream.write(BitConversions.intToByteArray(lastByteInOriginalDataPointer));
        outStream.write(transformedData);
        outStream.close();
        System.out.println("Burrows-Wheeler transform finished. Total time: " + (System.nanoTime() - BWTStartTime) * 1.0e-9 + " sec");
    }

    /**
     * String quicksort tähän tilanteeseen sovellettuna (O(nlogn) average time).
     * Metodi jakaa kaikki "sanat" indeksissä lcp (alussa 0) olevan tavun
     * perusteella suurempiin, pienempiin ja yhtäsuuriin. Tämän jälkeen metodi
     * kutsuu itseään rekursiivisesti näille kolmelle joukolle. Pienempien ja
     * suurempienn joukolle käytetään samaa lcp:tä, yhtäsuurille lcp+1.
     * Rekursion jälkeen osataulukot yhdistetään peräkkäin.
     *
     * @param strings Sanojen alkuindeksit datassa.
     * @param lcp "Longest common prefix", eli pisin yhteinen etuliite. Kertoo
     * kussakin rekursion tasossa, kuinka monta merkkiä vertailtavien sanojen
     * alussa ovat samoja (jotta vältytään turhilta vertailuilta).
     * @return Parametrina saadut sanojen indeksit sanojen aakkosjärjetystä
     * vastaavassa järjestyksessä.
     */
    public static int[] quickSortBWTLines(int[] strings, int lcp) {
        if (strings.length <= 1 || lcp == data.length) {
            return strings;
        }
        int pivot = strings[0];
        int b = 0;
        int s = 0;
        int e = 0;
        for (int i : strings) {
            if (data[(i + lcp) % data.length] > data[(pivot + lcp) % data.length]) {
                b++;
            } else if (data[(i + lcp) % data.length] < data[(pivot + lcp) % data.length]) {
                s++;
            } else {
                e++;
            }
        }
        int[] biggers = new int[b];
        int[] smallers = new int[s];
        int[] equals = new int[e];

        b = 0;
        s = 0;
        e = 0;
        for (int i : strings) {
            if (data[(i + lcp) % data.length] > data[(pivot + lcp) % data.length]) {
                biggers[b] = i;
                b++;
            } else if (data[(i + lcp) % data.length] < data[(pivot + lcp) % data.length]) {
                smallers[s] = i;
                s++;
            } else {
                equals[e] = i;
                e++;
            }
        }

        biggers = quickSortBWTLines(biggers, lcp);
        smallers = quickSortBWTLines(smallers, lcp);
        equals = quickSortBWTLines(equals, lcp + 1);
        return joinArrays(smallers, equals, biggers);
    }

    /**
     * Liittää taulukot peräkkäin muuttamatta sisäistä järjestystä.
     *
     * @param smallers ensimmäinen osa yhdistettyä taulukkoa.
     * @param equals Keskimmäinen osa yhdistettyä taulukkoa.
     * @param biggers Viimeinen osa yhdistettyä taulukkoa.
     * @return Taulukon osat yhdistettynä.
     */
    private static int[] joinArrays(int[] smallers, int[] equals, int[] biggers) {
        int[] strings = new int[smallers.length + equals.length + biggers.length];
        for (int i = 0; i < strings.length; i++) {
            if (i < smallers.length) {
                strings[i] = smallers[i];
            } else if (i < smallers.length + equals.length) {
                strings[i] = equals[i - smallers.length];
            } else {
                strings[i] = biggers[i - smallers.length - equals.length];
            }
        }
        return strings;
    }

    /**
     * Käänteisen muunnoksen tekevä kutsuttava metodi. Purkamisen alussa meillä
     * on käytössä BWT-matriisin viimeinen sarake. Tästä saadaan myös
     * ensimmäinen sarake järjestämällä viimeinen. Näistä kahdesta saamme taas
     * kaikki tavat, joilla kaksi tavua esiintyy peräkkäin: ensimmäisessä
     * sarakkeessa indeksissä i esiintyy viimeisessä sarakkeessa indeksissä i
     * olevaa tavua seuraava tavu. Näin saadaan kaikki peräkkäiset kahden tavun
     * osat alkuperäisestä tiedostosta. Näiden järjestäminen on helppoa kun
     * tehdään havainto: Kunkin tavun ensimmäinen esiintymä ensimmäisessä
     * sarakkeessa vastaa saman tavun ensimmäistä esiintymää viimeisessä
     * sarakkeessa, toinen esiintymä vastaa toista esiintymää jne...
     *
     *
     * @param inputFile Tiedosto, josta muunnettu data luetaan.
     * @param outputFile Tiedosto, johon purettu data kirjoitetaan.
     * @throws IOException
     */
    public static void inverseTransform(String inputFile, String outputFile) throws IOException {
        System.out.println("Burrows-Wheeler inverse transform started.");
        long inverseBWTStartTime = System.nanoTime();

        System.out.print("Phase 1/4. Generating first and last column ... ");
        long columnsStartTime = System.nanoTime();
        data = Files.readAllBytes(Paths.get(inputFile));
        int indexOfLastCharInOriginalData = readIndexOfLastFromTheBeginningOfData();
        byte[] sorted = Arrays.copyOf(data, data.length);
        ArrayUtils.quickSort(sorted, 0, sorted.length - 1);
        System.out.println(" "
                + "Finished. Time: " + String.format("%.3f", (System.nanoTime() - columnsStartTime) * 1.0e-9) + " sec");

        System.out.print("Phase 2/4. Indexing byte occurrences ... ");
        long indexingStartTime = System.nanoTime();
        MyLinkedList<Integer>[] arr = new MyLinkedList[256];
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            int index = b;
            if (index < 0) {
                index += 256;
            }
            MyLinkedList list = arr[index];
            if (list == null) {
                list = new MyLinkedList();
                arr[index] = list;
            }
            list.addLast(i);
        }
        System.out.println("\t Finished. Time: " + String.format("%.3f", (System.nanoTime() - indexingStartTime) * 1.0e-9) + " sec");

        System.out.print("Phase 3/4. Combining pairs of bytes ... ");
        long pairsStartTime = System.nanoTime();
        int[] followingLetters = new int[data.length];
        for (int i = 0; i < sorted.length; i++) {
            byte b = sorted[i];
            int index = b;
            if (index < 0) {
                index += 256;
            }
            MyLinkedList<Integer> list = arr[index];
            followingLetters[i] = list.getFirst();
            list.removeFirst();
        }
        System.out.println("\t Finished. Time: " + String.format("%.3f", (System.nanoTime() - pairsStartTime) * 1.0e-9) + " sec");

        System.out.print("Phase 4/4. Linking pairs to get complete data ...");
        long dataGenerationStartTime = System.nanoTime();
        byte[] originalData = new byte[data.length];
        int indexOfCurr = indexOfLastCharInOriginalData;
        for (int i = 0; i < data.length; i++) {
            originalData[(data.length - 1 + i) % data.length] = data[indexOfCurr];
            indexOfCurr = followingLetters[indexOfCurr];
        }

        FileOutputStream outStream = new FileOutputStream(new File(outputFile));
        outStream.write(originalData);
        outStream.close();
        System.out.println("Finished. Time: " + String.format("%.3f", (System.nanoTime() - dataGenerationStartTime) * 1.0e-9) + " sec");
        System.out.println("Burrows-Wheeler inverse transform finished. Total time: " + (System.nanoTime() - inverseBWTStartTime) * 1.0e-9 + " sec");
    }

    /**
     * Metodi lukee datan alusta neljä ensimmäistä tavua ja muuttaa ne int:iksi,
     * minkä jälkeen datan alusta poistetaan kyseiset tavut. Metodia käytetään
     * tässä lukemaan alkuperäisen muuntamattoman tavun sijainti Burrows-Wheeler
     * -muunnetussa datassa.
     *
     * @return Neljää ensimmäistä tavua vastaava kokonaisluku.
     */
    private static int readIndexOfLastFromTheBeginningOfData() {
        byte[] numberAsByteArray = {data[0], data[1], data[2], data[3]};
        int number = BitConversions.byteArrayToInt(numberAsByteArray);
        byte[] newData = new byte[data.length - 4];
        for (int i = 0; i < newData.length; i++) {
            newData[i] = data[i + 4];
        }
        data = newData;
        return number;
    }

}
