package souzxvini.com.ToDoAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
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
    private Integer id;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private ConclusionStatus conclusionStatus;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private LocalDate initialDate;

    private LocalDate deadline;

    private String priority;



}
