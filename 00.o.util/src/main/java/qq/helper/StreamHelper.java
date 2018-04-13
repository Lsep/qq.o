package qq.helper;

import org.springframework.util.StreamUtils;
import qq.extensions.JList;

import java.io.*;

public class StreamHelper {

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        StreamUtils.copy(inputStream, outputStream);
    }

    public static JList<String> readLines(InputStream stream) throws IOException {
        InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        JList<String> lines = new JList<>();
        String line = bufferedReader.readLine();
        while (line != null) {
            lines.add(line);
            line = bufferedReader.readLine();
        }


//            char c = (char) reader.read();
//            while (c != -1) {
//                if (c == '\r' || c == '\n') {
//                    if (line != null) {
//                        lines.add(line);
//                        line = null;
//                    }
//                }
//                else{
//                    if (line == null) {
//                        line = new String();
//                    }
//                    line += c;
//                }
//                c = (char) reader.read();
//            }
//            if (line != null) {
//                lines.add(line);
//            }
        return lines;
    }
}
