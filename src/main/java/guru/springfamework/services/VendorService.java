package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;

public interface VendorService {
    VendorListDTO getAllVendors();

    VendorDTO createNewVendor(VendorDTO vendorDTO);

    void deleteVendorById(Long id);

    VendorDTO getVendorById(Long id);

    VendorDTO updateVendorById(VendorDTO vendorDTO, Long id);

    VendorDTO replaceVendorById(VendorDTO vendorDTO, Long id);
}
