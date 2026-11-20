import type { Case } from "../../api/cases";
import { STATUS_MAP } from "../../constants/caseStatus";
import { formatAmount, formatDate } from "../../utils/format";
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

type Props = { caseData: Case };

/** Vises for felter, backenden ikke leverer endnu */
const EMPTY = "—";

function getCorrectAccountText(caseData: Case): string {
  const step = STATUS_MAP[caseData.status].step;
  if (step < STATUS_MAP.BANK_DETAILS_RECEIVED.step) return "IKKE INDTASTET";
  // TODO: vis det korrekte kontonummer, når backenden sender det med
  return EMPTY;
}

function getPaymentDateText(caseData: Case): string {
  const step = STATUS_MAP[caseData.status].step;
  if (step < STATUS_MAP.BANK_DETAILS_RECEIVED.step)
    return "AFVENTER KONTONUMMER";
  if (step < STATUS_MAP.RETRANSFERRED.step) return "AFVENTER UDBETALING";
  // TODO: vis udbetalingsdatoen, når backenden sender den med
  return EMPTY;
}

function buildCards(caseData: Case): CardData[] {
  return [
    {
      id: "case",
      title: "Sag",
      span: 1,
      rows: [
        {
          label: "OPRETTET",
          id: "createdAt",
          value: formatDate(caseData.createdAt),
        },
        {
          label: "ARRANGØR-ID",
          id: "organizerId",
          value: caseData.organizerId,
        },
        { label: "EVENT-ID", id: "eventId", value: caseData.eventId },
      ],
    },
    {
      id: "payment",
      title: "Betaling",
      span: 2,
      rows: [
        { label: "BELØB", id: "amount", value: formatAmount(caseData.amount) },
        {
          label: "FORKERT KONTONUMMER",
          id: "rejectedAccount",
          value: `${caseData.rejectedRegistrationNumber} / ${caseData.rejectedAccountNumber}`,
        },
        {
          label: "KORREKT KONTONUMMER",
          id: "correctAccount",
          value: getCorrectAccountText(caseData),
        },
        {
          label: "BETALINGSDATO",
          id: "paymentDate",
          value: getPaymentDateText(caseData),
        },
      ],
    },
    {
      id: "organizer",
      title: "Arrangør",
      span: 1,
      // TODO: kontaktoplysninger kræver et arrangør-endpoint
      rows: [
        { label: "KONTAKTPERSON", id: "contactPerson", value: EMPTY },
        { label: "E-MAIL", id: "email", value: EMPTY },
        { label: "TELEFON", id: "phoneNumber", value: EMPTY },
      ],
    },
  ];
}

export default function CaseCards({ caseData }: Props) {
  const cards = buildCards(caseData);

  const columns = cards.map((card) => `minmax(0, ${card.span}fr)`).join(" "); // => `minmax(0, 1fr) minmax(0, 2fr) minmax(0, 1fr)`

  return (
    <Container $columns={columns}>
      {cards.map((card) => (
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
