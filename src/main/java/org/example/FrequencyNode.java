package org.example;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class FrequencyNode {
    private int freq = 1;


    private FrequencyNode prev;
    private FrequencyNode next;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, DataNode> keyValueMap = new HashMap<>();

    public String getKey(String key) {
        return keyValueMap.get(key).getData();
    }

    public void addKey(String key, String value) {
        keyValueMap.put(key, new DataNode(key, value));
    }
    public String getAnyKey() {
        return keyValueMap.keySet().iterator().next();
    }
    public int getSize(){
        return keyValueMap.size();
    }
    public void removeKey(String key) {
        keyValueMap.remove(key);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("freq = ");
        sb.append(freq);
        sb.append(" data : ");
        for(String key : keyValueMap.keySet()) {
            sb.append(keyValueMap.get(key));
            sb.append(" ");
        }
        return  sb.toString();
    }

}
