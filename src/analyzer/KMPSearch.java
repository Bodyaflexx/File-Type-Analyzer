package analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class KMPSearch implements Strategy {
    @Override
    public void search(String fileName, List<Pattern> patterns) {
        byte[] allBytes;
        try {
            allBytes = Files.readAllBytes(Paths.get(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String text = new String(allBytes);
        for (var pattern : patterns) {
        String pat = pattern.value();
            boolean isHas = false;
            int M = pat.length();
            int N = text.length();

            int[] lps = new int[M];
            int j = 0;

            computeLPSArray(pat, M, lps);

            int i = 0;
            while ((N - i) >= (M - j)) {
                if (pat.charAt(j) == text.charAt(i)) {
                    j++;
                    i++;
                }
                if (j == M) {
                    System.out.println(fileName.split("\\\\")[1] + ": " + pattern.name());
                    isHas = true;
                    j = lps[j - 1];
                    break;
                } else if (i < N
                        && pat.charAt(j) != text.charAt(i)) {
                    if (j != 0)
                        j = lps[j - 1];
                    else
                        i = i + 1;
                }
            }
            if (!isHas) {
                System.out.println(fileName.split("\\\\")[1] + ": Unknown file type");
            }
        }
    }

    private void computeLPSArray(String pat, int M, int[] lps) {
        int len = 0;
        int i = 1;
        lps[0] = 0;

        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];

                } else {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }
}