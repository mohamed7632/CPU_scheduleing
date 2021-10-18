/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpuschedulting;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author mahmoudsaeed
 */
class Process {

    int at,st, bt, pri, pno,factor,q;
    public Process(){
        at=0;
        st=0;
        bt=0;
    }
    @Override
    public String toString() {
        return "Process{" + "at=" + at + ", st=" + st + ", bt=" + bt + ", pno=" + pno + '}';
    }

}

public class CpuScheduling {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner in = new Scanner(System.in);
        ArrayList<Process> nonpriority = new ArrayList<>();
        System.out.println("number of process");
        int number = in.nextInt();
        System.out.println("write like that");
        System.out.println("p1 ' ' AT ' ' BT");
        for (int i = 0; i < number; i++) {
            Process p = new Process();
            p.pno = in.nextInt();
            p.at = in.nextInt();
            p.bt = in.nextInt();
            nonpriority.add(p);
        }
        in.nextLine();
        while (true) {
            System.out.println("1-Non-Preemptive Shortest- Job First");
            System.out.println("2-Shortest- Remaining Time First (SRTF)");
            System.out.println("3-Non-preemptive Priority");
            System.out.println("4-AG Scheduling");
            int x = in.nextInt();
            if (x == 1) {
                nonShortest shortest = new nonShortest();
                shortest.findGc(nonpriority, number);
                
            } else if (x == 2) {
                
                System.out.println(" enter your context switching Time");
                int contextSwitchingTime=in.nextInt();
                SRTF srtf=new SRTF();
                srtf.shortestRemainingTimeFirst(nonpriority, number,contextSwitchingTime);

            } else if (x == 3) {
                nonPriority priority = new nonPriority();
                System.out.println("enter priority for each process");
                for (int i = 0; i < number; i++) {
                    nonpriority.get(i).pri = in.nextInt();
                }
                priority.findGc(nonpriority, number);
            }  else if (x == 4) {
                System.out.println("write the quantum ");
                int quantum = in.nextInt();
                System.out.println("enter priority for each process");
                for (int i = 0; i < number; i++) {
                    nonpriority.get(i).q = quantum;
                    nonpriority.get(i).pri = in.nextInt();
                }
                AG ag = new AG();
                int time=0;
                ag.findGc(nonpriority, number,time);
            } else {
                break;
            }
        }

    }

}
