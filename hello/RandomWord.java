import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {

    public static void main(String[] args) {
        
        int counter = 1;
        String champion = "";

        while (!StdIn.isEmpty()){

            String word = StdIn.readString();
            
            if (StdRandom.bernoulli(1.0 / counter)) {
                champion = word;
            }

            counter++;

        }
        System.out.println(champion);
    }
    
}
