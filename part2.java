import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Part2 {

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

    public Part2(int initialHeadPosition, List<Request> requests) {
        this.currentPosition = initialHeadPosition;
        this.requests = requests;
    }

    private double calculateSeekTime(int start, int end) {
        int cylinderDifference = Math.abs(start - end);
        return (cylinderDifference / 4000.0) * seekTimePer4000Cylinders + timeToStartStop;
    }

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

        upRequests.sort(Comparator.comparingInt(req -> req.cylinder));
        downRequests.sort((req1, req2) -> Integer.compare(req2.cylinder, req1.cylinder));

        List<Request> allRequests = new ArrayList<>();
        allRequests.addAll(upRequests);
        allRequests.addAll(downRequests);

        List<Double> responseTimes = new ArrayList<>();
        double totalTime = 0;

        // پردازش درخواست‌ها
        for (Request req : allRequests) {
            double seekTime = calculateSeekTime(currentPosition, req.cylinder);
            totalTime += seekTime + rotationalLatency + transferTime;
            responseTimes.add(totalTime);
            currentPosition = req.cylinder; // حرکت هد به موقعیت جدید
        }

        return responseTimes;
    }

    // الگوریتم FCFS
    public List<Double> fcfsAlgorithm() {
        List<Double> responseTimes = new ArrayList<>();
        double totalTime = 0;

        // پردازش درخواست‌ها به ترتیب ورود
        for (Request req : requests) {
            double seekTime = calculateSeekTime(currentPosition, req.cylinder);
            totalTime += seekTime + rotationalLatency + transferTime;
            responseTimes.add(totalTime);
            currentPosition = req.cylinder; // حرکت هد به موقعیت جدید
        }

        return responseTimes;
    }

    public static List<Request> generateRandomRequests(int numRequests) {
        List<Request> randomRequests = new ArrayList<>();
        for (int i = 0; i < numRequests; i++) {
            // سیلندر تصادفی بین 0 تا 65535
            int cylinder = ThreadLocalRandom.current().nextInt(0, 65536);
            int arrivalTime = ThreadLocalRandom.current().nextInt(0, 100);
            randomRequests.add(new Request(cylinder, arrivalTime));
        }
        return randomRequests;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the initial position of the disk head:");

        int initialHeadPosition = scanner.nextInt();

        System.out.println("Enter the number of random requests:");
        int numRequests = scanner.nextInt();

        // تولید درخواست‌های تصادفی
        List<Request> requests = generateRandomRequests(numRequests);

        Part2 scheduler = new Part2(initialHeadPosition, requests);

        // اجرای الگوریتم آسانسور
        List<Double> elevatorTimes = scheduler.elevatorAlgorithm();
        System.out.println("\nElevator Algorithm Results:");
        for (int i = 0; i < elevatorTimes.size(); i++) {
            System.out.printf(Locale.ENGLISH, "%d %.1f\n", requests.get(i).cylinder, elevatorTimes.get(i));
        }

        scheduler = new Part2(initialHeadPosition, requests);

        // اجرای الگوریتم FCFS
        List<Double> fcfsTimes = scheduler.fcfsAlgorithm();
        System.out.println("\nFCFS Algorithm Results:");
        for (int i = 0; i < fcfsTimes.size(); i++) {
            System.out.printf(Locale.ENGLISH, "%d %.1f\n", requests.get(i).cylinder, fcfsTimes.get(i));
        }

        scanner.close();
    }
}
