import styled from "styled-components";
import Header from "./components/Header";
import KpiCards from "./components/KpiCard/index";
import CaseCards from "./components/CaseCards";
import CaseDetails from "./components/CaseDetails";

const Heading = styled.h1`
  color: var(--primary-color);
`;

export default function App() {
  return (
    <>
      <Header />
      <main className="page">
        <Heading>Aktive sager</Heading>
        <KpiCards></KpiCards>
        <CaseCards></CaseCards>
        <CaseDetails></CaseDetails>
      </main>
    </>
  );
}
