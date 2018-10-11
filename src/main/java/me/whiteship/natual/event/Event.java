package me.whiteship.natual.event;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Event {

    @Id @GeneratedValue
    Integer id;

    String title;

    @Lob
    String description;

}
