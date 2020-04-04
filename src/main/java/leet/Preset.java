package leet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Preset {
    public static final String SEP = ",";
    public String name;
    public Map<Alphabet, Integer> indexMap = new HashMap<Alphabet, Integer>();
    
    public Preset(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public Map<Alphabet, Integer> getIndexMap() {
        return indexMap;
    }
    
    public Preset addMapping(Alphabet a, Integer i) {
        indexMap.put(a, i);
        return this;
    }
    
    public Preset mapFromString(String in) {
        String[] split = in.split("\\s*[,;]\\s*");
        for(int i=0; i<split.length; i+=2) {
            Alphabet a = Alphabet.valueOf(split[i].toUpperCase());
            Integer idx = Integer.parseInt(split[i+1]);
            addMapping(a, idx);
        }
        return this;
    }
    
    public String mapToString(Preset p) {
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<Alphabet, Integer>> i = p.indexMap.entrySet().iterator();
        while(i.hasNext()) {
            Entry<Alphabet, Integer> next = i.next();
            sb.append(next.getKey().name()).append(SEP).append(next.getValue());
            if(i.hasNext()) sb.append(SEP);
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return name;
    }
}
