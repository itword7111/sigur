package com.example.sigur.entity;

import com.example.sigur.model.PersonType;

import javax.persistence.*;

@MappedSuperclass
public abstract class Person {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "CARD", unique = true, columnDefinition = "bytea")
    private byte[] card;
    @Column(name = "TYPE")
    private PersonType type;

    public Person(byte[] card, PersonType type) {
        this.card = card;
        this.type = type;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getCard() {
        return card;
    }

    public void setCard(byte[] card) {
        this.card = card;
    }

    public PersonType getType() {
        return type;
    }

    public void setType(PersonType type) {
        this.type = type;
    }

    public String getCardLikeHexString(){
        StringBuilder stringBuilder=new StringBuilder();
        for (byte oneByte:card) {
            stringBuilder.append(String.format("%02X",oneByte));
        }
        return stringBuilder.toString();
    }
}
