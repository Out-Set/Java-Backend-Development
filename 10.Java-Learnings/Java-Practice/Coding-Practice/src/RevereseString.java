public class RevereseString {
    String res = "";
    String rev(String str){
        for(int i=str.length()-1; i>=0; i--){
            res = res + str.charAt(i);
        }
        return res;
    }
}
