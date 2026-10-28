package com.procompras.engine;

import com.procompras.model.RecipeLaAbuela;
import com.procompras.shared.ExtractCSV;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.stream.Stream;

public class RecipeData {
    private final Resource filePathResource = new ClassPathResource("datasets/recetasdelaabuela.csv");

    public List<RecipeLaAbuela> loadData() {
        try {
            String filePath = filePathResource.getFile().getAbsolutePath();

            try (Stream<RecipeLaAbuela> recipes = ExtractCSV.<RecipeLaAbuela>read(filePath, RecipeLaAbuela::fromRow)) {
                return recipes.toList();
            }

        } catch (Exception e) {
            throw new IllegalStateException("Error loading recipes", e);
        }
    }
}
