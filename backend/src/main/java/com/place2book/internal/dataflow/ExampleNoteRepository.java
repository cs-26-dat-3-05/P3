package com.place2book.internal.dataflow;

import java.time.LocalDateTime; 
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository 
public class ExampleNoteRepository {
    private ArrayList<Note> Notes = new ArrayList<>();

    @PostConstruct // Post Construct annotationen runs as soon as it is initialized as a Bean //
    public void initRepo(){
        // Initialize with three seed notes //
        for(int i=1; i<=3; i++){
            Note tmpNote = new Note(i, "Hello67", LocalDateTime.now());
            Notes.add(tmpNote);
        }
    }
    
    public Optional<Note> findByID(int id){
        for(int j=0; j<Notes.size(); j++){
            if(id == Notes.get(j).id()){
                // when the note when for the ID is found in the ArrayList, just return it as an optional //
                Optional<Note> theNote = Optional.of(Notes.get(j));
                return theNote;
            }
        }
        // Not found then return an empty optional //
        Optional<Note> theNote = Optional.empty();
        return theNote;
    }

    public Optional<Note> addNote(CreateExampleNoteRequest DTOtext){
        // find the length of current repo to find the next id //
        int nextID = Notes.size()+1;
        String text = DTOtext.note();

        Note newNote = new Note(nextID, text, LocalDateTime.now());
        Notes.add(newNote);


        // check if it has added succesfully //
        if(Notes.getLast().id() == nextID){
            Optional<Note> OptionalNewNote = Optional.of(newNote);
            return OptionalNewNote;
        }
        else{
            Optional<Note> OptionalNewNote = Optional.empty();
            return OptionalNewNote;
        }
    }

    public ArrayList<Note> getNoteList(){
        return Notes;
    }
    
}
