import { Container, Card, CardHeader, CardTable, TdRight } from "./style";

interface CardRow {
  label: string;
  id: string;
  value: string;
}

interface CardData {
  id: string;
  title: string;
  span: number;
  rows: CardRow[];
}

const MOCK_CASE_CARDS: CardData[] = [
  {
    id: "case",
    title: "Sag",
    span: 1,
    rows: [
      { label: "OPRETTET", id: "createdAt", value: "18.06.2025" },
      { label: "ARRANGØR-ID", id: "organizerId", value: "EM12345" },
      { label: "EVENT-ID", id: "eventId", value: "F12345" },
    ],
  },
  {
    id: "payment",
    title: "Betaling",
    span: 2,
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
    span: 1,
    rows: [
      { label: "KONTAKTPERSON", id: "contactPerson", value: "METTE JENSEN" },
      { label: "E-MAIL", id: "mail", value: "METTE@AALBORG.DK" },
      { label: "TELEFON", id: "phoneNumber", value: "+45 99 31 XX XX" },
    ],
  },
];

export default function CaseCards() {
  const columns = MOCK_CASE_CARDS.map(
    (card) => `minmax(0, ${card.span}fr)`,
  ).join(" "); // => `minmax(0, 1fr) minmax(0, 2fr) minmax(0, 1fr)`

  return (
    <Container $columns={columns}>
      {MOCK_CASE_CARDS.map((card) => (
        <Card key={card.id}>
          <CardHeader>{card.title}</CardHeader>
          <CardTable>
            <tbody>
              {card.rows.map((row) => (
                <tr key={row.id}>
                  <td>{row.label}</td>
                  <TdRight align="right">{row.value}</TdRight>
                </tr>
              ))}
            </tbody>
          </CardTable>
        </Card>
      ))}
    </Container>
  );
}
