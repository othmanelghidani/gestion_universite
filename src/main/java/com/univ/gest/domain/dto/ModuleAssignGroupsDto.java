package com.univ.gest.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class ModuleAssignGroupsDto {

    private Long moduleId;
    private List<Long> groupIds = new ArrayList<>();

    public ModuleAssignGroupsDto() {}

    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }

    public List<Long> getGroupIds() { return groupIds; }
    public void setGroupIds(List<Long> groupIds) { this.groupIds = groupIds; }
}
