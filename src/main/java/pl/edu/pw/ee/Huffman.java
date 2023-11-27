package pl.edu.pw.ee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import pl.edu.pw.ee.utils.MinHeap;

public class Huffman {

    private ArrayList<Character> signs;
    private ArrayList<String> signCodes;
    private final char EOF = (char) -1;

    public int huffman(String pathToRootDir, boolean compress) {

        if (compress) {
            signs = new ArrayList<>();
            signCodes = new ArrayList<>();
            int usedBits = 0;
            try {
                usedBits = encodeFile(pathToRootDir);
            } catch (IOException e) {
                throw new IllegalArgumentException("pathToRootDir should point dir with file named input.txt");
            }
            return usedBits;
        } else {
            int decodedCharacters;
            try {
                decodedCharacters = decodeFile(pathToRootDir);
                return decodedCharacters;
            } catch (IOException e) {
                throw new IllegalArgumentException(
                        "pathToRootDir should point dir with files named EncodedFile.txt and Dictionary.txt");
            }
        }
    }

    private int encodeFile(String pathToRootDir) throws IOException {

        ArrayList<Character> charactersList = new ArrayList<>();
        ArrayList<Integer> characterStrength = new ArrayList<>();
        determineCharsAndFrequency(pathToRootDir, charactersList, characterStrength);

        Node root = buildTree(charactersList, characterStrength);

        encodeChars(root);

        return createEncodedFile(pathToRootDir, charactersList, characterStrength);
    }

    private void determineCharsAndFrequency(String pathToRootDir, ArrayList<Character> charactersList,
            ArrayList<Integer> characterStrength) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(pathToRootDir + "/input.txt"));

        char inChar = (char) br.read();

        while (inChar != EOF) {
            int index = charactersList.indexOf(inChar);

            if (index != -1) {
                int strength = characterStrength.get(index);
                characterStrength.set(index, strength + 1);
            } else {
                charactersList.add(inChar);
                characterStrength.add(1);
            }

            inChar = (char) br.read();
        }

        br.close();
    }

    private Node buildTree(ArrayList<Character> charactersList, ArrayList<Integer> characterStrength) {

        Node root = null;
        MinHeap<Node> nodes = new MinHeap<>();

        for (int i = 0; i < charactersList.size(); i++) {
            nodes.put(new Node(charactersList.get(i), characterStrength.get(i)));
        }

        while (nodes.getSize() > 1) {
            Node node = new Node(nodes.pop(), nodes.pop());
            nodes.put(node);
        }

        root = nodes.pop();
        return root;
    }

    private void encodeChars(Node root) {
        encodeChars(root, "");
    }

    private void encodeChars(Node node, String code) {
        if (node == null) {
            return;
        }
        encodeChars(node.getLeft(), code + "0");
        encodeChars(node.getRight(), code + "1");
        if (!node.isEmpty()) {
            signCodes.add(code);
            signs.add(node.getSign());
        }
    }

    private int createEncodedFile(String pathToRootDir, ArrayList<Character> charactersList,
            ArrayList<Integer> characterStrength) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(pathToRootDir + "/input.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter(pathToRootDir + "/EncodedFile.txt"));
        int bitsCounter = 0;

        char bOut = 0x0;
        int charCapacity = 15;
        char bIn = (char) br.read();

        while (bIn != EOF) {

            int signIndex = signs.indexOf(bIn);
            char[] code = signCodes.get(signIndex).toCharArray();
            int codeSize = code.length;

            while (codeSize > 0) {
                charCapacity--;

                char bit = code[code.length - codeSize];

                if (bit == '1') {
                    bOut = (char) (bOut | 0x1);
                }

                if (charCapacity > 0) {
                    bOut = (char) (bOut << 1);
                }

                if (charCapacity == 0) {
                    bw.write(bOut);
                    bOut = 0x0;
                    charCapacity = 15;
                }

                codeSize--;
                bitsCounter++;
            }
            bIn = (char) br.read();
        }
        bw.write(bOut << (charCapacity - 1));
        createDictionary(charactersList, characterStrength, pathToRootDir, 15 - charCapacity);

        bw.close();
        br.close();
        return bitsCounter;

    }

    private void createDictionary(ArrayList<Character> charactersList, ArrayList<Integer> characterStrength,
            String pathToRootDir, int lastCodeLength) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(pathToRootDir + "/Dictionary.txt"));
        for (int i = 0; i < charactersList.size(); i++) {
            char sign = charactersList.get(i);
            int signFrequency = characterStrength.get(i);
            bw.write((int) sign + ":" + signFrequency + " ");
        }
        bw.newLine();
        bw.write(String.valueOf(lastCodeLength));
        bw.close();
    }

    private int decodeFile(String pathToRootDir) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(pathToRootDir + "/EncodedFile.txt"));
        FileWriter fw = new FileWriter(pathToRootDir + "/DecodedFile.txt");
        ArrayList<Character> charactersList = new ArrayList<>();
        ArrayList<Integer> characterStrength = new ArrayList<>();
        int decodedCharacters = 0;
        int lastCharBitsLength;

        lastCharBitsLength = decodeDictionary(pathToRootDir, charactersList, characterStrength);

        Node root = buildTree(charactersList, characterStrength);
        Node node = root;

        char iter = (char) 0x4000;
        char character = (char) br.read();

        while (character != EOF) {
            char next = (char) br.read();

            int max = 15;
            if (next == EOF) {
                max = lastCharBitsLength;
            }

            for (int j = 0; j < max; j++) {

                boolean leftOrRight = (character & iter) != 0 ? true : false;
                iter = (char) (iter / 2);

                if (!leftOrRight) {
                    node = node.getLeft();
                } else {
                    node = node.getRight();
                }

                if (!node.isEmpty()) {
                    fw.write(node.getSign());
                    decodedCharacters++;
                    node = root;
                }
            }

            iter = 0x4000;
            character = next;
        }

        br.close();
        fw.close();
        return decodedCharacters;
    }

    private int decodeDictionary(String pathToRootDir, ArrayList<Character> charactersList,
            ArrayList<Integer> characterStrength)
            throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(pathToRootDir + "/Dictionary.txt"));
        String[] charAndFreq = br.readLine().split(" ");
        for (String s : charAndFreq) {
            String[] tmp = s.split(":");
            charactersList.add((char) Integer.parseInt(tmp[0]));
            characterStrength.add(Integer.parseInt(tmp[1]));
        }
        String countBitsInLastChar = br.readLine();
        br.close();
        return Integer.parseInt(countBitsInLastChar);
    }

}
