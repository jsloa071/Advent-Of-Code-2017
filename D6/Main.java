import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static int[] distributeBanks(int[] arr){
        int max = 0;
        int index = -1;
        //find maximum block in bank
        for(int i=0; i<arr.length; i++){
            if(arr[i] > max){
                max = arr[i];
                index = i;
            }
        }
        //redistribute
        arr[index] = 0;
        while (max > 0){
            if(index < arr.length-1)
                index++;
            else
                index = 0;
            arr[index] += 1;
            max--;
        }
        return arr;
    }

    public static int redistributeUntilMatch(ArrayList<int[]> banksList, int[] arr){
        int loops = 0;
        boolean distributed = false;
        do{
            int[] newBank = distributeBanks(arr);
            for(int[] bank : banksList){
                if(Arrays.equals(bank, newBank)){
                    distributed = true;
                }
            }
            if(!distributed)
                banksList.add(newBank.clone());
            arr = newBank;
            loops++;
        }while(!distributed);
        return loops;
    }

    public static void main(String[] args) throws IOException {
	// write your code here
        //INPUT
//        String input = new String("11\t11\t13\t7\t0\t15\t5\t5\t4\t4\t1\t1\t7\t1\t15\t11");

        Path filePath = Paths.get("6.txt");
        Scanner in = new Scanner(filePath);
        String input = in.nextLine();
        String[] blocks = input.split("\t");

        //MEMORY
        //banks: holds all combinations of banks seen so far
        //blocksInt: holds current blocks for processing
        ArrayList<int[]> banks = new ArrayList<>();
        int[] blocksInt = new int[blocks.length];
        for(int i=0; i<blocks.length; i++){
            blocksInt[i] = Integer.parseInt(blocks[i]);
        }
        banks.add(blocksInt.clone());

        //ACTION
        //distribution
        int loops = redistributeUntilMatch(banks, blocksInt);

        //OUTPUT
        System.out.println("Part one: "+loops);

        //PART TWO:

        //clear banks and add previous bank
        banks.clear();
        banks.add(blocksInt.clone());

        //ACTION
        //re-distribution
        loops = redistributeUntilMatch(banks, blocksInt);

        //OUTPUT
        System.out.println("Part two: "+loops);

    }
}
