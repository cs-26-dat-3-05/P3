import {
    Card, NotesHeader, HeaderLabel, HeaderTitle, Badge,
    NoteItem, NoteIcon, NoteText, NoteMeta,
    NewNoteForm, NoteLabel, NoteInput, AddButton,
} from "./style";

export default function InternalNotes({ note }: { note: string }) {
    const notes = [{ id: 1, text: note, author: "Sagsoversigt" }];
    const noteCount = notes.length;

    return (
        <Card>
            <NotesHeader>
                <div>
                    <HeaderLabel>KUN TIL INTERN BRUG</HeaderLabel>
                    <HeaderTitle>Interne noter</HeaderTitle>    
                </div>
                <Badge>{noteCount} {noteCount === 1 ? "NOTAT" : "NOTATER"}</Badge>
            </NotesHeader>

            {notes.map((item) => (
                <NoteItem key={item.id}>
                    <NoteIcon>N</NoteIcon>
                    <div>
                        <NoteText>{item.text}</NoteText>
                        <NoteMeta>{item.author}</NoteMeta>
                    </div>
                </NoteItem>
            ))}
            <NewNoteForm>
                <NoteLabel htmlFor="new-note">NYT INTERNT NOTAT</NoteLabel>
                <NoteInput id="new-note" placeholder="Skriv et notat..." rows={4} ></NoteInput>
                <AddButton>TILFØJ NOTAT</AddButton>
            </NewNoteForm>
        </Card>
    );
}