/*
        银行家算法实验，为了显示结果规整，把资源数固定为3 , 进程数为4 ，资源分配情况如教材所示
*/
import java.util.Scanner;

public class Bank{
        public int processNum; //进程数
        public int resourceNum;//资源数
        public int Max[][];
        public int Allocation[][];
        public int Available[];
        public int Need[][];
        
        
        public int Work[][];
        public Boolean Finish[];
        public int proceesList[]; //安全序列
        //初始化构造
        Bank(){
                processNum = 4;
                resourceNum = 3;
                
                proceesList = new int[]{0,1,2,3};
                
                Max = new int[4][3];
                Allocation = new int[4][3];
                Need = new int[4][3];
                Available = new int[]{1,1,2};
                
                Work = new int[processNum][resourceNum];
                Finish = new Boolean[processNum];
                
                Max[0] = new int[]{3,2,2};
                Max[1] = new int[]{6,1,3};
                Max[2] = new int[]{3,1,4};
                Max[3] = new int[]{4,2,2};
                
                Allocation[0] = new int[]{1,0,0};
                Allocation[1] = new int[]{5,1,1};
                Allocation[2] = new int[]{2,1,1};
                Allocation[3] = new int[]{0,0,2};
                
                Need[0] = new int[]{2,2,2};
                Need[1] = new int[]{1,0,2};
                Need[2] = new int[]{1,0,3};
                Need[3] = new int[]{4,2,0};
                

                //Need[1][2] = 4;
                //System.out.println("Initialize ok"+Need[1][2]);
        }
        //显示资源分配表
        public void show(){
                System.out.println("__________________________________________________");
                System.out.println("|  资源|   Max  | Allocation |  Need  | Available |");
                System.out.println("|进程  |R1 R2 R3|  R1 R2 R3  |R1 R2 R3|  R1 R2 R3 |");
                System.out.println("--------------------------------------------------");
                System.out.println("|      |        |            |        | "+Available[0]+"   "+Available[1]+"   "+Available[2]+" |");
                for(int j=0;j<processNum;j++){

                        int i = proceesList[j];
                        System.out.println("|  P"+(i+1)+"  | "+Max[i][0]+"  "+Max[i][1]+"  "+Max[i][2]+
                        "|   "+Allocation[i][0]+"  "+Allocation[i][1]+"  "+Allocation[i][2]+
                        "  | "+Need[i][0]+"  "+Need[i][1]+"  "+Need[i][2]+
                        "|           |");
                }
                System.out.println("---------------------------------------------------");
        }
        
        // 资源申请
        public void request(int p,int r1,int r2,int r3){
                int step = 1;
                if(r1<=Need[p-1][0] && r2<=Need[p-1][1] && r3<=Need[p-1][2]){
                        step = 2;
                }else{
                        System.out.println("进程所需要的资源数目超过它所宣布的最大值");
                        return;
                }
                if(step == 2 && r1<=Available[0] && r2<=Available[1] && r3<=Available[2]){
                        step=3;
                }else{
                        System.out.println("系统中无足够的资源满足进程申请");
                        return;
                }
                if(step == 3){
                        Available[0] -=r1; 
                        Available[1] -=r2; 
                        Available[2] -=r3; 
                        
                        Allocation[p-1][0]+=r1;
                        Allocation[p-1][1]+=r2;
                        Allocation[p-1][2]+=r3;
                        
                        Need[p-1][0]-=r1;
                        Need[p-1][1]-=r2;
                        Need[p-1][2]-=r3;
                }
                //
                
                System.out.println("P"+p+":("+r1+","+r2+","+r3+") 申请资源后的资源分配表");
                show();
                System.out.println("检查P"+p+" "+Available[0]+Available[1]+Available[2]);
                if(check() == true){
                        System.out.println("检查通过，找到安全序列");
                        for(int i=0;i<processNum;i++){
                                System.out.print("->"+(proceesList[i]+1));
                        }
                        System.out.println("");
                }else{
                        System.out.println("检查不通过，回退分配操作");
                        Available[0] +=r1; 
                        Available[1] +=r2; 
                        Available[2] +=r3; 
                        
                        Allocation[p-1][0]-=r1;
                        Allocation[p-1][1]-=r2;
                        Allocation[p-1][2]-=r3;
                        
                        Need[p-1][0]+=r1;
                        Need[p-1][1]+=r2;
                        Need[p-1][2]+=r3;
                }
        }
        /*安全检查*/
        public Boolean check(){
                
//                int templist[] = new int[processNum];
//                // 进程顺序重新排序
//                for(int i=0;i<processNum;i++){ templist[i] = proceesList[i];}; //复制一个备份
//                int j=1;
//                templist[0] = p-1;
//                for(int i=0;i<processNum;i++){//重新排序，把当前进程放到第一个
//                        if(proceesList[i]!=p-1){
//                                templist[j] = proceesList[i];
//                                j++;
//                        }      
//                }
//                // 进程顺序重新排序结束
                
                for(int i=0;i<processNum;i++){Finish[i]=false;};//初始化finish
                
                int workTemp[] = new int[]{Available[0], Available[1], Available[2]};
                Boolean flag = true;
                int j = 0;
                //System.out.println(" workTemp"+workTemp[0]+workTemp[1]+workTemp[2]);
                while(flag){
                        flag = false;
                        //if(j==processNum-1)break;
                        for(int i=0;i<processNum;i++){
                                if(Finish[i] == false && Need[i][0] <= workTemp[0] && Need[i][1] <= workTemp[1] && Need[i][2] <= workTemp[2]){
                                        //System.out.println(" workTemp"+workTemp[0]+workTemp[1]+workTemp[2]);
                                        Work[i][0] = workTemp[0];
                                        Work[i][1] = workTemp[1];
                                        Work[i][2] = workTemp[2];
                                        
                                        flag = true;
                                        Finish[i] = true;
                                        //System.out.println("P"+(i+1)+" :true j="+j);
                                        proceesList[j]=i;
                                        j++;
                                        workTemp[0]=Work[i][0]+Allocation[i][0];
                                        workTemp[1]=Work[i][1]+Allocation[i][1];
                                        workTemp[2]=Work[i][2]+Allocation[i][2];
                                        
                                        //System.out.println("Work "+i+" "+Work[i][0]+Work[i][1]+Work[i][2]);
                                        //System.out.println(" workTemp"+workTemp[0]+workTemp[1]+workTemp[2]);
                                        //System.out.println(" Need1 "+Need[0][0]+Need[0][1]+Need[0][2]+Finish[0]);
                                        break;
                                }
                        }
                }
                //System.out.println(""+Work[1][0]+Work[1][1]+Work[1][2]);
                //for(int i=0;i<processNum;i++){ proceesList[i] = templist[i];};
                
                System.out.println("申请资源后的安全检查");
                System.out.println("______________________________________________________________________");
                System.out.println("|  资源|   Work |    Need    |  Allocation  | Work+Allocation |       ");
                System.out.println("|进程  |R1 R2 R3|  R1 R2 R3  |  R1  R2  R3  |     R1  R2 R3   | Finish");
                System.out.println("-----------------------------------------------------------------------");

                for(j=0;j<processNum;j++){

                        int i = proceesList[j];
                        System.out.println("|  P"+(i+1)+"  | "+Work[i][0]+"  "+Work[i][1]+"  "+Work[i][2]+
                        "|   "+Need[i][0]+"  "+Need[i][1]+"  "+Need[i][2]+
                        "  |  "+Allocation[i][0]+"   "+Allocation[i][1]+"   "+Allocation[i][2]+
                        "   |      "+(Work[i][0]+Allocation[i][0])+"   "+(Work[i][1]+Allocation[i][1])+"   "+(Work[i][2]+Allocation[i][2])+
                        "  | "+Finish[i]+"  ");
                }
                System.out.println("----------------------------------------------------------------------");
                if(j==processNum-1)return false;
                return true;
        }
        
        /* 主函数*/
        public static void main(String[] args){
                Scanner scn = new Scanner(System.in);
                int p,r1,r2,r3;
                Bank bank = new Bank();
                bank.check();
                bank.show();
                p=scn.nextInt();
                while(p>0){
                        r1 = scn.nextInt();
                        r2 = scn.nextInt();
                        r3 = scn.nextInt();
                        bank.request(p,r1,r2,r3);
                        p=scn.nextInt();
                }
        }
}
