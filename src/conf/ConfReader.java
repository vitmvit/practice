package conf;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ConfReader {

    Map<String, String> getConf(String fileConf) {
        List<String> list = readAllLines(fileConf);
        list.removeIf(line -> line == null || line.isEmpty() || line.startsWith("#"));
        Map<String, String> map = new HashMap<>(list.size());
        for (String s : list) {
            String[] array = s.split("=");
            map.put(array[0].trim(), array[1].trim());
        }
        return map;
    }

    private List<String> readAllLines(String fileConf) {
        try {
            return Files.readAllLines(Paths.get(fileConf), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
