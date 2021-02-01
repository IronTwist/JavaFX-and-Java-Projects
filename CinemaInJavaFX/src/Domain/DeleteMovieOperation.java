package Domain;

import Repository.IRepository;

import java.security.KeyException;
import java.util.List;

public class DeleteMovieOperation<T extends Entity> extends UndoRedoOperation<T> {

    private T entity;
    private IRepository<Reservation> reservationIRepository;
    private List<Reservation> reservationList;

    public DeleteMovieOperation(IRepository repository,
                                IRepository<Reservation> reservationIRepository, List<Reservation> reservationList,
                                T entity) {
        super(repository);
        this.reservationIRepository = reservationIRepository;
        this.reservationList = reservationList;
        this.entity = entity;
    }

    @Override
    public void undo() {

        this.repository.create(entity, entity.getId());
        for(Reservation r: reservationList){
            this.reservationIRepository.create(r,r.getId());
        }
    }

    @Override
    public void redo() {
        this.repository.delete(entity.getId());
        for(Reservation r: reservationList){
            this.reservationIRepository.delete(r.getId());
        }
    }
}
