package cpuschedulting;

import java.util.*;

// class to find Gantt chart 
class nonPriority {

// user define comparative method (first arrival first serve, 
// if arrival time same then heigh priority first) 
    class MyComparatorP implements Comparator {

        public int compare(Object o1, Object o2) {

            Process p1 = (Process) o1;
            Process p2 = (Process) o2;
            if (p1.pri < p2.pri) {
                return (-1);
            } else {
                return (1);
            }
        }
    }

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
        // to arrival time or priority (ready queue) 
        PriorityQueue<cpuschedulting.Process> qP
                = new PriorityQueue<Process>(number, new MyComparatorP());
        PriorityQueue<cpuschedulting.Process> qA
                = new PriorityQueue<Process>(number, new MyComparatorA());
        //iterating on the array list of processes
        //then add them to queue (qA) to be sorted by arrival time
        for (int i = 0; i < queue.size(); i++) {
            qA.add((Process) queue.get(i));
        }
        // Array List for store processes data
        //result
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
        //turn round time
        gcFirst.ttime = gcFirst.ctime - first.at;
        //waiting time 
        gcFirst.wtime = gcFirst.ttime - first.bt;
        /// store the exxtreted process 
        result.add(gcFirst);
        System.out.println(qA.size());
        while (qA.size() > 0 || qA.size() > 0) {
            int y = burstTime;
            int sizeA = qA.size();
            for (int i = 0; i < sizeA; i++) {
                cpuschedulting.Process check = (qA.peek());
                if (check.at <= y) {
                    System.out.println(i + " " + check.pno);
                    qP.add(check);
                    burstTime += check.bt;
                } else {
                    qA.add(check);

                }
                qA.poll();
            }
            int sizeP = qP.size();
            for (int i = 0; i < sizeP; i++) {
                // dispatcher dispatch the 
                // process ready to running state 
                cpuschedulting.Process obj = qP.peek();
                qP.poll();
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
//
//        // time set to according to first process 
//        time = first.at;
//
//        // scheduling process 
//        while (q.size() > 0) {
//            // dispatcher dispatch the 
//            // process ready to running state 
//            cpuschedulting.Process obj = q.peek();
//            q.poll();
//            GChart gc1 = new GChart();
//            gc1.pno = obj.pno;
//            gc1.stime = time;
//            time += obj.bt;
//            gc1.ctime = time;
//            gc1.ttime = gc1.ctime - obj.at;
//            gc1.wtime = gc1.ttime - obj.bt;
//            /// store the exxtreted process 
//            result.add(gc1);
//        }
        float avgWait = 0;
        float avgTTime = 0;
        for (int i = 0; i < result.size(); i++) {
            avgTTime+=result.get(i).ttime ;
            avgWait+= result.get(i).wtime ;      
            System.out.println("pno "+result.get(i).pno + "  sTime  " + result.get(i).stime + "  cTime  " + result.get(i).ctime + "  tTime " + result.get(i).ttime + "  wTime  " + result.get(i).wtime);
        }
                System.out.println("avg turn around time  "+avgTTime / number + "  avg waiting time  " + avgWait / number);


    }
}
