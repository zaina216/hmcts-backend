package uk.gov.hmcts.reform.dev;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import uk.gov.hmcts.reform.dev.controllers.TaskController;
import uk.gov.hmcts.reform.dev.models.ExampleTask;
import uk.gov.hmcts.reform.dev.repositories.ExampleTaskRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskControllerUnitTest {

    private final ExampleTaskRepository repository = mock(ExampleTaskRepository.class);
    private final TaskController controller = new TaskController(repository);

    // ---------------- GET ALL ----------------

    @Test
    void shouldReturnAllTasks() {
        ExampleTask task = new ExampleTask();
        task.setTitle("Test Task");

        when(repository.findAll()).thenReturn(List.of(task));

        var response = controller.getAllTasks();

        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().getFirst().getTitle()).isEqualTo("Test Task");
    }


    @Test
    void shouldCreateTask() {
        ExampleTask task = new ExampleTask();
        task.setTitle("New Task");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        when(repository.save(any())).thenReturn(task);

        var response = controller.createTask(task, bindingResult);

        Assertions.assertNotNull(response.getBody());

        ExampleTask responseBody = (ExampleTask) response.getBody();

        assertThat(responseBody.getTitle()).isEqualTo("New Task");
    }


    @Test
    void shouldReturnTaskById() {
        ExampleTask task = new ExampleTask();
        task.setId(1);
        task.setTitle("Task");

        when(repository.findById(1)).thenReturn(Optional.of(task));

        var response = controller.getTaskById(1);

        ExampleTask responseBody = response.getBody();

        Assertions.assertNotNull(responseBody);
        assertThat(responseBody.getId()).isEqualTo(1);
        assertThat(responseBody.getTitle()).isEqualTo("Task");
    }

    @Test
    void shouldReturnNotFoundWhenTaskDoesNotExist() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        ResponseEntity<ExampleTask> response = controller.getTaskById(99);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }


    @Test
    void shouldDeleteTask() {
        when(repository.existsById(1)).thenReturn(true);

        var response = controller.deleteTask(1);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);

        verify(repository).deleteById(1);
    }

    @Test
    void shouldReturnNotFoundWhenDeletingMissingTask() {
        when(repository.existsById(1)).thenReturn(false);

        var response = controller.deleteTask(1);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }
}
