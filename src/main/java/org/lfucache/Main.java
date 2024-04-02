package org.lfucache;

public class Main {
    public static void main(String[] args) {
        LFUCache cache = new LFUCache(3);
        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        System.out.println("getFun = " +  cache.getKey("1"));
        System.out.println("getFun = " +  cache.getKey("2"));
        cache.put("4", "4");
        System.out.println(cache.getCacheStatus());
    }
}