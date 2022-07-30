package com.cfm.AddressBook.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Addresses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addId")
    public long addId;

    @Column(name = "name")
    @NotNull
    public String name;

    @Column(name = "mobile")
    @NotNull
    public long mobile;

    @Column(name = "home")
    public long home;

    @Column(name = "email")
    public String email;
}
