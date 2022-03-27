/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package programming.task2;


import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import static org.junit.Assert.*;

public class PackRLETests {

    @Test
    public void packerStringTest() {
        PackRLE packRLE = new PackRLE();
        String packString1 = packRLE.packer("BBBBBBBBBBBBIIIIIIIIIIIGGGG DUBBBB MMMMMAAAAAAAAAAAATTTTTTEEEEEEEEE");
        assertEquals("12B11I4G DU4B 5M12A6T9E", packString1);

        String packString2 = packRLE.packer("TTTTTTTTTTTHHHHHIIIIIIIISSSS ISSSS AAA TTTTEEESSSSTTTT");
        assertEquals("11T5H8I4S I4S 3A 4T3E4S4T", packString2);
    }


    // Case 1: content only contains digits.
    @Test
    public void packerInputOnlyContainsDigitsTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            PackRLE packRLE = new PackRLE();
            packRLE.packer("11111111112222222222");
        });
        assertTrue(exception.getMessage().contains("Input string cannot only contain digits"));
    }

    // Case 2: impossible to unpack back the original String (packs to -> 201w152g).
    @Test
    public void packerInputContainsDigitSequencesTest1() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            PackRLE packRLE = new PackRLE();
            packRLE.packer("1111111111w2222222222g");
        });
        assertTrue(exception.getMessage().contains("Input string cannot contain digit sequences"));
    }

    // Case 3: impossible to unpack back the original String (packs to -> 2w102).
    @Test
    public void packerInputContainsDigitSequencesTest2() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            PackRLE packRLE = new PackRLE();
            packRLE.packer("ww2222222222222222");
        });
        assertTrue(exception.getMessage().contains("Input string cannot contain digit sequences"));
    }

    // Case 4: impossible to unpack back to the original String (packs to -> 83w7153h).
    @Test
    public void packerInputContainsDigitSequencesTest3() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            PackRLE packRLE = new PackRLE();
            packRLE.packer("33333333w11111112222222222g");
        });
        assertTrue(exception.getMessage().contains("Input string cannot contain digit sequences"));
    }


    @Test
    public void unpackerStringTest() {
        PackRLE packRLE = new PackRLE();
        String unpackString1 = packRLE.unpacker("11T5H8I4S I4S 3A 4T3E4S4T");
        assertEquals("TTTTTTTTTTTHHHHHIIIIIIIISSSS ISSSS AAA TTTTEEESSSSTTTT", unpackString1);

        String unpackString2 = packRLE.unpacker("12B11I4G DU4B 5M12A6T9E");
        assertEquals("BBBBBBBBBBBBIIIIIIIIIIIGGGG DUBBBB MMMMMAAAAAAAAAAAATTTTTTEEEEEEEEE", unpackString2);

        String unpackString3 = packRLE.unpacker("w10g");
        assertEquals("wgggggggggg", unpackString3);
    }

    // Case 1: content only contains digits.
    @Test
    public void unpackerInputOnlyContainsDigitsTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            PackRLE packRLE = new PackRLE();
            packRLE.unpacker("11111111112222222222");
        });

        assertEquals("Input string cannot only contain digits", exception.getMessage());
    }


    // Case 2: content cannot end with digits.
    @Test
    public void unpackerInputEndsWithNumbersTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            PackRLE packRLE = new PackRLE();
            packRLE.unpacker("111111111g33333333");
        });

        assertEquals("Input string cannot end with digits", exception.getMessage());
    }


    private boolean contentEquals(String expectedName, String actualName) throws IOException {
        List<String> expectedLines = Files.readAllLines(Paths.get(expectedName));
        List<String> actualLines = Files.readAllLines(Paths.get(actualName));

        for (int i = 0; i < expectedLines.size(); i++) {
            if (!expectedLines.get(i).equals(actualLines.get(i))) return false;
        }
        return true;
    }

    @Test
    public void packerFileTest() throws IOException {
        String cmd = "-z -out src/test/resources/testRes/output1.txt src/test/resources/testRes/input1.txt";
        String expected = "src/test/resources/testRes/result1.txt";
        String actual = "src/test/resources/testRes/output1.txt";

        PackRLEParser.main(cmd.split(" "));

        assertTrue(contentEquals(expected, actual));
    }


    @Test
    public void unpackerFileTest() throws IOException {
        String cmd = "-u -out src/test/resources/testRes/output2.txt src/test/resources/testRes/input2.txt";
        String expected = "src/test/resources/testRes/result2.txt";
        String actual = "src/test/resources/testRes/output2.txt";

        PackRLEParser.main(cmd.split(" "));

        assertTrue(contentEquals(expected, actual));
    }

    @Test
    public void packerPoemTest() throws IOException {
        String cmd = "-z -out src/test/resources/testRes/output3.txt src/test/resources/testRes/input3.txt";
        String expected = "src/test/resources/testRes/result3.txt";
        String actual = "src/test/resources/testRes/output3.txt";

        PackRLEParser.main(cmd.split(" "));

        assertTrue(contentEquals(expected, actual));
    }

    @Test
    public void unpackerPoemTest() throws IOException {
        String cmd = "-u -out src/test/resources/testRes/output4.txt src/test/resources/testRes/input4.txt";
        String expected = "src/test/resources/testRes/result4.txt";
        String actual = "src/test/resources/testRes/output4.txt";

        PackRLEParser.main(cmd.split(" "));

        assertTrue(contentEquals(expected, actual));
    }

    // Exception Case 1: throws IOException if input file is nonexistent.
    @Test
    public void inputFileNotFoundTest() {
        IOException exception = assertThrows(IOException.class, () -> {
            PackRLEParser packRLEParser = new PackRLEParser();
            CmdLineParser cmdLineParser = new CmdLineParser(packRLEParser);
            String cmd = "-z -out src/test/resources/testRes/output1.txt src/test/resources/testRes/input9.txt";
            cmdLineParser.parseArgument(cmd.split(" "));

            BufferedReader in = new BufferedReader(new FileReader("src/test/resources/testRes/input9.txt"));
        });

        assertTrue(exception.getMessage().contains("input9.txt"));
    }

    // Exception Case 2: throws CmdLineException if the one of the options is invalid.
    @Test
    public void invalidOptionTest() {
        CmdLineException exception = assertThrows(CmdLineException.class, () -> {
            PackRLEParser packRLEParser = new PackRLEParser();
            CmdLineParser cmdLineParser = new CmdLineParser(packRLEParser);
            String cmd = "-i -out src/test/resources/testRes/output1.txt src/test/resources/testRes/input1.txt";
            cmdLineParser.parseArgument(cmd.split(" "));
        });

        assertTrue(exception.getMessage().contains("is not a valid option"));
    }

    // Exception Case 3: throws CmdLineException if both options are chosen.
    @Test
    public void bothOptionsChosenTest() {
        CmdLineException exception = assertThrows(CmdLineException.class, () -> {
            PackRLEParser packRLEParser = new PackRLEParser();
            CmdLineParser cmdLineParser = new CmdLineParser(packRLEParser);
            String cmd = "-z -u -out src/test/resources/testRes/output1.txt src/test/resources/testRes/input1.txt";
            cmdLineParser.parseArgument(cmd.split(" "));
        });

        assertTrue(exception.getMessage().contains("cannot be used with the option(s)"));
    }

    // Exception Case 4: throws CmdLineException if no input file has been passed.
    @Test
    public void noInputFilePassedTest() {
        CmdLineException exception = assertThrows(CmdLineException.class, () -> {
            PackRLEParser packRLEParser = new PackRLEParser();
            CmdLineParser cmdLineParser = new CmdLineParser(packRLEParser);
            String cmd = "-z -out src/test/resources/testRes/output1.txt";
            cmdLineParser.parseArgument(cmd.split(" "));
        });

        assertTrue(exception.getMessage().contains("is required"));
    }

    // Exception Case 5: throws IllegalArgumentException if input String in given file only contains digits,
    // contains digit sequences when packing, or does not end with a letter when unpacking.
    @Test
    public void invalidInputStringTest() {
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> {

            PackRLE packRLE = new PackRLE();
            String line;
            BufferedReader in = new BufferedReader(new FileReader("src/test/resources/testRes/input5.txt"));
            BufferedWriter out = new BufferedWriter(new FileWriter("src/test/resources/testRes/output5.txt"));
            while ((line = in.readLine()) != null) {
                out.write(packRLE.packer(line));
                out.newLine();
            }
        });
        assertEquals("Input string cannot only contain digits", exception1.getMessage());


        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> {

            PackRLE packRLE = new PackRLE();
            String line;
            BufferedReader in = new BufferedReader(new FileReader("src/test/resources/testRes/input6.txt"));
            BufferedWriter out = new BufferedWriter(new FileWriter("src/test/resources/testRes/output6.txt"));
            while ((line = in.readLine()) != null) {
                out.write(packRLE.packer(line));
                out.newLine();
            }
        });

        assertEquals("Input string cannot contain digit sequences", exception2.getMessage());


        IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class, () -> {

            PackRLE packRLE = new PackRLE();
            String line;
            BufferedReader in = new BufferedReader(new FileReader("src/test/resources/testRes/input7.txt"));
            BufferedWriter out = new BufferedWriter(new FileWriter("src/test/resources/testRes/output7.txt"));
            while ((line = in.readLine()) != null) {
                out.write(packRLE.unpacker(line));
                out.newLine();
            }
        });

        assertEquals("Input string cannot end with digits", exception3.getMessage());
    }

}
