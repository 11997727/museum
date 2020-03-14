package common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teacher ZHANG on 2020/2/26
 */
public class TextUtils {
    public static List<Integer> textToList(String text) {
        List<Integer> list = null;

        if (text != null && !text.equals("")) {
            list = new ArrayList<>();

            String [] strs = text.split(",");
            for (String str : strs) {
                list.add(Integer.parseInt(str));
            }
        }

        return list;
    }
}
