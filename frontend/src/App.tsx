import styled from "styled-components";
import Header from "./components/Header";
import KpiCard from "./components/KpiCard";
const Heading = styled.h1`
  color: var(--primary-color);
`;

// Kpi card rows, 4 equal columns
const KpiRow = styled.div`
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
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
        <KpiCard label="KRÆVER HANDLING" count={2} highlighted={true}/>
      </KpiRow>
    </main>
    </>
  );
}
