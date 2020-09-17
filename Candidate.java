import java.util.ArrayList;

/**
 * @author Jon Reuvers, Joe Walbran
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

    public String toString() {
        if (getIsCompany()) {
            return String.format("Company %s: %s", getIdentifier(), getRankings());
        }
        return String.format("Programmer %s: %s", getIdentifier(), getRankings());
    }
}
