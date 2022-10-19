package souzxvini.com.ToDoAPI.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    private Status status = Status.TO_DO;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
