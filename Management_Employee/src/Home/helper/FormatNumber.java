
package Home.helper;

import java.text.DecimalFormat;

public class FormatNumber {
    
    static String DECIMAL_FORMAR;
    /**
     * Định dạng kiểu dữ liệu Double
     *
     * @param num là số được truyền vào
     * @param pattern là chuỗi định dạng cho kiểu double
     * @return chuỗi đã được định dạng
     *
     */
    public static String formatDouble(double num, String...pattern) {
        if (pattern.length > 0) {
            DECIMAL_FORMAR = pattern[0];
        } else {
            DECIMAL_FORMAR = "###,###.##";
        }
            
        DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_FORMAR);
        return decimalFormat.format(num);
    }
    
}
