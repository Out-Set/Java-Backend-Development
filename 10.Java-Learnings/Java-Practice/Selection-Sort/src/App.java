public class App {
    public static void main(String[] args) throws Exception {
        int arr[] = new int[]{2,1,4,3};

        SelectionSort sort = new SelectionSort();
        sort.sort(arr);
        
        for(int i=0; i<arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
