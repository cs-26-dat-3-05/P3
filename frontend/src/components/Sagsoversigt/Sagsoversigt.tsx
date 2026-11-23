import { useMemo, useState } from "react";
import { CASES } from "./caseData";
import type { CaseItem } from "./caseData";
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

const WORKFLOW_STEPS = [
  "Sag modtaget",
  "Arrangør identificeret",
  "Bilag gennemgået",
  "Kontonummer kontrolleret",
  "Udbetaling klargjort",
  "Udbetaling gennemført",
];

const getOwner = (step: number) => (step <= 4 ? "Kundeservice" : "Bekræfter");

type SagsoversigtProps = {
  onOpenCase: (caseItem: CaseItem) => void;
};

export default function Sagsoversigt({ onOpenCase }: SagsoversigtProps) {
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
                onClick={() => onOpenCase(item)}
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
