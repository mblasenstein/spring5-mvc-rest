package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {

    public Long id;

    @ApiModelProperty(value = "This is the first name", required = true)
    public String firstname;

    @ApiModelProperty(value = "This is the last name", required = true)
    public String lastname;

    @ApiModelProperty(value = "Vendor's direct URL in the service", readOnly = true)
    @JsonProperty("vendor_url")
    public String vendorUrl;
}
