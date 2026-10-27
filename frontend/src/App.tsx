import { useState } from "react";
import styled from "styled-components";
const Heading = styled.h1`
  color: var(--primary-color);
`;

// Box placeholder
const Card = styled.div.attrs({ className: "box" })`
  width: 300px;
`;
export default function App() {
  return (
    <main className="page">
      <Heading>Aktive sager</Heading>
      <Card>Box test</Card>
    </main>
  );
}
