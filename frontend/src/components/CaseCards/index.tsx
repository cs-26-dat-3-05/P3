import { Container, Card, CardHeader, CardTable, TdRight } from "./style";
import type { CaseItem } from "../Sagsoversigt/caseData";

export default function CaseCards({ caseItem }: { caseItem: CaseItem }) {
  const columns = caseItem.cards
    .map((card) => `minmax(0, ${card.span}fr)`)
    .join(" ");

  return (
    <Container $columns={columns}>
      {caseItem.cards.map((card) => (
        <Card key={card.id}>
          <CardHeader>{card.title}</CardHeader>
          <CardTable>
            <tbody>
              {card.rows.map((row) => (
                <tr key={row.id}>
                  <td>{row.label}</td>
                  <TdRight align="right">{row.value}</TdRight>
                </tr>
              ))}
            </tbody>
          </CardTable>
        </Card>
      ))}
    </Container>
  );
}
