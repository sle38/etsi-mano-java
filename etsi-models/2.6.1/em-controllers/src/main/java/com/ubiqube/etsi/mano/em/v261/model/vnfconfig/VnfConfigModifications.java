package com.ubiqube.etsi.mano.em.v261.model.vnfconfig;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ubiqube.etsi.mano.em.v261.model.vnfconfig.VnfConfigurationData;
import com.ubiqube.etsi.mano.em.v261.model.vnfconfig.VnfcConfigurationData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * This type represents request parameters for the \&quot;Set Configuration\&quot; operation.   * NOTE 1: At least one of \&quot;vnfConfigurationData\&quot; and \&quot;vnfcConfigurationData\&quot;             shall be present.   * NOTE 2: The VnfcConfiguration data type can only be used to modify the configuration             of existing VNFC instances. 
 */
@ApiModel(description = "This type represents request parameters for the \"Set Configuration\" operation.   * NOTE 1: At least one of \"vnfConfigurationData\" and \"vnfcConfigurationData\"             shall be present.   * NOTE 2: The VnfcConfiguration data type can only be used to modify the configuration             of existing VNFC instances. ")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-12-11T19:16:20.445+01:00")

public class VnfConfigModifications   {
  @JsonProperty("vnfConfigurationData")
  private VnfConfigurationData vnfConfigurationData = null;

  @JsonProperty("vnfcConfigurationData")
  @Valid
  private List<VnfcConfigurationData> vnfcConfigurationData = null;

  @JsonProperty("vnfcConfigurationDataDeleteIds")
  @Valid
  private List<String> vnfcConfigurationDataDeleteIds = null;

  public VnfConfigModifications vnfConfigurationData(VnfConfigurationData vnfConfigurationData) {
    this.vnfConfigurationData = vnfConfigurationData;
    return this;
  }

  /**
   * Modifications to configuration data for the VNF instance. See NOTE 1. If present, the modifications of the \"vnfConfigurationData\" attribute shall be applied according to the rules of JSON Merge PATCH (see IETF RFC 7396). 
   * @return vnfConfigurationData
  **/
  @ApiModelProperty(value = "Modifications to configuration data for the VNF instance. See NOTE 1. If present, the modifications of the \"vnfConfigurationData\" attribute shall be applied according to the rules of JSON Merge PATCH (see IETF RFC 7396). ")

  @Valid

  public VnfConfigurationData getVnfConfigurationData() {
    return vnfConfigurationData;
  }

  public void setVnfConfigurationData(VnfConfigurationData vnfConfigurationData) {
    this.vnfConfigurationData = vnfConfigurationData;
  }

  public VnfConfigModifications vnfcConfigurationData(List<VnfcConfigurationData> vnfcConfigurationData) {
    this.vnfcConfigurationData = vnfcConfigurationData;
    return this;
  }

  public VnfConfigModifications addVnfcConfigurationDataItem(VnfcConfigurationData vnfcConfigurationDataItem) {
    if (this.vnfcConfigurationData == null) {
      this.vnfcConfigurationData = new ArrayList<>();
    }
    this.vnfcConfigurationData.add(vnfcConfigurationDataItem);
    return this;
  }

  /**
   * Modifications to configuration data for certain VNFC instances. See NOTE 1 and NOTE 2. If present, the modifications of the \"vnfcConfigurationData\" attribute shall follow these provisions:   Modifying an attribute that is an array of objects of type \"VnfcConfigurationData\".     Assumptions:       1) \"oldList\" is the \"VnfcConfigurationData\" array to be modified and \"newList\"          is the \"VnfcConfigurationData\" array that contains the changes.       2) \"oldEntry\" is an entry in \"oldList\" and \"newEntry\" is an entry in \"newList\".       3) A \"newEntry\" has a \"corresponding entry\" if there exists an \"oldEntry\" that          has the same content of the \"vnfcInstanceId\" attribute as the \"newEntry\";          a \"newEntry\" has no corresponding entry if no such \"oldEntry\" exists.       4) In any array of \"VnfcConfigurationData\" structures, the content of \"vnfcInstanceId\"          is unique (i.e. there shall be no two entries with the same content of \"vnfcInstanceId\").     Provisions:       1) For each \"newEntry\" in \"newList\" that has no corresponding entry in \"oldList\",          the \"oldList\" array shall be modified by adding that \"newEntry\".        2) For each \"newEntry\" in \"newList\" that has a corresponding \"oldEntry\" in \"oldList\",          the value of \"oldEntry\" shall be updated with the value of \"newEntry\" according to         the rules of JSON Merge PATCH (see IETF RFC 7396 ). 
   * @return vnfcConfigurationData
  **/
  @ApiModelProperty(value = "Modifications to configuration data for certain VNFC instances. See NOTE 1 and NOTE 2. If present, the modifications of the \"vnfcConfigurationData\" attribute shall follow these provisions:   Modifying an attribute that is an array of objects of type \"VnfcConfigurationData\".     Assumptions:       1) \"oldList\" is the \"VnfcConfigurationData\" array to be modified and \"newList\"          is the \"VnfcConfigurationData\" array that contains the changes.       2) \"oldEntry\" is an entry in \"oldList\" and \"newEntry\" is an entry in \"newList\".       3) A \"newEntry\" has a \"corresponding entry\" if there exists an \"oldEntry\" that          has the same content of the \"vnfcInstanceId\" attribute as the \"newEntry\";          a \"newEntry\" has no corresponding entry if no such \"oldEntry\" exists.       4) In any array of \"VnfcConfigurationData\" structures, the content of \"vnfcInstanceId\"          is unique (i.e. there shall be no two entries with the same content of \"vnfcInstanceId\").     Provisions:       1) For each \"newEntry\" in \"newList\" that has no corresponding entry in \"oldList\",          the \"oldList\" array shall be modified by adding that \"newEntry\".        2) For each \"newEntry\" in \"newList\" that has a corresponding \"oldEntry\" in \"oldList\",          the value of \"oldEntry\" shall be updated with the value of \"newEntry\" according to         the rules of JSON Merge PATCH (see IETF RFC 7396 ). ")

  @Valid

  public List<VnfcConfigurationData> getVnfcConfigurationData() {
    return vnfcConfigurationData;
  }

  public void setVnfcConfigurationData(List<VnfcConfigurationData> vnfcConfigurationData) {
    this.vnfcConfigurationData = vnfcConfigurationData;
  }

  public VnfConfigModifications vnfcConfigurationDataDeleteIds(List<String> vnfcConfigurationDataDeleteIds) {
    this.vnfcConfigurationDataDeleteIds = vnfcConfigurationDataDeleteIds;
    return this;
  }

  public VnfConfigModifications addVnfcConfigurationDataDeleteIdsItem(String vnfcConfigurationDataDeleteIdsItem) {
    if (this.vnfcConfigurationDataDeleteIds == null) {
      this.vnfcConfigurationDataDeleteIds = new ArrayList<>();
    }
    this.vnfcConfigurationDataDeleteIds.add(vnfcConfigurationDataDeleteIdsItem);
    return this;
  }

  /**
   * List of identifiers entries to be deleted from the 'vnfcConfigurationData\" attribute array to be used as \"deleteIdList\" as defined below this table. 
   * @return vnfcConfigurationDataDeleteIds
  **/
  @ApiModelProperty(value = "List of identifiers entries to be deleted from the 'vnfcConfigurationData\" attribute array to be used as \"deleteIdList\" as defined below this table. ")


  public List<String> getVnfcConfigurationDataDeleteIds() {
    return vnfcConfigurationDataDeleteIds;
  }

  public void setVnfcConfigurationDataDeleteIds(List<String> vnfcConfigurationDataDeleteIds) {
    this.vnfcConfigurationDataDeleteIds = vnfcConfigurationDataDeleteIds;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfConfigModifications vnfConfigModifications = (VnfConfigModifications) o;
    return Objects.equals(this.vnfConfigurationData, vnfConfigModifications.vnfConfigurationData) &&
        Objects.equals(this.vnfcConfigurationData, vnfConfigModifications.vnfcConfigurationData) &&
        Objects.equals(this.vnfcConfigurationDataDeleteIds, vnfConfigModifications.vnfcConfigurationDataDeleteIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vnfConfigurationData, vnfcConfigurationData, vnfcConfigurationDataDeleteIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfConfigModifications {\n");
    
    sb.append("    vnfConfigurationData: ").append(toIndentedString(vnfConfigurationData)).append("\n");
    sb.append("    vnfcConfigurationData: ").append(toIndentedString(vnfcConfigurationData)).append("\n");
    sb.append("    vnfcConfigurationDataDeleteIds: ").append(toIndentedString(vnfcConfigurationDataDeleteIds)).append("\n");
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

