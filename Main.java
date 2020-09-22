import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Jon Reuvers, Nicolas Robertson, Joe Walbran
 */
public class Main {
    // If you'd like to read from a different file, change this variable here.
    static final String INPUT_FILENAME = "candidates1.txt";

    public static void main(String[] args) {
        ArrayList<Candidate> companies = new ArrayList<Candidate>();
        ArrayList<Candidate> programmers = new ArrayList<Candidate>();

        Scanner scanner;
        try {
            scanner = new Scanner(new File(INPUT_FILENAME));
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
            if(candidate.getIsCompany()){
                companies.add(candidate);
            } else {
                programmers.add(candidate);
            }
        }
        scanner.close();

        HashMap<Candidate, Candidate> pairings = stableMatching(companies, programmers);
        System.out.println("Pairings:");
        pairings.forEach((company, programmer) -> {
            System.out.println(company.getIdentifier() + ": " + programmer.getIdentifier());
        });
    }

    /**
     * Given `n` companies and `n` programmers, pair them in a stable manner.
     *
     * (By "stable", we mean that there isn't any company C and programmer P
     * such that C would rather employ P than their current programmer and
     * P would rather work at C than their current company. Swapping would
     * always make the programmer less happy or the company less happy.)
     *
     * @return a HashMap from companies to the programmer they have employed.
     *
     * (Our source for this algorithm was
     * https://en.wikipedia.org/wiki/Gale%E2%80%93Shapley_algorithm
     * --thanks to Aaron Walter for pointing us to this!)
     */
    static HashMap<Candidate, Candidate> stableMatching(
            ArrayList<Candidate> companies,
            ArrayList<Candidate> programmers) {
        // TODO
        HashMap<Candidate, Candidate> employees = new HashMap<>();
        for (Candidate company : companies) {
            employees.put(company, null);
        }

        // Each programmer has a list of rankings, right? So, as they go through
        // the algorithm, they're going to cross companies off their list. This
        // HashMap says how far down the list they are (where 0 is the start of
        // their list.)
        // That way, the programmers never backtrack.
        HashMap<Candidate, Integer> nextRankingToLookAt = new HashMap<>();
        for (Candidate programmer : programmers) {
            nextRankingToLookAt.put(programmer, 0);
        }


        Candidate programmer;

        ArrayList<Candidate> unemployedProgrammers = new ArrayList<>(programmers);
        while (!unemployedProgrammers.isEmpty()) {
            // Set programmer to first value of the current list of unemployed programmers
            programmer = unemployedProgrammers.get(0);

            // Find a spot for this programmer. Keep checking companies until
            // they get hired somewhere. (It's guaranteed that some company
            // will always hire them; see the writeup for why.)
            while(!employees.containsValue(programmer)) {
                int r = nextRankingToLookAt.get(programmer);
                Candidate currentCompany = programmer.getPreferredCompany(r, companies);
                // If the company currently does not have an employee, hire the programmer
                if (employees.get(currentCompany) == null) {
                    employees.replace(currentCompany, programmer);
                    unemployedProgrammers.remove(programmer);
                } else {
                    // Do they like their old employee better than this programmer?
                    Candidate oldEmployee = employees.get(currentCompany);
                    if (
                            currentCompany.getRankings().indexOf(oldEmployee.getIdentifier())
                            < currentCompany.getRankings().indexOf(programmer.getIdentifier())) {
                        // If so, just move on to the next company in the programmer's ranking.
                    }
                    else {
                        // But, if they happen to like this programmer better,
                        // fire the old employee and hire the new one.
                        employees.replace(currentCompany, programmer);
                        unemployedProgrammers.remove(programmer);
                        unemployedProgrammers.add(oldEmployee);
                    }
                }
                nextRankingToLookAt.replace(programmer, r + 1);
            }
        }
        return employees;
    }

    // We've made a conscious decision to not write a method to test that the pairings are optimal. Sorry about that! :)
}
