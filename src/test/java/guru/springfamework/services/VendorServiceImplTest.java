package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class VendorServiceImplTest {

    private VendorServiceImpl vendorService;
    
    @Mock
    private VendorRepository vendorRepository;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void getAllVendors() {
        
        when(vendorRepository.findAll()).thenReturn(getVendorFixtures());
        
        List<VendorDTO> vendorDTOs = vendorService.getAllVendors();
        
        assertEquals(3, vendorDTOs.size());
        
        verify(vendorRepository, times(1)).findAll();
    }

    @Test
    public void createNewVendor() {

        when(vendorRepository.save(any(Vendor.class))).thenReturn(new Vendor());

        VendorDTO vendorDTO = vendorService.createNewVendor(new VendorDTO());

        assertNotNull(vendorDTO);

        verify(vendorRepository, times(1)).save(any(Vendor.class));
    }

    @Test
    public void deleteVendorById() {

        Long id = 1L;

        vendorService.deleteVendorById(id);

        verify(vendorRepository, times(1)).deleteById(id);
    }

    @Test
    public void getVendorById() {

        Long id = 1L;

        when(vendorRepository.findById(id)).thenReturn(Optional.of(new Vendor()));

        assertNotNull(vendorRepository.findById(id).get());

        verify(vendorRepository, times(1)).findById(id);
    }

    @Test
    public void updateVendorById() {

        Vendor vendor = new Vendor();
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        assertNotNull(vendorService.updateVendor(new VendorDTO(), 1L));

        verify(vendorRepository, times(1)).findById(anyLong());
        verify(vendorRepository, times(1)).save(any(Vendor.class));
    }

    @Test
    public void replaceVendorById() {

        Vendor vendor = new Vendor();
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        assertNotNull( vendorService.replaceVendor(new VendorDTO(), 1L));

        verify(vendorRepository, times(1)).save(any(Vendor.class));
    }
    
    private List<Vendor> getVendorFixtures() {
        List<Vendor> vendors = new ArrayList<>();

        Vendor vendor1 = new Vendor();
        vendor1.setId(1L);
        vendors.add(vendor1);

        Vendor vendor2 = new Vendor();
        vendor2.setId(2L);
        vendors.add(vendor2);

        Vendor vendor3 = new Vendor();
        vendor3.setId(3L);
        vendors.add(vendor3);

        return vendors;
    }
}