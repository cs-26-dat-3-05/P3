import { useState } from "react";
import styled from "styled-components";
import Header from "./components/Header";
import KpiCards from "./components/KpiCard/index";
import Sagsoversigt from "./components/Sagsoversigt/Sagsoversigt";
import CasePage from "./components/CasePage";
import type { CaseItem } from "./components/Sagsoversigt/caseData";

const Heading = styled.h1`
  color: var(--primary-color);
`;

export default function App() {
  const [page, setPage] = useState<"overview" | "case">("overview");
  const [selectedCase, setSelectedCase] = useState<CaseItem | null>(null);
  const openCase = (caseItem: CaseItem) => {
    setSelectedCase(caseItem);
    setPage("case");
  };

  return (
    <>
      <Header />
      <main className="page">
          {page === "overview" && (
            <>
              <Heading>Aktive sager</Heading>
              <KpiCards></KpiCards>
              <Sagsoversigt onOpenCase={openCase} />
            </>
          )}
          {page === "case" && selectedCase && (
            <CasePage caseItem={selectedCase} onBack={() => setPage("overview")} />
          )}
      </main>
    </>
  );
}
