import java.util.Arrays;


public class Example {

    private static final int NUM_ENEMIES = 10;

    public void run() {
        int[] arr = new int[10];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }
        
        System.out.println(Arrays.toString(arr));
    }
    
    public double multiply(double a, double b) {
        return a * b;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args) {
        Example ex = new Example();
        ex.run();

    }

}
