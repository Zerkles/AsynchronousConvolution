import java.util.ArrayList;
import java.util.Iterator;

public class Convolution1D {

    public static ArrayList<Integer> conv1D_sync(ArrayList<Integer> datasetA, ArrayList<Integer> datasetB) {

        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> move = new ArrayList<>();

        Iterator<Integer> it_B = datasetB.iterator();

        for (int i = 0; i < datasetA.size(); i++) {
            move.add(0);
        }

        for (int i = 0; i < datasetA.size() + datasetB.size(); i++) {
            for (int j = 0; j < datasetB.size(); j++) {


                move.remove(move.size() - 1);
                if (it_B.hasNext()) {
                    move.add(0, it_B.next());
                } else {
                    move.add(0, 0);
                }
                int sum = 0;
                for (int k = 0; k < datasetA.size(); k++) {
                    sum += datasetA.get(k) * move.get(k);
                }
                result.add(sum);
            }
        }
        result.removeIf(n -> (n == 0));
        return result;
    }

    static ArrayList<Integer> conv1D_async(ArrayList<Integer> data1, ArrayList<Integer> data2, int thread_count) {

        ArrayList<ArrayList<Integer>> data_splitted = split_data(data2, thread_count);
        ArrayList<ArrayList<Integer>> result_sets = new ArrayList<>();
        for (int i = 0; i < data1.size() + data2.size() - 1; i++) {
            result_sets.add(new ArrayList<>());
        }
        ArrayList<AsyncConvThread> thread_array = new ArrayList<>();

        for (int i = 0; i < data_splitted.size(); i++) {
            thread_array.add(new AsyncConvThread(data1, data_splitted.get(i), result_sets, i));
            thread_array.get(thread_array.size() - 1).start();
        }

        for (AsyncConvThread t : thread_array) {
            try {
                t.join();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        ArrayList<Integer> result_flattened = new ArrayList<>();

        for (ArrayList<Integer> array : result_sets) {
            result_flattened.addAll(array);
        }

        result_flattened.removeIf(n -> (n == 0));
        return result_flattened;

    }

    static ArrayList<ArrayList<Integer>> split_data(ArrayList<Integer> data, Integer volumes_size) {

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        ArrayList<Integer> subset = new ArrayList<>();
        Integer counter = 0;

        for (int i = 0; i < data.size(); i++) {
            subset.add(data.get(i));
            counter++;

            if (counter.equals((int) (data.size() / volumes_size)) || data.size() - i - 1 <= volumes_size) {
                result.add(subset);
                subset = new ArrayList<>();
                counter = 0;
            }

        }

        return result;
    }
}


