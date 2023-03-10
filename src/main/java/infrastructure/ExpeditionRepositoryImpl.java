package infrastructure;

import domain.Expedition;
import domain.Participant;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ExpeditionRepositoryImpl implements ExpeditionRepository{
    @Override
    public Expedition safeExpedition(Expedition expedition) {
        return null;
    }

    @Override
    public Expedition findExpeditionById(UUID uuid) {
        return null;
    }

    @Override
    public Expedition findExpeditionByDate(LocalDate date) {
        return null;
    }

    @Override
    public List<Expedition> findExpeditionByLeader(Participant participant) {
        return null;
    }

    @Override
    public List<Expedition> findFutureExpeditions(LocalDate startDate) {
        return null;
    }

    @Override
    public List<Expedition> findAllExpeditions() {
        return null;
    }
}
