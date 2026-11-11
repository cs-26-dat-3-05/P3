import { useMemo, useState } from "react";
import styled from "styled-components";

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
  { id: "RP-10XX", organizer: "Aalborg Kommune", amount: "DKK XX.XXX", status: "Afventer", progress: 4, step: "Afventer korrekt kontonummer", note: "Kontaktoplysninger kontrolleret", created: "12. JUNI" },
  { id: "RP-10XX", organizer: "Musikkens Hus", amount: "DKK XX.XXX", status: "Afsluttet", progress: 6, step: "Udbetaling genindsendt", note: "Reg.nr. verificeret", created: "10. JUNI" },
  { id: "RP-10XX", organizer: "Studenterhuset Aalborg", amount: "DKK XX.XXX", status: "Kræver opmærksomhed", progress: 2, step: "Arrangør identificeret", note: "Ring igen fredag", created: "8. JUNI" },
  { id: "RP-10XX", organizer: "Nordjyllands Historiske Museum", amount: "DKK XX.XXX", status: "Ny", progress: 1, step: "Returnering registreret", note: "—", created: "18. JUNI" },
  { id: "RP-10XX", organizer: "Aalborg Teater", amount: "DKK XX.XXX", status: "Afventer", progress: 3, step: "Bilag afventer gennemgang", note: "Mangler kvittering", created: "17. JUNI" },
  { id: "RP-10XX", organizer: "Aalborg Symfoniorkester", amount: "DKK XX.XXX", status: "Under behandling", progress: 5, step: "Udbetaling klargjort", note: "Beløb godkendt", created: "16. JUNI" },
  { id: "RP-10XX", organizer: "Kulturhuset Trekanten", amount: "DKK XX.XXX", status: "Afventer", progress: 4, step: "Afventer svar fra arrangør", note: "Mail sendt 15. juni", created: "15. JUNI" },
  { id: "RP-10XX", organizer: "Børnekulturhuset", amount: "DKK XX.XXX", status: "Ny", progress: 1, step: "Sag modtaget", note: "—", created: "14. JUNI" },
  { id: "RP-10XX", organizer: "Aalborg Kongres & Kultur Center", amount: "DKK XX.XXX", status: "Afsluttet", progress: 6, step: "Udbetaling gennemført", note: "Afstemt", created: "13. JUNI" },
  { id: "RP-10XX", organizer: "Mikkel", amount: "DKK 500.000.000", status: "Under behandling", progress: 3, step: "Oplysninger gennemgå", note: "Tue er Sej", created: "11. JUNI" },
  { id: "RP-10XX", organizer: "Kunsten Museum of Modern Art", amount: "DKK XX.XXX", status: "Afventer", progress: 2, step: "Afventer dokumentation", note: "Rykkes i næste uge", created: "9. JUNI" },
  { id: "RP-10XX", organizer: "Aalborg Karneval", amount: "DKK XX.XXX", status: "Ny", progress: 1, step: "Sag modtaget", note: "—", created: "7. JUNI" },
];

const WORKFLOW_STEPS = [
  "Sag modtaget",
  "Arrangør identificeret",
  "Bilag gennemgået",
  "Kontonummer kontrolleret",
  "Udbetaling klargjort",
  "Udbetaling gennemført",
];

const getOwner = (step: number) => step <= 4 ? "Kundeservice" : "Bekræfter";

const Container = styled.section`
  width: 100%;
  box-sizing: border-box;
  border: 2px solid #000;
  color: #292929;
  background: #f9f8f4;
  font-family: "Courier New", monospace;
`;

const OverviewHeader = styled.header`
  height: 64px;
  box-sizing: border-box;
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-top: 3px solid #555;
  border-bottom: 1px solid #999;

  h2 { margin: 0; font: 700 15px Arial, sans-serif; }
  span { font-size: 15px; }
`;

const Filters = styled.div`
  display: grid;
  grid-template-columns: 1.2fr .86fr .86fr .86fr;
  gap: 14px;
  padding: 9px 20px 17px;
  border-bottom: 1px solid #888;

  label { display: grid; gap: 6px; color: #666; font-size: 10px; }
  input, select {
    width: 100%; height: 54px; box-sizing: border-box;
    border: 3px solid #5b5b5b; border-radius: 0; padding: 0 14px;
    color: #333; background: #fbfaf6; font: 14px "Courier New", monospace;
  }
  @media (max-width: 760px) { grid-template-columns: repeat(2, minmax(0, 1fr)); }
  @media (max-width: 460px) { grid-template-columns: 1fr; }
`;

const TableWrap = styled.div`
  max-height: 520px;
  overflow: auto;
`;

const CaseTable = styled.table`
  width: 100%; min-width: 1150px; border-collapse: collapse; table-layout: fixed;
  th, td { text-align: left; border-bottom: 1px solid #aaa; }
  th { position: sticky; top: 0; z-index: 1; height: 46px; padding: 0 20px; background: #e7e5df; font-size: 11px; font-weight: 400; }
  td { height: 84px; padding: 0 20px; font-size: 13px; }
  th:nth-child(1) { width: 20%; } th:nth-child(2) { width: 10%; }
  th:nth-child(3) { width: 16%; } th:nth-child(4) { width: 15%; }
  th:nth-child(5) { width: 9%; } th:nth-child(6) { width: 18%; } th:nth-child(7) { width: 9%; }
`;

const CaseName = styled.div`
  font-weight: 700;
  span { display: block; margin-top: 5px; color: #777; font-weight: 400; }
`;

const Status = styled.span`
  display: inline-block; max-width: 100%; padding: 5px 10px;
  border: 2px solid #777; border-radius: 18px; white-space: nowrap; font-size: 11px;
`;

const Progress = styled.div`
  color: #777; font-size: 12px; white-space: nowrap;
  div { height: 10px; margin-top: 7px; border: 2px solid #777; background: #f9f8f4; }
  span { display: block; height: 100%; background: #777; }
`;

const CaseRow = styled.tr<{ $attention: boolean }>`
  background: ${({ $attention }) => $attention
    ? "repeating-linear-gradient(135deg, #fbfaf7 0 7px, #e8e6df 7px 9px)"
    : "#fbfaf7"};
`;

export default function Sagsoversigt() {
  const [query, setQuery] = useState("");
  const [status, setStatus] = useState("alle");
  const [progress, setProgress] = useState("alle");
  const [owner, setOwner] = useState("alle");

  const visibleCases = useMemo(() => CASES.filter((item) => {
    const searchableText = `${item.id} ${item.organizer} ${item.note}`.toLowerCase();
    return searchableText.includes(query.toLowerCase())
      && (status === "alle" || item.status === status)
      && (progress === "alle" || item.progress === Number(progress))
      && (owner === "alle" || getOwner(item.progress) === owner);
  }), [query, status, progress, owner]);

  return (
    <Container>
      <OverviewHeader>
        <h2>SAGSOVERSIGT</h2>
        <span>{visibleCases.length} sager</span>
      </OverviewHeader>

      <Filters aria-label="Filtrer sager">
        <label>SØG
          <input
            value={query}
            onChange={(event) => setQuery(event.target.value)}
            placeholder="sag, arrangør..."
          />
        </label>
        <label>STATUS
          <select value={status} onChange={(event) => setStatus(event.target.value)}>
            <option value="alle">alle</option>
            {[...new Set(CASES.map((item) => item.status))].map((value) => <option key={value}>{value}</option>)}
          </select>
        </label>
        <label>FREMSKRIDT
          <select value={progress} onChange={(event) => setProgress(event.target.value)}>
            <option value="alle">alle</option>
            {WORKFLOW_STEPS.map((step, index) => (
              <option key={step} value={index + 1}>{index + 1}/6 · {step}</option>
            ))}
          </select>
        </label>
        <label>ANSVARLIG
          <select value={owner} onChange={(event) => setOwner(event.target.value)}>
            <option value="alle">alle</option>
            <option value="Kundeservice">Kundeservice</option>
            <option value="Bekræfter">Bekræfter</option>
          </select>
        </label>
      </Filters>

      <TableWrap>
        <CaseTable>
          <thead>
            <tr>
              <th>SAG / ARRANGØR</th><th>BELØB</th><th>STATUS</th><th>FREMSKRIDT</th>
              <th>ANSVARLIG</th><th>SENESTE INTERNE NOTAT</th><th>OPRETTET</th>
            </tr>
          </thead>
          <tbody>
            {visibleCases.map((item, index) => (
              <CaseRow key={`${item.organizer}-${index}`} $attention={item.status === "Kræver opmærksomhed"}>
                <td><CaseName>{item.id}<span>{item.organizer}</span></CaseName></td>
                <td>{item.amount}</td>
                <td><Status>{item.status.toUpperCase()}</Status></td>
                <td><Progress>{item.step} ({item.progress}/6)<div><span style={{ width: `${(item.progress / 6) * 100}%` }} /></div></Progress></td>
                <td>{getOwner(item.progress)}</td>
                <td>{item.note}</td>
                <td>{item.created}</td>
              </CaseRow>
            ))}
            {visibleCases.length === 0 && <tr><td colSpan={7}>Ingen sager matcher filtrene.</td></tr>}
          </tbody>
        </CaseTable>
      </TableWrap>
    </Container>
  );
}
