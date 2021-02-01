package Domain.Operations;

import Domain.Entity;
import Domain.Operations.UndoRedoOperation;
import Repository.IRepository;

public class DeleteOperation<T extends Entity> extends UndoRedoOperation<T> {

    private T addedEntity;

    public DeleteOperation(IRepository repository, T addedEntity) {
        super(repository);
        this.addedEntity = addedEntity;
    }

    @Override
    public void undo() {

        this.repository.create(addedEntity, addedEntity.getId());
    }

    @Override
    public void redo() {
        this.repository.delete(addedEntity.getId());
    }
}
