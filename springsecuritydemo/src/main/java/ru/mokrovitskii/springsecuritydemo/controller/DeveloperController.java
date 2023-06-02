package ru.mokrovitskii.springsecuritydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mokrovitskii.springsecuritydemo.model.Developer;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/developer")
public class DeveloperController {

    private final List<Developer> developers = Stream.of(
            new Developer(1L, "Ivan", "Ivanov"),
            new Developer(2L, "Sergey", "Sergeyev"),
            new Developer(3L, "Petr", "Petrov")
    ).toList();

    @GetMapping
    public List<Developer> getAll() {
        return developers;
    }

    @GetMapping("/{id}")
    public Developer getById(@PathVariable Long id) {
        return developers.stream().filter(developer -> developer.getId().equals(id)).findFirst().orElse(null);
    }
}
