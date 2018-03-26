import fr.univ_smb.isc.m2.controllers.rest.RestRegionController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import fr.univ_smb.isc.m2.models.Region;
import java.util.List;
import static org.junit.Assert.*;

public class RegionUnitTest {

    RestRegionController controller;

    public RegionUnitTest() {}

    @Autowired()
    public RegionUnitTest(RestRegionController controller) {
        this.controller = controller;
    }

    @Test
    public void getAllRegions() {
        List<Region> listRegions = controller.region();
        assertTrue("13/13 régions", listRegions.size() == 13);
    }

}
