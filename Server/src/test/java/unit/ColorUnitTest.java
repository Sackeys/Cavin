package unit;

import fr.univ_smb.isc.m2.models.Color;
import fr.univ_smb.isc.m2.repository.ColorRepository;
import fr.univ_smb.isc.m2.services.ColorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ColorUnitTest {

    @Mock
    ColorRepository serviceRepository;

    @InjectMocks
    ColorService service;

    @Test
    public void shouldAddOneColor() {
        Color color = new Color("Jaune vert", "e2dc85");

        serviceRepository = mock(ColorRepository.class);

        service = new ColorService(serviceRepository);
        assertThat(service.add(color)).isEqualTo(color);
    }

    @Test
    public void shouldNotAddEmptyColor() {
        Color color = new Color();

        serviceRepository = mock(ColorRepository.class);

        service = new ColorService(serviceRepository);
        assertThat(service.add(color)).isNull();
    }

    @Test
    public void shouldReturnAllColors() {
        List<Color> colors = new ArrayList<>();
        colors.add(new Color("Jaune vert", "e2dc85"));
        colors.add(new Color("Or vert", "e9d750"));
        colors.add(new Color("Doré", "d4aa2e"));
        colors.add(new Color("Roux", "c57d29"));

        serviceRepository = mock(ColorRepository.class);
        when(serviceRepository.findAll()).thenReturn(colors);

        service = new ColorService(serviceRepository);
        assertThat(service.all().size()).isEqualTo(4);
    }
}
