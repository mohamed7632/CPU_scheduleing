/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpuschedulting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 *
 * @author mahmoudsaeed
 */
// Data Structure 
// Gantt chart structure 
public class nonShortest {

//    class GChart {
//        // process number, start time, complete time, 
//        // turn around time, waiting time 
//
//        int pno, stime, ctime, wtime, ttime;
//    }
    //  comparative method (lower burst time serve first)
    class MyComparatorB implements Comparator {

        public int compare(Object o1, Object o2) {

            Process p1 = (Process) o1;
            Process p2 = (Process) o2;
            if (p1.bt < p2.bt) {
                return (-1);
            } else {
                return (1);
            }
        }
    }

    // user define comparative method (first arrival first serve, )
    class MyComparatorA implements Comparator {

        public int compare(Object o1, Object o2) {

            Process p1 = (Process) o1;
            Process p2 = (Process) o2;
            if (p1.at < p2.at) {
                return (-1);
            } else {
                return (1);
            }
        }
    }

    void findGc(ArrayList<cpuschedulting.Process> queue, int number) {

        // initial time = 0 
        int time = 0;

        // priority Queue sort data according 
        // burst time
        PriorityQueue<cpuschedulting.Process> qB
                = new PriorityQueue<Process>(number, new nonShortest.MyComparatorB());
        // Arrival time
        PriorityQueue<cpuschedulting.Process> qA
                = new PriorityQueue<Process>(number, new nonShortest.MyComparatorA());
        for (int i = 0; i < queue.size(); i++) {
            qA.add((Process) queue.get(i));
        }
        // Array List for store processes data 
        ArrayList<GChart> result = new ArrayList<>();
        // process in ready queue from new state queue 
        cpuschedulting.Process first = (qA.peek());
        qA.poll();
        GChart gcFirst = new GChart(); // to get the first process then below check other process
        gcFirst.pno = first.pno;
        time += first.at;
        gcFirst.stime = time;
        time += first.bt;
        int burstTime = first.bt;
        gcFirst.ctime = time;
        gcFirst.ttime = gcFirst.ctime - first.at;
        gcFirst.wtime = gcFirst.ttime - first.bt;
        /// store the exxtreted process 
        result.add(gcFirst);
        System.out.println(qA.size());
        // check that one of two queues is empty or both 
        while (qA.size() > 0 || qA.size() > 0) {
            int y = burstTime;
            int sizeA = qA.size();
            // loop to get the process that arrive before the burst time of the process before it
            for (int i = 0; i < sizeA; i++) {
                cpuschedulting.Process check = (qA.peek());
                if (check.at <= y) {
                    System.out.println(i + " " + check.pno);
                    qB.add(check);
                    burstTime += check.bt;
                } else {
                    qA.add(check);

                }
                qA.poll();
            }
            int sizeB = qB.size();
            // Working on the process ready for the processing
            for (int i = 0; i < sizeB; i++) {
                // dispatcher dispatch the 
                // process ready to running state 
                cpuschedulting.Process obj = qB.peek();
                qB.poll();
                GChart gc1 = new GChart();
                gc1.pno = obj.pno;
                gc1.stime = time;
                time += obj.bt;
                gc1.ctime = time;
                gc1.ttime = gc1.ctime - obj.at;
                gc1.wtime = gc1.ttime - obj.bt;
                /// store the exxtreted process 
                result.add(gc1);

            }
        }
        float avgWait = 0;
        float avgTTime = 0;
        for (int i = 0; i < result.size(); i++) {
            avgTTime += result.get(i).ttime;
            avgWait += result.get(i).wtime;
            System.out.println("pno "+result.get(i).pno + "  sTime  " + result.get(i).stime + "  cTime  " + result.get(i).ctime + "  tTime " + result.get(i).ttime + "  wTime  " + result.get(i).wtime);
        }
        System.out.println("avg turn around time  "+avgTTime / number + "  avg waiting time  " + avgWait / number);

    }
}
