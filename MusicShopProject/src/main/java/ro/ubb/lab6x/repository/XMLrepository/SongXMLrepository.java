package ro.ubb.lab6x.repository.XMLrepository;


import org.w3c.dom.Element;

import ro.ubb.lab6x.model.Exceptions.ValidatorException;
import ro.ubb.lab6x.model.Song;
import ro.ubb.lab6x.model.validators.Validator;

import java.util.Optional;


public class SongXMLrepository extends XMLrepository<Song> {

    public SongXMLrepository(Validator<Song> validator, String filepath) {
        super(validator, filepath);
    }

    @Override
    protected void buildEntityFromElement(Element songElement) {
        long id = Long.parseLong(getTextFromTagName(songElement, "id"));
        String title = getTextFromTagName(songElement, "title");
        int year = Integer.parseInt(getTextFromTagName(songElement, "year"));
        double price = Double.parseDouble(getTextFromTagName(songElement, "price"));
        Song song = new Song(title, year, price);
        song.setId(id);
        super.save(song);
    }

    @Override
    public Optional<Song> save(Song entity) throws ValidatorException {
        Optional<Song> optional = super.save(entity);
        saveToXml(entity);
        return optional;
    }



    @Override
    protected Element createNodeFromEntity(Song entity) {
        Element songElement = document.createElement("song");
        addChildWithTextContent(songElement,"id",String.valueOf(entity.getId()));
        addChildWithTextContent(songElement,"title",entity.getTitle());
        addChildWithTextContent(songElement,"year",Integer.toString(entity.getYearOfRelease()));
        addChildWithTextContent(songElement,"price",String.valueOf(entity.getPrice()));
        return songElement;
    }


}
