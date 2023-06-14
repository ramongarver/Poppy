package com.ramongarver.poppy.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.User;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.entity.WorkGroup;
import com.ramongarver.poppy.api.repository.ActivityRepository;
import com.ramongarver.poppy.api.repository.UserRepository;
import com.ramongarver.poppy.api.repository.VolunteerRepository;
import com.ramongarver.poppy.api.repository.WorkGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Profile("!prod")
@AllArgsConstructor
@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final VolunteerRepository volunteerRepository;
    private final ActivityRepository activityRepository;
    private final WorkGroupRepository workGroupRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ResourceLoader resourceLoader;

    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) {
        loadFromJsonAndSave("users.json", userRepository, User.class);
        loadFromJsonAndSave("volunteers.json", volunteerRepository, Volunteer.class);
        loadFromJsonAndSave("activities.json", activityRepository, Activity.class);
        loadFromJsonAndSave("workGroups.json", workGroupRepository, WorkGroup.class);
    }

    private <T> void loadFromJsonAndSave(String filename, JpaRepository<T, Long> repository, Class<T> type) {
        final String commonPassword = passwordEncoder.encode("password");

        try {
            final Resource resource = resourceLoader.getResource("classpath:json/" + filename);
            final InputStream inputStream = resource.getInputStream();

            final List<T> entities;
            try {
                entities = objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
            } catch (IOException e) {
                throw new RuntimeException("Failed to parse entities from " + filename, e);
            }

            entities.forEach(entity -> {
                if (entity instanceof User user) {
                    user.setPassword(passwordEncoder.encode(commonPassword));
                }
            });

            repository.saveAll(entities);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load entities from " + filename, e);
        }
    }

}
