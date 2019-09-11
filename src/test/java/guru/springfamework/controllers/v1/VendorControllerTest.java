package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
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
import static org.hamcrest.Matchers.equalTo;
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

        when(vendorService.getAllVendors()).thenReturn(getVendorDTOFixtures());

        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(3)));
    }

    @Test
    public void createNewVendor() throws Exception {

        VendorDTO createdVendor = new VendorDTO();
        createdVendor.setFirstname("Rick");
        createdVendor.setLastname("Sanchez");

        VendorDTO returnedVendor = new VendorDTO();
        returnedVendor.setFirstname(createdVendor.getFirstname());
        returnedVendor.setLastname(createdVendor.getLastname());
        returnedVendor.setVendorUrl(VendorController.BASE_URL + "/1");

        when(vendorService.createNewVendor(createdVendor)).thenReturn(returnedVendor);

        mockMvc.perform(post(VendorController.BASE_URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createdVendor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.firstname", equalTo(returnedVendor.getFirstname())))
                .andExpect(jsonPath("$.lastname", equalTo(returnedVendor.getLastname())))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }

    @Test
    public void deleteVendorById() throws Exception {

        mockMvc.perform(delete("/api/v1/endor/delete/" + anyInt())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService, times(1)).deleteVendorById(anyLong());
    }

    @Test
    public void getVendorById() throws Exception {

        Long id = 1L;
        VendorDTO vendor = new VendorDTO();
        vendor.setId(id);
        vendor.setFirstname("Ned");
        vendor.setLastname("Flanders");
        vendor.setVendorUrl(VendorController.BASE_URL + "/" + id);

        when(vendorService.getVendorById(id)).thenReturn(vendor);

        mockMvc.perform(get("/api/v1/vendor/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(vendor.getId())))
                .andExpect(jsonPath("$.firstname", equalTo(vendor.getFirstname())))
                .andExpect(jsonPath("$.lastname", equalTo(vendor.getLastname())))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendor.getVendorUrl())));
    }

    @Test
    public void patchVendor() throws Exception {

        Long id = 1L;

        VendorDTO vendorToPatch = new VendorDTO();
        vendorToPatch.setId(id);
        vendorToPatch.setFirstname("Gina");
        vendorToPatch.setLastname("Linetti");

        String newLastname = "Boyle";

        VendorDTO patchingVendor = new VendorDTO();
        patchingVendor.setLastname(newLastname);

        VendorDTO patchedVendor = new VendorDTO();
        patchedVendor.setId(vendorToPatch.getId());
        patchedVendor.setFirstname(vendorToPatch.getFirstname());
        patchedVendor.setLastname(newLastname);
        patchedVendor.setVendorUrl(VendorController.BASE_URL + "/" + id);

        when(vendorService.updateVendor(patchingVendor, id))
                .thenReturn(patchedVendor);

        mockMvc.perform(post("/api/v1/vendor/update/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(asJsonString(patchingVendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(vendorToPatch.getFirstname())))
                .andExpect(jsonPath("$.lastname", equalTo(newLastname)))
                .andExpect(jsonPath("$.vendor_url", equalTo(patchedVendor.getVendorUrl())));
    }

    @Test
    public void putVendor() throws Exception {

        Long id = 1L;

        VendorDTO vendorToPut = new VendorDTO();
        vendorToPut.setId(id);
        vendorToPut.setFirstname("Raymond");
        vendorToPut.setLastname("Holt");
        vendorToPut.setVendorUrl(VendorController.BASE_URL + "/" + id);

        VendorDTO replacedVendor = new VendorDTO();
        replacedVendor.setId(vendorToPut.getId());
        replacedVendor.setFirstname(vendorToPut.getFirstname());
        replacedVendor.setLastname(vendorToPut.getLastname());
        replacedVendor.setVendorUrl(vendorToPut.getVendorUrl());

        when(vendorService.replaceVendor(vendorToPut, id))
                .thenReturn(replacedVendor);

        mockMvc.perform(post("/api/v1/vendor/replace/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(asJsonString(vendorToPut)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(replacedVendor.getFirstname())))
                .andExpect(jsonPath("$.lastname", equalTo(replacedVendor.getLastname())))
                .andExpect(jsonPath("$.vendor_url", equalTo(replacedVendor.getVendorUrl())));
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