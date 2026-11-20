import { useState } from "react";
import type { Note } from "../../api/notes";
import { useCreateNote } from "../../hooks/useNotes";
import {
  Card,
  NotesHeader,
  HeaderLabel,
  HeaderTitle,
  Badge,
  NoteItem,
  NoteIcon,
  NoteText,
  NoteMeta,
  NewNoteForm,
  NoteLabel,
  NoteInput,
  AddButton,
} from "./style";
import { formatDate, formatTime } from "../../utils/format";

type Props = { caseId: number; notes: Note[] };

export default function InternalNotes({ caseId, notes }: Props) {
  const [text, setText] = useState("");
  const createNote = useCreateNote(caseId);

  // Nyeste først
  const sortedNotes = [...notes].sort((a, b) =>
    b.createdAt.localeCompare(a.createdAt),
  );
  const noteCount = notes.length;

  const handleAdd = () => {
    const trimmed = text.trim();
    if (!trimmed) return;

    createNote.mutate(trimmed, {
      onSuccess: () => setText(""),
    });
  };

  return (
    <Card>
      <NotesHeader>
        <div>
          <HeaderLabel>KUN TIL INTERN BRUG</HeaderLabel>
          <HeaderTitle>Interne noter</HeaderTitle>
        </div>
        <Badge>
          {noteCount} {noteCount === 1 ? "NOTAT" : "NOTATER"}
        </Badge>
      </NotesHeader>

      {sortedNotes.length === 0 && (
        <NoteText>Ingen interne notater endnu.</NoteText>
      )}

      {sortedNotes.map((note) => (
        <NoteItem key={note.id}>
          <NoteIcon>{note.createdBy.charAt(0).toUpperCase()}</NoteIcon>
          <div>
            <NoteText>{note.text}</NoteText>
            <NoteMeta>
              {formatDate(note.createdAt)} kl. {formatTime(note.createdAt)} ·{" "}
              {note.createdBy}
            </NoteMeta>
          </div>
        </NoteItem>
      ))}

      <NewNoteForm>
        <NoteLabel htmlFor="new-note">NYT INTERNT NOTAT</NoteLabel>
        <NoteInput
          id="new-note"
          placeholder="Skriv et notat..."
          rows={4}
          value={text}
          onChange={(e) => setText(e.target.value)}
        />
        {createNote.isError && (
          <NoteMeta>Notatet kunne ikke gemmes. Prøv igen.</NoteMeta>
        )}
        <AddButton
          type="button"
          onClick={handleAdd}
          disabled={createNote.isPending || !text.trim()}
        >
          {createNote.isPending ? "GEMMER…" : "TILFØJ NOTAT"}
        </AddButton>
      </NewNoteForm>
    </Card>
  );
}
