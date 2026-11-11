import styled from "styled-components";
import Header from "./components/Header";
import KpiCard from "./components/KpiCard";
import CaseCards from "./components/CaseCards";
import AktuelleTrin from "./components/AktuelleTrin/index";
import InternalNotes from "./components/InternalNotes/index";

const Heading = styled.h1`
  color: var(--primary-color);
`;

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

export default function App() {
  return (
    <>
    <Header />
    <main className="page">
      <Heading>Aktive sager</Heading>
      <KpiRow>
        <KpiCard label="AKTIVE SAGER" count={12} />
        <KpiCard label="AFVENTER SVAR" count={5} />
        <KpiCard label="AFSLUTTEDE SAGER" count={3} />
        <KpiCard label="KRÆVER HANDLING" count={2} highlighted />
      </KpiRow>  
      <CaseCards></CaseCards>
      <CaseDetails>
        <AktuelleTrin></AktuelleTrin>
        <InternalNotes></InternalNotes>
      </CaseDetails>
    </main>
    </>
  );
}
