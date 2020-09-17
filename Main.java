import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Jon Reuvers, Nicolas Robertson, Joe Walbran
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<Candidate> companies = new ArrayList<Candidate>();
        ArrayList<Candidate> employees = new ArrayList<Candidate>();

        Scanner scanner;
        try {
            scanner = new Scanner(new File("candidates.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);

            // We need to initialize the scanner variable to make the compiler happy :)
            scanner = null;
        }

        while(scanner.hasNextLine()){
            boolean isCompany;
            String identifier;
            ArrayList<String> rankings = new ArrayList<String>();
            if (scanner.next().equals("company")) {
                isCompany = true;
            } else {
                isCompany = false;
            }

            identifier = scanner.next();

            String next;
            while (true) {
                next = scanner.next();
                if (next.equals(";")) {
                    break;
                }
                rankings.add(next);
            }

            Candidate candidate = new Candidate(isCompany, identifier, rankings);
            System.out.println(candidate.toString());
            if(candidate.getIsCompany()){
                companies.add(candidate);
            } else {
                employees.add(candidate);
            }
        }
        scanner.close();
    }
}
