package com.proiectsd.vcs.form;

import com.proiectsd.vcs.constraint.FieldMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@FieldMatch(first = "password", second = "passwordConfirm", message = "The password fields must match")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String passwordConfirm;
}
