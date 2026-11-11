import { useMemo, useState } from "react";
import {
  CaseName,
  CaseRow,
  CaseTable,
  Container,
  Filters,
  FilterSelect,
  OverviewHeader,
  Progress,
  Status,
  TableWrap,
} from "./style";

interface CaseItem {
  id: string;
  organizer: string;
  amount: string;
  status: string;
  progress: number;
  step: string;
  note: string;
  created: string;
}

const CASES: CaseItem[] = [
  {
    id: "RP-10XX",
    organizer: "Aalborg Kommune",
    amount: "DKK XX.XXX",
    status: "Afventer",
    progress: 4,
    step: "Afventer korrekt kontonummer",
    note: "Kontaktoplysninger kontrolleret",
    created: "12. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Musikkens Hus",
    amount: "DKK XX.XXX",
    status: "Afsluttet",
    progress: 6,
    step: "Udbetaling genindsendt",
    note: "Reg.nr. verificeret",
    created: "10. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Studenterhuset Aalborg",
    amount: "DKK XX.XXX",
    status: "Kræver opmærksomhed",
    progress: 2,
    step: "Arrangør identificeret",
    note: "Ring igen fredag",
    created: "8. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Nordjyllands Historiske Museum",
    amount: "DKK XX.XXX",
    status: "Ny",
    progress: 1,
    step: "Returnering registreret",
    note: "—",
    created: "18. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Aalborg Teater",
    amount: "DKK XX.XXX",
    status: "Afventer",
    progress: 3,
    step: "Bilag afventer gennemgang",
    note: "Mangler kvittering",
    created: "17. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Aalborg Symfoniorkester",
    amount: "DKK XX.XXX",
    status: "Under behandling",
    progress: 5,
    step: "Udbetaling klargjort",
    note: "Beløb godkendt",
    created: "16. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Kulturhuset Trekanten",
    amount: "DKK XX.XXX",
    status: "Afventer",
    progress: 4,
    step: "Afventer svar fra arrangør",
    note: "Mail sendt 15. juni",
    created: "15. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Børnekulturhuset",
    amount: "DKK XX.XXX",
    status: "Ny",
    progress: 1,
    step: "Sag modtaget",
    note: "—",
    created: "14. JUNI",
  },
  {
    id: "RP-10XX",
    organizer: "Aalborg Kongres & Kultur Center",
    amount: "DKK XX.XXX",
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

const WORKFLOW_STEPS = [
  "Sag modtaget",
  "Arrangør identificeret",
  "Bilag gennemgået",
  "Kontonummer kontrolleret",
  "Udbetaling klargjort",
  "Udbetaling gennemført",
];

const getOwner = (step: number) => (step <= 4 ? "Kundeservice" : "Bekræfter");

export default function Sagsoversigt() {
  const [query, setQuery] = useState("");
  const [status, setStatus] = useState("alle");
  const [progress, setProgress] = useState("alle");
  const [owner, setOwner] = useState("alle");

  const visibleCases = useMemo(
    () =>
      CASES.filter((item) => {
        const searchableText =
          `${item.id} ${item.organizer} ${item.note}`.toLowerCase();
        return (
          searchableText.includes(query.toLowerCase()) &&
          (status === "alle" || item.status === status) &&
          (progress === "alle" || item.progress === Number(progress)) &&
          (owner === "alle" || getOwner(item.progress) === owner)
        );
      }),
    [query, status, progress, owner],
  );

  return (
    <Container>
      <OverviewHeader>
        <h2>SAGSOVERSIGT</h2>
        <span>{visibleCases.length} sager</span>
      </OverviewHeader>

      <Filters aria-label="Filtrer sager">
        <label>
          SØG
          <input
            value={query}
            onChange={(event) => setQuery(event.target.value)}
            placeholder="sag, arrangør..."
          />
        </label>
        <label>
          STATUS
          <FilterSelect
            value={status}
            onChange={(event) => setStatus(event.target.value)}
          >
            <option value="alle">alle</option>
            {[...new Set(CASES.map((item) => item.status))].map((value) => (
              <option key={value}>{value}</option>
            ))}
          </FilterSelect>
        </label>
        <label>
          FREMSKRIDT
          <FilterSelect
            value={progress}
            onChange={(event) => setProgress(event.target.value)}
          >
            <option value="alle">alle</option>
            {WORKFLOW_STEPS.map((step, index) => (
              <option key={step} value={index + 1}>
                {index + 1}/6 · {step}
              </option>
            ))}
          </FilterSelect>
        </label>
        <label>
          ANSVARLIG
          <FilterSelect
            value={owner}
            onChange={(event) => setOwner(event.target.value)}
          >
            <option value="alle">alle</option>
            <option value="Kundeservice">Kundeservice</option>
            <option value="Bekræfter">Bekræfter</option>
          </FilterSelect>
        </label>
      </Filters>

      <TableWrap>
        <CaseTable>
          <thead>
            <tr>
              <th>SAG / ARRANGØR</th>
              <th>BELØB</th>
              <th>STATUS</th>
              <th>FREMSKRIDT</th>
              <th>ANSVARLIG</th>
              <th>SENESTE INTERNE NOTAT</th>
              <th>OPRETTET</th>
            </tr>
          </thead>
          <tbody>
            {visibleCases.map((item, index) => (
              <CaseRow
                key={`${item.organizer}-${index}`}
                $attention={item.status === "Kræver opmærksomhed"}
              >
                <td>
                  <CaseName>
                    <b>{item.id}</b>
                    <span>{item.organizer}</span>
                  </CaseName>
                </td>
                <td>{item.amount}</td>
                <td>
                  <Status>{item.status.toUpperCase()}</Status>
                </td>
                <td>
                  <Progress>
                    {item.step} ({item.progress}/6)
                    <div>
                      <span
                        style={{ width: `${(item.progress / 6) * 100}%` }}
                      />
                    </div>
                  </Progress>
                </td>
                <td>{getOwner(item.progress)}</td>
                <td>{item.note}</td>
                <td>{item.created}</td>
              </CaseRow>
            ))}
            {visibleCases.length === 0 && (
              <tr>
                <td colSpan={7}>Ingen sager matcher filtrene.</td>
              </tr>
            )}
          </tbody>
        </CaseTable>
      </TableWrap>
    </Container>
  );
}
