import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private WeightedQuickUnionUF sites;
    private boolean[][] open;
    private int N;
    private int openSites;
    private int totalSites;
    private int topSite;
    private int bottomSite;
    
    private int IndexAdjust(int index) {

        return index - 1;
    }

    private int SiteFinder(int row, int col) {

        int site = row * N + col;

        return site;
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        N = n;

        totalSites = N * N - 1;
        openSites = 0;

        // creating two virtual sites together with matrix
        sites = new WeightedQuickUnionUF(totalSites + 2);
        open = new boolean[N][N];

        // index starts at 0, so totalSites is a new site
        topSite = totalSites;
        bottomSite = totalSites + 1;

    }

    // opens the site (row, col)
    public void open(int row, int col) {
        
        if (row < 1 || row > N || col < 1 || col > N ) throw new IllegalArgumentException();

        row = IndexAdjust(row);
        col = IndexAdjust(col);

        open[row][col] = true;
        openSites++;
            
        // connect to virtual sites if in top row or in bottom row
        if (row == 0) sites.union(topSite, SiteFinder(row, col));
        if (row == N - 1) sites.union(bottomSite, SiteFinder(row, col));

        // connect sites

        // below
        if (row > 0 && open[row - 1][col]) sites.union(SiteFinder(row, col), SiteFinder(row - 1, col));
        // left
        if (col > 0 && open[row][col - 1]) sites.union(SiteFinder(row, col), SiteFinder(row, col - 1));
        // right
        if (col < N - 1 && open[row][col + 1]) sites.union(SiteFinder(row, col), SiteFinder(row, col + 1));
        // top
        if (row < N - 1 && open[row + 1][col]) sites.union(SiteFinder(row, col), SiteFinder(row + 1, col));


    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        
        if (row < 1 || row > N || col < 1 || col > N ) throw new IllegalArgumentException();

        row = IndexAdjust(row);
        col = IndexAdjust(col);
        
        if (open[row][col]) return true;
        else return false;
        
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        
        if (row < 1 || row > N || col < 1 || col > N ) throw new IllegalArgumentException();

        row = IndexAdjust(row);
        col = IndexAdjust(col);
        
        // a site is full if it is connected to the topSite
        if (sites.find(topSite) == sites.find(SiteFinder(row, col))) return true;
        else return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {

        // DEBUG - System.out.println(sites.find(topSite) + " " + sites.find(bottomSite));
        
        if (sites.find(topSite) == sites.find(bottomSite)) return true;
        else return false;


    }

    public static void main(String[] args) {

        int N = 20; 

        Percolation test = new Percolation(N);

        // test functions
        int row = StdRandom.uniform(N) + 1;
        int col = StdRandom.uniform(N) + 1;

        System.out.println("row: " + row + ", col: " + col);
        System.out.println("Row Index Adjust: " + test.IndexAdjust(row));
        System.out.println("Site Finder:      " + test.SiteFinder(row, col));
        System.out.println("Is Site Full:     " + test.isFull(row, col));
        
        test.open(row, col);
        System.out.println("... opening site ...");

        System.out.println("Is Site Open:     " + test.isOpen(row, col));
        System.out.println("Percolates:       " + test.percolates());
        
        
        // test percolation logic

        while (!test.percolates()) {
            
            row = StdRandom.uniform(N) + 1;
            col = StdRandom.uniform(N) + 1;

            // the challenge is to open a site only if it's closed. How? 
            test.open(row, col);

        }
        System.out.println("Open Sites        " + test.openSites);
        System.out.println("p threshold:      " + (double) test.openSites / test.totalSites);

    }

}
