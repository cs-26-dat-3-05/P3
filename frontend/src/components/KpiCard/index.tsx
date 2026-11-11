import styled from "styled-components";
import KpiCard from "./Card";
import AktuelleTrin from "../AktuelleTrin";
import InternalNotes from "../InternalNotes";

const KpiRow = styled.div`
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
  margin-bottom: 24px;
`;

const CaseDetails = styled.div`
  display: grid;
  grid-template-columns: 3fr 2fr;
  gap: 24px;
  margin-top: 24px;
  align-items: start;
`;
export default function KpiCards() {
  return (
    <>
      <KpiRow>
        <KpiCard label="AKTIVE SAGER" count={12} />
        <KpiCard label="AFVENTER SVAR" count={5} />
        <KpiCard label="AFSLUTTEDE SAGER" count={3} />
        <KpiCard label="KRÆVER HANDLING" count={2} highlighted />
      </KpiRow>
      <CaseDetails>
        <AktuelleTrin></AktuelleTrin>
        <InternalNotes></InternalNotes>
      </CaseDetails>
    </>
  );
}
