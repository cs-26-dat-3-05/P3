import { useState } from "react";
import styled from "styled-components";
import Header from "./components/Header";
import KpiCards from "./components/KpiCard/index";
import Sagsoversigt from "./components/Sagsoversigt/Sagsoversigt";
import { useOrganizer } from "./hooks/useOrganizer";
import CasePage from "./components/CasePage";
import { casesApi } from "./api/cases";
casesApi;

const Heading = styled.h1`
  color: var(--primary-color);
`;

export default function App() {
  const { organizer, loading, error } = useOrganizer(1);
  const [page, setPage] = useState<"overview" | "case">("overview");
  console.log(casesApi.getCases);

  return (
    <>
      <Header />
      {loading && <p>Indlæser...</p>}
      {error && <p>Fejl: {error.message}</p>}
      {organizer && <p>{organizer.name}</p>}
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
