import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ReportGenerator {
    public static void saveReport(List<String> reportLines, String filename) throws IOException {
        Files.write(Paths.get(filename), reportLines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
