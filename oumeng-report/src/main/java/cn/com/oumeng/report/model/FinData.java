package cn.com.oumeng.report.model;

import java.util.*;

public class FinData {

    public List<Map<String, Object>> loadData (String dsName,String datasetName,Map<String,Object> parameters) {
        List<Map<String, Object>> list = new ArrayList<> ();
       String salary = (String) parameters.get("salary");
       String salary1 = (String) parameters.get("salary1");
       String salary2 = (String) parameters.get("salary2");
       String salary3 = (String) parameters.get("salary3");
        for (int i = 0; i < 100; i++) {
            Map<String, Object> map = new HashMap<> ();
            map.put ("aa", "aa" + i + "=" + salary);
            map.put ("bb", "bb" + i + "=" + salary1);
            map.put ("cc", "cc" + i + "=" + salary2);
            map.put ("dd", "dd" + i + "=" + salary3);
            map.put ("ee", "ee" + i + "=" + salary);
            map.put ("ff", "ff" + i + "=" + salary);
            map.put ("gg", "gg" + i + "=" + salary);
            map.put ("hh", "hh" + i + "=" + salary);
            list.add (map);
        }
        return list;
    }
}
