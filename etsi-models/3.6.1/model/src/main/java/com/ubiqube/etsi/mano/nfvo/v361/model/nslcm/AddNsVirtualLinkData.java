package com.ubiqube.etsi.mano.nfvo.v361.model.nslcm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * This type specifies the parameters used for the creation of a new NsVirtualLink instance. It shall comply with the provisions defined in table 6.5.3.95-1. NOTE: All NsVirtualLink instances of a particular NS DF based on a specific \&quot;NsVirtualLinkDesc\&quot; have the same characteristics as they use the same \&quot;VirtualLinkProfile\&quot;. 
 */
@Schema(description = "This type specifies the parameters used for the creation of a new NsVirtualLink instance. It shall comply with the provisions defined in table 6.5.3.95-1. NOTE: All NsVirtualLink instances of a particular NS DF based on a specific \"NsVirtualLinkDesc\" have the same characteristics as they use the same \"VirtualLinkProfile\". ")
@Validated


public class AddNsVirtualLinkData   {
  @JsonProperty("nsVirtualLinkProfileId")
  private String nsVirtualLinkProfileId = null;

  public AddNsVirtualLinkData nsVirtualLinkProfileId(String nsVirtualLinkProfileId) {
    this.nsVirtualLinkProfileId = nsVirtualLinkProfileId;
    return this;
  }

  /**
   * Get nsVirtualLinkProfileId
   * @return nsVirtualLinkProfileId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getNsVirtualLinkProfileId() {
    return nsVirtualLinkProfileId;
  }

  public void setNsVirtualLinkProfileId(String nsVirtualLinkProfileId) {
    this.nsVirtualLinkProfileId = nsVirtualLinkProfileId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddNsVirtualLinkData addNsVirtualLinkData = (AddNsVirtualLinkData) o;
    return Objects.equals(this.nsVirtualLinkProfileId, addNsVirtualLinkData.nsVirtualLinkProfileId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nsVirtualLinkProfileId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddNsVirtualLinkData {\n");
    
    sb.append("    nsVirtualLinkProfileId: ").append(toIndentedString(nsVirtualLinkProfileId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
