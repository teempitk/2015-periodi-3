
package Utils;

/**
 * Luokka muutoksiin bittijonojen ja niiden merkkijonoesitysten välillä
 * @author teemupitkanen1
 */
public class StringBitConversions {
    /**
     * Kukin taulukon alkio vastaa ykkösbittiä tavun vastaavassa indeksissä
     */
    private static final int[] oneBitsInByte = {1,2,4,8,16,32,64,-128};
    /**
     * Kahden potenssit positiveIntegerAsOneByteBitstrin-metodin käyttöön
     */
    private static final int[] powersOfTwo = {1,2,4,8,16,32,64,128};
    /**
     * Muuntaa parametrina annetun merkkijonoesityksen tavusta tavuksi
     * @param bits merkkijonoesitys tavusta
     * @return vastaava tavu
     */
    public static byte asByte(String bits) {
        byte B = 0;
        for (int i = 0; i < 8; i++) {
            if (bits.charAt(i) == '1') {
                B = (byte) (B | oneBitsInByte[7-i]);
            }
        }
        return B;
    }
    /**
     * EI TOIMI VIELÄ! Muuntaa parametrina annetun tavun merkkijonoesityksekseen, esim "01010101"
     * @param B muunnettava tavu
     * @return merkkijonoesitys tavusta
     */
    public static String asString(byte B){
        String bitstring = "";
        if (B<0){
            bitstring += "1";
        }else bitstring+="0";
        return bitstring;
    }
    /**
     * Metodin avulla voidaan koodata positiivisia lukuja (<256) bittiesityksikseen.
     * @param number Koodattava luku
     * @return luvun bittiesitys (8bit) merkkijonona
     */
    public static String positiveIntegerAsOneByteBitstring(int number) {
        String bitstring = "";
        for (int i = 7; i>=0; i--){
            if (number >= powersOfTwo[i]){
                number -= powersOfTwo[i];
                bitstring += "1";
            }else{
                bitstring += "0";
            }
        }
        return bitstring;
    }
}
