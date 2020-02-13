import java.util.ArrayList;

public class AsyncConvThread extends Thread {
    ArrayList<Integer> datasetA;
    ArrayList<Integer> datasetB;
    ArrayList<ArrayList<Integer>> result;
    Integer position_in_result;

    public AsyncConvThread(ArrayList<Integer> datasetA, ArrayList<Integer> datasetB, ArrayList<ArrayList<Integer>> result, Integer position_in_result) {
        this.datasetA = datasetA;
        this.datasetB = datasetB;
        this.result = result;
        this.position_in_result = position_in_result;
    }

    public void run() {
        result.set(position_in_result, Convolution1D.conv1D_sync(this.datasetA, this.datasetB));
    }
}
