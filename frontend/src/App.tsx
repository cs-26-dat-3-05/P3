import styled from "styled-components";
import Header from "./components/Header";
import KpiCard from "./components/KpiCard";
const Heading = styled.h1`
  color: var(--primary-color);
`;
import CaseCards from "./components/CaseCards";
import EmailLayout from "./components/Emails/IndexEmail";

export default function App() {
  return (
    <>
    <Header />
    <main className="page">
      <Heading>Aktive sager</Heading>
      <CaseCards></CaseCards>
      <EmailLayout></EmailLayout>
    </main>
    </>
  );
}

/* 

*/
