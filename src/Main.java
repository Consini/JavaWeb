/**
 * @Description TODO
 * @Author K
 * @Date 2019/11/4 23:06
 **/
public class Main {
        public static int fun(int n,int k){//1248
            if(n == 0){
                return 0;
            }

            if(n % 10 == k){
                return 1;//包含k
            }
            return fun(n / 10,k);
        }
        public static void main(String[] args) {
            int count = 0;
            //        for(int i = 1;i < 2000;i++){
//            if((i + "").indexOf("2") == -1 && (i + "").indexOf("4") == -1){
//                for(int j = i + 1;j < 2000;j++){
//                    if((j + "").indexOf("2") == -1 && (j + "").indexOf("4") == -1){
//                        int k = 2019 - i - j;
//                        if(k > 0 && k != j && k != i && (k + "").indexOf("2") == -1 && (k + "").indexOf("4") == -1){
//                            count++;
//                        }
//                    }
//                }
//            }
//        }
            for(int i = 1;i < 2000;i++){
                if(fun(i,2) == 0 && fun(i,4) == 0){
                    for(int j = 1;j < 2000;j++){
                        if(i != j && 0 == fun(j,2) && 0 == fun(j,4)){
                            int k = 2019 - i - j;
                            if(k > 0 &&  k != i && k != j && 0 == fun(k,2) && 0 == fun(k,4)){
                                count++;
                            }
                        }
                    }
                }
            }
            //System.out.println(count/6);
            System.out.println(count/3);
        }
}
