import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.xsom.impl.scd.ParseException;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.security.Key;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception {
        String output = (new Main()).readRawDataToString();
       // System.out.println(output);


        final String regex = "[^a-zA-Z0-9_:/.]";
        List<String> stringList = Pattern.compile(regex, Pattern.MULTILINE)
                .splitAsStream(output)
                .map(String::toLowerCase)
                .collect(Collectors.toList());

      //  stringList.forEach(System.out::println);

        Map<String, Integer> counts = new LinkedHashMap<>();
        stringList.forEach(word ->
                counts.compute(word, (k, v) -> v != null ? v + 1 : 1)
        );

        counts.forEach((k,v) -> System.out.println(String.format("%-25s Seen: %d times", k, v)));
        //makingFile(counts.toString());

    }


    public static void makingFile(String text) {
        try {
            FileWriter file = new FileWriter(new File("Result.txt"));
            file.write(text);
            file.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
    }
}
