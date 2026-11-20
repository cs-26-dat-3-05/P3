import { useState } from "react";
import { useQueries } from "@tanstack/react-query";
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
import { useCases } from "../../hooks/useCases";
import { useNotes } from "../../hooks/useNotes";
import { caseHistoryQuery } from "../../hooks/useCaseHistory";
import { useNavigate } from "react-router";
import { STATUS_MAP } from "../../constants/caseStatus";
import { formatAmount, formatDateMonth } from "../../utils/format";

function LatestNoteCell({ caseId }: { caseId: number }) {
  const { notes, loading } = useNotes(caseId);

  if (loading) return <>…</>;
  if (notes.length === 0) return <>—</>;

  const latest = notes.reduce((a, b) => (a.createdAt > b.createdAt ? a : b));
  return <>{latest.text}</>;
}

export default function Sagsoversigt() {
  const [query, setQuery] = useState("");
  const [status, setStatus] = useState("alle");
  const [progress, setProgress] = useState("alle");
  const [owner, setOwner] = useState("alle");

  const navigate = useNavigate();

  const { cases, loading, error } = useCases();

  // fetches history from cases
  const historyResults = useQueries({
    queries: cases.map((c) => caseHistoryQuery(c.id)),
  });

  // set owner to last perfomedBy entry from case history
  const ownerByCase = new Map<number, string>();
  cases.forEach((c, i) => {
    const history = historyResults[i]?.data ?? [];
    if (history.length === 0) return;
    const latest = history.reduce((a, b) =>
      a.createdAt > b.createdAt ? a : b,
    );
    ownerByCase.set(c.id, latest.performedBy);
  });

  const ownerOptions = [...new Set(ownerByCase.values())].sort();

  const visibleCases = cases.filter((item) => {
    const step = STATUS_MAP[item.status]?.step;
    const searchableText =
      `${item.id} RP-${item.organizerId} ${item.organizerName}`.toLowerCase();
    return (
      searchableText.includes(query.toLowerCase()) &&
      (status === "alle" || item.status === status) &&
      (progress === "alle" || step === Number(progress)) &&
      (owner === "alle" || ownerByCase.get(item.id) === owner)
    );
  });

  if (error) return <Container>Kunne ikke hente sager.</Container>;

  return (
    <Container>
      <OverviewHeader>
        <h2>SAGSOVERSIGT</h2>
        <span>{cases.length} sager</span>
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
            {[...new Set(cases.map((item) => item.status))].map((value) => (
              <option key={value} value={value}>
                {STATUS_MAP[value]?.label ?? value}
              </option>
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
            {Object.entries(STATUS_MAP).map(([key, status]) => (
              <option key={key} value={status.step}>
                {status.step}/5 · {status.label}
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
            {ownerOptions.map((name) => (
              <option key={name} value={name}>
                {name}
              </option>
            ))}
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
            {visibleCases.map((item) => (
              <CaseRow
                key={item.id}
                $attention={item.requiresAction == true}
                onClick={() => navigate(`/cases/${item.id}`)}
              >
                <td>
                  <CaseName>
                    <b>RP-{item.organizerId}</b>
                    <span>{item.organizerName}</span>
                  </CaseName>
                </td>
                <td>{formatAmount(item.amount)}</td>
                <td>
                  <Status>
                    {item.requiresAction
                      ? "Kræver handling"
                      : STATUS_MAP[item.status].label.toUpperCase()}
                  </Status>
                </td>
                <td>
                  <Progress>
                    {STATUS_MAP[item.status].label.toUpperCase()} (
                    {STATUS_MAP[item.status].step}/5)
                    <div>
                      <span
                        style={{
                          width: `${(STATUS_MAP[item.status].step / 5) * 100}%`,
                        }}
                      />
                    </div>
                  </Progress>
                </td>
                <td>{ownerByCase.get(item.id) ?? "—"}</td>
                <td>
                  <LatestNoteCell caseId={item.id} />
                </td>
                <td>{formatDateMonth(item.createdAt)}</td>
              </CaseRow>
            ))}
            {!loading && visibleCases.length === 0 && (
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
