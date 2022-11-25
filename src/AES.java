import java.util.Scanner;
import java.util.ArrayList;

public class AES {

    short[][] Sbox =
    {
            {0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76},
            {0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0},
            {0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15},
            {0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75},
            {0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84},
            {0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf},
            {0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8},
            {0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2},
            {0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73},
            {0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb},
            {0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79},
            {0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08},
            {0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a},
            {0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e},
            {0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf},
            {0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16}
    };

    short[][] Inv_Sbox =
    {
            {0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb},
            {0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb},
            {0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e},
            {0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25},
            {0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92},
            {0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84},
            {0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06},
            {0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b},
            {0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73},
            {0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e},
            {0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b},
            {0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4},
            {0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f},
            {0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef},
            {0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61},
            {0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d}
    };

    short[][] Rcon =
        {
                {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36},
                {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00},
                {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00},
                {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}
        };

    short[][] mixColumn = {
            {2,3,1,1},
            {1,2,3,1},
            {1,1,2,3},
            {3,1,1,2}
    };

    short[][] INV_mixColumns = {
            {0x0E,0x0B,0x0D,0x09},
            {0x09,0x0E,0x0B,0x0D},
            {0x0D,0x09,0x0E,0x0B},
            {0x0B,0x0D,0x09,0x0E}
    };


    short[][] convertTextIntoState(String hexaText){
        short[][] matrix2D = new short[4][4];
        for(int i = 0; i < (hexaText.length()) && (i/2 < 4); i+=2) {
            for (int j = i; j < (hexaText.length()) && (j/9 < 4); j+=8) {
                matrix2D[i/2][j/8] = Short.parseShort(String.valueOf(hexaText.charAt(j)).concat(String.valueOf(hexaText.charAt(j+1))),16);
            }
        }
        return matrix2D;
    }

    public String convertStateIntoHexString(short[][] dataState){
        String cipherText = "";
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                cipherText = cipherText.concat(Long.toHexString(dataState[j][i]));
            }
        }
        return cipherText;
    }

    // hexadecimal to binary conversion
    String hexToBin(String input) {
        String output = "";
        int n = input.length() * 4;
        ArrayList<String> blocks = splitText(input,input.length()/2);
        for (String block : blocks){
            output = output.concat(Long.toBinaryString(Long.parseUnsignedLong(block,16)));
        }
        while (output.length() < n)
            output = "0" + output;
        return input;

    }

    // binary to hexadecimal conversion
    String binToHex(String input) {
        String output = "";
        int n = input.length() / 4;
        ArrayList<String> blocks = splitText(input,4);
        for(String block : blocks){
            output = output.concat(Long.toHexString(Long.parseUnsignedLong(block,2)));
        }
        while (output.length() < n)
            output = "0" + output;
        return output;
    }
    public String convertStringToBinary(String data) { // data is either (a block from the plain text) or (a key)
        StringBuilder result = new StringBuilder();
        char[] chars = data.toCharArray();
        for (char c : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(c))   // char -> int, auto-cast
                            .replaceAll(" ", "0")     // zero padding
            );
        }
        return result.toString();
    }

    public String convertBinaryToString(String binary){
        String encryptedData = "";
        ArrayList<String> splitted = splitText(binary,8);
        for (String temp : splitted) {
            int num = Integer.parseInt(temp, 2);
            char letter = (char) num;
            encryptedData = encryptedData.concat(String.valueOf(letter));
        }
        return encryptedData;
    }

    public short[][] sBox_InvSbox(short[][] matrix, short[][] sboxMatrix){
        short[][] newMatrix = new short[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++) {
                String cell = Integer.toHexString(matrix[i][j]);
                if (cell.length() < 2) {
                    cell = "0" + cell;
                }
                short row = (short) Long.parseUnsignedLong(String.valueOf(cell.charAt(0)), 16);
                short col = (short) Long.parseUnsignedLong(String.valueOf(cell.charAt(1)), 16);
                newMatrix[i][j] = sboxMatrix[row][col];
            }
        }
        return newMatrix;
    }

    public short[][] xor(short[][] data1, short[][] data2){
        short[][] output = new short[data1.length][data1[0].length];
        for(int i = 0; i < data1.length; i++){
            for (int j = 0; j < data1[0].length; j++) {
                output[i][j] = (short) (data1[i][j] ^ data2[i][j]);
            }
        }
        return output;
    }

    public short[][] shiftRowsRight(short[][] matrix){
        short[][] newMatrix = matrix;
        for (int i = 1; i < matrix.length; i++){
            ArrayList<Short> temp = new ArrayList<>();
            for (int k = 0; k < i; k++){
                temp.add(matrix[i][3-k]);              // elemnets I want to shift
            }
            for (int k = 0; k < temp.size(); k++){
                if ((3 - temp.size() - k) >= 0) {
                    newMatrix[i][3 - k] = matrix[i][3 - temp.size() - k];
                }
            }
            for (int k = 0; k < temp.size(); k++){
                newMatrix[i][k] = temp.get(temp.size()-k-1);
            }
        }
        return matrix;
    }

    public short[][] shiftRowsLeft(short[][] matrix){
        short[][] newMatrix = matrix;
        for (int i = 1; i < matrix.length; i++){
            ArrayList<Short> temp = new ArrayList<>();
            for (int k = 0; k < i; k++){
                temp.add(matrix[i][k]);              // elemnets I want to shift
            }
            for (int k = temp.size(); k < 4; k++){
                newMatrix[i][k - temp.size()] = matrix[i][k];
            }
            for (int k = temp.size(); k > 0; k--){
                newMatrix[i][4 - k] = temp.get(temp.size() - k);
            }
        }
        return newMatrix;
    }

    public short multiplyCell(short[][] matrix1, short[][] matrix2, int row, int col){
        short cell = 0;
        for(int i = 0; i < 4; i++){
            cell += matrix1[row][i] * matrix2[i][col];
        }
        return cell;
    }

    public short[][] mixCol(short[][] matrix, short[][] matrix_columns){
        short[][] result = new short[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++){
                result[i][j] = multiplyCell(matrix, matrix_columns, i, j);
            }
        }
        return result;
    }

    public short[][] rotWord(short[][] word){
        short[][] newWord = new short[word.length][word[0].length];
        for (int i = 0; i < word.length; i++){
            if (i != 3) {
                newWord[i] = word[i + 1];
            } else {
                newWord[i] = word[0];
            }
        }
        return newWord;
    }

    public short[][] subColumn(short[][] matrix, int col){
        short[][] column = new short[4][1];
        for (int i = 0; i < 4; i++){
            column[i][0] = matrix[i][col];
        }
        return column;
    }
    public ArrayList<short[][]> keyGeneration(String key){
        short[][] keyState = convertTextIntoState(key);
        ArrayList<short[][]> keys = new ArrayList<>();
        keys.add(keyState);                             // k0 is the key state

        for (int i = 0; i < 10; i++){
            short[][] tempKey = new short[4][4];
            short[][] rotatedWord = subColumn(keys.get(i),3);
            short[][] firstCol = subColumn(keys.get(i),0);
            short[][] RconColumn = subColumn(Rcon,i);
            rotatedWord = rotWord(rotatedWord);
            rotatedWord = sBox_InvSbox(rotatedWord,Sbox);
            rotatedWord = xor(rotatedWord,firstCol);
            rotatedWord = xor(rotatedWord,RconColumn);

            for (int k = 0; k < 4; k++){
                tempKey[k][0] = rotatedWord[k][0];
            }

            for (int j = 1; j < 4; j++) {
                rotatedWord = xor(rotatedWord, subColumn(keys.get(i), j));

                for (int k = 0; k < 4; k++){
                    tempKey[k][j] = rotatedWord[k][0];
                }
            }
            keys.add(tempKey);
        }

        return keys;
    }

    public short[][] encryption_round(short[][] dataState, short[][] key){
        dataState = sBox_InvSbox(dataState, Sbox);                  // confusion Layer
        dataState = shiftRowsLeft(dataState);                           // diffusion layer {shiftRows & mixColumns}
        dataState = mixCol(dataState, mixColumn);
        dataState = xor(dataState, key);                             // keyAddition Layer
        return dataState;
    }

    public short[][] decryption_round(short[][] cipherState, short[][] key){
        cipherState = xor(cipherState, key);                             // keyAddition Layer
        cipherState = mixCol(cipherState, INV_mixColumns);
        cipherState = shiftRowsRight(cipherState);
        cipherState = sBox_InvSbox(cipherState, Inv_Sbox);
        return cipherState;
    }

    public String encrypt(String data, String key){
        short[][] dataState = convertTextIntoState(data);
        ArrayList<short[][]> keys = keyGeneration(key);
        dataState = xor(dataState, keys.get(0));                    // initial keyAddition Layer

        for (int i = 1; i < 10; i++){
            dataState = encryption_round(dataState, keys.get(i));
        }
        dataState = sBox_InvSbox(dataState, Sbox);                  // confusion Layer
        dataState = shiftRowsLeft(dataState);                       // diffusion layer shiftRows only for last round
        dataState = xor(dataState, keys.get(10));                    // keyAddition Layer

        String cipher = convertStateIntoHexString(dataState);
        return cipher;
    }

    public String decrypt(String encryptedText, String key){
        short[][] decryption = convertTextIntoState(encryptedText);
        String decryptedText = "";
        ArrayList<short[][]> keys = keyGeneration(key);
        decryption = xor(decryption,keys.get(keys.size()-1));

        decryption = shiftRowsRight(decryption);
        decryption = sBox_InvSbox(decryption,Inv_Sbox);
        for (int i = keys.size()-2; i > 0; i++){
            decryption = decryption_round(decryption,keys.get(i));
        }
        decryption = xor(decryption, keys.get(0));

        decryptedText = convertStateIntoHexString(decryption);
        return decryptedText;
    }

    public ArrayList<String> splitText(String text, int size){
        int i, j;
        ArrayList<String> plainText = new ArrayList<>();
        for(i = 0; i < text.length(); i++){
            String temp = "";
            for(j = i; j < (i+size); j++){
                if(j < text.length()) {
                    temp += text.charAt(j);
                }else {
                    break;
                }
            }
            if(temp.length() < size){
                for(int k = temp.length(); k < size; k++){
                    temp = temp.concat("#");
                }
            }
            plainText.add(temp);
            i = j-1;
        }
        return plainText;
    }

    public void printMatrix(short[][] state, String name){
        System.out.println("\n" + name + "=\n");
        for (int i = 0; i < state.length; i++){
            for (int j = 0; j < state[0].length; j++) {
                System.out.print(" " + state[i][j]);
            }
            System.out.println("");
        }
    }
    public static void main(String[] args) {
        System.out.println("Enter Plaintext to encrypt:");
        Scanner input = new Scanner(System.in);
        String plainText = input.nextLine();
        String key = "";
        while(key.length() != 16) {
            System.out.println("Please Enter new key of length 16 characters");
            key = input.nextLine();
        }

        AES Aes = new AES();
        ArrayList<String> text = new ArrayList<>();
        if(plainText.length() < 16){
            for(int i = plainText.length(); i < 16; i++){
                plainText = plainText.concat("#");
            }
        }
        if (plainText.length() >= 16) {
            text = Aes.splitText(plainText, 16);
        }
        System.out.println(text);
        for (int i = 0; i < text.size();i++){
            String data = text.get(i);
            data = Aes.convertStringToBinary(data);
            System.out.println(data);
            data = Aes.binToHex(data);
            text.set(i,data);                               // Replace String text with hex
        }

        key = Aes.convertStringToBinary(key);
        key = Aes.binToHex(key);                      // key in hex
        short[][] keyState = Aes.convertTextIntoState(key);
        Aes.printMatrix(keyState, "Key");


        short[][] state = Aes.convertTextIntoState(text.get(0));
        Aes.printMatrix(state, "Data");
        System.out.println(Aes.convertStateIntoHexString(state));
        /********************************************************************************/
        /*short[][] shifted = Aes.shiftRowsRight(state);
        Aes.printMatrix(shifted,"Shifted");*/

        ArrayList<short[][]> keys = Aes.keyGeneration(key);
        for (int i = 0; i < keys.size(); i++){
            Aes.printMatrix(keys.get(i), "Key" + i);
        }
        String encryptedText = Aes.encrypt(text.get(0), key);
        System.out.println("Encryption Text\n" + encryptedText);
    }
}