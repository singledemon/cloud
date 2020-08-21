package cn.com.oumeng.common.core.util;

import java.io.InputStream;
import java.util.Properties;


public class ReadPropertiesUtils {

    public static String getResourceValue(String key) {
        try {
            InputStream input = ReadPropertiesUtils.class.getResourceAsStream("/application.properties");
            Properties p = new Properties();
            p.load(input);
            input.close();
            if (!p.containsKey(key)) {
                return null;
            }
            return p.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    //读取国际化
    public static String getI18n(String key) {
        try {
            InputStream input = ReadPropertiesUtils.class
                    .getResourceAsStream("/i18n_zh_CN.properties");
            Properties p = new Properties();
            p.load(input);
            input.close();
            if (!p.containsKey(key)) {
                return null;
            }
            return p.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
