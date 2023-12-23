package post;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The language tool including English and Chinese.
 */
public class LanguageTool {
    /**
     * English to Chinese.
     */
    public static Map<String, String> englishToChinese = new HashMap<>();
    /**
     * Chinese to English.
     */
    public static Map<String, String> chineseToEnglish = new HashMap<>();
    /**
     * provinces in Chinese.
     */
    public static Set<String> provinceSetChn = new HashSet<>();
    /**
     * provinces in English.
     */
    public static Set<String> provinceSetEng = new HashSet<>();

    static {
        englishToChinese.put("anhui", "安徽");
        englishToChinese.put("beijing", "北京");
        englishToChinese.put("tianjin", "天津");
        englishToChinese.put("aomen", "澳门");
        englishToChinese.put("chongqing", "重庆");
        englishToChinese.put("fujian", "福建");
        englishToChinese.put("gansu", "甘肃");
        englishToChinese.put("guangdong", "广东");
        englishToChinese.put("guangxi", "广西");
        englishToChinese.put("guizhou", "贵州");
        englishToChinese.put("hainan", "海南");
        englishToChinese.put("hebei", "河北");
        englishToChinese.put("heilongjiang", "黑龙江");
        englishToChinese.put("henan", "河南");
        englishToChinese.put("hubei", "湖北");
        englishToChinese.put("hunan", "湖南");
        englishToChinese.put("jiangsu", "江苏");
        englishToChinese.put("jiangxi", "江西");
        englishToChinese.put("jilin", "吉林");
        englishToChinese.put("liaoning", "辽宁");
        englishToChinese.put("neimenggu", "内蒙古");
        englishToChinese.put("ningxia", "宁夏");
        englishToChinese.put("qinghai", "青海");
        englishToChinese.put("shaanxi", "陕西");
        englishToChinese.put("shandong", "山东");
        englishToChinese.put("shanghai", "上海");
        englishToChinese.put("shanxi", "山西");
        englishToChinese.put("sichuan", "四川");
        englishToChinese.put("taiwan", "台湾");
        englishToChinese.put("xianggang", "香港");
        englishToChinese.put("xinjiang", "新疆");
        englishToChinese.put("xizang", "西藏");
        englishToChinese.put("yunnan", "云南");
        englishToChinese.put("zhejiang", "浙江");

        for (String key : englishToChinese.keySet()){
            chineseToEnglish.put(englishToChinese.get(key), key);
            provinceSetChn.add(englishToChinese.get(key));
            provinceSetEng.add(key);
        }
    }
}
