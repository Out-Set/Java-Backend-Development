public class SelectionSort {
    void sort(int arr[]){
        int n = arr.length;
        int last = n-1;
        while(n > 0){
            int pos = 0, larEle = 0;
            for(int i=0; i<n; i++){
                if(larEle < arr[i]){
                    larEle = arr[i];
                    pos = i;
                    System.out.println(pos+" "+arr[i]);
                }
            }
            System.out.println(pos+" "+ arr[pos]+" "+ n);
            System.out.println();
            int temp = arr[last];
            System.out.println("        " +arr[n-1]);
            arr[last] = arr[pos];
            arr[pos] = temp;
            n--;
        }
    }
}
