package com.place2book.internal.dataflow;

import java.util.Optional;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/example-notes")
public class Controller {
    // counstructor based dependency injection //
    private final ExampleNoteRepository noteRepo;
    private final JdbcTemplate jdbctemplate;

     // JDBC templaten is ready for use in the Controller by dependency injection //
    public Controller(ExampleNoteRepository noteRepo, JdbcTemplate jdbctemplate){
        this.noteRepo = noteRepo;
        this.jdbctemplate = jdbctemplate;
    }

    @GetMapping("")
    public ArrayList<Note> allNotes(){
        return noteRepo.findAll();
    }

    @GetMapping("/{id}")
    public Note noteByID(@PathVariable int id){
        // unpack the optional from the .findbyID method //
        Optional<Note> theNoteOptional = noteRepo.findByID(id);

        // if note is not in the optional it will throw an exception (NoSuchElementException) //
        try {
            Note theNote = theNoteOptional.get();
            return theNote;
        } catch (NoSuchElementException e) {
            // TODO: handle exception
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found");
        }        
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public int Insert(@Valid @RequestBody CreateExampleNoteRequest text){
        Optional<Note> AddedNoteOptional = noteRepo.addNote(text);

        // if note is not in the optional it will throw an exception (NoSuchElementException) //
        try{
            Note theAddedNote = AddedNoteOptional.get();
            return theAddedNote.id();
        } catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something wrong happened when inserting the note");
        }
    }
}
