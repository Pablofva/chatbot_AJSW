package com.ajsw.chatbot_backend.models;

import com.ajsw.chatbot_backend.models.enums.HogwartsHouse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@Entity
@Table(name = "content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String type;

    @ElementCollection
    @CollectionTable(name = "content_tags", joinColumns = @JoinColumn(name = "content_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private HogwartsHouse primaryHouse;
}
