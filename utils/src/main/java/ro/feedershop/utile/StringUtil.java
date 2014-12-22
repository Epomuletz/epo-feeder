package ro.feedershop.utile;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by nevastuica on 12/22/2014.
 */
public class StringUtil {


    public static List<String> getListFromStringTokenizer(StringTokenizer st) {
        List<String> produse = new ArrayList<String>();
        int i = 0;
        while (st.hasMoreElements()) {
            produse.add(i++, st.nextToken());
        }
        return produse;
    }


    public static int nrOccurences(String str, String toFind) {

        int lastIndex = 0;
        int count = 0;

        do {
            lastIndex = str.indexOf(toFind, lastIndex);
            if (lastIndex != -1) {
                str = str.substring(lastIndex + 1);
                count++;
            }
        } while (lastIndex != -1);
        return count;
    }

    /**
     * If we have a string like 1.332.79 it should transform it to 1332.79
     * @param str
     * @return
     */
    public static String removeExtraPoint(String str){

        String temp = str.substring(0, str.lastIndexOf("."));
        temp = temp.replace(".", "");
        str = temp + str.substring(str.lastIndexOf("."));

        return str;
    }
}
