package com.anurag.backend.controller;

import com.anurag.backend.model.Item;
import com.anurag.backend.repository.ItemRepository;
import com.anurag.backend.service.MatchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/matches")
@CrossOrigin(origins = "*")
public class MatchController {

    private final ItemRepository itemRepository;
    private final MatchingService matchingService;

    public MatchController(ItemRepository itemRepository,
                           MatchingService matchingService) {
        this.itemRepository = itemRepository;
        this.matchingService = matchingService;
    }

    @GetMapping("/{lostItemId}")
    public ResponseEntity<List<Map<String, Object>>> findMatches(
            @PathVariable Long lostItemId) {

        Optional<Item> lostItemOptional = itemRepository.findById(lostItemId);

        if (lostItemOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Item lostItem = lostItemOptional.get();

        List<Item> allItems = itemRepository.findAll();
        List<Map<String, Object>> matches = new ArrayList<>();

        for (Item foundItem : allItems) {

            if (!"FOUND".equalsIgnoreCase(foundItem.getType())) {
                continue;
            }

            int score = matchingService.calculateMatchScore(
                    lostItem,
                    foundItem
            );

            if (score > 0) {
                Map<String, Object> result = new LinkedHashMap<>();
                result.put("item", foundItem);
                result.put("matchScore", score);

                matches.add(result);
            }
        }

        matches.sort((a, b) ->
                Integer.compare(
                        (Integer) b.get("matchScore"),
                        (Integer) a.get("matchScore")
                )
        );

        return ResponseEntity.ok(matches);
    }
}