package com.place2book.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ArrayList;

import com.place2book.internal.dataflow.CreateExampleNoteRequest;
import com.place2book.internal.dataflow.ExampleNoteRepository;
import com.place2book.internal.dataflow.Note;

import java.lang.Class;

public class NoteRepoTest {

    @Test 
    @DisplayName("Fetching a negative OR nonexisting ID, will return an empty optional")
    void NegativeID(){
        ExampleNoteRepository testRepo = new ExampleNoteRepository();
        testRepo.initRepo();
        Optional<Note> returnedOptional = testRepo.findByID(-67);
        
        
        assertTrue(returnedOptional.isEmpty());
    }

    @Test 
    @DisplayName("Fetching a nonexisting ID, will return an empty optional")
    void nonexistingID(){
        ExampleNoteRepository testRepo = new ExampleNoteRepository();
        testRepo.initRepo();
        Optional<Note> returnedOptional = testRepo.findByID(67676767);
        
        assertTrue(returnedOptional.isEmpty());
    }

    @Test
    @DisplayName("Fetching an existing ID, will return the note record itself from the repo")
    void returnNote(){
        ExampleNoteRepository testRepo = new ExampleNoteRepository();
        // for initRepo two notes has been created with ID of 1, 2 and 3 //
        testRepo.initRepo();

        Optional<Note> returnedOptional = testRepo.findByID(1);
        Note returnedNote = returnedOptional.get();
        
        assertEquals(1, returnedNote.id());
    }


    @Test
    @DisplayName("The inserted note gets inserted into the repo")
    void InsertNote(){
        ExampleNoteRepository testRepo = new ExampleNoteRepository();
        // for initRepo two notes has been created with ID of 1, 2 and 3 //
        testRepo.initRepo();

        // find the next id for the inserted note //
        int nextID = testRepo.getNoteList().size()+1;


        // create Mock DTO //
        String insertedString = "676767676767";
        CreateExampleNoteRequest MockDTO = new CreateExampleNoteRequest(insertedString);

        Optional<Note> returnedOptional = testRepo.addNote(MockDTO);
        Note returnedInsertedNote = returnedOptional.get();
        
        // check if the new note has been inserted correctly with the right ID //
        assertEquals(nextID, returnedInsertedNote.id());
        assertEquals(insertedString, returnedInsertedNote.text());
        //checks if the returned note is the same as the last note in the list //
        assertEquals(returnedInsertedNote, testRepo.getNoteList().get(nextID-1));
    }

    @Test
    @DisplayName("Checks if all Notes are returned from the repo")
    void findAll(){
        ExampleNoteRepository testRepo = new ExampleNoteRepository();
        // for initRepo two notes has been created with ID of 1, 2 and 3 //
        testRepo.initRepo();
    
        assertEquals(testRepo.getNoteList().size(), 3);

        for(int i=0; i<testRepo.getNoteList().size(); i++){
            assertTrue(testRepo.getNoteList().get(i) instanceof Note);
        }
        
    }

}
