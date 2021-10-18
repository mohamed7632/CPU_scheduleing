/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpuschedulting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 *
 * @author COMPU1
 */
public class SRTF {

    class MyComparatorA implements Comparator {

        public int compare(Object o1, Object o2) {

            Process p1 = (Process) o1;
            Process p2 = (Process) o2;
            if (p1.at < p2.at) {
                return (-1);
            } else if (p1.at == p2.at && p1.bt < p2.bt) {
                return (-1);

            } else {
                return (1);
            }
        }
    }

    class MyComparatorP implements Comparator {

        public int compare(Object o1, Object o2) {

            Process p1 = (Process) o1;
            Process p2 = (Process) o2;

            if (p1.bt == p2.bt && p1.at < p2.at) {
                return (-1);

            } else if (p1.bt < p2.bt) {
                return (-1);
            } else {
                return (1);
            }
        }
    }

    class Sortbyroll implements Comparator<GChart> {
        // Used for sorting in ascending order of 
        // roll number 

        public int compare(GChart a, GChart b) {
            return a.pno - b.pno;
        }
    }

    void shortestRemainingTimeFirst(ArrayList<cpuschedulting.Process> input, int number, int contextSwitchingTime) {

        ArrayList<GChart> result = new ArrayList<>();
         ArrayList<GChart> exceutionTime = new ArrayList<>();
        PriorityQueue<cpuschedulting.Process> qA
                = new PriorityQueue<Process>(number, new SRTF.MyComparatorA());
        PriorityQueue<cpuschedulting.Process> qP
                = new PriorityQueue<Process>(number, new SRTF.MyComparatorP());
        //put the processes into qA queue to sort them according to arrival time         
        for (int i = 0; i < input.size(); i++) {
            qA.add((Process) input.get(i));
        }

        int time = 0;
        //process 2 used to carry the current process if terminated 
        cpuschedulting.Process Proces2 = null;
        cpuschedulting.Process first = (qA.peek());
        int startTime = 0;
        // used to determine the last process exceute before the 
        //queue(qA) be empty
        cpuschedulting.Process lastcurrentProces = null;
        while (qA.size() > 0) {
            GChart newObj = new GChart();
            cpuschedulting.Process process = (qA.peek());
            //process1 is used to carry the last current process 
            //before adding the new process in this loop only
            cpuschedulting.Process Proces1 = new cpuschedulting.Process();
            //if the process is the first process
            if (process.pno == first.pno) {
                Proces1.pno = -1;
                time = first.at;
        
            }
            //put the last current process in proces1
            //before add the new one
            else if (process.pno != first.pno) {
                Proces1 = qP.peek();
            }
            //adding proces to the ready queue
            if (time >= process.at) {
                qP.add(process);
                qA.poll();
            }

            //-------------------------------------------------
            if (time == process.at && process.bt > 0) {
                   
                cpuschedulting.Process shortestProcess = qP.peek();
                lastcurrentProces = shortestProcess;
                //if the current process terminated
                if (Proces2 != null) {
                 
                    Proces2 = null;
                    exceutionTime.add(newObj);
                    time += contextSwitchingTime;
                    if (shortestProcess.st == 0) {
                        startTime = time;
                        shortestProcess.st = startTime;
                 
                    }
                } else if (Proces2 == null && Proces1.pno != shortestProcess.pno) {
                    //System.out.println("==,  null, pno " + shortestProcess.pno + " its at time is " + time);
                    if (shortestProcess.pno != first.pno && shortestProcess.st == 0) {
                        time += contextSwitchingTime;
                        startTime = time;
                        shortestProcess.st = startTime;
                  
                    }
                 

                }
                   newObj.pno=shortestProcess.pno;
                    newObj.stime=time;    
                //if the coming proces is already the current one directly pass here
                //and if it the first process
                shortestProcess.bt--;
                time++;
                newObj.ctime=time;
                exceutionTime.add(newObj);
                //updating the current process and
                //store it again in the queue
                if (shortestProcess.bt > 0) {
                    qP.poll();
                    qP.add(shortestProcess);

                } else if (shortestProcess.bt == 0) {
                   
                    Proces2 = shortestProcess;
                    qP.poll();
                    GChart obj1 = new GChart();
                    obj1.pno = shortestProcess.pno;
                    obj1.stime = startTime;
                    obj1.ctime = time+contextSwitchingTime;;
                    obj1.ttime = obj1.ctime - shortestProcess.at;
                    obj1.wtime = obj1.stime - shortestProcess.at;
                    result.add(obj1);

                }

            } //-----------------------------
            else if (time != process.at) {

                if (qP.size() != 0) {

                    cpuschedulting.Process shortestProcess = qP.peek();
                    lastcurrentProces = shortestProcess;
                    //if the current process terminated
                    if (Proces2 != null) {
                        //System.out.println(" !=,not null, pno " + shortestProcess.pno + "at time: " + time);
                        Proces2 = null;
                        time += contextSwitchingTime;
                        if (shortestProcess.st == 0) {
                           // System.out.println("!=,pno again1 " + shortestProcess.pno);
                            startTime = time;
                            shortestProcess.st = startTime;
                             
                        }
                              
                    } //if the current process is not the new process
                    else if (Proces2 == null && Proces1.pno != shortestProcess.pno) {
                         time += contextSwitchingTime;
                              if (shortestProcess.st == 0) {
                           // System.out.println("!=,pno again2" + shortestProcess.pno);
                            startTime = time;
                            shortestProcess.st = startTime;
                                                    }
                     
                                    }
                    newObj.pno=shortestProcess.pno;
                     newObj.stime=time;
                    //if the coming proces is already the current one pass here direct  
                     shortestProcess.bt--;
                     time++;
                     newObj.ctime=time;
                     exceutionTime.add(newObj);
                    if (shortestProcess.bt > 0) {
                        qP.poll();
                        qP.add(shortestProcess);
                    } else if (shortestProcess.bt == 0) {
             
                        //pop the peek proces from qP queue
                        Proces2 = shortestProcess;
                        qP.poll();
                        GChart obj1 = new GChart();
                        obj1.pno = shortestProcess.pno;
                        obj1.stime = shortestProcess.st;
                        obj1.ctime = time+contextSwitchingTime;
                        obj1.ttime = obj1.ctime - shortestProcess.at;
                        obj1.wtime = obj1.stime - shortestProcess.at;
                        result.add(obj1);
                    }

                }

            }

        }
        //-------------------------------
        if (qP.size() > 0) {
        
            while (qP.size() > 0) {
                  GChart newObj = new GChart();
                cpuschedulting.Process shortestProcess = qP.peek();
                if (lastcurrentProces.pno != shortestProcess.pno) {
                    time += contextSwitchingTime;
                }
                //update the last current proces
                lastcurrentProces = shortestProcess;
                //if  coming proces is the first time to be exceuted then set its startime  
                if (shortestProcess.st == 0 && shortestProcess.pno != first.pno) {

                    startTime = time;
                    shortestProcess.st = startTime;
                }
                    newObj.pno=shortestProcess.pno;
                    newObj.stime=time;
                    
                while (shortestProcess.bt != 0) {
                    shortestProcess.bt--;
                    time++;
                }
                    

                if (shortestProcess.bt == 0) {
                    newObj.ctime=time;
                    exceutionTime.add(newObj);
                    qP.poll();
                    GChart obj = new GChart();
                    obj.pno = shortestProcess.pno;
                    obj.stime = shortestProcess.st;
                    obj.ctime = time+contextSwitchingTime;
                    obj.ttime = obj.ctime - shortestProcess.at;
                    obj.wtime = obj.stime - shortestProcess.at;
                    result.add(obj);

                }
            }
        }

//        Collections.sort(result, new SRTF.Sortbyroll());

for(int i=0;i<exceutionTime.size();i++){
    System.out.println("pno "+exceutionTime.get(i).pno+" start time "+exceutionTime.get(i).stime+" completion time "+exceutionTime.get(i).ctime);
    
}
System.out.println();
System.out.println();
        float waitAvg = 0;
        float turnAvg = 0;
        for (int i = 0; i < result.size(); i++) {
            System.out.println("pno " + result.get(i).pno + " start time" + result.get(i).stime + "  completion time" + result.get(i).ctime + "  turn arround " + result.get(i).ttime + " waiting time " + result.get(i).wtime);
            waitAvg+=result.get(i).wtime;
            turnAvg+=result.get(i).ttime;
        }
        System.out.println("avg waiting time  "+waitAvg/number);
        System.out.println("avg turn around time  "+turnAvg/number);

    }

}
