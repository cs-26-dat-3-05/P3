import { Container, TableCard } from "./style";

interface CardRow {
  label: string;
  id: string;
  value: string;
}

interface CardData {
  id: string;
  title: string;
  width: string;
  rows: CardRow[];
}

const MOCK_CASE_CARDS: CardData[] = [
  {
    id: "case",
    title: "Sag",
    width: "25%",
    rows: [
      { label: "OPRETTET", id: "cretedAt", value: "18.06.2025" },
      { label: "ARRANGØR-ID", id: "organizerId", value: "EM12345" },
      { label: "EVENT-ID", id: "eventId", value: "F12345" },
    ],
  },
  {
    id: "payment",
    title: "Betaling",
    width: "50%",
    rows: [
      { label: "BELØB", id: "amount", value: "DKK 48.520,00" },
      {
        label: "FORKERT KONTONUMMER",
        id: "accMain",
        value: "1234 / 567890123",
      },
      {
        label: "KORREKT KONTONUMMER",
        id: "accPrefix",
        value: "IKKE INDTASTET",
      },
      { label: "BETALINGSDATO", id: "dueDate", value: "AFVENTER KONTONUMMER" },
    ],
  },
  {
    id: "organizer",
    title: "Arrangør",
    width: "25%",
    rows: [
      { label: "KONTAKTPERSON", id: "contactPerson", value: "METTE JENSEN" },
      { label: "E-MAIL", id: "mail", value: "METTE@AALBORG.DK" },
      { label: "TELEFON", id: "phoneNumber", value: "+45 99 31 XX XX" },
    ],
  },
];

export default function CaseCards() {
  return (
    <Container>
      {MOCK_CASE_CARDS.map((card) => (
        <TableCard key={card.id} $width={card.width}>
          <thead>
            <tr>
              <th colSpan={2}>Sag</th>
            </tr>
          </thead>
          <tbody>
            {card.rows.map((row, index) => (
              <tr key={index}>
                <td>{row.label}</td>
                <td align="right">{row.value}</td>
              </tr>
            ))}
          </tbody>
        </TableCard>
      ))}
    </Container>
  );
}
