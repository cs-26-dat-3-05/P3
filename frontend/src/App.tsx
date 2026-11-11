import styled from "styled-components";
import Header from "./components/Header";
import KpiCards from "./components/KpiCard/index";
import Sagsoversigt from "./components/Sagsoversigt/Sagsoversigt";

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
        <Sagsoversigt></Sagsoversigt>
      </main>
    </>
  );
}
