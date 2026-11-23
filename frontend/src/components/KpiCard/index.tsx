import styled from "styled-components";
import KpiCard from "./Card";
import { CASES } from "../Sagsoversigt/caseData";

const KpiRow = styled.div`
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
  margin-bottom: 24px;
`;

const countByStatus = (status: string) =>
  CASES.filter((item) => item.status === status).length;

export default function KpiCards() {
  return (
    <>
      <KpiRow>
        <KpiCard label="aktive sager" count={CASES.length - countByStatus("Afsluttet")} />
        <KpiCard label="afventer svar" count={countByStatus("Afventer")} />
        <KpiCard label="afsluttede sager" count={countByStatus("Afsluttet")} />
        <KpiCard label="kræver handling" count={countByStatus("Kræver opmærksomhed")} highlighted />
      </KpiRow>
    </>
  );
}
