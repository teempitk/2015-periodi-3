package Utils;

/**
 * ArrayUtils-luokkaassa on lopulta vain byte-taulukkojen quicksort.
 *
 * @author teemupitkanen1
 */
public class ArrayUtils {

    /**
     * BWT:ssa käytössä oleva Quicksort, jonka avulla saadaan muunnoksen
     * purkamisessa "matriisin ensimmäinen sarake" järjestämällä viimeinen.
     *
     * @param arr Järjestettävä taulukko.
     * @param left Järjestettävän osan vasen raja.
     * @param right Järjestettävän osan oikea raja.
     */
    public static void quickSort(byte[] arr, int left, int right) {
        if (left < right) {
            int pivot = partition(arr, left, right);
            quickSort(arr, left, pivot);
            quickSort(arr, pivot + 1, right);
        }
    }

    /**
     * Quicksortin tarvitsema ositusmetodi, joka siirtää pivotia pienemmät
     * alkiot sen vasemmalle puolelle ja suuremmat oikealle.
     *
     * @param arr Ositettava taulukko.
     * @param left Ositettavan osan vasen raja.
     * @param right Ositettavan osan oikea raja.
     * @return indeksi, johon jako-alkio sijoittui.
     */
    private static int partition(byte[] arr, int left, int right) {
        byte pivot = arr[left];
        int i = left - 1;
        int j = right + 1;
        while (i < j) {
            do {
                j--;
            } while (arr[j] > pivot);
            do {
                i++;
            } while (arr[i] < pivot);
            if (i < j) {
                byte tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        return j;
    }
}
