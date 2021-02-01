package Domain;

import Repository.IRepository;

import java.security.KeyException;

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
