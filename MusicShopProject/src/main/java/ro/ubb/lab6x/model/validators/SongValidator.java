package ro.ubb.lab6x.model.validators;

import ro.ubb.lab6x.model.Exceptions.ValidatorException;
import ro.ubb.lab6x.model.Song;

import java.time.Year;

public class SongValidator implements Validator<Song> {
    int currentYear = Integer.parseInt(Year.now().toString());

    @Override
    public void validate(Song entity) {
        StringBuilder stringBuilder = new StringBuilder("");
        if(entity.getTitle().trim().isEmpty()){
            stringBuilder.append("a song must have a name\n");
        }
        if(entity.getYearOfRelease() > currentYear || entity.getYearOfRelease() < 1800){
            stringBuilder.append("invalid year\n");
        }
        if(entity.getPrice() < 0.0){
            stringBuilder.append("invalid price\n");
        }
        if(!stringBuilder.toString().equals("")){
            throw new ValidatorException(stringBuilder.toString());
        }

    }
}
