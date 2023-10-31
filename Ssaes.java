import java.util.Scanner;

public class Ssaes {
    // TODO 实现x^nfx的函数
    public static void x_fx(int xfx[], int a[])
    {
        if (a[0] == 0)
        {
            for (int i = 0; i < 3; i++)
                xfx[i] = a[i + 1];
        }
        else
        {
            xfx[1] = a[2];
            xfx[2] = a[3] == 1 ? 0 : 1;
            xfx[3] = 1;
        }
    }
// TODO 乘法
    public static int[] multip(int a[], int b[])
    {
        //* 储存结果的系数
        int[] result = new int[4];
        for (int i = 0; i < 4; i++)
            result[i] = 0;

        //* 记录下x^nfx
        int xfx[] = {0,0,0,0};
        x_fx(xfx, a);
        int x2fx[] = {0,0,0,0};
        x_fx(x2fx, xfx);
        int x3fx[] = {0,0,0,0};
        x_fx(x3fx, x2fx);

        //* 现在需要根据多项式a和b开始异或
        if (b[0] == 1)
            for (int i = 0; i < 4; i++)
                result[i] ^= x3fx[i];
        if (b[1] == 1)
            for (int i = 0; i < 4; i++)
                result[i] ^= x2fx[i];
        if (b[2] == 1)
            for (int i = 0; i < 4; i++)
                result[i] ^= xfx[i];
        if (b[3] == 1)
            for (int i = 0; i < 4; i++)
                result[i] ^= a[i];
        return result;
    }

 static int[][] s = {
        {9, 4, 10, 11},
        {13, 1, 8, 5},
        {6, 2, 0, 3},
        {12, 14, 15, 7}
    };

    static int[][] ns = {
            {10, 5, 9, 11},
            {1, 7, 8, 15},
            {6, 2, 0, 3},
            {12, 4, 13, 14}
    };
 static int[][] swapto = {
        {0, 0, 0, 0},
        {0, 0, 0, 1},
        {0, 0, 1, 0},
        {0, 0, 1, 1},
        {0, 1, 0, 0},
        {0, 1, 0, 1},
        {0, 1, 1, 0},
        {0, 1, 1, 1},
        {1, 0, 0, 0},
        {1, 0, 0, 1},
        {1, 0, 1, 0},
        {1, 0, 1, 1},
        {1, 1, 0, 0},
        {1, 1, 0, 1},
        {1, 1, 1, 0},
        {1, 1, 1, 1}};

    //* 定义轮常数
    static int[] rcon1 = {1, 0, 0, 0, 0, 0, 0, 0};
    static int[] rcon2 = {0, 0, 1, 1, 0, 0, 0, 0};

    public static int[] OR_8bits(int []a, int []b)//8位的异或
    {
        int []t = new int[8];
        for (int i = 0; i < 8; i++)
            t[i] = a[i] ^ b[i];
        return t;
    }

    public static int[] OR_4bits(int []a, int []b)//4位的异或
    {
        int []t = new int[4];
        for (int i = 0; i < 4; i++)
            t[i] = a[i] ^ b[i];
        return t;
    }

    public static void Sbox_swap(int []temp) //使用s盒替换的函数，8位换
    {
        int t1 = 2 * temp[0] + temp[1];
        int t2 = 2 * temp[2] + temp[3];
        int t3 = 2 * temp[4] + temp[5];
        int t4 = 2 * temp[6] + temp[7];
        int tihuan1 = ns[t1][t2]; //记录替换后的数字
        int tihuan2 = ns[t3][t4];
        //* 四位四位进行替换
        for (int i = 0; i < 4; i++)
            temp[i] = swapto[tihuan1][i];
        for (int i = 0; i < 4; i++)
            temp[i + 4] = swapto[tihuan2][i];
    }
    public static void NSbox_swap(int []temp) //使用s盒替换的函数，8位换
    {
        int t1 = 2 * temp[0] + temp[1];
        int t2 = 2 * temp[2] + temp[3];
        int t3 = 2 * temp[4] + temp[5];
        int t4 = 2 * temp[6] + temp[7];
        int tihuan1 = s[t1][t2]; //记录替换后的数字
        int tihuan2 = s[t3][t4];
        //* 四位四位进行替换
        for (int i = 0; i < 4; i++)
            temp[i] = swapto[tihuan1][i];
        for (int i = 0; i < 4; i++)
            temp[i + 4] = swapto[tihuan2][i];
    }

    public static void leftMove(int[][] temp) {
        for (int i = 4; i < 8; i++) {
            int t = temp[0][i];
            temp[0][i] = temp[1][i];
            temp[1][i] = t;
        }
    }
    public static int [] Roundf(int []temp, int []rcon) // temp是一个八位的数组,rcon是轮常数
    {
        //! 注意这个temp是密钥，不能改动，要复制一个新的进行计算
        int []t = new int[8];
        for (int i = 0; i < 8; i++)
            t[i] = temp[i];
        //* 循环左移
        for (int i = 0; i < 4; i++)
        {
            int tt = t[i + 4];
            t[i + 4] = t[i];
            t[i] = tt;
        }

        //* 进行s盒替换
        Sbox_swap(t);

        //* 进行轮常数异或
        return OR_8bits(t, rcon);
    }

    static void listMix(int[][] mingwen)
    {
        int si_de2jinzhi[] = {0, 1, 0, 0};
        int []m00 = new int[4];
        int []m10 = new int[4];
        int []m01 = new int[4];
        int []m11 = new int[4];
        for (int i = 0; i < 4; i++)
        {
            m00[i] = mingwen[0][i];
            m10[i] = mingwen[0][i + 4];
            m01[i] = mingwen[1][i];
            m11[i] = mingwen[1][i + 4];
        }
        int []n00 = new int[4];
        int []n10 = new int[4];
        int []n01 = new int[4];
        int []n11 = new int[4];
        n00 = OR_4bits(m00, multip(si_de2jinzhi, m10));//乘法结果是1011
        n10 = OR_4bits(multip(si_de2jinzhi, m00), m10);//0101
        n01 = OR_4bits(m01, multip(si_de2jinzhi, m11));//0100
        n11 = OR_4bits(multip(si_de2jinzhi, m01), m11);//0010
        for (int i = 0; i < 4; i++)
        {
            mingwen[0][i] = n00[i];
            mingwen[0][i + 4] = n10[i];
            mingwen[1][i] = n01[i];
            mingwen[1][i + 4] = n11[i];
        }
    }
    static void n_listMix(int[][] plaintext)
    {
        int si_de2jinzhi[] = {0, 0, 1, 0};
        int si_de9jinzhi[] = {1, 0, 0, 1};
        int []m00 = new int[4];
        int []m10 = new int[4];
        int []m01 = new int[4];
        int []m11 = new int[4];
        for (int i = 0; i < 4; i++)
        {
            m00[i] = plaintext[0][i];
            m10[i] = plaintext[0][i + 4];
            m01[i] = plaintext[1][i];
            m11[i] = plaintext[1][i + 4];
        }
        int []n00 = new int[4];
        int []n10 = new int[4];
        int []n01 = new int[4];
        int []n11 = new int[4];
        n00 = OR_4bits(multip(si_de9jinzhi, m00), multip(si_de2jinzhi, m10));
        n10 = OR_4bits(multip(si_de2jinzhi, m00), multip(si_de9jinzhi, m10));
        n01 = OR_4bits(multip(si_de9jinzhi, m01), multip(si_de2jinzhi, m11));
        n11 = OR_4bits(multip(si_de2jinzhi, m01), multip(si_de9jinzhi, m11));
        for (int i = 0; i < 4; i++)
        {
            plaintext[0][i] = n00[i];
            plaintext[0][i + 4] = n10[i];
            plaintext[1][i] = n01[i];
            plaintext[1][i + 4] = n11[i];
        }
    }

    static void round_ec(int[][] mingwen, int[][] key)
    {
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 8; j++)
                mingwen[i][j] ^= key[i][j];
    }

    //用于输出
    static void shuchu(int[][] a)
    {
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 8; j++){
                System.out.print(a[i][j]);
            }
        }
        System.out.println("\n");
    }

    static int[][] Encrypt(int[][] plaintext,int[][] key){
        //* 密钥扩展算法，由于只有三轮加密，第一轮还只使用了原始key
        int[][]key1 = new int[2][8];
        int[][]key2 = new int[2][8];

        for (int i = 0; i < 2; i++) {
            key1[i] = new int[8];
            key2[i] = new int[8];
        }
        key1[0] = OR_8bits(key[0], Roundf(key[1], rcon1));
        key1[1] = OR_8bits(key1[0], key[1]);
        key2[0] = OR_8bits(key1[0], Roundf(key1[1], rcon2));
        key2[1] = OR_8bits(key2[0], key1[1]);
        //* 第0轮的轮密钥加
        round_ec(plaintext, key);
        //*第一轮
        //* 明文半字节代替
        Sbox_swap(plaintext[0]);
        Sbox_swap(plaintext[1]);
        //* 明文的行移位
        leftMove(plaintext);
        //* 明文的列混淆
        listMix(plaintext);
        round_ec(plaintext, key1);
        //*第二轮
        Sbox_swap(plaintext[0]);
        Sbox_swap(plaintext[1]);
        //* 明文的行移位
        leftMove(plaintext);
        //* 明文的轮密钥加
        round_ec(plaintext, key2);
        return plaintext;
    }

    static int[][] Decrypt(int[][] ciphertext,int[][] key){

        int[][]key1 = new int[2][8];
        int[][]key2 = new int[2][8];

        for (int i = 0; i < 2; i++) {
            key1[i] = new int[8];
            key2[i] = new int[8];
        }
        key1[0] = OR_8bits(key[0], Roundf(key[1], rcon1));
        key1[1] = OR_8bits(key1[0], key[1]);
        key2[0] = OR_8bits(key1[0], Roundf(key1[1], rcon2));
        key2[1] = OR_8bits(key2[0], key1[1]);
        //* 第0轮的轮密钥加
        round_ec(ciphertext, key2);
        leftMove(ciphertext);
        //*第一轮
        //* 明文半字节代替
        NSbox_swap(ciphertext[1]);
        NSbox_swap(ciphertext[0]);
        //* 明文的行移位
        round_ec(ciphertext, key1);
        n_listMix(ciphertext);
        leftMove(ciphertext);
        NSbox_swap(ciphertext[1]);
        NSbox_swap(ciphertext[0]);
        round_ec(ciphertext, key);
        return ciphertext;
    }
    static  int[][] StringtoIntArray28(String m){
        String input = m;
        int[][] result = new int[2][8];

        for (int i = 0; i < input.length(); i++) {
            int num = Integer.parseInt(input.substring(i, i + 1));
            result[i/8][i%8] = num;
        }


        return result;
    }
    static String IntArrayToString(int[][] intArray) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < intArray.length; i++) {
            for (int j = 0; j < intArray[i].length; j++) {
                sb.append(intArray[i][j]);
            }
        }
        return sb.toString();
    }
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
    public static void main(String[] args)
    {
        //* 输入明文和密钥
        int[][]mingwen = new int[2][8];
        int[][]key = new int[2][8];

        for (int i = 0; i < 2; i++) {
            mingwen[i] = new int[8];
        }
        for (int i = 0; i < 2; i++) {
            key[i] = new int[8];
        }
        /*
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print("Enter mingwen[" + i + "][" + j + "]: ");
                Scanner scanner = new Scanner(System.in);
                int mingwenValue = scanner.nextInt();
                mingwen[i][j] = mingwenValue;
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print("Enter key[" + i + "][" + j + "]: ");
                Scanner scanner = new Scanner(System.in);
                int keyValue = scanner.nextInt();
                key[i][j] = keyValue;
            }
        }*/
        String stringplaintext,stringkey;
        java.util.Scanner scan = new java.util.Scanner(System.in);
        System.out.println("请输入明文:");
        stringplaintext=scan.next();
        System.out.println("请输入密钥:");
        stringkey=scan.next();
        mingwen = StringtoIntArray28(stringplaintext);
        key = StringtoIntArray28(stringkey);
        int[][] miwen = Encrypt(mingwen,key);
        int[][] plaintext =Decrypt(miwen,key);
    }

}
