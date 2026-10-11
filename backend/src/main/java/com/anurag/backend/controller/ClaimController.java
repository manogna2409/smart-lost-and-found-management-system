package com.anurag.backend.controller;

import com.anurag.backend.model.Claim;
import com.anurag.backend.service.ClaimService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@CrossOrigin(origins = "*")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    // Create a new claim
    @PostMapping
    public ResponseEntity<Claim> createClaim(@RequestBody Claim claim) {
        return ResponseEntity.ok(claimService.createClaim(claim));
    }

    // Get all claims
    @GetMapping
    public ResponseEntity<List<Claim>> getAllClaims() {
        return ResponseEntity.ok(claimService.getAllClaims());
    }

    // Get claims of a particular user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Claim>> getClaimsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                claimService.getClaimsByUser(userId)
        );
    }

    // Approve a claim
    @PutMapping("/{claimId}/approve")
    public ResponseEntity<Claim> approveClaim(
            @PathVariable Long claimId) {

        return ResponseEntity.ok(
                claimService.approveClaim(claimId)
        );
    }

    // Reject a claim
    @PutMapping("/{claimId}/reject")
    public ResponseEntity<Claim> rejectClaim(
            @PathVariable Long claimId) {

        return ResponseEntity.ok(
                claimService.rejectClaim(claimId)
        );
    }
}
