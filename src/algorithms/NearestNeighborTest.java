package algorithms;

import algoname.AlgorithmName;
import org.junit.Test;
import model.Model;
import path.Path;


public class NearestNeighborTest {

    @Test
    public void testSolve() {
        // just to make sure no errors pop up/check for unexpected algorithmic issues
        Model model = new Model(10);
        model.solvePath(AlgorithmName.NEAREST_NEIGHBOR);

    }

}
