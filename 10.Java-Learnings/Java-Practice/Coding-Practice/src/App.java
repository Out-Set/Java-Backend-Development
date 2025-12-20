public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        String str = "Hello World!";
        String s[] = str.split(" ");

        for (String string : s) {
            System.out.println(string);
        }

        String res = "";
        for(int i=0; i<s.length; i++){
            String init = s[i];
            
            for(int j=init.length()-1; j>=0; j--){
                res = res + init.charAt(j);
            }
            res += " ";
        }
        System.out.println(res);

        RevereseString rev = new RevereseString();
        System.out.println(rev.rev(str));

        //Majority element
        int arr[] = new int[]{1,8,8,3,4,8,8};
        MajorityElement me = new MajorityElement();
        System.out.println(me.majEle(arr));

    }
}
