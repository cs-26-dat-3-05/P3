import styled from "styled-components";
const Heading = styled.h1`
  color: var(--primary-color);
`;
import CaseCards from "./components/CaseCards";

export default function App() {
  return (
    <main className="page">
      <Heading>Aktive sager</Heading>
      <CaseCards></CaseCards>
    </main>
  );
}
