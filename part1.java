import java.util.*;
import java.util.Locale;

public class Part1 {

    private int currentPosition;
    private List<Request> requests;
    private final int totalCylinders = 65535;
    private final double seekTimePer4000Cylinders = 1.0;
    private final double timeToStartStop = 1.0;
    private final double rotationalLatency = 4.17;
    private final double transferTime = 0.13;

    static class Request {
        int cylinder;
        int arrivalTime;

        Request(int cylinder, int arrivalTime) {
            this.cylinder = cylinder;
            this.arrivalTime = arrivalTime;
        }
    }

    public Part1(int initialHeadPosition, List<Request> requests) {
        this.currentPosition = initialHeadPosition;
        this.requests = requests;
    }

    // محاسبه زمان جستجو برای حرکت هد
    private double calculateSeekTime(int start, int end) {
        int cylinderDifference = Math.abs(start - end);
        return (cylinderDifference / 4000.0) * seekTimePer4000Cylinders + timeToStartStop;
    }

    // الگوریتم آسانسور
    public List<Double> elevatorAlgorithm() {
        List<Request> upRequests = new ArrayList<>();
        List<Request> downRequests = new ArrayList<>();

        for (Request req : requests) {
            if (req.cylinder > currentPosition) {
                upRequests.add(req);
            } else if (req.cylinder < currentPosition) {
                downRequests.add(req);
            }
        }

        // مرتب‌سازی درخواست‌ها بر اساس سیلندر
        upRequests.sort(Comparator.comparingInt(req -> req.cylinder));
        downRequests.sort((req1, req2) -> Integer.compare(req2.cylinder, req1.cylinder));

        List<Request> allRequests = new ArrayList<>();
        allRequests.addAll(upRequests);
        allRequests.addAll(downRequests);

        List<Double> responseTimes = new ArrayList<>();
        double totalTime = 0;

        for (Request req : allRequests) {
            double seekTime = calculateSeekTime(currentPosition, req.cylinder);
            totalTime += seekTime + rotationalLatency + transferTime;
            responseTimes.add(totalTime);
            currentPosition = req.cylinder;
        }

        return responseTimes;
    }

    //  FCFS
    public List<Double> fcfsAlgorithm() {
        List<Double> responseTimes = new ArrayList<>();
        double totalTime = 0;

        for (Request req : requests) {
            double seekTime = calculateSeekTime(currentPosition, req.cylinder);
            totalTime += seekTime + rotationalLatency + transferTime;
            responseTimes.add(totalTime);
            currentPosition = req.cylinder;
        }

        return responseTimes;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the initial position of the disk head and the requests:");

        int initialHeadPosition = scanner.nextInt();

        int k = scanner.nextInt();

        List<Request> requests = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            int cylinder = scanner.nextInt();
            int arrivalTime = scanner.nextInt();
            requests.add(new Request(cylinder, arrivalTime));
        }

        Part1 scheduler = new Part1(initialHeadPosition, requests);

        List<Double> elevatorTimes = scheduler.elevatorAlgorithm();
        System.out.println("\nElevator Algorithm Results:");
        for (int i = 0; i < elevatorTimes.size(); i++) {
            System.out.printf(Locale.ENGLISH, "%d %.1f\n", requests.get(i).cylinder, elevatorTimes.get(i));
        }

        // بازنشانی موقعیت هد برای الگوریتم FCFS
        scheduler = new Part1(initialHeadPosition, requests);

        // اجرای الگوریتم FCFS
        List<Double> fcfsTimes = scheduler.fcfsAlgorithm();
        System.out.println("\nFCFS Algorithm Results:");
        for (int i = 0; i < fcfsTimes.size(); i++) {
            System.out.printf(Locale.ENGLISH, "%d %.1f\n", requests.get(i).cylinder, fcfsTimes.get(i));
        }

        scanner.close();
    }
}
