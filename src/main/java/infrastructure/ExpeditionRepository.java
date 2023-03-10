package infrastructure;

import domain.Expedition;
import domain.Participant;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ExpeditionRepository {

    Expedition safeExpedition(Expedition expedition);

    Expedition findExpeditionById(UUID uuid);

    Expedition findExpeditionByDate(LocalDate date);

    List<Expedition> findExpeditionByLeader(Participant participant);

    List<Expedition> findFutureExpeditions(LocalDate startDate);

    List<Expedition> findAllExpeditions();
}
