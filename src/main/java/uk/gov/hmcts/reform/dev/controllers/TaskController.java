package uk.gov.hmcts.reform.dev.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uk.gov.hmcts.reform.dev.models.ExampleTask;
import uk.gov.hmcts.reform.dev.repositories.ExampleTaskRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final ExampleTaskRepository repository;

    public TaskController(ExampleTaskRepository repository) {
        this.repository = repository;
    }

    // Create a new task
    @PostMapping("/save-task")
    public ResponseEntity<?> createTask(@Valid @RequestBody ExampleTask exampleTask, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body((ExampleTask) result.getFieldErrors());
        }

        exampleTask.setCreatedDate(LocalDate.now());
        ExampleTask savedTask = repository.save(exampleTask);

        var test = ResponseEntity.ok(savedTask);
        return ResponseEntity.ok(savedTask);
    }

    // List all tasks
    @GetMapping("get-all-tasks")
    public ResponseEntity<List<ExampleTask>> getAllTasks() {
        return ResponseEntity.ok(repository.findAll());
    }

    // Get a specific task by ID
    @GetMapping("get-task/{id}")
    public ResponseEntity<ExampleTask> getTaskById(@PathVariable Integer id) {
        Optional<ExampleTask> exampleTask = repository.findById(id);
        return exampleTask.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a task by ID
    @PutMapping("update-task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Integer id,
                                                  @RequestBody ExampleTask updatedTask, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body((ExampleTask) result.getFieldErrors());
        }
        return repository.findById(id)
            .map(existingTask -> {
                existingTask.setTaskNumber(updatedTask.getTaskNumber());
                existingTask.setTitle(updatedTask.getTitle());
                existingTask.setDescription(updatedTask.getDescription());
                existingTask.setStatus(updatedTask.getStatus());
                ExampleTask saved = repository.save(existingTask);
                return ResponseEntity.ok(saved);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a task by ID
    @DeleteMapping("delete-task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
