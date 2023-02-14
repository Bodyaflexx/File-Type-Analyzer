package analyzer;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class RabinKarpSearch implements Strategy {

    @Override
    public void search(String fileName, List<Pattern> patterns) {
        byte[] allBytes;
        boolean isHas = false;
        try {
            allBytes = Files.readAllBytes(Paths.get(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String s = new String(allBytes);
        for (var pat : patterns) {
            if (method(s, pat.value())) {
                isHas = true;
                System.out.println(fileName.split("\\\\")[1] + ": " + pat.name());
            }
        }
        if (!isHas) {
            System.out.println(fileName.split("\\\\")[1] + ": Unknown file type");
        }
    }

    private boolean method(String fileContent, String pattern) {
        int a = 117;
        long m = 173_961_102_589_771L;

        if (pattern.length() > fileContent.length()) {
            return false;
        }

        long patternHash = 0;
        long currSubStrHash = 0;
        long pow = 1;

        for (int i = 0; i < pattern.length(); i++) {
            patternHash += (long) pattern.charAt(i) * pow;
            patternHash %= m;

            currSubStrHash += (long) fileContent.charAt(fileContent.length() - pattern.length() + i) * pow;
            currSubStrHash %= m;

            if (i != pattern.length() - 1) {
                pow = pow * a % m;
            }
        }

        for (int i = fileContent.length(); i >= pattern.length(); i--) {
            if (patternHash == currSubStrHash) {
                for (int j = 0; j < pattern.length(); j++) {
                    if (fileContent.charAt(i - pattern.length() + j) != pattern.charAt(j)) {
                        break;
                    }
                }

                return true;
            }

            if (i > pattern.length()) {
                currSubStrHash = (currSubStrHash - fileContent.charAt(i - 1) * pow % m + m) * a % m;
                currSubStrHash = (currSubStrHash + fileContent.charAt(i - pattern.length() - 1)) % m;
            }
        }

        return false;
    }
}