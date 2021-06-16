package com.example.testcontainer.dummy.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Table(name = "TEST_TABLE")
@Entity
public class Dummy {

    @Id
    @NotNull
    @SequenceGenerator(name = "dummyIdGenerator", sequenceName = "DUMMY_ID_GEN")
    @GeneratedValue(generator = "dummyIdGenerator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private String text;

    public Dummy() {
    }

    private Dummy(final Long id, final String text) {
        this.id = id;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public static class DummyBuilder {

        private final Long id;

        private String text;

        private DummyBuilder(final Long id, final String text) {
            this.id = id;
            this.text = text;
        }

        public static DummyBuilder forCreation() {
            return new DummyBuilder(null, null);
        }

        public static DummyBuilder forUpdate(@NotNull @Valid Dummy dummy) {
            return new DummyBuilder(dummy.getId(), dummy.getText());
        }

        public DummyBuilder withText(@NotNull final String text) {
            this.text = text;
            return this;
        }

        public Dummy build() {
            return new Dummy(id, text);
        }
    }
}
