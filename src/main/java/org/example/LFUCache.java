package org.example;

import java.util.HashMap;
import java.util.Map;

public class LFUCache {
    private FrequencyNode lowestFrequency;
    private final Map<String, FrequencyNode> keyMap = new HashMap<>();
    private final int cacheSize;
    private int currSize = 0;

    LFUCache(int size) {
        this.cacheSize = size;
    }

    public String getKey(String key) {
        if(!keyMap.containsKey(key)) {
            return null;
        }
        increaseFrequency(key);
        return keyMap.get(key).getKey(key);
    }

    public void put(String key, String value) {
        if(keyMap.containsKey(key)) {
            keyMap.get(key).addKey(key, value);
            return;
        }
        if(currSize == cacheSize) {
            invalidateCache();
            currSize--;
        }

        if(lowestFrequency == null) {
            lowestFrequency = new FrequencyNode();
        }

        if(lowestFrequency.getFreq() != 1) {
            lowestFrequency = addNodeAhead(lowestFrequency);
        }

        lowestFrequency.addKey(key, value);
        keyMap.put(key, lowestFrequency);
        currSize++;
    }

    private FrequencyNode addNodeAhead(FrequencyNode frequencyNode) {
        FrequencyNode newNode = new FrequencyNode();
        newNode.setNext(frequencyNode.getNext());
        if(frequencyNode.getNext() != null) {
            newNode.getNext().setPrev(newNode);
        }
        frequencyNode.setNext(newNode);
        newNode.setPrev(frequencyNode);
        return newNode;
    }


    private void invalidateCache() {
        String key = lowestFrequency.getAnyKey();
        removeKey(key, lowestFrequency);
    }

    private void removeKey(String key, FrequencyNode currFrequencyNode) {
        currFrequencyNode.removeKey(key);
        FrequencyNode prevNode = currFrequencyNode.getPrev();
        keyMap.remove(key);

        if(currFrequencyNode.getSize() == 0) {
            prevNode.setNext(currFrequencyNode.getNext());
            if(currFrequencyNode.getNext() != null) {
                currFrequencyNode.getNext().setPrev(prevNode);
            } else {
                currFrequencyNode.setPrev(null);
                lowestFrequency = prevNode;
            }
        }
    }


    private void increaseFrequency(String key) {
        FrequencyNode currFrequencyNode = keyMap.get(key);
        int freq = currFrequencyNode.getFreq();
        FrequencyNode prevNode = currFrequencyNode.getPrev();


        if(prevNode == null) {
            prevNode = new FrequencyNode();
            currFrequencyNode.setPrev(prevNode);
            prevNode.setNext(currFrequencyNode);
        } else {
            if(prevNode.getFreq() != freq + 1) {
                prevNode = addNodeAhead(prevNode);
            }
        }
        prevNode.setFreq(freq + 1);
        prevNode.addKey(key, currFrequencyNode.getKey(key));
        removeKey(key, currFrequencyNode);
        keyMap.put(key, prevNode);
    }

    public String getCacheStatus() {
        StringBuilder sb = new StringBuilder();

        FrequencyNode curr = lowestFrequency;
        while (curr != null) {
            sb.append(curr);
            sb.append(System.getProperty("line.separator")); // or "\n"
            curr = curr.getPrev();
        }
        return sb.toString();
    }
}
