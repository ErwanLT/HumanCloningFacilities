package com.erwan.human.services;

import com.erwan.human.dao.CloneRepository;
import com.erwan.human.domaine.kamino.Clone;
import com.erwan.human.domaine.kamino.CloneCreationRequest;
import com.mifmif.common.regex.Generex;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CloningService {
    private final CloneRepository cloneRepository;
    private final static String REPUBLIC_GALACTIC = "Republic Galactic";

    public Clone createClone(CloneCreationRequest cloneCreationRequest) {
        Generex generex = new Generex("[A-Z]{2}-[0-9]{3}-[A-Z]{2}");
        Clone clone = new Clone();
        clone.setAffiliation(REPUBLIC_GALACTIC);
        clone.setCodeName(generex.random());
        clone.setPlatoon(cloneCreationRequest.getPlatoon());
        clone.setType(cloneCreationRequest.getType());
        return cloneRepository.save(clone);
    }

    public List<Clone> findAllClones() {
        return cloneRepository.findAll();
    }

    public void deleteClone(Clone clone) {
        cloneRepository.delete(clone);
    }

    public List<Clone> executeOrder66() {
        List<Clone> clones = cloneRepository.findAll();
        clones.forEach(clone -> clone.setAffiliation("Galactic Empire"));
        return cloneRepository.saveAll(clones);
    }

    public Optional<Clone> findOneClone(Long id) {
        return cloneRepository.findById(id);
    }
}
