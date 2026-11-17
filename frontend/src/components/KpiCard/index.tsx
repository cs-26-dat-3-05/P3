import styled from "styled-components";
import KpiCard from "./Card";
import { useCases } from "../../hooks/useCases";

const KpiRow = styled.div`
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
  margin-bottom: 24px;
`;

export default function KpiCards() {
  const { cases, loading } = useCases();

  const activeCount = cases.filter((c) => c.status !== "RETRANSFERRED").length;
  const closedCount = cases.filter((c) => c.status == "RETRANSFERRED").length;
  const awaitingCount = cases.filter(
    (c) => c.status == "BANK_DETAILS_REQUESTED",
  ).length;

  if (loading) return <p>Indlæser…</p>;
  return (
    <>
      <KpiRow>
        <KpiCard label="aktive sager" count={activeCount} />
        <KpiCard label="afventer svar" count={awaitingCount} />
        <KpiCard label="afsluttede sager" count={closedCount} />
        <KpiCard label="kræver handling" count={0} highlighted />
      </KpiRow>
    </>
  );
}
