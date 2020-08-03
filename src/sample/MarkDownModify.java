package sample;

import java.io.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkDownModify {
    private final static Pattern PATTERN = Pattern.compile("(.*?)(<img.+>)(.*?)");

    public static void addReferrerPolicy(File directory) throws IOException {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            modify(file);
        }
    }

    private static void modify(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            line = regex(line);
            content.append(line).append("\n");
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(content.toString());
        writer.flush();
    }

    private static String regex(String line) {
        Matcher matcher = PATTERN.matcher(line);
        if (matcher.matches()) {
            String s1 = matcher.group(1);
            String s2 = matcher.group(2);
            String s3 = matcher.group(3);
            System.out.printf("%s:%s:%s%n", s1, s2, s3);
            s2 = "<img " + "referrerpolicy=\"no-referrer\" " + s2.substring(5);
            return s1 + s2 + s3;
        } else {
            return line;
        }
    }
}
