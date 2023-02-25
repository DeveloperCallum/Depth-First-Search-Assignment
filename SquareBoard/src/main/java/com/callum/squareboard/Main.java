package com.callum.squareboard;

public class Main {
    public static void main(String[] args) {
        Dictionary hashTable = new Dictionary();
        hashTable.put(new Record("Pair", 0, 0));
        hashTable.put(new Record("lckmqpyf", 0, 0));
        hashTable.put(new Record("jinqcroudllqguligujitdlbcmcuoihopaxcgmej", 0, 0));
        hashTable.put(new Record("okjwjeshpaublcpxppkhaerrjgyhyrhkcqknavawadbroeicitjajserdyej", 0, 0));

//        while (true) {
//            LinkedList<HashData> hashSet = new LinkedList<>();
//            Random random = new Random();
//            int generated = 0;
//            int duplicates = 0;
//
//            for (int i = 0; i < 10000; i++) {
//                int size = random.nextInt(100 - 4) + 4;
//
//                StringBuilder str = new StringBuilder(size);
//
//                for (int x = 0; x < size; x++) {
//                    char c = (char) (random.nextInt(25) + 97);
//                    str.append(c);
//                }
//
//                String currentString = str.toString();
//
//                long code = Hash.hashCode(currentString);
//                generated++;
//
//                List<HashData> hashdata = hashSet.stream().filter(hashData -> hashData.hashcode == code).collect(Collectors.toList());
//
//                hashTable.put(currentString, String.valueOf(code));
//
//                if (!hashdata.isEmpty()) {
//                    duplicates++;
//
//                    hashdata.forEach(hashData -> {
//                        System.out.println("Key: " + currentString + " HashCode: " + code);
//                        System.out.println("Key: " + hashData.key + " HashCode: " + hashData.hashcode);
//                    });
//                } else {
//                    hashSet.add(new HashData(code, currentString));
//                }
//            }
//
//            System.out.println("Generated: " + generated + " Duplicates: " + duplicates);
//        }

        System.out.println(hashTable.get("Pair").getKey());
        hashTable.remove("Pair");
        System.out.println(hashTable.get("Pair"));
        System.out.println(hashTable.get("lckmqpyf").getKey());
        System.out.println(hashTable.get("okjwjeshpaublcpxppkhaerrjgyhyrhkcqknavawadbroeicitjajserdyej").getKey());
    }
}
