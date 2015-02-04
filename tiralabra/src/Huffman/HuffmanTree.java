package Huffman;

import DataStructures.OrderedLinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * HuffmanTree-luokan tarkoitus on tarjota merkkikohtaiset koodisanat luova
 * huffmanCodewords -metodi. Koodisanojen luonti tapahtuu luomalla yksityisissä
 * metodeissa Huffman-puu tietorakenne.
 *
 * @author teemupitkanen1
 */
public class HuffmanTree {

    /**
     * root-muuttujaa käytetään nimensä mukaisesti Huffman-puun juuren
     * muistamiseen.
     */
    private static HuffmanNode root;
    /**
     * codewords-taulukkoon luodaan varsinaiset Huffman-koodisanat.
     */
    private static String[] codewords;

    /**
     * Luokkametodi huffmanCodewords luo symbolikohtaiset koodisanat parametrina
     * annetujen merkkien esiintymismäärien mukaisesti - mitä yleisempi merkki,
     * sitä lyhyempi koodisana. Huffman-koodaus on optimaalinen symbolikoodaus,
     * toisin sanoen lyhin mahdollinen tapa koodata teksti siten, että kukin
     * merkki korvataan aina tietyllä bittijonolla.
     *
     * @param frequencyTable Kunkin merkin esiintymiskerrat pakattavassa
     * tekstissä. Taulukko on annettava seuraavassa muodossa. Taulukon
     * indeksointi vastaa merkkejä 8-bit ASCII -numeroinnin mukaisesti.
     * Indeksissä oleva lukuarvo kertoo merkin esiintymiskertojen määrän
     * lähdetekstissä.
     * @return Metodi palauttaa koodisanat taulukkona '0' ja '1' -merkkejä
     * sisältäviä merkkijonoja. Koodisanataulukon indeksointi vastaa jälleen
     * 8-bit ASCII-numerointia.
     */
    public static String[] huffmanCodewords(int[] frequencyTable) {
        root = null;
        codewords = new String[frequencyTable.length];
        generateTree(frequencyTable);
        findCodewords(root, "");
        return codewords;
    }

    /**
     * Metodi luo koodisanojen muodostamiseen tarvittavan Huffman-puu
     * -tietorakenteen.
     *
     * @param frequencyTable Sisältää merkkien esiintymiskerrat alkuperäisessä
     * tekstissä.
     */
    private static void generateTree(int[] frequencyTable) {
        OrderedLinkedList list = new OrderedLinkedList();
        for (int i = 0; i < frequencyTable.length; i++) {
            if (frequencyTable[i] > 0) {
                HuffmanNode newnode = new HuffmanNode((char) i, frequencyTable[i]);
                list.insert(newnode);
            } else {
                codewords[frequencyTable[i]] = null;
            }
        }
        while (list.size() >= 2) {
            root = new HuffmanNode(list.removeSmallest(), list.removeSmallest());
            list.insert(root);
        }
        root = list.removeSmallest();
    }

    /**
     * findCodewords-metodi luo varsinaiset koodisanat. Se kulkee juuresta
     * alkaen rekursiivisesti koko puun läpi aina jokaiseen lehteen asti. Aina,
     * kun solmusta siirrytään vasempaan lapseen, rekursiossa parametrina
     * etenevään koodisanaan lisätään '0'. Vastaavasti oikeaan lapseen
     * siirrytäessä lisätään '1'. Kun saavutetaan lehti, lehteä vastaavan
     * symbolin koodisanaksi asetetaan rekursiossa 'kertynyt' koodisana.
     *
     * @param node Solmu, jonka kohdalla rekursiossa ollaan. Yleensä
     * kutsuttaessa root.
     * @param prefix Rekursiossa kertynyt koodisana. Alussa "" (tyhjä).
     */
    private static void findCodewords(HuffmanNode node, String prefix) {
        if (node.getLeft() != null) {
            findCodewords(node.getLeft(), prefix + "0");
            findCodewords(node.getRight(), prefix + "1");
        } else {
            if (prefix.equals("")) {
                codewords[node.getSymbol()] = "0"; // Jos tekstissä vain yhtä samaa tavua, root on lehti
            } else {
                codewords[node.getSymbol()] = prefix;
            }
        }
    }

}
