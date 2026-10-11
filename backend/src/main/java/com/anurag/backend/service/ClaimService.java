package com.anurag.backend.service;

import com.anurag.backend.model.Claim;
import com.anurag.backend.model.Item;
import com.anurag.backend.repository.ClaimRepository;
import com.anurag.backend.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final ItemRepository itemRepository;

    public ClaimService(ClaimRepository claimRepository,
                        ItemRepository itemRepository) {
        this.claimRepository = claimRepository;
        this.itemRepository = itemRepository;
    }

    public Claim createClaim(Claim claim) {

        claim.setStatus("PENDING");

        return claimRepository.save(claim);
    }

    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    public List<Claim> getClaimsByUser(Long userId) {
        return claimRepository.findByUserId(userId);
    }

    public Claim approveClaim(Long claimId) {

        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        claim.setStatus("APPROVED");

        // Hide/resolve the lost item after verification
        Item lostItem = itemRepository.findById(claim.getLostItemId())
                .orElseThrow(() -> new RuntimeException("Lost item not found"));

        lostItem.setStatus("RESOLVED");
        itemRepository.save(lostItem);

        return claimRepository.save(claim);
    }

    public Claim rejectClaim(Long claimId) {

        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        claim.setStatus("REJECTED");

        return claimRepository.save(claim);
    }
}
