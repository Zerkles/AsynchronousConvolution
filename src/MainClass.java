import java.util.ArrayList;

public class MainClass {


    public static void main(String[] args) {
        ArrayList<Integer> data_A = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            data_A.add(i);
        }

        ArrayList<Integer> data_B = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            data_B.add(i);
        }

        long start_time;
        long stop_time;

        start_time = System.nanoTime();
        ArrayList<Integer> result = Convolution1D.conv1D_sync(data_A, data_B);
        stop_time = System.nanoTime();
        System.out.println("Convolve synchronized result: " + result);
        long syn_time = (stop_time - start_time);
        System.out.println("Execution time: " + syn_time);

        start_time = System.nanoTime();
        ArrayList<Integer> result2 = Convolution1D.conv1D_async(data_A, data_B, 10);
        stop_time = System.nanoTime();
        System.out.println("Convolve asynchronized result:" + result2);
        long asyn_time = (stop_time - start_time);
        System.out.println("Execution time: " + asyn_time);

        System.out.println("Time diffrence (SYN - ASYN): " + ((syn_time - asyn_time) / 1000000) + "[ms]");
    }

}




