package application.services;

import domain.Literature;
import domain.Person;
import infrastructure.LiteratureRepository;

import java.util.List;
import java.util.UUID;

public class LiteraturService {

    private LiteratureRepository literatureRepository;

    public LiteraturService(LiteratureRepository literatureRepository) {
        this.literatureRepository = literatureRepository;
    }

    public LiteraturService() {
        this.literatureRepository = new LiteratureRepository();
    }

    public List<Literature> getALlLiterature() {
        return this.literatureRepository.findAll();
    }

    public Literature findLiteratureWithId(UUID id) {
        return this.literatureRepository.findLiteratureById(id);
    }

    public void createLiterature(Literature literature) {
        this.literatureRepository.safeLiterature(literature);
    }

    public void createLiteratureFromList(List<String> information) {
        Person person = new Person(information.get(1), information.get(2));
        Literature literature = new Literature(information.get(0), person, Integer.parseInt(information.get(3)),
            information.get(4), information.get(5));
        createLiterature(literature);
    }

    public void updateLiterature(String literature, List<String> information) {
        Literature changedLiterature = this.findLiteratureWithId(UUID.fromString(literature));
        changedLiterature.changeTitle(information.get(0));
        changedLiterature.getAuthor().changeFirstName(information.get(1));
        changedLiterature.getAuthor().changeLastName(information.get(2));
        changedLiterature.changeRelease(Integer.parseInt(information.get(3)));
        changedLiterature.changeEdition(information.get(4));
        changedLiterature.changePublisher(information.get(5));

        this.literatureRepository.update(changedLiterature);
    }

    public void delete(String id) {
        Literature literature = this.literatureRepository.findLiteratureById(UUID.fromString(id));
        this.literatureRepository.delete(literature);
    }
}
