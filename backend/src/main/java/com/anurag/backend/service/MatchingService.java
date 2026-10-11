package com.anurag.backend.service;

import com.anurag.backend.model.Item;
import org.springframework.stereotype.Service;

@Service
public class MatchingService {

    public int calculateMatchScore(Item lost, Item found) {

        int score = 0;

        // Brand match - 25%
        if (same(lost.getBrand(), found.getBrand())) {
            score += 25;
        }

        // Category match - 20%
        if (same(lost.getCategory(), found.getCategory())) {
            score += 20;
        }

        // Color match - 20%
        if (same(lost.getColor(), found.getColor())) {
            score += 20;
        }

        // Location match - 20%
        if (same(lost.getLocation(), found.getLocation())) {
            score += 20;
        }

        // Title match - 10%
        if (same(lost.getTitle(), found.getTitle())) {
            score += 10;
        }

        // Description match - 5%
        if (lost.getDescription() != null &&
            found.getDescription() != null &&
            lost.getDescription().toLowerCase()
                .contains(found.getDescription().toLowerCase())) {
            score += 5;
        }

        return score;
    }

    private boolean same(String value1, String value2) {
        if (value1 == null || value2 == null) {
            return false;
        }

        return value1.trim().equalsIgnoreCase(value2.trim());
    }
}

