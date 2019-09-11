package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorMapper mapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper mapper, VendorRepository vendorRepository) {
        this.mapper = mapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public VendorListDTO getAllVendors() {
        return null;
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return null;
    }

    @Override
    public void deleteVendorById(Long id) {

    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return null;
    }

    @Override
    public VendorDTO updateVendor(VendorDTO vendorDTO, Long id) {
        return null;
    }

    @Override
    public VendorDTO replaceVendor(VendorDTO vendorDTO, Long id) {
        return null;
    }
}
