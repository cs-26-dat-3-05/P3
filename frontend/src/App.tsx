import styled from "styled-components";
import Header from "./components/Header";
import KpiCard from "./components/KpiCard";
const Heading = styled.h1`
  color: var(--primary-color);
`;
import CaseCards from "./components/CaseCards";

export default function App() {
  return (
    <>
    <Header />
    <main className="page">
      <Heading>Aktive sager</Heading>
      <CaseCards></CaseCards>
    </main>
    </>
  );
}
