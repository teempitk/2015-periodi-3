package Utils;

/**
 * Luokka muutoksiin bittijonojen ja niiden merkkijonoesitysten välillä
 *
 * @author teemupitkanen1
 */
public class BitConversions {

    /**
     * Kukin taulukon alkio vastaa ykkösbittiä tavun vastaavassa indeksissä
     */
    private static final int[] oneBitsInByte = {1, 2, 4, 8, 16, 32, 64, -128};
    /**
     * Kahden potenssit positiveIntegerAsOneByteBitstrin-metodin käyttöön
     */
    private static final int[] powersOfTwo = {1, 2, 4, 8, 16, 32, 64, 128};

    /**
     * Muuntaa parametrina annetun merkkijonoesityksen tavusta tavuksi
     *
     * @param bits merkkijonoesitys tavusta
     * @return vastaava tavu
     */
    public static byte asByte(String bits) {
        byte B = 0;
        for (int i = 0; i < 8; i++) {
            if (bits.charAt(i) == '1') {
                B = (byte) (B | oneBitsInByte[7 - i]);
            }
        }
        return B;
    }

    /**
     * Metodin avulla voidaan koodata positiivisia lukuja (<256)
     * bittiesityksikseen. @param number Koo
     *
     * dattava luku @return luvun bittiesitys (8bit) merkkijonona
     */
    public static String integerAsByteString(int number) {
        String bitstring = "";
        for (int i = 7; i >= 0; i--) {
            if (number >= powersOfTwo[i]) {
                number -= powersOfTwo[i];
                bitstring += "1";
            } else {
                bitstring += "0";
            }
        }
        return bitstring;
    }

    /**
     * Metodi muuntaa parametrina annetun kokonaisluvun bytearrayksi (käytetään
     * bwt:ssä pointterin tallentamiseen).
     *
     * @param number muunnettava luku
     * @return Luvun tavuesitys
     */
    public static byte[] intToByteArray(int number) {
        byte[] returnArray = new byte[4];
        returnArray[3] = (byte) (number & 0xFF);
        returnArray[2] = (byte) ((number >> 8) & 0xFF);
        returnArray[1] = (byte) ((number >> 16) & 0xFF);
        returnArray[0] = (byte) ((number >> 24) & 0xFF);
        return returnArray;
    }
    /**
     * Muuntaa parametrina annetun neljän tavun bytearrayn intiksi. Käytetään bwt:ssä.
     * @param b Muunnetava bytearray.
     * @return Arrayta vastaava int.
     */
    public static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF
                | (b[2] & 0xFF) << 8
                | (b[1] & 0xFF) << 16
                | (b[0] & 0xFF) << 24;
    }
}
