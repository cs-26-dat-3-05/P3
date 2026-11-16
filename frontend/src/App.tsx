import { useState } from "react";
import styled from "styled-components";
import Header from "./components/Header";
import KpiCards from "./components/KpiCard/index";
import Sagsoversigt from "./components/Sagsoversigt/Sagsoversigt";
import CasePage from "./components/CasePage"

const Heading = styled.h1`
  color: var(--primary-color);
`;

export default function App() {
  const [page, setPage] = useState<"overview" | "case">("overview");

  return (
    <>
      <Header />
      <main className="page">
          {page === "overview" && (
            <>
              <Heading>Aktive sager</Heading>
              <KpiCards></KpiCards>
              <Sagsoversigt onOpenCase={() => setPage("case")} />
            </>
          )}
          {page === "case" && <CasePage onBack={() => setPage("overview")} />}
      </main>
    </>
  );
}
