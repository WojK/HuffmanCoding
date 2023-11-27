package pl.edu.pw.ee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class HuffmanTest {

     @Test(expected = IllegalArgumentException.class)
     public void should_ThrowIOException_When_PassInvalidPathToRootDir() {
          // given
          Huffman huffman = new Huffman();
          String path = System.getProperty("user.dir");
          path += "\\src\\test\\java\\pl\\edu\\pw\\ee\\test_resourceA";

          // when
          huffman.huffman(path, true);

          // then
          assert false;
     }

     @Test
     public void should_EncodeFile_When_PassCorrectDir() {
          // given
          Huffman huffman = new Huffman();
          String path = System.getProperty("user.dir");
          path += "\\src\\test\\java\\pl\\edu\\pw\\ee\\test_resource\\folder_with_input";

          // when
          huffman.huffman(path, true);

          // then
          File file = new File(path + "\\EncodedFile.txt");
          assertEquals(true, file.exists());
     }

     @Test(expected = IllegalArgumentException.class)
     public void should_ThrowIOException_When_PassRootDirWithoutDictionery() {
          // given
          Huffman huffman = new Huffman();
          String path = System.getProperty("user.dir");
          path += "\\src\\test\\java\\pl\\edu\\pw\\ee\\test_resource\\folder_without_dictionary";

          // when
          huffman.huffman(path, true);

          // then
          assert false;
     }

     @Test(expected = IllegalArgumentException.class)
     public void should_ThrowIOException_When_PassRootPathToEmptyFolder() {
          // given
          Huffman huffman = new Huffman();
          String path = System.getProperty("user.dir");
          path += "\\src\\test\\java\\pl\\edu\\pw\\ee\\test_resource\\empty_folder";

          // when
          huffman.huffman(path, true);

          // then
          assert false;
     }

     @Test
     public void should_DecodeFile_When_PassCorrectFoler() {
          // given
          Huffman huffman = new Huffman();
          String path = System.getProperty("user.dir");
          path += "\\src\\test\\java\\pl\\edu\\pw\\ee\\test_resource\\folder_with_EncodedFile";

          // when
          huffman.huffman(path, false);

          // then
          File file = new File(path + "\\DecodedFile.txt");
          assertEquals(true, file.exists());
     }

     @Test
     public void should_ReturnValidCharsAmount_When_DecodeEncodedFile() {
          // given
          Huffman huffman = new Huffman();
          String path = System.getProperty("user.dir");
          path += "\\src\\test\\java\\pl\\edu\\pw\\ee\\test_resource\\folder_with_EncodedFile";
          int expected = 1966;

          // when
          int encodedChars = huffman.huffman(path, false);

          // then
          assertEquals(expected, encodedChars);
     }

     @Test
     public void should_ReturnValidBitsAmount_When_EncodeFile() {
          // given
          Huffman huffman = new Huffman();
          String path = System.getProperty("user.dir");
          path += "\\src\\test\\java\\pl\\edu\\pw\\ee\\test_resource\\folder_with_input";
          int expected = 9278;

          // when
          int usedBits = huffman.huffman(path, true);

          // then
          assertEquals(expected, usedBits);
     }

     @Test
     public void should_CorrectlyDecodeFile_When_DecodeFile() throws IOException {
          // given
          Huffman huffman = new Huffman();
          String path = System.getProperty("user.dir");
          path += "\\src\\test\\java\\pl\\edu\\pw\\ee\\test_resource\\folder_with_input";
          BufferedReader br = new BufferedReader(new FileReader(path + "\\input.txt"));
          String input = "";
          String output = "";
          String line = br.readLine();
          while (line != null) {
               input += line;
               line = br.readLine();
          }
          br.close();

          // when
          huffman.huffman(path, false);
          br = new BufferedReader(new FileReader(path + "\\DecodedFile.txt"));
          line = br.readLine();
          while (line != null) {
               output += line;
               line = br.readLine();
          }
          br.close();

          // then
          assertEquals(input, output);
     }


     // @Test
     // public void test_encode() {
     //      String desktopPath = System.getProperty("user.home") + File.separator +"Desktop";
     //      Huffman huffman = new Huffman();
     //      huffman.huffman(desktopPath, true);
         
     // }

     // @Test
     // public void test_decode() {
     //      String desktopPath = System.getProperty("user.home") + File.separator +"Desktop";
     //      Huffman huffman = new Huffman();
     //      huffman.huffman(desktopPath, false);
     // }

}
