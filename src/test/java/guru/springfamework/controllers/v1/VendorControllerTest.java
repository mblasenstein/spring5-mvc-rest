package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.domain.Vendor;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest {

    @Mock
    private VendorService vendorService;

    @InjectMocks
    private VendorController vendorController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getAllVendors() throws Exception {

        when(vendorService.getAllVendors()).thenReturn(new VendorListDTO(getVendorDTOFixtures()));

        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(3)));
    }

    @Test
    public void createNewVendor() throws Exception {

        VendorDTO createdVendor = new VendorDTO();
        createdVendor.setName("Rick Sanchez");

        VendorDTO returnedVendor = new VendorDTO();
        returnedVendor.setName(createdVendor.getName());
        returnedVendor.setVendorUrl(VendorController.BASE_URL + "/1");

        when(vendorService.createNewVendor(createdVendor)).thenReturn(returnedVendor);

        mockMvc.perform(post(VendorController.BASE_URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createdVendor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.name", equalTo(returnedVendor.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1"));
    }

    @Test
    public void deleteVendorById() throws Exception {

        mockMvc.perform(delete("/api/v1/endor/delete/" + anyInt())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService, times(1)).deleteVendorById(anyLong());
    }

    @Test
    public void getVendorById() {
    }

    @Test
    public void updateVendorById() {
    }

    @Test
    public void replaceVendorById() {
    }

    private List<VendorDTO> getVendorDTOFixtures() {
        List<VendorDTO> vendors = new ArrayList<>();

        VendorDTO vendor1 = new VendorDTO();
        vendor1.setId(1L);
        vendors.add(vendor1);

        VendorDTO vendor2 = new VendorDTO();
        vendor2.setId(2L);
        vendors.add(vendor2);

        VendorDTO vendor3 = new VendorDTO();
        vendor3.setId(3L);
        vendors.add(vendor3);

        return vendors;
    }
}