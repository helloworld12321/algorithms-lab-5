import java.util.ArrayList;

/**
 * @author Jon Reuvers, Nicolas Robertson, Joe Walbran
 */
public class Candidate {
    boolean isCompany;
    String identifier;
    ArrayList<String> rankings;

    public Candidate(boolean isCompanyLocal, String identifierLocal, ArrayList<String> rankingsLocal) {
        this.isCompany = isCompanyLocal;
        this.identifier = identifierLocal;
        this.rankings = new ArrayList<String>(rankingsLocal);
    }

    public boolean getIsCompany() {
        return isCompany;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ArrayList<String> getRankings() {
        return rankings;
    }

    public Candidate getPreferredCompany(int index, ArrayList<Candidate> companies) {
        String companyIdentifier = this.rankings.get(index);
        for (int i = 0; i < companies.size(); i++) {
            if (companyIdentifier.equals(companies.get(i).getIdentifier())) {
                return companies.get(i);
            }
        }
        throw new RuntimeException("No company found with the identifier '" + companyIdentifier + "'");
    }

    public String toString() {
        if (getIsCompany()) {
            return String.format("Company %s: %s", getIdentifier(), getRankings());
        }
        return String.format("Programmer %s: %s", getIdentifier(), getRankings());
    }
}
