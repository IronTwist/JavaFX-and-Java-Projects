package Service;

import Domain.Entity;
import Domain.Movie;
import Domain.Reservation;
import Domain.Operations.UndoRedoOperation;
import Repository.IRepository;

import java.util.Stack;

public class UndoRedoService {
    //TODO move undo and redo logic here
    private IRepository<Movie> movieIRepository;
    private IRepository<Reservation> reservationIRepository;

    private Stack<UndoRedoOperation<? extends Entity>> undoStack;
    private Stack<UndoRedoOperation<? extends Entity>> redoStack;

    public UndoRedoService(IRepository<Movie> movieIRepository, IRepository<Reservation> reservationIRepository) {
        this.movieIRepository = movieIRepository;
        this.reservationIRepository = reservationIRepository;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public void add(UndoRedoOperation <? extends Entity> undoRedoOperation){
        this.undoStack.push(undoRedoOperation);
        this.redoStack.clear();
    }

    public boolean undo(){
        if(!this.undoStack.isEmpty()){
            UndoRedoOperation<? extends Entity> entityUndoRedoOperation = this.undoStack.pop();
            entityUndoRedoOperation.undo();
            redoStack.push(entityUndoRedoOperation);
            return true;
        }
        return false;
    }

    public boolean redo(){
        if(!this.redoStack.isEmpty()){
            UndoRedoOperation<? extends Entity> entityUndoRedoOperation = this.redoStack.pop();
            entityUndoRedoOperation.redo();
            undoStack.push(entityUndoRedoOperation);
            return true;
        }
        return false;
    }

    public Stack<UndoRedoOperation<? extends  Entity>> getUndoStack() {
        return undoStack;
    }

    public void setUndoStack(Stack<UndoRedoOperation<? extends  Entity>> undoStack) {
        this.undoStack = undoStack;
    }

    public Stack<UndoRedoOperation<? extends  Entity>> getRedoStack() {
        return redoStack;
    }

    public void setRedoStack(Stack<UndoRedoOperation<? extends  Entity>> redoStack) {
        this.redoStack = redoStack;
    }
}
