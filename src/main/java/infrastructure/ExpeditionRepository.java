package infrastructure;

import domain.Expedition;
import domain.Participant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpeditionRepository {

    int saveExpedition(Expedition expedition);

    Optional<Expedition> findExpeditionById(int id);

    List<Expedition> findExpeditionByDate(LocalDate date);

    List<Expedition> findExpeditionByLeader(Participant participant);

    List<Expedition> findFutureExpeditions(LocalDate startDate);

    List<Expedition> findAllExpeditions();
}
