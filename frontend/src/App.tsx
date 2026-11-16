import styled from "styled-components";
import Header from "./components/Header";
import KpiCards from "./components/KpiCard/index";
import Sagsoversigt from "./components/Sagsoversigt/Sagsoversigt";
import { useOrganizer } from "./hooks/useOrganizer";

const Heading = styled.h1`
  color: var(--primary-color);
`;

export default function App() {
  const { organizer, loading, error } = useOrganizer(1);
  return (
    <>
      <Header />
      <main className="page">
        <Heading>Aktive sager</Heading>
        {loading && <p>Indlæser...</p>}
        {error && <p>Fejl: {error.message}</p>}
        {organizer && <p>{organizer.name}</p>}
        <KpiCards></KpiCards>
        <Sagsoversigt></Sagsoversigt>
      </main>
    </>
  );
}
