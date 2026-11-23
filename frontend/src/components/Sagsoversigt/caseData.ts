export interface CaseItem {
  id: string;
  organizer: string;
  amount: string;
  status: string;
  progress: number;
  step: string;
  note: string;
  created: string;
  cards: CaseCard[];
}

export interface CaseCard {
  id: string;
  title: string;
  span: number;
  rows: { id: string; label: string; value: string }[];
}

type CaseRecord = Omit<CaseItem, "cards">;

const CASE_RECORDS: CaseRecord[] = [
  {
    id: "Test-Test1234",
    organizer: "Test Kommune",
    amount: "DKK 12.345",
    status: "Kræver opmærksomhed",
    progress: 4,
    step: "Afventer korrekt kontonummer",
    note: "Dette er en test",
    created: "00. Test",
  },
  {
    id: "RP-10XX",
    organizer: "Aalborg Kommune",
    amount: "DKK 250.000",
    status: "Afventer",
    progress: 4,
    step: "Afventer korrekt kontonummer",
    note: "Kontaktoplysninger kontrolleret",
    created: "12. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Musikkens Hus",
    amount: "DKK 900",
    status: "Afsluttet",
    progress: 6,
    step: "Udbetaling genindsendt",
    note: "Reg.nr. verificeret",
    created: "10. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Studenterhuset Aalborg",
    amount: "DKK 12.500",
    status: "Kræver opmærksomhed",
    progress: 2,
    step: "Arrangør identificeret",
    note: "Ring igen fredag",
    created: "8. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Nordjyllands Historiske Museum",
    amount: "DKK 80.000",
    status: "Ny",
    progress: 1,
    step: "Returnering registreret",
    note: "—",
    created: "18. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Aalborg Teater",
    amount: "DKK 69.000",
    status: "Afventer",
    progress: 3,
    step: "Bilag afventer gennemgang",
    note: "Mangler kvittering",
    created: "17. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Aalborg Symfoniorkester",
    amount: "DKK 120.000",
    status: "Under behandling",
    progress: 5,
    step: "Udbetaling klargjort",
    note: "Beløb godkendt",
    created: "16. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Kulturhuset Trekanten",
    amount: "DKK 123.321",
    status: "Afventer",
    progress: 4,
    step: "Afventer svar fra arrangør",
    note: "Mail sendt 15. juni",
    created: "15. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Børnekulturhuset",
    amount: "DKK 12.000",
    status: "Ny",
    progress: 1,
    step: "Sag modtaget",
    note: "—",
    created: "14. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Aalborg Kongres & Kultur Center",
    amount: "DKK 81.000",
    status: "Afsluttet",
    progress: 6,
    step: "Udbetaling gennemført",
    note: "Afstemt",
    created: "13. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Mikkel",
    amount: "DKK 500.000.000",
    status: "Under behandling",
    progress: 3,
    step: "Oplysninger gennemgå",
    note: "Tue er Sej",
    created: "11. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Kunsten Museum of Modern Art",
    amount: "DKK XX.XXX",
    status: "Afventer",
    progress: 2,
    step: "Afventer dokumentation",
    note: "Rykkes i næste uge",
    created: "9. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Aalborg Karneval",
    amount: "DKK XX.XXX",
    status: "Ny",
    progress: 1,
    step: "Sag modtaget",
    note: "—",
    created: "7. JUNI",
  },
];

export const CASES: CaseItem[] = CASE_RECORDS.map((item, index) => {
  const sequence = 12345 + index;
  const organizerSlug = item.organizer
    .toLowerCase()
    .replace(/[^a-z0-9]+/g, ".")
    .replace(/^\.|\.$/g, "");

  return {
    ...item,
    cards: [
      {
        id: "case",
        title: "Sag",
        span: 1,
        rows: [
          { id: "createdAt", label: "OPRETTET", value: item.created },
          { id: "organizerId", label: "ARRANGØR-ID", value: `EM${sequence}` },
          { id: "eventId", label: "EVENT-ID", value: `F${sequence}` },
        ],
      },
      {
        id: "payment",
        title: "Betaling",
        span: 2,
        rows: [
          { id: "amount", label: "BELØB", value: item.amount },
          {
            id: "wrongAccountNumber",
            label: "FORKERT KONTONUMMER",
            value: `1234 / ${567890123 + index}`,
          },
          {
            id: "correctAccountNumber",
            label: "KORREKT KONTONUMMER",
            value: "IKKE INDTASTET",
          },
          {
            id: "paymentDate",
            label: "BETALINGSDATO",
            value: item.status === "Afsluttet" ? "BETALING GENNEMFØRT" : "AFVENTER KONTONUMMER",
          },
        ],
      },
      {
        id: "organizer",
        title: "Arrangør",
        span: 1,
        rows: [
          { id: "contactPerson", label: "KONTAKTPERSON", value: "METTE JENSEN" },
          {
            id: "email",
            label: "E-MAIL",
            value: `${organizerSlug || "arrangoer"}@example.dk`.toUpperCase(),
          },
          { id: "phone", label: "TELEFON", value: "+45 99 31 XX XX" },
        ],
      },
    ],
  };
});

