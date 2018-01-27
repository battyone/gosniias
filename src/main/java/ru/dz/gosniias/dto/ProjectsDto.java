package ru.dz.gosniias.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vassaeve
 */
public class ProjectsDto implements Serializable {

    private static final long serialVersionUID = 5436352759294122041L;

    private int size;
    private List<ProjectDto> projects;

    public ProjectsDto() {
        this.projects = new ArrayList<>(0);
    }

    public ProjectsDto(List<ProjectDto> list) {
        this.projects = new ArrayList<>(list);
    }

    public ProjectsDto(int size, List<ProjectDto> list) {
        this.size = size;
        this.projects = new ArrayList<>(list);
    }

    public List<ProjectDto> getProjects() {
        return projects;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setProjects(List<ProjectDto> projects) {
        this.projects.clear();
        this.projects.addAll(projects);
    }

}
