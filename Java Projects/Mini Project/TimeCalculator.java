import java.util.Scanner;
public class TimeCalculator {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("enter the day");
        int day = in.nextInt();
        System.out.println("enter the present time (hrs)");
        int timehour = in.nextInt();
        System.out.println("enter the present time (m)");
        int timeminutes = in.nextInt();
        System.out.println("enter the present time (s)");
        int timeseconds = in.nextInt();
        System.out.println("enter the time to be calculated (hrs)");
        int timehr = in.nextInt();
        System.out.println("enter the time to be calculated (m)");
        int timem = in.nextInt();
        System.out.println("enter the time to be calculated (s)");
        int times = in.nextInt();
        timehr += timehour;
        timem  += timeminutes;
        times += timeseconds;
        while ( times > 59){
            times -= 60;
            timem++;
        }
        while ( timem > 59){
            timem -= 60;
            timehr++;
        }
        while (timehr >= 24 ){
            timehr -= 24;
            day++;
        }
       System.out.println( "the day is " + day);
       if (timehr >= 12){
            timehr -= 12;
           System.out.println("the time is " + timehr + ":" + timem + ":" + times + "p.m");
        } else {
            System.out.println( "the time is " + timehr + ":" + timem + ":" + times + "a.m");
          }
    }
}
