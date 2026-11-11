import styled from "styled-components";
import KpiCard from "./Card";

const KpiRow = styled.div`
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
  margin-bottom: 24px;
`;

export default function KpiCards() {
  return (
    <>
      <KpiRow>
        <KpiCard label="aktive sager" count={12} />
        <KpiCard label="afventer svar" count={5} />
        <KpiCard label="afsluttede sager" count={3} />
        <KpiCard label="kræver handling" count={2} highlighted />
      </KpiRow>
    </>
  );
}
