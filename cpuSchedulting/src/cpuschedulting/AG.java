/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpuschedulting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;

/**
 *
 * @author mahmoudsaeed
 */
public class AG {

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

    class MyComparatorF implements Comparator {

        public int compare(Object o1, Object o2) {

            Process p1 = (Process) o1;
            Process p2 = (Process) o2;
            if (p1.factor < p2.factor) {
                return (-1);
            } else {
                return (1);
            }
        }
    }

    class MyComparatorN implements Comparator {

        public int compare(Object o1, Object o2) {
            return 0;
        }
    }

    class Sortbyroll implements Comparator<cpuschedulting.Process> {
        // Used for sorting in ascending order of 
        // roll number 

        public int compare(cpuschedulting.Process a, cpuschedulting.Process b) {
            return a.factor - b.factor;
        }
    }

    cpuschedulting.Process getSmallestProces(ArrayList<cpuschedulting.Process> list) {
        ArrayList<cpuschedulting.Process> newList = new ArrayList<>();
        for (cpuschedulting.Process tempProces : list) {
            newList.add(tempProces);

        }
        Collections.sort(newList, new Sortbyroll());
        return newList.get(0);
    }

    void displayQueue(ArrayList<cpuschedulting.Process> add1, ArrayList<cpuschedulting.Process> addRemaining, ArrayList<GChart> result, int time) {

        cpuschedulting.Process proces = new cpuschedulting.Process();

        // boolean check=false;
        proces = add1.get(0);
        while (!add1.isEmpty()) {
            
            GChart gcFirst = new GChart();
            //adjust the quantum to its half 
            int quantum = 0;
            if (proces.q % 2 == 0) {
                quantum = (proces.q / 2);
            } else {
                quantum = ((proces.q + 1) / 2);
            }
            //if the remaining burst time of the current process less than quantum
            //then adjust the burst time to zero then increment time by the remaining burst time
            gcFirst.pno = proces.pno;
            gcFirst.stime = time;
            if (proces.bt < quantum) {
                //System.out.println("proces pno is "+proces.pno);
                
                time += proces.bt;
                proces.bt = 0;

            } else {
                proces.bt -= quantum;
                time += quantum;
            }
            // if the proces terminated after excuting it's first half quantam
            //remove it from the arraylist:add1(ready queue)
            //then get the first proces to be excuted
            if (proces.bt == 0) {
                //System.out.println("proces pno is "+proces.pno);
                // System.out.println("proces "+proces.pno);
                //completion time
                gcFirst.ctime = time;
                result.add(gcFirst);
                System.out.println("quataum for process" + proces.pno + " " + proces.q + " ");
                add1.remove(proces);
                System.out.println("quataum for process" + proces.pno + "  0" );
                proces = add1.get(0);
            } //if the proces has a job to complete and not terminated
            //check at first if there is a aproces in the ready queue has
            // a AG factor smaller than the current one 
            else if (proces.bt != 0) {
                int quantumRemaining = proces.q - quantum;
                if (getSmallestProces(add1).pno == proces.pno) {
                    int j = 0;
                    while (j < quantumRemaining) {
                        //if it use its quantam
                        proces.bt--;
                        time++;
                        if (j == quantumRemaining - 1) {
                            if (proces.bt > 0) {
                                //ceil to 10% of its quantam
                                //  System.out.println("proces pno is "+proces.pno);
                                System.out.println("quataum for process" + proces.pno + " " + proces.q + " ");
                                add1.remove(proces);
                                proces.q += 1;
                                gcFirst.ctime = time;
                                result.add(gcFirst);
                                add1.add(proces);
                                proces = add1.get(0);
                            } else if (proces.bt == 0) {
                                //completion time
                                // System.out.println("proces pno is "+proces.pno);

                                System.out.println("quataum for process" + proces.pno + " " + proces.q + " ");
                                add1.remove(proces);
                                gcFirst.ctime = time;
                                result.add(gcFirst);
                                if (add1.size() != 0) {
                                    proces = add1.get(0);
                                } else {
                                    System.out.println("quataum for process" + proces.pno + " " +"0 ");
                                    break;
                                }

                            }
                        } else if (j != quantumRemaining - 1) {

                            if (proces.bt == 0) {
                                //completion time
                                System.out.println("quataum for process" + proces.pno + " " + proces.q + " ");
                                add1.remove(proces);
                                gcFirst.ctime = time;
                                result.add(gcFirst);
                                if (add1.size() != 0) {
                                    proces = add1.get(0);
                                } else {
                                    break;
                                }

                            }
                        }
                        j++;
                    }

                } else if (getSmallestProces(add1).pno != proces.pno) {
                    // System.out.println("proces pno is  "+proces.pno);
                    System.out.println("quataum for process" + proces.pno + " " + proces.q + " ");
                    add1.remove(proces);
                    gcFirst.ctime = time;
                    result.add(gcFirst);
                    proces.q += quantumRemaining;
                    add1.add(proces);
                    if (add1.size() != 0) {
                        proces = getSmallestProces(add1);
                    } else {
                        break;
                    }

                }

            }
        }
        if (addRemaining.size() > 1) {
            findGc(addRemaining, addRemaining.size(), time);

        } else {
            GChart gcFirst = new GChart();
            gcFirst.pno = addRemaining.get(0).pno;
            gcFirst.stime = time;
            time += addRemaining.get(0).bt;
            gcFirst.ctime = time;
            gcFirst.bt = addRemaining.get(0).bt;
            System.out.println("quataum for process" + addRemaining.get(0).pno + " " +"0");
            result.add(gcFirst);
        }
    }

    void findGc(ArrayList<cpuschedulting.Process> queue, int number, int time) {

        ArrayList<GChart> result = new ArrayList<>();
        PriorityQueue<cpuschedulting.Process> qA
                = new PriorityQueue<cpuschedulting.Process>(number, new AG.MyComparatorA());
        for (int i = 0; i < queue.size(); i++) {
            queue.get(i).factor = queue.get(i).bt + queue.get(i).at + queue.get(i).pri;
            System.out.println(queue.get(i).at + " " + queue.get(i).bt + " " + queue.get(i).pri + " " + queue.get(i).factor);
            qA.add(queue.get(i));
        }
        ArrayList<cpuschedulting.Process> process = new ArrayList<>();
        ArrayList<cpuschedulting.Process> add = new ArrayList<>();
        ArrayList<cpuschedulting.Process> addRemaining = new ArrayList<>();
        int queueSize = qA.size();
        for (int i = 0; i < queueSize; i++) {
            process.add(qA.peek());
            System.out.print("quataum for process" + qA.peek().pno + " " + qA.peek().q + " ");
            qA.poll();
        }
        System.out.println();
        int size = process.size();
        cpuschedulting.Process first = process.get(0);
        if(time==0){
            time += first.at;
        }
        for (int i = 0; i < size; i++) {
            first = process.get(i);
//            System.out.println(process.get(i).pno);
            GChart gcFirst = new GChart(); // to get the first process then below check other process
            gcFirst.pno = first.pno;
            gcFirst.bt = first.bt;
            gcFirst.stime = time;
            int quantum = 0;
            //to handel the quantum  to get the 50% first 
            if (first.q % 2 == 0) {
                quantum = (first.q / 2);
                first.bt -= quantum;
                time += quantum;
            } else {
                quantum = (first.q + 1 / 2);
                first.bt -= quantum;
                time += quantum;
            }
            gcFirst.ctime = time;
            /// store the exxtreted process 
            result.add(gcFirst);
            // quantum  - the burst that we used 
            int j = 0;
            if (i != size - 1) {
                int quantumRemaining = first.q - quantum;
                gcFirst.pno = first.pno;
                while (j < quantumRemaining) {
                    if (process.get(i + 1).at > gcFirst.ctime) {
                        first.bt--;
                        time++;
                        gcFirst.ctime = time;
                        if (j == quantumRemaining - 1) {
                            if (first.bt > 0) {
                                //ceil to 10% of its quantam
                                first.q += 1;
                                add.add(first);
                                System.out.println("quataum for process" + first.pno + " " + first.q + " ");
                                for (int k = i + 1; k < size; k++) {
                                    addRemaining.add(process.get(k));
                                }
                                i = size;
                                displayQueue(add, addRemaining, result, time);
                            }
                        }
                        j++;
                    } else if (process.get(i + 1).at <= gcFirst.ctime) {
                        if (first.bt > 0) {
                            first.q += (quantumRemaining - j);
                            add.add(first);
                        }
                        System.out.println("quataum for process" + first.pno + " " + first.q + " ");
                        j = quantumRemaining;

                    }

                }

            } else {
                addRemaining.add(process.get(i));
                displayQueue(add, addRemaining, result, time);
            }
        }
        ArrayList<GChart> agResult = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            boolean bool = true;
            GChart gcFirst = new GChart();
            for (int j = result.size() - 1; j >= i; j--) {
                if (result.get(j).pno == result.get(i).pno) {
                    gcFirst.pno = result.get(i).pno;
                    gcFirst.ttime = result.get(j).ctime - result.get(i).stime;
                    gcFirst.wtime = gcFirst.ttime - result.get(i).bt;
                    break;
                }
            }
            for (int j = 0; j < agResult.size(); j++) {
                if (result.get(i).pno == agResult.get(j).pno) {
                    bool = false;
                }
            }
            if (bool) {
                agResult.add(gcFirst);
            }

        }
        System.out.println("Processes execution order");
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i).pno + " " + result.get(i).stime + " " + result.get(i).ctime + " ");
        }
        float avgWait = 0;
        float avgTTime = 0;
        System.out.println("Processes Waiting Time and Turnaround Time");
        for (int i = 0; i < agResult.size(); i++) {
            avgTTime += agResult.get(i).ttime;
            avgWait += agResult.get(i).wtime;
            System.out.println("pno "+result.get(i).pno + "  sTime  " + result.get(i).stime + "  cTime  " + result.get(i).ctime + "  tTime " + result.get(i).ttime + "  wTime  " + result.get(i).wtime);
        }
        System.out.println(avgTTime / number + "  " + avgWait / number);
    }

}
