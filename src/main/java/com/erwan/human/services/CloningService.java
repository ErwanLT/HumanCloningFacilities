package com.erwan.human.services;

import com.erwan.human.dao.CloneRepository;
import com.erwan.human.domaine.kamino.Clone;
import com.erwan.human.domaine.kamino.CloneByCategorie;
import com.erwan.human.reference.CloneType;
import com.mifmif.common.regex.Generex;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CloningService {
    private final CloneRepository cloneRepository;
    private final static String REPUBLIC_GALACTIC = "Republic Galactic";
    private final static List<String> PLATOONS = List.of("501", "99", "123", "666");

    @PostConstruct
    private void init(){
        for (int i = 0; i < 30; i++){
            createClone();
        }
    }

    public Clone createClone() {
        Generex generex = new Generex("[A-Z]{2}-[0-9]{3}-[A-Z]{2}");
        Clone clone = new Clone();
        clone.setAffiliation(REPUBLIC_GALACTIC);
        clone.setCodeName(generex.random());
        Random rand = new Random();
        clone.setPlatoon(PLATOONS.get(rand.nextInt(PLATOONS.size())));
        clone.setType(CloneType.values()[new Random().nextInt(CloneType.values().length)]);
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

    public List<CloneByCategorie> groupCloneByCategories() {
        var myCategories = cloneRepository.findAll()
                .stream().collect(Collectors.groupingBy(Clone::getType,Collectors.counting()));
        List<CloneByCategorie> list = new ArrayList<>();
        for (Map.Entry<CloneType, Long> entry : myCategories.entrySet()) {
            list.add(new CloneByCategorie(entry.getKey(), entry.getValue()));
        }
        return list;
    }
}
