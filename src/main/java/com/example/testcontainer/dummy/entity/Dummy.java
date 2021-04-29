package com.example.testcontainer.dummy.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "TEST_TABLE")
@Entity
public class Dummy {

    @Id
    @SequenceGenerator(name = "dummyIdGenerator", sequenceName = "DUMMY_ID_GEN")
    @GeneratedValue(generator = "dummyIdGenerator", strategy = GenerationType.SEQUENCE)
    private long id;

    private String text;

    public Dummy() {

    }

    public Dummy(String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
