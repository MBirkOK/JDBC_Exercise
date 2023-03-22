package application;

import domain.Expedition;
import infrastructure.ExpeditionRepository;
import infrastructure.ExpeditionRepositoryImpl;

import java.security.spec.ECPoint;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ExpeditionService {
    private ExpeditionRepositoryImpl expeditionRepository;

    public ExpeditionService(ExpeditionRepositoryImpl expeditionRepository) {
        this.expeditionRepository = expeditionRepository;
    }

    public ExpeditionService() {
        this.expeditionRepository = new ExpeditionRepositoryImpl();
    }

    public int createExpedition(Expedition expedition) {
        return this.expeditionRepository.saveExpedition(expedition);
    }

    public Optional<Expedition> getExpeditionById(int id) {
        Optional<Expedition> expedition = this.expeditionRepository.findExpeditionById(id);
        return expedition;
    }

    public List<Expedition> getAllExpeditions() {
        return this.expeditionRepository.findAllExpeditions();
    }

    public List<Expedition> getFutureExpeditions(){
        return this.expeditionRepository.findExpeditionByDate(LocalDate.now());
    }
}
