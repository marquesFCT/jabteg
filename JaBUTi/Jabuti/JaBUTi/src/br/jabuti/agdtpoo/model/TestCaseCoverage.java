
package br.jabuti.agdtpoo.model;

public class TestCaseCoverage {

    private int expectedCoverage;
    private int realizedCoverage;
    private String criterion;

    public String getCriterion() {
        return this.criterion;
    }

    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }
   
    public int getExpectedCoverage() {
        return this.expectedCoverage;
    }

    public void setExpectedCoverage(int expectedCoverage) {
        this.expectedCoverage = expectedCoverage;
    }

    public int getRealizedCoverage() {
        return this.realizedCoverage;
    }

    public void setRealizedCoverage(int realizedCoverage) {
        this.realizedCoverage = realizedCoverage;
    }
}
