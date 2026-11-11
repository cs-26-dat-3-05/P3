import { styled } from "styled-components";

export const Card = styled.div.attrs({ className: "box" })`
    && {
        padding: 0;
    }
`;

export const NotesHeader = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 16px 20px;
    border-bottom: var(--border-sm);
    background: #e4e1d9;
`;

export const HeaderLabel = styled.p`
    margin: 0;
    font-size: 11px;
    color: #999;
`;

export const HeaderTitle = styled.p`
    margin: 4px 0 0;
    font-weight: bold;
`;

export const Badge = styled.span`
    border: var(--border-sm);
    padding: 4px 10px;
    font-size: 11px;
    border-radius: 999px;
`;

export const NoteItem = styled.div`
    display: flex;
    gap: 16px;
    padding: 16px 20px;
    border-bottom: 1px solid #ddd;
`;

export const NoteIcon = styled.span`
    display: flex;
    align-items: center;
    justify-content: center;
    width: 32px;
    height: 32px;
    border: var(--border-sm);
    font-size: 12px;
    flex-shrink: 0;
`;

export const NoteText = styled.p`
    margin: 0;
    font-size: 14px;
`;

export const NoteMeta = styled.p`
    margin: 4px 0 0;
    font-size: 11px;
    color: #999;
`;

export const NewNoteForm = styled.div`
    display: flex;
    flex-direction: column;
    gap: 8px;
    padding: 16px 20px;
`;

export const NoteLabel = styled.label`
    font-size: 11px;
`;

export const NoteInput = styled.textarea`
    width: 100%;
    box-sizing: border-box;
    padding: 10px;
    border: var(--border-sm);
    font-family: inherit;
    resize: vertical;
`;

export const AddButton = styled.button`
    padding: 14px;
    background: var(--primary-color);
    color: white;
    border: none;
    box-shadow: var(--shadow-sm);
    cursor: pointer;
`;