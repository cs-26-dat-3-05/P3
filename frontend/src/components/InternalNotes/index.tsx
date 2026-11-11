import {
    Card, NotesHeader, HeaderLabel, HeaderTitle, Badge,
    NoteItem, NoteIcon, NoteText, NoteMeta,
    NewNoteForm, NoteLabel, NoteInput, AddButton,
} from "./style";

interface Note {
    text: string;
    date: string;
    time: string;
    author: string;
    id: number;
}

const MOCK_NOTES: Note[] = [
    {
        id: 1,
        text: "Arrangørens kontaktoplysninger er kontrolleret",
        date: "18.06.2025",
        time: "11.00",
        author: "Louise"
    },
];

export default function InternalNotes() {
    const noteCount = MOCK_NOTES.length;

    return (
        <Card>
            <NotesHeader>
                <div>
                    <HeaderLabel>KUN TIL INTERN BRUG</HeaderLabel>
                    <HeaderTitle>Interne noter</HeaderTitle>    
                </div>
                <Badge>{noteCount} {noteCount === 1 ? "NOTAT" : "NOTATER"}</Badge>
            </NotesHeader>

            {MOCK_NOTES.map((note) => (
                <NoteItem key={note.id}>
                    <NoteIcon>N</NoteIcon>
                    <div>
                        <NoteText>{note.text}</NoteText>
                        <NoteMeta>{note.date} kl. {note.time} · {note.author}</NoteMeta>
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