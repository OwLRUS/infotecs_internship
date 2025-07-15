package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ParserJSON {
    public List<String> domList;
    public List<String> ipList;

    private static String fileToString(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line.trim());
        }
        reader.close();
        return sb.toString();
    }

    public void parseString(String localDir) throws IOException {
        String json = fileToString(localDir);

        Pattern pattern = Pattern.compile("\\{\\s*\"domain\"\\s*:\\s*\"([^\"]+)\",\\s*\"ip\"\\s*:\\s*\"([^\"]+)\"\\s*\\}");
        Matcher matcher = pattern.matcher(json);

        domList = new ArrayList<>();
        ipList = new ArrayList<>();
        while (matcher.find()) {
            domList.add(matcher.group(1));
            ipList.add(matcher.group(2));
        }
    }

    private String stringToJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"addresses\": [\n");

        int size = domList.size();
        for (int i = 0; i < size; i++) {
            String dom = domList.get(i);
            String ip = ipList.get(i);
            sb.append("    {\n");
            sb.append("      \"domain\": \"").append(dom).append("\",\n");
            sb.append("      \"ip\": \"").append(ip).append("\"\n");
            sb.append("    }");
            if (i != size - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }

        sb.append("  ]\n}");
        return sb.toString();
    }

    public void saveJSON(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(stringToJSON());
        }
    }
}
