package com.proiectsd.vcs.form;

import lombok.Data;

@Data
public class ProjectForm {
    private String title;
    private String description;

    public ProjectForm() {
    }

    public ProjectForm(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
