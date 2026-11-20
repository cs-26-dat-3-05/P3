import type { Case } from "../../api/cases";
import type { Organizer } from "../../api/organizer";
import { useOrganizer } from "../../hooks/useOrganizer";
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

const EMPTY = "—";
const LOADING = "…";

function getCorrectAccountText(caseData: Case): string {
  const step = STATUS_MAP[caseData.status].step;
  if (step < STATUS_MAP.BANK_DETAILS_RECEIVED.step) return "IKKE INDTASTET";
  // todo: show correct acc details when it's added to backend
  return EMPTY;
}

function getPaymentDateText(caseData: Case): string {
  const step = STATUS_MAP[caseData.status].step;
  if (step < STATUS_MAP.BANK_DETAILS_RECEIVED.step)
    return "AFVENTER KONTONUMMER";
  if (step < STATUS_MAP.RETRANSFERRED.step) return "AFVENTER UDBETALING";
  // todo: show payment date when it's added to backend
  return EMPTY;
}

function buildCards(
  caseData: Case,
  organizer: Organizer | null,
  organizerLoading: boolean,
): CardData[] {
  const organizerValue = (value: string | null | undefined) =>
    organizerLoading ? LOADING : value || EMPTY;

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
      rows: [
        {
          label: "KONTAKTPERSON",
          id: "contact",
          value: organizerValue(EMPTY), // update to contact when backend is updated
        },
        {
          label: "E-MAIL",
          id: "email",
          value: organizerValue(organizer?.email),
        },
        {
          label: "TELEFON",
          id: "phone",
          value: organizerValue(organizer?.phone),
        },
      ],
    },
  ];
}

export default function CaseCards({ caseData }: Props) {
  const { organizer, loading: organizerLoading } = useOrganizer(
    Number(caseData.organizerId),
  );
  const cards = buildCards(caseData, organizer, organizerLoading);

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
