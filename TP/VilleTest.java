import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class VilleTest {
    private Ville ville;
    @BeforeEach
    protected void setUp() throws Exception{
         ville = new Ville("Pau",77215,new Pays("France","Europe"));
    }

    @AfterEach
    protected void tearDown() throws Exception{
        ville = null;
    }

    @Test
    public void testVille(){
        assertNotNull(ville);
        assertSame(ville,ville);
    }

    @Test
    void name() {
    }
}
