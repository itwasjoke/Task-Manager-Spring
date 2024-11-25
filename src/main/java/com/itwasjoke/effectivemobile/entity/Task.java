package com.itwasjoke.effectivemobile.entity;

import com.itwasjoke.effectivemobile.customEnum.PriorityTask;
import com.itwasjoke.effectivemobile.customEnum.StatusTask;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private PriorityTask priority;
    @Enumerated(EnumType.STRING)
    private StatusTask status;
    @ManyToOne
    @JoinColumn(name = "executor_id")
    private User executor;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;
}
