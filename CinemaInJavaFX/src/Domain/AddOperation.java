package Domain;

import Repository.IRepository;

import java.security.KeyException;

public class AddOperation<T extends Entity> extends UndoRedoOperation<T>{

    private T addedEntity;

    public AddOperation(IRepository<T> repository, T addedEntity){
        super(repository);
        this.addedEntity = addedEntity;
    }

    @Override
    public void undo() {
        this.repository.delete(addedEntity.getId());
    }

    public void redo(){
        this.repository.create(addedEntity);
    }
}
