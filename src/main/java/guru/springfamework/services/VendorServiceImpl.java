package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorMapper mapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper mapper, VendorRepository vendorRepository) {
        this.mapper = mapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<VendorDTO> getAllVendors() {

        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = mapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {

        Vendor vendorToCreate = mapper.vendorDtoToVendor(vendorDTO);
        Vendor createdVendor = vendorRepository.save(vendorToCreate);
        VendorDTO createdVendorDTO = mapper.vendorToVendorDTO(createdVendor);
        createdVendorDTO.setVendorUrl(getVendorUrl(createdVendorDTO.getId()));

        return createdVendorDTO;
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(mapper::vendorToVendorDTO)
                .map(vendorDTO -> {
                    vendorDTO.setVendorUrl(getVendorUrl(vendorDTO.getId()));
                    return vendorDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO updateVendor(VendorDTO vendorDTO, Long id) {
        VendorDTO vendorToUpdate = getVendorById(id);
        if (vendorDTO.getFirstname() != null) {
            vendorToUpdate.setFirstname(vendorDTO.getFirstname());
        }
        if (vendorDTO.getLastname() != null) {
            vendorToUpdate.setLastname(vendorDTO.getLastname());
        }
        Vendor vendor = vendorRepository.save(mapper.vendorDtoToVendor(vendorToUpdate));
        VendorDTO updatedVendor = mapper.vendorToVendorDTO(vendor);
        updatedVendor.setVendorUrl(getVendorUrl(vendor.getId()));

        return updatedVendor;
    }

    @Override
    public VendorDTO replaceVendor(VendorDTO vendorDTO, Long id) {

        Vendor replaced = mapper.vendorDtoToVendor(vendorDTO);
        replaced.setId(id);

        VendorDTO replacedDTO = mapper.vendorToVendorDTO(vendorRepository.save(replaced));

        replacedDTO.setVendorUrl(getVendorUrl(replacedDTO.getId()));

        return replacedDTO;
    }

    private String getVendorUrl(Long id) {
        return VendorController.BASE_URL + "/" + id;
    }
}
