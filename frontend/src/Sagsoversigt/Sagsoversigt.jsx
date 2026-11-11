import { useMemo, useState } from "react";
import styled from "styled-components";

const cases = [
  { id: "RP-10XX", organizer: "Aalborg Kommune", amount: "DKK XX.XXX", status: "Afventer", progress: 4, step: "Afventer korrekt kontonummer", owner: "NAVN", note: "Kontaktoplysninger kontrolleret", created: "12. JUNI" },
  { id: "RP-10XX", organizer: "Musikkens Hus", amount: "DKK XX.XXX", status: "Afsluttet", progress: 6, step: "Udbetaling genindsendt", owner: "NAVN", note: "Reg.nr. verificeret", created: "10. JUNI" },
  { id: "RP-10XX", organizer: "Studenterhuset Aalborg", amount: "DKK XX.XXX", status: "Kræver opmærksomhed", progress: 2, step: "Arrangør identificeret", owner: "NAVN", note: "Ring igen fredag", created: "8. JUNI" },
  { id: "RP-10XX", organizer: "Nordjyllands Historiske Museum", amount: "DKK XX.XXX", status: "Ny", progress: 1, step: "Returnering registreret", owner: "NAVN", note: "—", created: "18. JUNI" },
  { id: "RP-10XX", organizer: "Aalborg Teater", amount: "DKK XX.XXX", status: "Afventer", progress: 3, step: "Bilag afventer gennemgang", owner: "NAVN", note: "Mangler kvittering", created: "17. JUNI" },
  { id: "RP-10XX", organizer: "Aalborg Symfoniorkester", amount: "DKK XX.XXX", status: "Under behandling", progress: 5, step: "Udbetaling klargøres", owner: "NAVN", note: "Beløb godkendt", created: "16. JUNI" },
  { id: "RP-10XX", organizer: "Kulturhuset Trekanten", amount: "DKK XX.XXX", status: "Afventer", progress: 4, step: "Afventer svar fra arrangør", owner: "NAVN", note: "Mail sendt 15. juni", created: "15. JUNI" },
  { id: "RP-10XX", organizer: "Børnekulturhuset", amount: "DKK XX.XXX", status: "Ny", progress: 1, step: "Sag oprettet", owner: "NAVN", note: "—", created: "14. JUNI" },
  { id: "RP-10XX", organizer: "Aalborg Kongres & Kultur Center", amount: "DKK XX.XXX", status: "Afsluttet", progress: 6, step: "Udbetaling gennemført", owner: "NAVN", note: "Afstemt", created: "13. JUNI" },
  { id: "RP-10XX", organizer: "Skråen", amount: "DKK XX.XXX", status: "Under behandling", progress: 3, step: "Oplysninger gennemgås", owner: "NAVN", note: "Kontrol igangsat", created: "11. JUNI" },
  { id: "RP-10XX", organizer: "Kunsten Museum of Modern Art", amount: "DKK XX.XXX", status: "Afventer", progress: 2, step: "Afventer dokumentation", owner: "NAVN", note: "Rykkes i næste uge", created: "9. JUNI" },
  { id: "RP-10XX", organizer: "Aalborg Karneval", amount: "DKK XX.XXX", status: "Ny", progress: 1, step: "Sag modtaget", owner: "NAVN", note: "—", created: "7. JUNI" },
];

const Page = styled.main`
  min-height: 100vh;
  background: #f9f8f4;
  color: #292929;
  font-family: "Courier New", monospace;
`;
const Header = styled.header`
  height: 64px;
  box-sizing: border-box;
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-top: 3px solid #555;
  border-bottom: 1px solid #999;
  h1 { margin: 0; font: 700 15px Arial, sans-serif; }
  span { font-size: 15px; }
`;
const Filters = styled.section`
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
const TableWrap = styled.div`overflow-x: auto;`;
const Table = styled.table`
  width: 100%; min-width: 1150px; border-collapse: collapse; table-layout: fixed;
  th, td { text-align: left; border-bottom: 1px solid #aaa; }
  th { height: 46px; padding: 0 20px; background: #e7e5df; font-size: 11px; font-weight: 400; }
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
  display: inline-block; max-width: 100%; padding: 5px 10px; border: 2px solid #777;
  border-radius: 18px; white-space: nowrap; font-size: 11px;
`;
const Progress = styled.div`
  color: #777; font-size: 12px; white-space: nowrap;
  div { height: 10px; margin-top: 7px; border: 2px solid #777; background: #f9f8f4; }
  span { display: block; height: 100%; background: #777; }
`;
const Row = styled.tr`
  background: ${({ $attention }) => $attention ? "repeating-linear-gradient(135deg, #fbfaf7 0 7px, #e8e6df 7px 9px)" : "#fbfaf7"};
`;

export default function Sagsoversigt() {
  const [query, setQuery] = useState("");
  const [status, setStatus] = useState("alle");
  const [progress, setProgress] = useState("alle");
  const [owner, setOwner] = useState("alle");
  const filteredCases = useMemo(() => cases.filter((item) => {
    const text = `${item.id} ${item.organizer} ${item.note}`.toLowerCase();
    return text.includes(query.toLowerCase())
      && (status === "alle" || item.status === status)
      && (progress === "alle" || item.progress === Number(progress))
      && (owner === "alle" || item.owner === owner);
  }), [query, status, progress, owner]);

  return (
    <Page>
      <Header><h1>SAGSOVERSIGT</h1><span>{filteredCases.length} sager</span></Header>
      <Filters aria-label="Filtrer sager">
        <label>SØG<input placeholder="sag, arrangør..." value={query} onChange={(event) => setQuery(event.target.value)} /></label>
        <label>STATUS<select value={status} onChange={(event) => setStatus(event.target.value)}><option value="alle">alle</option>{[...new Set(cases.map((item) => item.status))].map((value) => <option key={value}>{value}</option>)}</select></label>
        <label>FREMSKRIDT<select value={progress} onChange={(event) => setProgress(event.target.value)}><option value="alle">alle</option>{[1, 2, 3, 4, 5, 6].map((value) => <option key={value} value={value}>{value}/6</option>)}</select></label>
        <label>ANSVARLIG<select value={owner} onChange={(event) => setOwner(event.target.value)}><option value="alle">alle</option>{[...new Set(cases.map((item) => item.owner))].map((value) => <option key={value}>{value}</option>)}</select></label>
      </Filters>
      <TableWrap>
        <Table>
          <thead><tr><th>SAG / ARRANGØR</th><th>BELØB</th><th>STATUS</th><th>FREMSKRIDT</th><th>ANSVARLIG</th><th>SENESTE INTERNE NOTAT</th><th>OPRETTET</th></tr></thead>
          <tbody>{filteredCases.map((item, index) => <Row key={`${item.organizer}-${index}`} $attention={item.status === "Kræver opmærksomhed"}>
            <td><CaseName>{item.id}<span>{item.organizer}</span></CaseName></td>
            <td>{item.amount}</td>
            <td><Status>{item.status.toUpperCase()}</Status></td>
            <td><Progress>{item.step} ({item.progress}/6)<div><span style={{ width: `${(item.progress / 6) * 100}%` }} /></div></Progress></td>
            <td>{item.owner}</td><td>{item.note}</td><td>{item.created}</td>
          </Row>)}
          {filteredCases.length === 0 && <tr><td colSpan={7}>Ingen sager matcher filtrene.</td></tr>}</tbody>
        </Table>
      </TableWrap>
    </Page>
  );
}
