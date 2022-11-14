import java.util.Scanner;
import java.util.ArrayList;

public class AES {

    String hexToBin(String input) {

        int n = input.length() * 4;
        input = Long.toBinaryString(Long.parseUnsignedLong(input, 16));
        while (input.length() < n)
            input = "0" + input;
        return input;

    }

    // binary to hexadecimal conversion
    String binToHex(String input) {

        int n = input.length() / 4;
        input = Long.toHexString(Long.parseUnsignedLong(input, 2));
        while (input.length() < n)
            input = "0" + input;
        return input;

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

    public String permutation(String binaryData, int[] permutationMatrix){
        String permutatedBinary = "";
        for (int element : permutationMatrix) {
            permutatedBinary = permutatedBinary.concat(String.valueOf(binaryData.charAt(element-1)));
        }
        return permutatedBinary;
    }

    public String xor(String data1, String data2){
        String output = "";
        for(int i = 0; i < data1.length(); i++){
            if(data1.charAt(i) != data2.charAt(i)){
                output = output.concat("1");
            } else {
                output = output.concat("0");
            }
        }
        return output;
    }

    public String circularLeftShift(String binaryData){
        String temp = "";
        for(int i = 0; i < binaryData.length(); i++){
            if(i != binaryData.length()-1) {
                temp = temp.concat(String.valueOf(binaryData.charAt(i+1)));
            } else{
                temp = temp.concat(String.valueOf(binaryData.charAt(0)));
            }
        }
        return temp;
    }

    public ArrayList<String> generate16Keys(String key){
        ArrayList<String> dividedKeys = new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> permKeys = new ArrayList<>();
        String keyInBinary = convertStringToBinary(key);
        String permutatedKey_Pc1 = permutation(keyInBinary,PC1);
        String C0 = splitText(permutatedKey_Pc1,28).get(0);
        String D0 = splitText(permutatedKey_Pc1,28).get(1);
        String C1 = "", D1 = "";
        for(int i = 0; i < shiftBits[0]; i++){
            C1 = circularLeftShift(C0);
            D1 = circularLeftShift(D0);
        }
        dividedKeys.add(C1);                    // In this arraylist c is always the even index and 0 index keys.get(2) ==> C2 keys.get(3) ==> D2.
        // to get n, if C: n = (index/2) + 1. if D: n = index/2
        dividedKeys.add(D1);                    // what is the index of Dn = (n*2)-1 , and what is the index of Cn = (n-1)*2
        for(int i = 0; i < 15; i++){
            String C = "", D = "";
            for(int j = 0; j < shiftBits[i+1]; j++){
                C = circularLeftShift(dividedKeys.get(i));
                D = circularLeftShift(dividedKeys.get(i+1));
            }
            dividedKeys.add(C);
            dividedKeys.add(D);
        }
        for(int i = 0; i < dividedKeys.size(); i+=2){
            keys.add(dividedKeys.get(i).concat(dividedKeys.get(i+1)));      // k1 = c1 + c2
        }
        String permutatedKeys_Pc2 = "";
        for(int i = 0; i < keys.size(); i++){
            permutatedKeys_Pc2 = permutation(keys.get(i),PC2);
            permKeys.add(permutatedKeys_Pc2);
        }

        return permKeys;
    }

    public String sBox(String input){

        String output = "";
        for (int i = 0; i < 48; i += 6) {

            String temp = input.substring(i, i + 6);
            int num = i / 6;
            int row = Integer.parseInt(temp.charAt(0) + "" + temp.charAt(5), 2);
            int col = Integer.parseInt(temp.substring(1, 5), 2);
            output = output.concat(String.format("%4s", Integer.toBinaryString(sbox[num][row][col]))   // char -> int, auto-cast
                    .replaceAll(" ", "0"));     // zero padding)//(sbox[num][row][col]));
        }
        return output;
    }

    public String round(String L_previous, String R_previous, String key){
        String R_perm = permutation(R_previous,E);         // 32 bits ==> 48 bits
        String R_xor_key = xor(R_perm,key);
        String output = sBox(R_xor_key);                    // 48 bits ==> 32 bits
        R_perm = permutation(output,P);
        String R_xor_L = xor(L_previous,R_perm);
        return R_xor_L;
    }

    public String encrypt(String data, String key){
        ArrayList<String> keys = generate16Keys(key);
        String dataInBinary = convertStringToBinary(data);
        String permutatedData_IP = permutation(dataInBinary,IP);
        String L0 = splitText(permutatedData_IP,32).get(0);
        String R0 = splitText(permutatedData_IP,32).get(1);
        ArrayList<String> R = new ArrayList<>();
        String L = "";
        for(int i = 0; i < keys.size(); i++){
            if(i == 0){
                R.add(round(L0,R0,keys.get(i)));
                L = R0;
            } else{
                R.add(round(L,R.get(i-1),keys.get(i)));
                L = R.get(i-1);
            }
        }
        String output = R.get(15).concat(L);        // swapping R16 with L16 and concatenating them
        output = permutation(output,IP1);
        output = binToHex(output);
        return output;

    }

    public String decrypt(String encryptedText, String key){
        String decrypted = "";
        ArrayList<String> keys = generate16Keys(key);
        String encryptedBinary = hexToBin(encryptedText);
        encryptedBinary = permutation(encryptedBinary,IP);
        String L16 = splitText(encryptedBinary,32).get(1);
        String R16 = splitText(encryptedBinary,32).get(0);
        ArrayList<String> L = new ArrayList<>();
        String R = "";
        int j = 0;
        for(int i = keys.size()-1; i >= 0; i--){       // getting L0,R0
            if(i == 15){
                L.add(round(R16,L16,keys.get(i)));
                R = L16;
            } else{
                L.add(round(R,L.get(j),keys.get(i)));
                R = L.get(j);
                j++;
            }
        }
        String decryptedBinary = L.get(15).concat(R);
        String reversePermuattion = permutation(decryptedBinary,IP1);
        decrypted = convertBinaryToString(reversePermuattion);
        decrypted = decrypted.replace("#","");
        return decrypted;
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

        ArrayList<String> keys = Aes.generate16Keys(key);
        System.out.println("\nKeys:\n");
        for(int k = 0; k < keys.size(); k++){
            System.out.println(k+1 + ") " + keys.get(k));
        }
        String encryptedText = "";
        for(String temp : text) {
            encryptedText = encryptedText.concat(Aes.encrypt(temp,key));
        }
        System.out.println("Encrypted text:\n" + encryptedText);

        ArrayList<String> blocks = Aes.splitText(encryptedText,32);
        String decryptedText = "";
        for(String block : blocks){
            decryptedText = decryptedText.concat(Aes.decrypt(block,key));
        }
        System.out.println("Decrypted Text:");
        System.out.println(decryptedText);
    }
}