public class MajorityElement {
    int majEle(int arr[]){
        for(int i=0; i<arr.length; i++){
            int count = 0;
            int ele = arr[i];
            for(int j=i+1; j<arr.length; j++){
                if(arr[j] == ele){
                    count++;
                }

                if(count > arr.length/2) return j;
            }
        }
        return -1;
    }
}
