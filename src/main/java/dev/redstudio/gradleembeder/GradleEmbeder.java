package dev.redstudio.gradleembeder;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.tasks.bundling.Jar;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//    /$$$$$$                           /$$ /$$                 /$$$$$$$$               /$$                       /$$
//   /$$__  $$                         | $$| $$                | $$_____/              | $$                      | $$
//  | $$  \__/  /$$$$$$  /$$$$$$   /$$$$$$$| $$  /$$$$$$       | $$       /$$$$$$/$$$$ | $$$$$$$   /$$$$$$   /$$$$$$$  /$$$$$$   /$$$$$$
//  | $$ /$$$$ /$$__  $$|____  $$ /$$__  $$| $$ /$$__  $$      | $$$$$   | $$_  $$_  $$| $$__  $$ /$$__  $$ /$$__  $$ /$$__  $$ /$$__  $$
//  | $$|_  $$| $$  \__/ /$$$$$$$| $$  | $$| $$| $$$$$$$$      | $$__/   | $$ \ $$ \ $$| $$  \ $$| $$$$$$$$| $$  | $$| $$$$$$$$| $$  \__/
//  | $$  \ $$| $$      /$$__  $$| $$  | $$| $$| $$_____/      | $$      | $$ | $$ | $$| $$  | $$| $$_____/| $$  | $$| $$_____/| $$
//  |  $$$$$$/| $$     |  $$$$$$$|  $$$$$$$| $$|  $$$$$$$      | $$$$$$$$| $$ | $$ | $$| $$$$$$$/|  $$$$$$$|  $$$$$$$|  $$$$$$$| $$
//   \______/ |__/      \_______/ \_______/|__/ \_______/      |________/|__/ |__/ |__/|_______/  \_______/ \_______/ \_______/|__/
public final class GradleEmbeder implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        final Configuration embedConfig = project.getConfigurations().create("embed", configuration -> configuration.setDescription("Included in output JAR"));

        project.getConfigurations().getByName("implementation").extendsFrom(embedConfig);

        final Jar jarTask = (Jar) project.getTasks().getByName("jar");

        jarTask.setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE);
        jarTask.dependsOn(embedConfig);

        jarTask.from(project.provider(() -> {
            final List<Object> mappedFiles = new ArrayList<>();

            for (final File file : project.files(embedConfig))
                mappedFiles.add(file.isDirectory() ? file : project.zipTree(file));

            return Collections.unmodifiableList(mappedFiles);
        }));
    }
}
