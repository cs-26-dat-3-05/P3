import { styled } from "styled-components";

type KpiCardProps = {
  label: string;
  count: number;
  highlighted?: boolean;
};
const Label = styled.p`
  margin: 0;
  font-size: 14px;
`;

const Value = styled.p`
  margin: 8px 0 0;
  font-size: 22px;
  font-weight: bold;
`;

const Card = styled.div.attrs({ className: "box" })<{ $highlighted?: boolean }>`
  && {
    padding: 24px 16px;
  }

  ${({ $highlighted }) =>
    $highlighted &&
    `
            background: var(--background-gradient)
        `}
`;

export default function KpiCard({ label, count, highlighted }: KpiCardProps) {
  return (
    <Card $highlighted={highlighted}>
      <Label>{label.toUpperCase()}</Label>
      <Value>{count}</Value>
    </Card>
  );
}
