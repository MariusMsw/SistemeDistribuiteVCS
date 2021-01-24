package com.proiectsd.vcs.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CommitForm {
    @NotEmpty
    private String name;

    private String description;

    public CommitForm() {
    }

    public CommitForm(String name) {
        this.name = name;
    }

    public CommitForm(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
