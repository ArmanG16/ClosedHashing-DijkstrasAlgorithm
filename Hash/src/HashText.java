import java.io.*;
import java.nio.file.*;
import java.util.*;

public class HashText {
    static final int SIZE = 997;
    static final int MULTIPLIER = 123;
    static final int MOD = 997;

    public static void main(String[] args) {
        int[] hashTbl = new int[SIZE];
        Arrays.fill(hashTbl, -1);

        String fileLoc = "C:\\Users\\Owner\\OneDrive\\Desktop\\HW_5 Hash\\TwelfthNightFULL.txt";

        try {
            List<String> allLines = Files.readAllLines(Paths.get(fileLoc));
            Set<Integer> uniqueHashes = new HashSet<>();
            HashMap<Integer, String> hashWords = new HashMap<>();

            for (String singleLine : allLines) {
                String[] words = singleLine.toLowerCase().replaceAll("[^a-z ]", "").split("\\s+");

                for (String word : words) {
                    if (!word.trim().isEmpty()) {
                        int hashVal = hashWord(word);

                        if (!uniqueHashes.contains(hashVal)) {
                            uniqueHashes.add(hashVal);
                            int pos = hashVal % SIZE;

                            while (hashTbl[pos] != -1) {
                                pos = (pos + 1) % SIZE;
                            }

                            hashTbl[pos] = hashVal;
                            hashWords.put(hashVal, word);
                            System.out.println("Hash Pos: " + pos + ", Word: " + word + ", Hash: " + hashVal);
                        }
                    }
                }
            }

            System.out.println("Filled slots: " + countNonEmptyAddresses(hashTbl));
            System.out.println("Longest empty space: " + findLongestEmptyArea(hashTbl));
            System.out.println("Biggest cluster: " + findLongestCluster(hashTbl));
            System.out.println("Max words in a hash: " + findMostDistinctWords(hashWords));
            System.out.println("Word farthest from hash: " + findFarthestWord(hashTbl, hashWords));

        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    static int hashWord(String word) {
        int hash = 0;
        for (int i = 0; i < word.length(); i++) {
            hash = (hash * MULTIPLIER + word.charAt(i)) % MOD;
        }
        return hash;
    }

    static int countNonEmptyAddresses(int[] table) {
        int count = 0;
        for (int i = 0; i < table.length; i++) {
            if (table[i] != -1) {
                count++;
            }
        }
        return count;
    }

    static int findLongestEmptyArea(int[] tbl) {
        int longest = 0;
        int current = 0;

        for (int i = 0; i < tbl.length; i++) {
            if (tbl[i] == -1) {
                current++;
            } else {
                if (current > longest) {
                    longest = current;
                }
                current = 0;
            }
        }

        return longest;
    }

    static int findLongestCluster(int[] tbl) {
        int longest = 0;
        int currLength = 0;

        for (int i = 0; i < tbl.length; i++) {
            if (tbl[i] != -1) {
                currLength++;
            } else {
                if (currLength > longest) {
                    longest = currLength;
                }
                currLength = 0;
            }
        }

        return longest;
    }

    static int findMostDistinctWords(HashMap<Integer, String> hashMap) {
        HashMap<Integer, Integer> freqMap = new HashMap<>();

        for (int key : hashMap.keySet()) {
            int count = freqMap.getOrDefault(key, 0) + 1;
            freqMap.put(key, count);
        }

        int max = 0;
        for (int count : freqMap.values()) {
            if (count > max) {
                max = count;
            }
        }

        return max;
    }

    static String findFarthestWord(int[] tbl, HashMap<Integer, String> wordMap) {
        int maxDist = 0;
        String farthest = "";

        for (int i = 0; i < tbl.length; i++) {
            if (tbl[i] != -1) {
                int origHash = tbl[i];
                int origIdx = origHash % SIZE;
                int dist = (i >= origIdx) ? (i - origIdx) : (SIZE - origIdx + i);

                if (dist > maxDist) {
                    maxDist = dist;
                    farthest = wordMap.get(origHash);
                }
            }
        }

        return farthest + " (distance from actual hash: " + maxDist + ")";
    }
}
