public class MIDATTACK {

    public static String BaseSystem_2(int Scanner){
        int n = Scanner;
        String result = "";
        boolean minus = false;

        //如果该数字为负数，那么进行该负数+1之后的绝对值的二进制码的对应位取反，然后将它保存在result结果中
        if(n < 0){
            minus = true;
            n = Math.abs(n + 1);
        }
        while(true){
            int remainder = (!minus && n % 2 == 0) || (minus && n % 2 == 1) ? 0 : 1;
            //将余数保存在结果中
            result = remainder + result;
            n /= 2;
            if(n == 0){
                break;
            }
        }
        //判断是否为负数，如果是负数，那么前面所有位补1
        if(minus){
            n = result.length();
            for(int i = 1; i <= 32 - n; i++){
                result = 1 + result;
            }
        }
        if(result.length()<16){
            for(int i=0;i<16-result.length();i++)
                result="0"+result;
        }
        return result;
    }
    static void attackcode(String plaintext,String cypetext){
        long startTime,endTime;
        startTime=System.currentTimeMillis();//获取开始时间
        String []A = new String[65536];
        String []enk= new String[65536];
        for(int i=0;i<=65535;i++)
        {
            String key = BaseSystem_2(i);
            int[][] intkey =Ssaes.StringtoIntArray28(key);
            enk[i] = key;
            int [][]p1 = Ssaes.StringtoIntArray28(plaintext);
            String m1 = Ssaes.IntArrayToString(Ssaes.Encrypt(p1,intkey));
            A[i] = m1;
        }
        for(int i=0;i<=65535;i++)
        {
            String key = BaseSystem_2(i);

            int[][] intkey =Ssaes.StringtoIntArray28(key);
            int [][]p2 = Ssaes.StringtoIntArray28(cypetext);
            String m2 = Ssaes.IntArrayToString(Ssaes.Decrypt(p2,intkey));
            for(int j=0;j<=65535;j++)
            {
                if(m2.equals(A[j])&&(key.length()==16)&&(enk[j].length()==16)){
                    System.out.println("加密秘钥为："+enk[j]);
                    System.out.println("解密秘钥为："+key);
                }
            }
        }
        endTime=System.currentTimeMillis();//获取结束时间

    }
    public  static  void  main(String args[]){
        String m1= "0000111100001111";
        String m2 ="0111110000000110";
        attackcode(m1,m2);

    }
}
