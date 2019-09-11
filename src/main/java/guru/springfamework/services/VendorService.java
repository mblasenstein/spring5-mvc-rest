package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;

import java.util.List;

public interface VendorService {
    List<VendorDTO> getAllVendors();

    VendorDTO createNewVendor(VendorDTO vendorDTO);

    void deleteVendorById(Long id);

    VendorDTO getVendorById(Long id);

    VendorDTO updateVendor(VendorDTO vendorDTO, Long id);

    VendorDTO replaceVendor(VendorDTO vendorDTO, Long id);
}
