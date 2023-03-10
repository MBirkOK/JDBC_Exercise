package application;

import domain.Expedition;
import infrastructure.ExpeditionRepository;
import infrastructure.ExpeditionRepositoryImpl;

import java.time.LocalDate;

public class ExpeditionService {
    private ExpeditionRepositoryImpl expeditionRepository;

    public ExpeditionService(ExpeditionRepositoryImpl expeditionRepository) {
        this.expeditionRepository = expeditionRepository;
    }

    public ExpeditionService() {
        this.expeditionRepository = new ExpeditionRepositoryImpl();
    }

    public Expedition createExpedition(Expedition expedition){
        return this.expeditionRepository.safeExpedition(expedition);
    }
}
