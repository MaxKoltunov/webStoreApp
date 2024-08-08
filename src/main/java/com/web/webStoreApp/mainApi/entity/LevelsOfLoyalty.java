package com.web.webStoreApp.mainApi.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "levelsofloyalty", schema = "mainschema")
public class LevelsOfLoyalty {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "levelsOfLoyalty_id_seq")
    @SequenceGenerator(name = "levelsOfLoyalty_id_seq", sequenceName = "mainSchema.levelsOfLoyalty_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "level_name", nullable = false, unique = true)
    private String level_name;

    @OneToMany(mappedBy = "level_of_loyalty")
    private Set<User> users;

    public LevelsOfLoyalty() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevelName() {
        return level_name;
    }

    public void setLevelName(String levelName) {
        this.level_name = levelName;
    }

}
