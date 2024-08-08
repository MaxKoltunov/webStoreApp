package com.web.webStoreApp.mainApi.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "users", schema = "mainschema")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "mainschema.users_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth_day", nullable = false)
    private String birthDay;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phone_number;

    @ManyToOne
    @JoinColumn(name = "level_name", referencedColumnName = "level_name",  nullable = false)
    private LevelsOfLoyalty level_of_loyalty;

    @Column(name = "existing", nullable = false)
    private boolean existing;


    public boolean isExisting() {
        return existing;
    }

    public void setExisting(boolean existing) {
        this.existing = existing;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public LevelsOfLoyalty getLevel_of_loyalty() {
        return level_of_loyalty;
    }

    public void setLevel_of_loyalty(LevelsOfLoyalty level_of_loyalty) {
        this.level_of_loyalty = level_of_loyalty;
    }
}