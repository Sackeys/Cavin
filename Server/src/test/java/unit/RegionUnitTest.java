package unit;

import fr.univ_smb.isc.m2.repository.RegionRepository;
import fr.univ_smb.isc.m2.services.RegionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import fr.univ_smb.isc.m2.models.Region;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegionUnitTest {

    @Mock
    RegionRepository serviceRepository;

    @InjectMocks
    RegionService service;

    @Test
    public void shouldAddOneRegion() {
        Region region = new Region("Grand Est");

        serviceRepository = mock(RegionRepository.class);

        service = new RegionService(serviceRepository);
        assertThat(service.add(region)).isEqualTo(region);
    }

    @Test
    public void shouldNotAddEmptyRegion() {
        Region region = new Region();

        serviceRepository = mock(RegionRepository.class);

        service = new RegionService(serviceRepository);
        assertThat(service.add(region)).isNull();
    }

    @Test
    public void shouldReturnAllRegions() {
        List<Region> regions = new ArrayList<>();
        regions.add(new Region("Auvergne-Rhône-Alpes"));
        regions.add(new Region("Centre-Val de Loire"));
        regions.add(new Region("Occitanie"));
        regions.add(new Region("Grand Est"));

        serviceRepository = mock(RegionRepository.class);
        when(serviceRepository.findAll()).thenReturn(regions);

        service = new RegionService(serviceRepository);
        assertThat(service.all().size()).isEqualTo(4);

        assertThat(service.all()).containsExactlyElementsOf(regions);
    }

    @Test
    public void shouldReturnAllOrderedRegions() {
        List<Region> regions = new ArrayList<>();
        Region auvergne = new Region("Auvergne-Rhône-Alpes");
        Region centreval = new Region("Centre-Val de Loire");
        Region occitanie = new Region("Occitanie");
        Region grandest = new Region("Grand Est");
        regions.add(auvergne);
        regions.add(centreval);
        regions.add(occitanie);
        regions.add(grandest);

        serviceRepository = mock(RegionRepository.class);
        when(serviceRepository.findAll()).thenReturn(regions);

        service = new RegionService(serviceRepository);
        assertThat(service.all()).containsExactlyInAnyOrderElementsOf(regions);
    }

}
