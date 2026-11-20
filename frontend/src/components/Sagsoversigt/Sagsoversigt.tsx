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
import { useCases } from "../../hooks/useCases";

const STATUS_MAP = {
  REGISTERED: { step: 1, label: "Sag modtaget" },
  ORGANIZER_IDENTIFIED: { step: 2, label: "Arrangør identificeret" },
  BANK_DETAILS_REQUESTED: { step: 3, label: "Anmodet om kontooplysninger" },
  BANK_DETAILS_RECEIVED: { step: 4, label: "Kontooplysninger modtaget" },
  RETRANSFERRED: { step: 5, label: "Udbetaling gennemført" },
};

const formatDateMonth = (date: Date): string => {
  return new Intl.DateTimeFormat("da-DK", {
    day: "numeric",
    month: "long",
  }).format(date);
};

const getOwner = (step: number) => (step <= 4 ? "Kundeservice" : "Bekræfter");

type SagsoversigtProps = {
  onOpenCase: () => void;
};

export default function Sagsoversigt({ onOpenCase }: SagsoversigtProps) {
  const [query, setQuery] = useState("");
  const [status, setStatus] = useState("alle");
  const [progress, setProgress] = useState("alle");
  const [owner, setOwner] = useState("alle");

  const { cases, loading, error } = useCases();

  if (error) console.log(error.message);

  const visibleCases = useMemo(
    () =>
      cases.filter((item) => {
        const step = STATUS_MAP[item.status]?.step;
        const searchableText =
          `${item.id} RP-${item.organizerId} ${item.organizerName}`.toLowerCase();
        return (
          searchableText.includes(query.toLowerCase()) &&
          (status === "alle" || item.status === status) &&
          (progress === "alle" || step === Number(progress)) &&
          (owner === "alle" || getOwner(step) === owner)
        );
      }),
    [cases, query, status, progress, owner],
  );

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
                key={`${item.organizerId}-${index}`}
                $attention={item.requiresAction == true}
                onClick={onOpenCase}
              >
                <td>
                  <CaseName>
                    <b>RP-{item.organizerId}</b>
                    <span>{item.organizerName}</span>
                  </CaseName>
                </td>
                <td>{item.amount} DKK</td>
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
                <td>Navn</td>
                <td>-</td>
                <td>{formatDateMonth(new Date(item.createdAt))}</td>
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
